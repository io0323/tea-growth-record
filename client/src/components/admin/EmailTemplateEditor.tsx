import { useState, useEffect } from 'react';
import {
  Box,
  Paper,
  Typography,
  TextField,
  Button,
  Grid,
  Alert,
  CircularProgress,
  Divider,
} from '@mui/material';
import { useSnackbar } from 'notistack';
import { authApi } from '../../api/auth';
import { EmailTemplateVersionHistory } from './EmailTemplateVersionHistory';

interface EmailTemplate {
  subject: string;
  body: string;
  variables: string[];
}

interface EmailTemplateEditorProps {
  templateType: 'password-reset' | 'welcome' | 'notification';
}

/**
 * メールテンプレート編集コンポーネント
 */
export const EmailTemplateEditor = ({ templateType }: EmailTemplateEditorProps) => {
  const { enqueueSnackbar } = useSnackbar();
  const [loading, setLoading] = useState(false);
  const [preview, setPreview] = useState(false);
  const [template, setTemplate] = useState<EmailTemplate>({
    subject: '',
    body: '',
    variables: [],
  });

  /**
   * テンプレートを保存
   */
  const handleSave = async () => {
    try {
      setLoading(true);
      await authApi.updateEmailTemplate(templateType, template);
      enqueueSnackbar('テンプレートを保存しました', { variant: 'success' });
    } catch (error) {
      enqueueSnackbar('テンプレートの保存に失敗しました', { variant: 'error' });
    } finally {
      setLoading(false);
    }
  };

  /**
   * プレビューを表示
   */
  const handlePreview = () => {
    setPreview(true);
  };

  /**
   * プレビューを閉じる
   */
  const handleClosePreview = () => {
    setPreview(false);
  };

  return (
    <Box sx={{ p: 3 }}>
      <Typography variant="h5" gutterBottom>
        メールテンプレート編集
      </Typography>
      <Paper sx={{ p: 3, mb: 3 }}>
        <Grid container spacing={3}>
          <Grid item xs={12}>
            <TextField
              fullWidth
              label="件名"
              value={template.subject}
              onChange={(e) => setTemplate({ ...template, subject: e.target.value })}
              helperText="使用可能な変数: {username}, {resetUrl}, {expiryTime}"
            />
          </Grid>
          <Grid item xs={12}>
            <TextField
              fullWidth
              multiline
              rows={10}
              label="本文"
              value={template.body}
              onChange={(e) => setTemplate({ ...template, body: e.target.value })}
              helperText="使用可能な変数: {username}, {resetUrl}, {expiryTime}"
            />
          </Grid>
          <Grid item xs={12}>
            <Box sx={{ display: 'flex', gap: 2 }}>
              <Button
                variant="contained"
                onClick={handleSave}
                disabled={loading}
              >
                {loading ? <CircularProgress size={24} /> : '保存'}
              </Button>
              <Button
                variant="outlined"
                onClick={handlePreview}
              >
                プレビュー
              </Button>
            </Box>
          </Grid>
        </Grid>
      </Paper>

      {preview && (
        <Paper sx={{ p: 3, mb: 3 }}>
          <Typography variant="h6" gutterBottom>
            プレビュー
          </Typography>
          <Box
            sx={{
              border: 1,
              borderColor: 'divider',
              p: 3,
              borderRadius: 1,
            }}
          >
            <Typography variant="subtitle1" gutterBottom>
              {template.subject.replace('{username}', 'テストユーザー')}
            </Typography>
            <Typography variant="body1">
              {template.body
                .replace('{username}', 'テストユーザー')
                .replace('{resetUrl}', 'https://example.com/reset')
                .replace('{expiryTime}', '1時間')}
            </Typography>
          </Box>
          <Box sx={{ mt: 2, textAlign: 'right' }}>
            <Button onClick={handleClosePreview}>閉じる</Button>
          </Box>
        </Paper>
      )}

      <Divider sx={{ my: 4 }} />

      <EmailTemplateVersionHistory templateType={templateType} />
    </Box>
  );
}; 