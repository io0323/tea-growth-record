import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import {
  Container,
  Typography,
  Box,
  Grid,
  Paper,
  Button,
  CircularProgress,
} from "@mui/material";
import { teaApi } from "../api/teaApi";
import { useErrorHandler } from "../hooks/useErrorHandler";
import { useLoading } from "../contexts/LoadingContext";
import { Tea } from "../api/teaApi";

export const TeaDetailPage: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const [tea, setTea] = useState<Tea | null>(null);
  const [isLoading, setIsLoading] = useState(true);
  const { handleError } = useErrorHandler();
  const { showLoading, hideLoading } = useLoading();

  useEffect(() => {
    const fetchTea = async () => {
      if (!id) return;

      try {
        showLoading();
        const data = await teaApi.getTea(id);
        setTea(data);
      } catch (error) {
        handleError(error);
      } finally {
        setIsLoading(false);
        hideLoading();
      }
    };

    fetchTea();
  }, [id, handleError, showLoading, hideLoading]);

  const handleDelete = async () => {
    if (!id) return;

    if (window.confirm("このお茶を削除してもよろしいですか？")) {
      try {
        showLoading();
        await teaApi.deleteTea(id);
        navigate("/teas");
      } catch (error) {
        handleError(error);
      } finally {
        hideLoading();
      }
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

  if (!tea) {
    return (
      <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
        <Typography variant="h5" component="h2" gutterBottom>
          お茶が見つかりません
        </Typography>
        <Button
          variant="contained"
          color="primary"
          onClick={() => navigate("/teas")}
        >
          お茶一覧に戻る
        </Button>
      </Container>
    );
  }

  return (
    <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
      <Box display="flex" justifyContent="space-between" alignItems="center" mb={4}>
        <Typography variant="h4" component="h1">
          {tea.name}
        </Typography>
        <Box>
          <Button
            variant="contained"
            color="primary"
            onClick={() => navigate(`/teas/${id}/edit`)}
            sx={{ mr: 2 }}
          >
            編集
          </Button>
          <Button
            variant="outlined"
            color="error"
            onClick={handleDelete}
          >
            削除
          </Button>
        </Box>
      </Box>

      <Grid container spacing={3}>
        <Grid item xs={12} md={6}>
          <Paper sx={{ p: 3 }}>
            <Typography variant="h6" gutterBottom>
              基本情報
            </Typography>
            <Box sx={{ mb: 2 }}>
              <Typography variant="subtitle2" color="text.secondary">
                種類
              </Typography>
              <Typography variant="body1">{tea.type}</Typography>
            </Box>
            <Box sx={{ mb: 2 }}>
              <Typography variant="subtitle2" color="text.secondary">
                産地
              </Typography>
              <Typography variant="body1">{tea.origin}</Typography>
            </Box>
            <Box sx={{ mb: 2 }}>
              <Typography variant="subtitle2" color="text.secondary">
                購入日
              </Typography>
              <Typography variant="body1">
                {new Date(tea.purchaseDate).toLocaleDateString()}
              </Typography>
            </Box>
            <Box sx={{ mb: 2 }}>
              <Typography variant="subtitle2" color="text.secondary">
                状態
              </Typography>
              <Typography variant="body1">{tea.status}</Typography>
            </Box>
          </Paper>
        </Grid>

        <Grid item xs={12} md={6}>
          <Paper sx={{ p: 3 }}>
            <Typography variant="h6" gutterBottom>
              在庫情報
            </Typography>
            <Box sx={{ mb: 2 }}>
              <Typography variant="subtitle2" color="text.secondary">
                価格
              </Typography>
              <Typography variant="body1">
                ¥{tea.price.toLocaleString()}
              </Typography>
            </Box>
            <Box sx={{ mb: 2 }}>
              <Typography variant="subtitle2" color="text.secondary">
                在庫数
              </Typography>
              <Typography variant="body1">
                {tea.quantity} {tea.unit}
              </Typography>
            </Box>
          </Paper>
        </Grid>

        <Grid item xs={12}>
          <Paper sx={{ p: 3 }}>
            <Typography variant="h6" gutterBottom>
              説明
            </Typography>
            <Typography variant="body1">{tea.description}</Typography>
          </Paper>
        </Grid>
      </Grid>

      <Box sx={{ mt: 4, textAlign: "center" }}>
        <Button
          variant="outlined"
          color="primary"
          onClick={() => navigate("/teas")}
        >
          お茶一覧に戻る
        </Button>
      </Box>
    </Container>
  );
}; 