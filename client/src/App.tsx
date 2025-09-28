import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { ThemeProvider, CssBaseline } from "@mui/material";
import { SnackbarProvider } from "notistack";
import { theme } from "./theme";
import { Layout } from "./components/Layout";
import { DashboardPage } from "./pages/DashboardPage";
import { TeaListPage } from "./pages/TeaListPage";
import { TeaFormPage } from "./pages/TeaFormPage";
import { TeaDetailPage } from "./pages/TeaDetailPage";
import { ProfilePage } from "./pages/ProfilePage";
import { LoginPage } from "./pages/LoginPage";
import { RegisterPage } from "./pages/RegisterPage";
import { PrivateRoute } from "./components/PrivateRoute";
import { AuthProvider } from "./contexts/AuthContext";
import { LoadingProvider } from "./contexts/LoadingContext";

/**
 * アプリケーションのルートコンポーネント
 */
const App: React.FC = () => {
  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <SnackbarProvider maxSnack={3}>
        <AuthProvider>
          <LoadingProvider>
            <Router>
              <Routes>
                <Route path="/login" element={<LoginPage />} />
                <Route path="/register" element={<RegisterPage />} />
                <Route
                  path="/"
                  element={
                    <PrivateRoute>
                      <Layout />
                    </PrivateRoute>
                  }
                >
                  <Route index element={<DashboardPage />} />
                  <Route path="teas" element={<TeaListPage />} />
                  <Route path="teas/new" element={<TeaFormPage />} />
                  <Route path="teas/:id" element={<TeaDetailPage />} />
                  <Route path="teas/:id/edit" element={<TeaFormPage />} />
                  <Route path="profile" element={<ProfilePage />} />
                </Route>
              </Routes>
            </Router>
          </LoadingProvider>
        </AuthProvider>
      </SnackbarProvider>
    </ThemeProvider>
  );
};

export default App;
