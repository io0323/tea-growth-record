import { useState, useEffect } from 'react';
import {
  Box,
  Paper,
  Typography,
  Grid,
  Select,
  MenuItem,
  FormControl,
  InputLabel,
  Button,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Chip,
  Menu,
  MenuItem as MuiMenuItem,
  TextField,
  ToggleButton,
  ToggleButtonGroup,
  IconButton,
  Tooltip,
  Popover,
  List,
  ListItem,
  ListItemText,
  Switch,
  FormControlLabel,
} from '@mui/material';
import {
  Download as DownloadIcon,
  PictureAsPdf as PdfIcon,
  TextFields as TextIcon,
  Email as EmailIcon,
  Add as AddIcon,
  Remove as RemoveIcon,
  DragHandle as UnchangedIcon,
  Search as SearchIcon,
  History as HistoryIcon,
  RegexIcon as RegexIcon,
  Clear as ClearIcon,
} from '@mui/icons-material';
import { diffWords, diffLines } from 'diff';
import { EmailTemplateVersion } from '../../api/auth';
import { jsPDF } from 'jspdf';
import 'jspdf-autotable';

interface EmailTemplateVersionDiffProps {
  versions: EmailTemplateVersion[];
  onClose: () => void;
}

interface DiffStats {
  added: number;
  removed: number;
  unchanged: number;
}

type FilterType = 'all' | 'added' | 'removed' | 'unchanged';

interface AdvancedSearchOptions {
  useRegex: boolean;
  caseSensitive: boolean;
  searchHistory: string[];
  multipleKeywords: boolean;
}

interface DiffFilters {
  type: FilterType;
  searchText: string;
  searchOptions: AdvancedSearchOptions;
}

/**
 * メールテンプレートのバージョン比較コンポーネント
 */
