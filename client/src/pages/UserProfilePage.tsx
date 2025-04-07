import { useState, useEffect } from 'react';
import {
  Box,
  Paper,
  Typography,
  TextField,
  Button,
  Grid,
  Avatar,
  IconButton,
  Divider,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
} from '@mui/material';
import { PhotoCamera } from '@mui/icons-material';
import { useAuth } from '../contexts/AuthContext';
import { useNotification } from '../contexts/NotificationContext';
import { useLoading } from '../contexts/LoadingContext';
import { User } from '../types';

/**
 * ユーザー情報編集ページコンポーネント
 */
export const UserProfilePage = () => {
  const { user, updateUser, updatePassword } = useAuth();
  const { showNotification } = useNotification();
  const { showLoading, hideLoading } = useLoading();
  const [formData, setFormData] = useState<Partial<User>>({
    username: '',
    email: '',
    displayName: '',
    bio: '',
  });
  const [avatarPreview, setAvatarPreview] = useState<string | null>(null);
  const [passwordData, setPasswordData] = useState({
    currentPassword: '',
    newPassword: '',
    confirmPassword: '',
  });
  const [openPasswordDialog, setOpenPasswordDialog] = useState(false);

  useEffect(() => {
    if (user) {
      setFormData({
        username: user.username,
        email: user.email,
        displayName: user.displayName || '',
        bio: user.bio || '',
      });
      setAvatarPreview(user.avatar || null);
    }
  }, [user]);

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handlePasswordChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setPasswordData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleAvatarChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (file) {
      const reader = new FileReader();
      reader.onloadend = () => {
        setAvatarPreview(reader.result as string);
        setFormData((prev) => ({
          ...prev,
          avatar: reader.result as string,
        }));
      };
      reader.readAsDataURL(file);
    }
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      showLoading();
      await updateUser(formData);
      showNotification('ユーザー情報を更新しました', 'success');
    } catch (error) {
      console.error('ユーザー情報の更新に失敗しました:', error);
      showNotification('ユーザー情報の更新に失敗しました', 'error');
    } finally {
      hideLoading();
    }
  };

  const handlePasswordSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (passwordData.newPassword !== passwordData.confirmPassword) {
      showNotification('新しいパスワードが一致しません', 'error');
      return;
    }
    setOpenPasswordDialog(true);
  };

  const handlePasswordConfirm = async () => {
    try {
      showLoading();
      await updatePassword(passwordData.currentPassword, passwordData.newPassword);
      showNotification('パスワードを更新しました', 'success');
      setPasswordData({
        currentPassword: '',
        newPassword: '',
        confirmPassword: '',
      });
      setOpenPasswordDialog(false);
    } catch (error) {
      console.error('パスワードの更新に失敗しました:', error);
      showNotification('パスワードの更新に失敗しました', 'error');
    } finally {
      hideLoading();
    }
  };

  const handlePasswordCancel = () => {
    setOpenPasswordDialog(false);
  };

  if (!user) {
    return null;
  }

  return (
    <Box sx={{ maxWidth: 600, mx: 'auto' }}>
      <Paper sx={{ p: 4 }}>
        <Typography variant="h5" component="h1" gutterBottom>
          プロフィール編集
        </Typography>
        <form onSubmit={handleSubmit}>
          <Grid container spacing={3}>
            <Grid item xs={12} sx={{ display: 'flex', justifyContent: 'center' }}>
              <Box sx={{ position: 'relative' }}>
                <Avatar
                  src={avatarPreview || undefined}
                  sx={{ width: 120, height: 120 }}
                />
                <IconButton
                  component="label"
                  sx={{
                    position: 'absolute',
                    bottom: 0,
                    right: 0,
                    backgroundColor: 'primary.main',
                    '&:hover': {
                      backgroundColor: 'primary.dark',
                    },
                  }}
                >
                  <input
                    type="file"
                    hidden
                    accept="image/*"
                    onChange={handleAvatarChange}
                  />
                  <PhotoCamera sx={{ color: 'white' }} />
                </IconButton>
              </Box>
            </Grid>
            <Grid item xs={12}>
              <TextField
                fullWidth
                label="ユーザー名"
                name="username"
                value={formData.username}
                onChange={handleInputChange}
                required
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                fullWidth
                label="メールアドレス"
                name="email"
                type="email"
                value={formData.email}
                onChange={handleInputChange}
                required
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                fullWidth
                label="表示名"
                name="displayName"
                value={formData.displayName}
                onChange={handleInputChange}
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                fullWidth
                label="自己紹介"
                name="bio"
                multiline
                rows={4}
                value={formData.bio}
                onChange={handleInputChange}
              />
            </Grid>
            <Grid item xs={12}>
              <Button
                type="submit"
                variant="contained"
                color="primary"
                fullWidth
                size="large"
              >
                保存
              </Button>
            </Grid>
          </Grid>
        </form>

        <Divider sx={{ my: 4 }} />

        <Typography variant="h6" gutterBottom>
          パスワード変更
        </Typography>
        <form onSubmit={handlePasswordSubmit}>
          <Grid container spacing={3}>
            <Grid item xs={12}>
              <TextField
                fullWidth
                label="現在のパスワード"
                name="currentPassword"
                type="password"
                value={passwordData.currentPassword}
                onChange={handlePasswordChange}
                required
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                fullWidth
                label="新しいパスワード"
                name="newPassword"
                type="password"
                value={passwordData.newPassword}
                onChange={handlePasswordChange}
                required
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                fullWidth
                label="新しいパスワード（確認）"
                name="confirmPassword"
                type="password"
                value={passwordData.confirmPassword}
                onChange={handlePasswordChange}
                required
              />
            </Grid>
            <Grid item xs={12}>
              <Button
                type="submit"
                variant="contained"
                color="primary"
                fullWidth
                size="large"
              >
                パスワードを変更
              </Button>
            </Grid>
          </Grid>
        </form>
      </Paper>

      {/* パスワード変更確認ダイアログ */}
      <Dialog
        open={openPasswordDialog}
        onClose={handlePasswordCancel}
        aria-labelledby="password-dialog-title"
      >
        <DialogTitle id="password-dialog-title">
          パスワード変更の確認
        </DialogTitle>
        <DialogContent>
          <Typography>
            パスワードを変更してもよろしいですか？
            変更後は新しいパスワードでログインする必要があります。
          </Typography>
        </DialogContent>
        <DialogActions>
          <Button onClick={handlePasswordCancel} color="primary">
            キャンセル
          </Button>
          <Button onClick={handlePasswordConfirm} color="primary" variant="contained">
            変更する
          </Button>
        </DialogActions>
      </Dialog>
    </Box>
  );
}; 