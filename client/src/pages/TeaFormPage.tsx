import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import {
  Container,
  Typography,
  Box,
  TextField,
  Button,
  Grid,
  MenuItem,
  CircularProgress,
} from "@mui/material";
import { teaApi } from "../api/teaApi";
import { useErrorHandler } from "../hooks/useErrorHandler";
import { useLoading } from "../contexts/LoadingContext";
import { Tea, CreateTeaRequest } from "../api/teaApi";

const TEA_TYPES = ["緑茶", "紅茶", "烏龍茶", "白茶", "黄茶", "黒茶"];
const TEA_STATUSES = ["在庫あり", "在庫切れ", "注文済み"];

export const TeaFormPage: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const [tea, setTea] = useState<Tea | null>(null);
  const [formData, setFormData] = useState<CreateTeaRequest>({
    name: "",
    type: "",
    origin: "",
    purchaseDate: "",
    status: "",
    description: "",
    price: 0,
    quantity: 0,
    unit: "",
  });
  const [errors, setErrors] = useState<Partial<Record<keyof CreateTeaRequest, string>>>({});
  const [isLoading, setIsLoading] = useState(!!id);
  const { handleError } = useErrorHandler();
  const { showLoading, hideLoading } = useLoading();

  useEffect(() => {
    const fetchTea = async () => {
      if (!id) return;

      try {
        showLoading();
        const data = await teaApi.getTea(id);
        setTea(data);
        setFormData({
          name: data.name,
          type: data.type,
          origin: data.origin,
          purchaseDate: data.purchaseDate,
          status: data.status,
          description: data.description,
          price: data.price,
          quantity: data.quantity,
          unit: data.unit,
        });
      } catch (error) {
        handleError(error);
      } finally {
        setIsLoading(false);
        hideLoading();
      }
    };

    fetchTea();
  }, [id, handleError, showLoading, hideLoading]);

  const validateForm = (): boolean => {
    const newErrors: Partial<Record<keyof CreateTeaRequest, string>> = {};

    if (!formData.name) {
      newErrors.name = "お茶の名前を入力してください";
    }
    if (!formData.type) {
      newErrors.type = "種類を選択してください";
    }
    if (!formData.origin) {
      newErrors.origin = "産地を入力してください";
    }
    if (!formData.purchaseDate) {
      newErrors.purchaseDate = "購入日を入力してください";
    }
    if (!formData.status) {
      newErrors.status = "状態を選択してください";
    }
    if (!formData.price || formData.price <= 0) {
      newErrors.price = "価格を入力してください";
    }
    if (!formData.quantity || formData.quantity <= 0) {
      newErrors.quantity = "数量を入力してください";
    }
    if (!formData.unit) {
      newErrors.unit = "単位を入力してください";
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
      showLoading();
      if (id) {
        await teaApi.updateTea(id, formData);
      } else {
        await teaApi.createTea(formData);
      }
      navigate("/teas");
    } catch (error) {
      handleError(error);
    } finally {
      hideLoading();
    }
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: name === "price" || name === "quantity" ? Number(value) : value,
    }));
    // エラーをクリア
    if (errors[name as keyof CreateTeaRequest]) {
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
        {id ? "お茶の編集" : "お茶の新規登録"}
      </Typography>

      <form onSubmit={handleSubmit}>
        <Grid container spacing={3}>
          <Grid item xs={12}>
            <TextField
              fullWidth
              label="お茶の名前"
              name="name"
              value={formData.name}
              onChange={handleChange}
              error={!!errors.name}
              helperText={errors.name}
            />
          </Grid>

          <Grid item xs={12} sm={6}>
            <TextField
              fullWidth
              select
              label="種類"
              name="type"
              value={formData.type}
              onChange={handleChange}
              error={!!errors.type}
              helperText={errors.type}
            >
              {TEA_TYPES.map((type) => (
                <MenuItem key={type} value={type}>
                  {type}
                </MenuItem>
              ))}
            </TextField>
          </Grid>

          <Grid item xs={12} sm={6}>
            <TextField
              fullWidth
              label="産地"
              name="origin"
              value={formData.origin}
              onChange={handleChange}
              error={!!errors.origin}
              helperText={errors.origin}
            />
          </Grid>

          <Grid item xs={12} sm={6}>
            <TextField
              fullWidth
              type="date"
              label="購入日"
              name="purchaseDate"
              value={formData.purchaseDate}
              onChange={handleChange}
              error={!!errors.purchaseDate}
              helperText={errors.purchaseDate}
              InputLabelProps={{
                shrink: true,
              }}
            />
          </Grid>

          <Grid item xs={12} sm={6}>
            <TextField
              fullWidth
              select
              label="状態"
              name="status"
              value={formData.status}
              onChange={handleChange}
              error={!!errors.status}
              helperText={errors.status}
            >
              {TEA_STATUSES.map((status) => (
                <MenuItem key={status} value={status}>
                  {status}
                </MenuItem>
              ))}
            </TextField>
          </Grid>

          <Grid item xs={12} sm={6}>
            <TextField
              fullWidth
              type="number"
              label="価格"
              name="price"
              value={formData.price}
              onChange={handleChange}
              error={!!errors.price}
              helperText={errors.price}
              InputProps={{
                startAdornment: "¥",
              }}
            />
          </Grid>

          <Grid item xs={12} sm={6}>
            <TextField
              fullWidth
              type="number"
              label="数量"
              name="quantity"
              value={formData.quantity}
              onChange={handleChange}
              error={!!errors.quantity}
              helperText={errors.quantity}
            />
          </Grid>

          <Grid item xs={12} sm={6}>
            <TextField
              fullWidth
              label="単位"
              name="unit"
              value={formData.unit}
              onChange={handleChange}
              error={!!errors.unit}
              helperText={errors.unit}
            />
          </Grid>

          <Grid item xs={12}>
            <TextField
              fullWidth
              multiline
              rows={4}
              label="説明"
              name="description"
              value={formData.description}
              onChange={handleChange}
            />
          </Grid>

          <Grid item xs={12}>
            <Box display="flex" justifyContent="flex-end" gap={2}>
              <Button
                variant="outlined"
                color="primary"
                onClick={() => navigate("/teas")}
              >
                キャンセル
              </Button>
              <Button
                type="submit"
                variant="contained"
                color="primary"
                disabled={isLoading}
              >
                保存
              </Button>
            </Box>
          </Grid>
        </Grid>
      </form>
    </Container>
  );
}; 