export const EmailTemplateVersionDiff = ({ versions, onClose }: EmailTemplateVersionDiffProps) => {
  const [oldVersion, setOldVersion] = useState<string>('');
  const [newVersion, setNewVersion] = useState<string>('');
  const [showDetailedDiff, setShowDetailedDiff] = useState(false);
  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
  const [filters, setFilters] = useState<DiffFilters>({
    type: 'all',
    searchText: '',
    searchOptions: {
      useRegex: false,
      caseSensitive: false,
      searchHistory: [],
      multipleKeywords: false,
    },
  });
  const [searchAnchorEl, setSearchAnchorEl] = useState<null | HTMLElement>(null);
  const [historyAnchorEl, setHistoryAnchorEl] = useState<null | HTMLElement>(null);

  /**
   * 検索履歴を保存
   */
  useEffect(() => {
    const savedHistory = localStorage.getItem('searchHistory');
    if (savedHistory) {
      setFilters(prev => ({
        ...prev,
        searchOptions: {
          ...prev.searchOptions,
          searchHistory: JSON.parse(savedHistory),
        },
      }));
    }
  }, []);

  /**
   * 検索履歴を更新
   */
  const updateSearchHistory = (searchText: string) => {
    if (!searchText) return;

    const newHistory = [
      searchText,
      ...filters.searchOptions.searchHistory.filter(h => h !== searchText),
    ].slice(0, 10);

    localStorage.setItem('searchHistory', JSON.stringify(newHistory));
    setFilters(prev => ({
      ...prev,
      searchOptions: {
        ...prev.searchOptions,
        searchHistory: newHistory,
      },
    }));
  };

  /**
   * テキストが検索条件にマッチするかチェック
   */
  const isTextMatching = (text: string, searchText: string): boolean => {
    if (!searchText) return true;

    const { useRegex, caseSensitive, multipleKeywords } = filters.searchOptions;

    if (multipleKeywords) {
      const keywords = searchText.split(' ').filter(k => k);
      return keywords.every(keyword => {
        if (useRegex) {
          try {
            const regex = new RegExp(keyword, caseSensitive ? '' : 'i');
            return regex.test(text);
          } catch {
            return false;
          }
        } else {
          return caseSensitive
            ? text.includes(keyword)
            : text.toLowerCase().includes(keyword.toLowerCase());
        }
      });
    } else {
      if (useRegex) {
        try {
          const regex = new RegExp(searchText, caseSensitive ? '' : 'i');
          return regex.test(text);
        } catch {
          return false;
        }
      } else {
        return caseSensitive
          ? text.includes(searchText)
          : text.toLowerCase().includes(searchText.toLowerCase());
      }
    }
  };

  /**
   * 検索テキストをハイライト
   */
  const highlightSearchText = (text: string): React.ReactNode => {
    if (!filters.searchText) return text;

    const { useRegex, caseSensitive, multipleKeywords } = filters.searchOptions;
    let parts: { text: string; highlight: boolean }[] = [{ text, highlight: false }];

    if (multipleKeywords) {
      const keywords = filters.searchText.split(' ').filter(k => k);
      keywords.forEach(keyword => {
        parts = parts.flatMap(part => {
          if (!part.highlight) {
            if (useRegex) {
              try {
                const regex = new RegExp(keyword, caseSensitive ? 'g' : 'gi');
                const splits = part.text.split(regex);
                const matches = part.text.match(regex) || [];
                return splits.flatMap((text, i) => [
                  { text, highlight: false },
                  ...(i < splits.length - 1 ? [{ text: matches[i], highlight: true }] : []),
                ]);
              } catch {
                return [part];
              }
            } else {
              const searchRegex = new RegExp(
                keyword.replace(/[.*+?^${}()|[\]\\]/g, '\\$&'),
                caseSensitive ? 'g' : 'gi'
              );
              const splits = part.text.split(searchRegex);
              const matches = part.text.match(searchRegex) || [];
              return splits.flatMap((text, i) => [
                { text, highlight: false },
                ...(i < splits.length - 1 ? [{ text: matches[i], highlight: true }] : []),
              ]);
            }
          }
          return [part];
        });
      });
    } else {
      if (useRegex) {
        try {
          const regex = new RegExp(filters.searchText, caseSensitive ? 'g' : 'gi');
          const splits = text.split(regex);
          const matches = text.match(regex) || [];
          parts = splits.flatMap((text, i) => [
            { text, highlight: false },
            ...(i < splits.length - 1 ? [{ text: matches[i], highlight: true }] : []),
          ]);
        } catch {
          parts = [{ text, highlight: false }];
        }
      } else {
        const searchRegex = new RegExp(
          filters.searchText.replace(/[.*+?^${}()|[\]\\]/g, '\\$&'),
          caseSensitive ? 'g' : 'gi'
        );
        const splits = text.split(searchRegex);
        const matches = text.match(searchRegex) || [];
        parts = splits.flatMap((text, i) => [
          { text, highlight: false },
          ...(i < splits.length - 1 ? [{ text: matches[i], highlight: true }] : []),
        ]);
      }
    }

    return parts.map((part, i) => (
      part.highlight ? (
        <span key={i} style={{ backgroundColor: '#fff3cd', fontWeight: 'bold' }}>
          {part.text}
        </span>
      ) : (
        part.text
      )
    ));
  };

  /**
   * 選択されたバージョンを取得
   */
  const getSelectedVersion = (versionId: string) => {
    return versions.find((v) => v.id === versionId);
  };

  /**
   * 差分の統計情報を計算
   */
  const calculateDiffStats = (diff: ReturnType<typeof diffWords>): DiffStats => {
    return diff.reduce(
      (stats, part) => ({
        added: stats.added + (part.added ? part.count || 0 : 0),
        removed: stats.removed + (part.removed ? part.count || 0 : 0),
        unchanged: stats.unchanged + (!part.added && !part.removed ? part.count || 0 : 0),
      }),
      { added: 0, removed: 0, unchanged: 0 }
    );
  };

  /**
   * 差分を生成
   */
  const generateDiff = () => {
    const old = getSelectedVersion(oldVersion);
    const new_ = getSelectedVersion(newVersion);

    if (!old || !new_) return null;

    const wordDiff = {
      subject: diffWords(old.subject, new_.subject),
      body: diffWords(old.body, new_.body),
    };

    const lineDiff = {
      subject: diffLines(old.subject, new_.subject),
      body: diffLines(old.body, new_.body),
    };

    const stats = {
      subject: calculateDiffStats(wordDiff.subject),
      body: calculateDiffStats(wordDiff.body),
    };

    return {
      wordDiff,
      lineDiff,
      stats,
    };
  };

  const diff = generateDiff();

  /**
   * フィルタリングされた差分を取得
   */
  const getFilteredDiff = (diffParts: any[]) => {
    return diffParts.filter((part) => {
      const matchesType =
        filters.type === 'all' ||
        (filters.type === 'added' && part.added) ||
        (filters.type === 'removed' && part.removed) ||
        (filters.type === 'unchanged' && !part.added && !part.removed);

      const matchesSearch = isTextMatching(part.value, filters.searchText);

      return matchesType && matchesSearch;
    });
  };

  /**
   * フィルタータイプを変更
   */
  const handleFilterTypeChange = (
    _event: React.MouseEvent<HTMLElement>,
    newType: FilterType | null,
  ) => {
    if (newType !== null) {
      setFilters({ ...filters, type: newType });
    }
  };

  /**
   * 検索テキストを変更
   */
  const handleSearchChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setFilters({ ...filters, searchText: event.target.value });
  };

  /**
   * 検索オプションを変更
   */
  const handleSearchOptionChange = (option: keyof AdvancedSearchOptions) => {
    setFilters(prev => ({
      ...prev,
      searchOptions: {
        ...prev.searchOptions,
        [option]: !prev.searchOptions[option],
      },
    }));
  };

  /**
   * 検索履歴から検索
   */
  const handleHistoryItemClick = (searchText: string) => {
    setFilters(prev => ({ ...prev, searchText }));
    setHistoryAnchorEl(null);
  };

  /**
   * 差分の行を表示
   */
  const renderDiffLine = (part: any, index: number) => {
    const lineNumber = index + 1;
    return (
      <TableRow key={index}>
        <TableCell sx={{ width: 100, color: 'text.secondary' }}>
          {lineNumber}
        </TableCell>
        <TableCell>
          <Chip
            label={part.added ? '追加' : part.removed ? '削除' : '変更なし'}
            color={part.added ? 'success' : part.removed ? 'error' : 'default'}
            size="small"
            sx={{ mr: 1 }}
          />
          <span
            style={{
              backgroundColor: part.added
                ? '#e6ffe6'
                : part.removed
                ? '#ffe6e6'
                : 'transparent',
            }}
          >
            {part.value}
          </span>
        </TableCell>
      </TableRow>
    );
  };

  /**
   * 差分をテキスト形式で取得
   */
  const getDiffAsText = () => {
    const old = getSelectedVersion(oldVersion);
    const new_ = getSelectedVersion(newVersion);
    if (!old || !new_ || !diff) return '';

    let text = `バージョン比較: v${old.version} → v${new_.version}\n\n`;
    text += `件名の差分:\n`;
    text += `追加: ${diff.stats.subject.added}文字\n`;
    text += `削除: ${diff.stats.subject.removed}文字\n`;
    text += `変更なし: ${diff.stats.subject.unchanged}文字\n\n`;
    text += `本文の差分:\n`;
    text += `追加: ${diff.stats.body.added}文字\n`;
    text += `削除: ${diff.stats.body.removed}文字\n`;
    text += `変更なし: ${diff.stats.body.unchanged}文字\n\n`;

    text += `詳細な差分:\n\n`;
    text += `件名:\n`;
    diff.lineDiff.subject.forEach((part, index) => {
      text += `${index + 1}: ${part.added ? '+' : part.removed ? '-' : ' '} ${part.value}\n`;
    });

    text += `\n本文:\n`;
    diff.lineDiff.body.forEach((part, index) => {
      text += `${index + 1}: ${part.added ? '+' : part.removed ? '-' : ' '} ${part.value}\n`;
    });

    return text;
  };

  /**
   * 差分をPDFとしてエクスポート
   */
  const exportAsPdf = () => {
    const doc = new jsPDF();
    const old = getSelectedVersion(oldVersion);
    const new_ = getSelectedVersion(newVersion);
    if (!old || !new_ || !diff) return;

    // タイトル
    doc.setFontSize(16);
    doc.text('バージョン比較', 14, 15);
    doc.setFontSize(12);
    doc.text(`v${old.version} → v${new_.version}`, 14, 25);

    // 統計情報
    doc.setFontSize(14);
    doc.text('変更統計', 14, 40);
    doc.setFontSize(10);
    doc.text(`件名: 追加 ${diff.stats.subject.added}文字 / 削除 ${diff.stats.subject.removed}文字 / 変更なし ${diff.stats.subject.unchanged}文字`, 14, 50);
    doc.text(`本文: 追加 ${diff.stats.body.added}文字 / 削除 ${diff.stats.body.removed}文字 / 変更なし ${diff.stats.body.unchanged}文字`, 14, 60);

    // 件名の差分
    doc.setFontSize(14);
    doc.text('件名の差分', 14, 80);
    doc.setFontSize(10);
    const subjectData = diff.lineDiff.subject.map((part, index) => [
      `${index + 1}`,
      part.added ? '+' : part.removed ? '-' : ' ',
      part.value,
    ]);
    (doc as any).autoTable({
      startY: 85,
      head: [['行番号', '変更', '内容']],
      body: subjectData,
      theme: 'grid',
    });

    // 本文の差分
    doc.setFontSize(14);
    doc.text('本文の差分', 14, (doc as any).lastAutoTable.finalY + 20);
    doc.setFontSize(10);
    const bodyData = diff.lineDiff.body.map((part, index) => [
      `${index + 1}`,
      part.added ? '+' : part.removed ? '-' : ' ',
      part.value,
    ]);
    (doc as any).autoTable({
      startY: (doc as any).lastAutoTable.finalY + 25,
      head: [['行番号', '変更', '内容']],
      body: bodyData,
      theme: 'grid',
    });

    doc.save(`diff_v${old.version}_v${new_.version}.pdf`);
  };

  /**
   * 差分をテキストファイルとしてエクスポート
   */
  const exportAsText = () => {
    const text = getDiffAsText();
    const blob = new Blob([text], { type: 'text/plain;charset=utf-8' });
    const url = URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = `diff_v${getSelectedVersion(oldVersion)?.version}_v${getSelectedVersion(newVersion)?.version}.txt`;
    document.body.appendChild(a);
    a.click();
    document.body.removeChild(a);
    URL.revokeObjectURL(url);
  };

  /**
   * 差分をメールで送信
   */
  const sendDiffByEmail = () => {
    const text = getDiffAsText();
    const subject = `テンプレート差分: v${getSelectedVersion(oldVersion)?.version} → v${getSelectedVersion(newVersion)?.version}`;
    const mailtoLink = `mailto:?subject=${encodeURIComponent(subject)}&body=${encodeURIComponent(text)}`;
    window.location.href = mailtoLink;
  };

  /**
   * エクスポートメニューを開く
   */
  const handleExportClick = (event: React.MouseEvent<HTMLElement>) => {
    setAnchorEl(event.currentTarget);
  };

  /**
   * エクスポートメニューを閉じる
   */
  const handleExportClose = () => {
    setAnchorEl(null);
  };

  return (
    <Dialog
      open={true}
      onClose={onClose}
      maxWidth="lg"
      fullWidth
    >
      <DialogTitle>
        <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
          <Typography>バージョン比較</Typography>
          <Button
            variant="outlined"
            startIcon={<DownloadIcon />}
            onClick={handleExportClick}
            disabled={!diff}
          >
            エクスポート
          </Button>
        </Box>
      </DialogTitle>
      <DialogContent>
        <Grid container spacing={3}>
          <Grid item xs={6}>
            <FormControl fullWidth>
              <InputLabel>古いバージョン</InputLabel>
              <Select
                value={oldVersion}
                onChange={(e) => setOldVersion(e.target.value)}
                label="古いバージョン"
              >
                {versions.map((version) => (
                  <MenuItem key={version.id} value={version.id}>
                    v{version.version} - {version.subject}
                  </MenuItem>
                ))}
              </Select>
            </FormControl>
          </Grid>
          <Grid item xs={6}>
            <FormControl fullWidth>
              <InputLabel>新しいバージョン</InputLabel>
              <Select
                value={newVersion}
                onChange={(e) => setNewVersion(e.target.value)}
                label="新しいバージョン"
              >
                {versions.map((version) => (
                  <MenuItem key={version.id} value={version.id}>
                    v{version.version} - {version.subject}
                  </MenuItem>
                ))}
              </Select>
            </FormControl>
          </Grid>
        </Grid>

        {diff && (
          <Box sx={{ mt: 3 }}>
            {/* フィルターコントロール */}
            <Box sx={{ mb: 3 }}>
              <Grid container spacing={2} alignItems="center">
                <Grid item>
                  <ToggleButtonGroup
                    value={filters.type}
                    exclusive
                    onChange={handleFilterTypeChange}
                    size="small"
                  >
                    <ToggleButton value="all">
                      すべて
                    </ToggleButton>
                    <ToggleButton value="added">
                      <AddIcon sx={{ mr: 1 }} />
                      追加
                    </ToggleButton>
                    <ToggleButton value="removed">
                      <RemoveIcon sx={{ mr: 1 }} />
                      削除
                    </ToggleButton>
                    <ToggleButton value="unchanged">
                      <UnchangedIcon sx={{ mr: 1 }} />
                      変更なし
                    </ToggleButton>
                  </ToggleButtonGroup>
                </Grid>
                <Grid item xs>
                  <Box sx={{ display: 'flex', gap: 1 }}>
                    <TextField
                      fullWidth
                      size="small"
                      placeholder="テキストを検索..."
                      value={filters.searchText}
                      onChange={handleSearchChange}
                      InputProps={{
                        startAdornment: <SearchIcon sx={{ mr: 1, color: 'text.secondary' }} />,
                        endAdornment: (
                          <>
                            <Tooltip title="検索オプション">
                              <IconButton
                                size="small"
                                onClick={(e) => setSearchAnchorEl(e.currentTarget)}
                              >
                                <RegexIcon />
                              </IconButton>
                            </Tooltip>
                            <Tooltip title="検索履歴">
                              <IconButton
                                size="small"
                                onClick={(e) => setHistoryAnchorEl(e.currentTarget)}
                              >
                                <HistoryIcon />
                              </IconButton>
                            </Tooltip>
                            {filters.searchText && (
                              <Tooltip title="クリア">
                                <IconButton
                                  size="small"
                                  onClick={() => setFilters(prev => ({ ...prev, searchText: '' }))}
                                >
                                  <ClearIcon />
                                </IconButton>
                              </Tooltip>
                            )}
                          </>
                        ),
                      }}
                    />
                  </Box>
                </Grid>
              </Grid>
            </Box>

            {/* 件名の差分 */}
            <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 2 }}>
              <Typography variant="h6">
                件名の差分
              </Typography>
              <Button
                variant="outlined"
                size="small"
                onClick={() => setShowDetailedDiff(!showDetailedDiff)}
              >
                {showDetailedDiff ? '簡易表示' : '詳細表示'}
              </Button>
            </Box>

            {showDetailedDiff ? (
              <TableContainer component={Paper}>
                <Table size="small">
                  <TableHead>
                    <TableRow>
                      <TableCell>行番号</TableCell>
                      <TableCell>内容</TableCell>
                    </TableRow>
                  </TableHead>
                  <TableBody>
                    {getFilteredDiff(diff.lineDiff.subject).map(renderDiffLine)}
                  </TableBody>
                </Table>
              </TableContainer>
            ) : (
              <Paper sx={{ p: 2, mb: 3 }}>
                {getFilteredDiff(diff.wordDiff.subject).map((part, index) => (
                  <span
                    key={index}
                    style={{
                      backgroundColor: part.added
                        ? '#e6ffe6'
                        : part.removed
                        ? '#ffe6e6'
                        : 'transparent',
                    }}
                  >
                    {part.value}
                  </span>
                ))}
              </Paper>
            )}

            <Box sx={{ mb: 2 }}>
              <Typography variant="subtitle2" color="text.secondary">
                変更統計: 追加 {diff.stats.subject.added}文字 / 削除 {diff.stats.subject.removed}文字 / 変更なし {diff.stats.subject.unchanged}文字
              </Typography>
            </Box>

            {/* 本文の差分 */}
            <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 2 }}>
              <Typography variant="h6">
                本文の差分
              </Typography>
            </Box>

            {showDetailedDiff ? (
              <TableContainer component={Paper}>
                <Table size="small">
                  <TableHead>
                    <TableRow>
                      <TableCell>行番号</TableCell>
                      <TableCell>内容</TableCell>
                    </TableRow>
                  </TableHead>
                  <TableBody>
                    {getFilteredDiff(diff.lineDiff.body).map(renderDiffLine)}
                  </TableBody>
                </Table>
              </TableContainer>
            ) : (
              <Paper sx={{ p: 2 }}>
                {getFilteredDiff(diff.wordDiff.body).map((part, index) => (
                  <span
                    key={index}
                    style={{
                      backgroundColor: part.added
                        ? '#e6ffe6'
                        : part.removed
                        ? '#ffe6e6'
                        : 'transparent',
                    }}
                  >
                    {part.value}
                  </span>
                ))}
              </Paper>
            )}

            <Box sx={{ mt: 2 }}>
              <Typography variant="subtitle2" color="text.secondary">
                変更統計: 追加 {diff.stats.body.added}文字 / 削除 {diff.stats.body.removed}文字 / 変更なし {diff.stats.body.unchanged}文字
              </Typography>
            </Box>
          </Box>
        )}
      </DialogContent>
      <DialogActions>
        <Button onClick={onClose}>閉じる</Button>
      </DialogActions>

      <Menu
        anchorEl={anchorEl}
        open={Boolean(anchorEl)}
        onClose={handleExportClose}
      >
        <MuiMenuItem onClick={() => {
          exportAsText();
          handleExportClose();
        }}>
          <TextIcon sx={{ mr: 1 }} /> テキストファイルとして保存
        </MuiMenuItem>
        <MuiMenuItem onClick={() => {
          exportAsPdf();
          handleExportClose();
        }}>
          <PdfIcon sx={{ mr: 1 }} /> PDFとして保存
        </MuiMenuItem>
        <MuiMenuItem onClick={() => {
          sendDiffByEmail();
          handleExportClose();
        }}>
          <EmailIcon sx={{ mr: 1 }} /> メールで送信
        </MuiMenuItem>
      </Menu>

      {/* 検索オプションのポップオーバー */}
      <Popover
        open={Boolean(searchAnchorEl)}
        anchorEl={searchAnchorEl}
        onClose={() => setSearchAnchorEl(null)}
        anchorOrigin={{
          vertical: 'bottom',
          horizontal: 'left',
        }}
      >
        <Box sx={{ p: 2 }}>
          <Typography variant="subtitle2" gutterBottom>
            検索オプション
          </Typography>
          <FormControlLabel
            control={
              <Switch
                checked={filters.searchOptions.useRegex}
                onChange={() => handleSearchOptionChange('useRegex')}
                size="small"
              />
            }
            label="正規表現を使用"
          />
          <FormControlLabel
            control={
              <Switch
                checked={filters.searchOptions.caseSensitive}
                onChange={() => handleSearchOptionChange('caseSensitive')}
                size="small"
              />
            }
            label="大文字小文字を区別"
          />
          <FormControlLabel
            control={
              <Switch
                checked={filters.searchOptions.multipleKeywords}
                onChange={() => handleSearchOptionChange('multipleKeywords')}
                size="small"
              />
            }
            label="複数キーワード検索"
          />
        </Box>
      </Popover>

      {/* 検索履歴のポップオーバー */}
      <Popover
        open={Boolean(historyAnchorEl)}
        anchorEl={historyAnchorEl}
        onClose={() => setHistoryAnchorEl(null)}
        anchorOrigin={{
          vertical: 'bottom',
          horizontal: 'left',
        }}
      >
        <List sx={{ minWidth: 200 }}>
          {filters.searchOptions.searchHistory.length > 0 ? (
            filters.searchOptions.searchHistory.map((item, index) => (
              <ListItem
                key={index}
                button
                onClick={() => handleHistoryItemClick(item)}
              >
                <ListItemText primary={item} />
              </ListItem>
            ))
          ) : (
            <ListItem>
              <ListItemText primary="検索履歴がありません" />
            </ListItem>
          )}
        </List>
      </Popover>
    </Dialog>
  );
}; 