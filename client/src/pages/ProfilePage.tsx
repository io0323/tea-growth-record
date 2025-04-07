import React, { useEffect, useState } from "react";
import {
  Container,
  Typography,
  Box,
  TextField,
  Button,
  Grid,
  Paper,
  CircularProgress,
} from "@mui/material";
import { authApi } from "../api/authApi";
import { useErrorHandler } from "../hooks/useErrorHandler";
import { useLoading } from "../contexts/LoadingContext";
import { User, UpdateProfileRequest } from "../api/authApi";

export const ProfilePage: React.FC = () => {
  const [user, setUser] = useState<User | null>(null);
  const [formData, setFormData] = useState<UpdateProfileRequest>({
    name: "",
    email: "",
  });
  const [passwordData, setPasswordData] = useState({
    currentPassword: "",
    newPassword: "",
    confirmPassword: "",
  });
  const [errors, setErrors] = useState<{
    name?: string;
    email?: string;
    currentPassword?: string;
    newPassword?: string;
    confirmPassword?: string;
  }>({});
  const [isLoading, setIsLoading] = useState(true);
  const [isSaving, setIsSaving] = useState(false);
  const { handleError } = useErrorHandler();
  const { showLoading, hideLoading } = useLoading();

  useEffect(() => {
    const fetchProfile = async () => {
      try {
        showLoading();
        const data = await authApi.getProfile();
        setUser(data);
        setFormData({
          name: data.name,
          email: data.email,
        });
      } catch (error) {
        handleError(error);
      } finally {
        setIsLoading(false);
        hideLoading();
      }
    };

    fetchProfile();
  }, [handleError, showLoading, hideLoading]);

  const validateForm = (): boolean => {
    const newErrors: typeof errors = {};

    if (!formData.name) {
      newErrors.name = "名前を入力してください";
    }
    if (!formData.email) {
      newErrors.email = "メールアドレスを入力してください";
    } else if (!/^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i.test(formData.email)) {
      newErrors.email = "有効なメールアドレスを入力してください";
    }

    // パスワード変更の場合のバリデーション
    if (passwordData.newPassword || passwordData.currentPassword) {
      if (!passwordData.currentPassword) {
        newErrors.currentPassword = "現在のパスワードを入力してください";
      }
      if (!passwordData.newPassword) {
        newErrors.newPassword = "新しいパスワードを入力してください";
      }
      if (!passwordData.confirmPassword) {
        newErrors.confirmPassword = "新しいパスワード（確認）を入力してください";
      }
      if (passwordData.newPassword !== passwordData.confirmPassword) {
        newErrors.confirmPassword = "パスワードが一致しません";
      }
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (!validateForm()) {
      return;
    }

    try {
      setIsSaving(true);
      showLoading();

      const updateData: UpdateProfileRequest = {
        ...formData,
        ...(passwordData.newPassword && {
          currentPassword: passwordData.currentPassword,
          newPassword: passwordData.newPassword,
        }),
      };

      const updatedUser = await authApi.updateProfile(updateData);
      setUser(updatedUser);
      setPasswordData({
        currentPassword: "",
        newPassword: "",
        confirmPassword: "",
      });
    } catch (error) {
      handleError(error);
    } finally {
      setIsSaving(false);
      hideLoading();
    }
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    if (name in passwordData) {
      setPasswordData((prev) => ({
        ...prev,
        [name]: value,
      }));
    } else {
      setFormData((prev) => ({
        ...prev,
        [name]: value,
      }));
    }
    // エラーをクリア
    if (errors[name as keyof typeof errors]) {
      setErrors((prev) => ({
        ...prev,
        [name]: undefined,
      }));
    }
  };

  if (isLoading) {
    return (
      <Box
        display="flex"
        justifyContent="center"
        alignItems="center"
        minHeight="60vh"
      >
        <CircularProgress />
      </Box>
    );
  }

  return (
    <Container maxWidth="md" sx={{ mt: 4, mb: 4 }}>
      <Typography variant="h4" component="h1" gutterBottom>
        プロフィール設定
      </Typography>

      <Paper sx={{ p: 3 }}>
        <form onSubmit={handleSubmit}>
          <Grid container spacing={3}>
            <Grid item xs={12}>
              <TextField
                fullWidth
                label="名前"
                name="name"
                value={formData.name}
                onChange={handleChange}
                error={!!errors.name}
                helperText={errors.name}
              />
            </Grid>

            <Grid item xs={12}>
              <TextField
                fullWidth
                label="メールアドレス"
                name="email"
                type="email"
                value={formData.email}
                onChange={handleChange}
                error={!!errors.email}
                helperText={errors.email}
              />
            </Grid>

            <Grid item xs={12}>
              <Typography variant="h6" gutterBottom>
                パスワード変更
              </Typography>
            </Grid>

            <Grid item xs={12}>
              <TextField
                fullWidth
                label="現在のパスワード"
                name="currentPassword"
                type="password"
                value={passwordData.currentPassword}
                onChange={handleChange}
                error={!!errors.currentPassword}
                helperText={errors.currentPassword}
              />
            </Grid>

            <Grid item xs={12} sm={6}>
              <TextField
                fullWidth
                label="新しいパスワード"
                name="newPassword"
                type="password"
                value={passwordData.newPassword}
                onChange={handleChange}
                error={!!errors.newPassword}
                helperText={errors.newPassword}
              />
            </Grid>

            <Grid item xs={12} sm={6}>
              <TextField
                fullWidth
                label="新しいパスワード（確認）"
                name="confirmPassword"
                type="password"
                value={passwordData.confirmPassword}
                onChange={handleChange}
                error={!!errors.confirmPassword}
                helperText={errors.confirmPassword}
              />
            </Grid>

            <Grid item xs={12}>
              <Box display="flex" justifyContent="flex-end">
                <Button
                  type="submit"
                  variant="contained"
                  color="primary"
                  disabled={isSaving}
                >
                  保存
                </Button>
              </Box>
            </Grid>
          </Grid>
        </form>
      </Paper>
    </Container>
  );
}; 