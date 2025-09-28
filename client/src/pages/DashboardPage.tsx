import React from "react";
import {
  Container,
  Grid,
  Paper,
  Typography,
  Box,
  Card,
  CardContent,
  Button,
} from "@mui/material";
import { Add as AddIcon } from "@mui/icons-material";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../contexts/AuthContext";

export const DashboardPage: React.FC = () => {
  const navigate = useNavigate();
  const { user } = useAuth();

  return (
    <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
      <Grid container spacing={3}>
        {/* ウェルカムメッセージ */}
        <Grid item xs={12}>
          <Paper
            sx={{
              p: 3,
              display: "flex",
              flexDirection: "column",
              height: 140,
              background: "linear-gradient(45deg, #4CAF50 30%, #81C784 90%)",
              color: "white",
            }}
          >
            <Typography variant="h4" gutterBottom>
              ようこそ、{user?.name || "ユーザー"}さん
            </Typography>
            <Typography variant="subtitle1">
              お茶の成長記録を管理しましょう
            </Typography>
          </Paper>
        </Grid>

        {/* クイックアクション */}
        <Grid item xs={12} md={6}>
          <Card>
            <CardContent>
              <Typography variant="h6" gutterBottom>
                クイックアクション
              </Typography>
              <Box sx={{ display: "flex", gap: 2, flexWrap: "wrap" }}>
                <Button
                  variant="contained"
                  color="primary"
                  startIcon={<AddIcon />}
                  onClick={() => navigate("/teas/new")}
                >
                  新規登録
                </Button>
                <Button
                  variant="outlined"
                  color="primary"
                  onClick={() => navigate("/teas")}
                >
                  お茶一覧
                </Button>
              </Box>
            </CardContent>
          </Card>
        </Grid>

        {/* 統計情報 */}
        <Grid item xs={12} md={6}>
          <Card>
            <CardContent>
              <Typography variant="h6" gutterBottom>
                統計情報
              </Typography>
              <Grid container spacing={2}>
                <Grid item xs={6}>
                  <Typography color="textSecondary" gutterBottom>
                    登録済みのお茶
                  </Typography>
                  <Typography variant="h4">0</Typography>
                </Grid>
                <Grid item xs={6}>
                  <Typography color="textSecondary" gutterBottom>
                    今月の記録
                  </Typography>
                  <Typography variant="h4">0</Typography>
                </Grid>
              </Grid>
            </CardContent>
          </Card>
        </Grid>

        {/* 最近の記録 */}
        <Grid item xs={12}>
          <Paper sx={{ p: 3, display: "flex", flexDirection: "column" }}>
            <Typography variant="h6" gutterBottom>
              最近の記録
            </Typography>
            <Box sx={{ mt: 2 }}>
              <Typography color="textSecondary">
                まだ記録がありません
              </Typography>
            </Box>
          </Paper>
        </Grid>
      </Grid>
    </Container>
  );
}; 