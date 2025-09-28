import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import {
  Container,
  Grid,
  Card,
  CardContent,
  Typography,
  TextField,
  Button,
  Box,
  CircularProgress,
} from "@mui/material";
import { teaApi } from "../api/teaApi";
import { useErrorHandler } from "../hooks/useErrorHandler";
import { useLoading } from "../contexts/LoadingContext";
import { Tea } from "../api/teaApi";

export const TeaListPage: React.FC = () => {
  const navigate = useNavigate();
  const [teas, setTeas] = useState<Tea[]>([]);
  const [searchQuery, setSearchQuery] = useState("");
  const [isLoading, setIsLoading] = useState(true);
  const { handleError } = useErrorHandler();
  const { showLoading, hideLoading } = useLoading();

  useEffect(() => {
    const fetchTeas = async () => {
      try {
        showLoading();
        const data = await teaApi.getTeas();
        setTeas(data);
      } catch (error) {
        handleError(error);
      } finally {
        setIsLoading(false);
        hideLoading();
      }
    };

    fetchTeas();
  }, [handleError, showLoading, hideLoading]);

  const filteredTeas = teas.filter((tea) =>
    tea.name.toLowerCase().includes(searchQuery.toLowerCase())
  );

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
    <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
      <Box display="flex" justifyContent="space-between" alignItems="center" mb={4}>
        <Typography variant="h4" component="h1">
          お茶一覧
        </Typography>
        <Button
          variant="contained"
          color="primary"
          onClick={() => navigate("/teas/new")}
        >
          新規登録
        </Button>
      </Box>

      <TextField
        fullWidth
        label="お茶を検索..."
        variant="outlined"
        value={searchQuery}
        onChange={(e) => setSearchQuery(e.target.value)}
        sx={{ mb: 4 }}
      />

      <Grid container spacing={3}>
        {filteredTeas.map((tea) => (
          <Grid item xs={12} sm={6} md={4} key={tea.id}>
            <Card
              sx={{
                height: "100%",
                display: "flex",
                flexDirection: "column",
                cursor: "pointer",
              }}
              onClick={() => navigate(`/teas/${tea.id}`)}
            >
              <CardContent>
                <Typography gutterBottom variant="h5" component="h2">
                  {tea.name}
                </Typography>
                <Typography color="text.secondary" gutterBottom>
                  {tea.type}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  産地: {tea.origin}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  購入日: {new Date(tea.purchaseDate).toLocaleDateString()}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  状態: {tea.status}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  在庫: {tea.quantity} {tea.unit}
                </Typography>
              </CardContent>
            </Card>
          </Grid>
        ))}
      </Grid>

      {filteredTeas.length === 0 && (
        <Box textAlign="center" mt={4}>
          <Typography variant="body1" color="text.secondary">
            お茶が見つかりません
          </Typography>
        </Box>
      )}
    </Container>
  );
}; 