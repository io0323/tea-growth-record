import React, { useState } from 'react';
import {
  Container,
  Paper,
  Typography,
  TextField,
  Button,
  Box,
  CircularProgress,
} from '@mui/material';
import { useNavigate } from 'react-router-dom';
import { useSnackbar } from '../contexts/SnackbarContext';
import { useAuth } from '../contexts/AuthContext';

/**
 * パスワードリセットページコンポーネント
 */
export const PasswordResetPage: React.FC = () => {
  const [email, setEmail] = useState('');
  const [loading, setLoading] = useState(false);
  const { showSnackbar } = useSnackbar();
  const { requestPasswordReset } = useAuth();
  const navigate = useNavigate();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);

    try {
      await requestPasswordReset(email);
      showSnackbar('パスワードリセットのメールを送信しました。', 'success');
      navigate('/login');
    } catch (error) {
      showSnackbar('パスワードリセットに失敗しました。', 'error');
    } finally {
      setLoading(false);
    }
  };

  return (
    <Container maxWidth="sm">
      <Box sx={{ mt: 8 }}>
        <Paper elevation={3} sx={{ p: 4 }}>
          <Typography variant="h5" component="h1" gutterBottom>
            パスワードリセット
          </Typography>
          <Typography variant="body2" color="text.secondary" sx={{ mb: 3 }}>
            登録したメールアドレスを入力してください。
            パスワードリセット用のリンクをメールで送信します。
          </Typography>
          <form onSubmit={handleSubmit}>
            <TextField
              fullWidth
              label="メールアドレス"
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
              sx={{ mb: 3 }}
            />
            <Button
              type="submit"
              variant="contained"
              fullWidth
              disabled={loading}
              sx={{ mb: 2 }}
            >
              {loading ? <CircularProgress size={24} /> : 'リセットリンクを送信'}
            </Button>
            <Button
              variant="text"
              fullWidth
              onClick={() => navigate('/login')}
              sx={{ mt: 1 }}
            >
              ログインページに戻る
            </Button>
          </form>
        </Paper>
      </Box>
    </Container>
  );
}; 