import { useState, useEffect } from 'react';
import {
  Box,
  Paper,
  Typography,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Button,
  IconButton,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  CircularProgress,
} from '@mui/material';
import {
  Restore as RestoreIcon,
  Delete as DeleteIcon,
  CheckCircle as CheckCircleIcon,
  Compare as CompareIcon,
} from '@mui/icons-material';
import { useSnackbar } from 'notistack';
import { authApi } from '../../api/auth';
import { format } from 'date-fns';
import { ja } from 'date-fns/locale';
import { EmailTemplateVersionDiff } from './EmailTemplateVersionDiff';

interface EmailTemplateVersion {
  id: string;
  version: number;
  subject: string;
  body: string;
  variables: string[];
  createdAt: string;
  createdBy: string;
  isActive: boolean;
}

interface EmailTemplateVersionHistoryProps {
  templateType: 'password-reset' | 'welcome' | 'notification';
}

/**
 * メールテンプレートのバージョン履歴コンポーネント
 */
export const EmailTemplateVersionHistory = ({ templateType }: EmailTemplateVersionHistoryProps) => {
  const { enqueueSnackbar } = useSnackbar();
  const [loading, setLoading] = useState(false);
  const [versions, setVersions] = useState<EmailTemplateVersion[]>([]);
  const [selectedVersion, setSelectedVersion] = useState<EmailTemplateVersion | null>(null);
  const [previewDialog, setPreviewDialog] = useState(false);
  const [deleteDialog, setDeleteDialog] = useState(false);
  const [diffDialog, setDiffDialog] = useState(false);

  /**
   * バージョン履歴を取得
   */
  const fetchVersions = async () => {
    try {
      setLoading(true);
      const data = await authApi.getEmailTemplateVersions(templateType);
      setVersions(data);
    } catch (error) {
      enqueueSnackbar('バージョン履歴の取得に失敗しました', { variant: 'error' });
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchVersions();
  }, [templateType]);

  /**
   * バージョンを有効化
   */
  const handleActivate = async (versionId: string) => {
    try {
      await authApi.activateEmailTemplateVersion(templateType, versionId);
      enqueueSnackbar('テンプレートを有効化しました', { variant: 'success' });
      fetchVersions();
    } catch (error) {
      enqueueSnackbar('テンプレートの有効化に失敗しました', { variant: 'error' });
    }
  };

  /**
   * バージョンを削除
   */
  const handleDelete = async () => {
    if (!selectedVersion) return;
    try {
      await authApi.deleteEmailTemplateVersion(templateType, selectedVersion.id);
      enqueueSnackbar('テンプレートを削除しました', { variant: 'success' });
      setDeleteDialog(false);
      fetchVersions();
    } catch (error) {
      enqueueSnackbar('テンプレートの削除に失敗しました', { variant: 'error' });
    }
  };

  /**
   * バージョンを復元
   */
  const handleRestore = async (versionId: string) => {
    try {
      await authApi.restoreEmailTemplateVersion(templateType, versionId);
      enqueueSnackbar('テンプレートを復元しました', { variant: 'success' });
      fetchVersions();
    } catch (error) {
      enqueueSnackbar('テンプレートの復元に失敗しました', { variant: 'error' });
    }
  };

  return (
    <Box sx={{ mt: 4 }}>
      <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 2 }}>
        <Typography variant="h6">
          バージョン履歴
        </Typography>
        <Button
          variant="outlined"
          startIcon={<CompareIcon />}
          onClick={() => setDiffDialog(true)}
        >
          バージョン比較
        </Button>
      </Box>

      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>バージョン</TableCell>
              <TableCell>件名</TableCell>
              <TableCell>作成日時</TableCell>
              <TableCell>作成者</TableCell>
              <TableCell>ステータス</TableCell>
              <TableCell>操作</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {loading ? (
              <TableRow>
                <TableCell colSpan={6} align="center">
                  <CircularProgress />
                </TableCell>
              </TableRow>
            ) : (
              versions.map((version) => (
                <TableRow key={version.id}>
                  <TableCell>{version.version}</TableCell>
                  <TableCell>{version.subject}</TableCell>
                  <TableCell>
                    {format(new Date(version.createdAt), 'yyyy/MM/dd HH:mm', { locale: ja })}
                  </TableCell>
                  <TableCell>{version.createdBy}</TableCell>
                  <TableCell>
                    {version.isActive ? (
                      <CheckCircleIcon color="success" />
                    ) : (
                      '非アクティブ'
                    )}
                  </TableCell>
                  <TableCell>
                    <IconButton
                      onClick={() => {
                        setSelectedVersion(version);
                        setPreviewDialog(true);
                      }}
                    >
                      プレビュー
                    </IconButton>
                    {!version.isActive && (
                      <>
                        <IconButton
                          onClick={() => handleActivate(version.id)}
                          color="primary"
                        >
                          有効化
                        </IconButton>
                        <IconButton
                          onClick={() => handleRestore(version.id)}
                          color="primary"
                        >
                          <RestoreIcon />
                        </IconButton>
                      </>
                    )}
                    <IconButton
                      onClick={() => {
                        setSelectedVersion(version);
                        setDeleteDialog(true);
                      }}
                      color="error"
                    >
                      <DeleteIcon />
                    </IconButton>
                  </TableCell>
                </TableRow>
              ))
            )}
          </TableBody>
        </Table>
      </TableContainer>

      {/* プレビューダイアログ */}
      <Dialog
        open={previewDialog}
        onClose={() => setPreviewDialog(false)}
        maxWidth="md"
        fullWidth
      >
        <DialogTitle>テンプレートプレビュー</DialogTitle>
        <DialogContent>
          {selectedVersion && (
            <Box sx={{ p: 2 }}>
              <Typography variant="subtitle1" gutterBottom>
                {selectedVersion.subject}
              </Typography>
              <Typography variant="body1">
                {selectedVersion.body}
              </Typography>
            </Box>
          )}
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setPreviewDialog(false)}>閉じる</Button>
        </DialogActions>
      </Dialog>

      {/* 削除確認ダイアログ */}
      <Dialog
        open={deleteDialog}
        onClose={() => setDeleteDialog(false)}
      >
        <DialogTitle>テンプレートの削除</DialogTitle>
        <DialogContent>
          <Typography>
            このテンプレートを削除してもよろしいですか？
            この操作は取り消すことができません。
          </Typography>
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setDeleteDialog(false)}>キャンセル</Button>
          <Button onClick={handleDelete} color="error">
            削除
          </Button>
        </DialogActions>
      </Dialog>

      {/* バージョン比較ダイアログ */}
      <EmailTemplateVersionDiff
        versions={versions}
        onClose={() => setDiffDialog(false)}
      />
    </Box>
  );
}; 