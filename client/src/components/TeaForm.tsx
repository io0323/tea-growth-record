import React from "react";
import {
  TextField,
  Button,
  Grid,
  Box,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  CircularProgress,
} from "@mui/material";
import { useFormValidation } from "../hooks/useFormValidation";
import { commonRules } from "../utils/validation";
import { Tea } from "../types/tea";

interface TeaFormProps {
  initialValues?: Partial<Tea>;
  onSubmit: (values: Partial<Tea>) => Promise<void>;
  onCancel: () => void;
}

const validationRules = {
  name: [
    commonRules.required("お茶の名前を入力してください"),
    commonRules.minLength(2, "お茶の名前は2文字以上で入力してください"),
    commonRules.maxLength(100, "お茶の名前は100文字以内で入力してください"),
  ],
  type: [
    commonRules.required("お茶の種類を選択してください"),
  ],
  origin: [
    commonRules.required("産地を入力してください"),
    commonRules.minLength(2, "産地は2文字以上で入力してください"),
    commonRules.maxLength(100, "産地は100文字以内で入力してください"),
  ],
  purchaseDate: [
    commonRules.required("購入日を入力してください"),
    commonRules.date("有効な日付を入力してください"),
  ],
  status: [
    commonRules.required("状態を選択してください"),
  ],
  description: [
    commonRules.maxLength(1000, "説明は1000文字以内で入力してください"),
  ],
  price: [
    commonRules.required("価格を入力してください"),
    commonRules.positiveNumber("価格は0より大きい数値を入力してください"),
    commonRules.priceRange(0, 1000000, "価格は0円から1,000,000円の間で入力してください"),
  ],
  quantity: [
    commonRules.required("数量を入力してください"),
    commonRules.positiveNumber("数量は0より大きい数値を入力してください"),
    commonRules.quantityRange(0, 1000, "数量は0から1,000の間で入力してください"),
  ],
  unit: [
    commonRules.required("単位を選択してください"),
  ],
};

const initialValues: Partial<Tea> = {
  name: "",
  type: "",
  origin: "",
  purchaseDate: "",
  status: "未開封",
  description: "",
  price: "",
  quantity: "",
  unit: "g",
};

export const TeaForm: React.FC<TeaFormProps> = React.memo(({
  initialValues: propsInitialValues,
  onSubmit,
  onCancel,
}) => {
  const {
    values,
    errors,
    isSubmitting,
    handleChange,
    handleBlur,
    handleSubmit,
    getFieldError,
    hasFieldError,
  } = useFormValidation({
    initialValues: { ...initialValues, ...propsInitialValues },
    validationRules,
    onSubmit,
  });

  return (
    <Box component="form" onSubmit={(e) => { e.preventDefault(); handleSubmit(); }} noValidate>
      <Grid container spacing={3}>
        <Grid item xs={12}>
          <TextField
            fullWidth
            label="お茶の名前"
            name="name"
            value={values.name}
            onChange={(e) => handleChange("name", e.target.value)}
            onBlur={() => handleBlur("name")}
            error={hasFieldError("name")}
            helperText={getFieldError("name")}
            aria-label="お茶の名前"
            inputProps={{
              'aria-describedby': hasFieldError("name") ? "name-error" : undefined,
            }}
          />
        </Grid>

        <Grid item xs={12} sm={6}>
          <FormControl fullWidth error={hasFieldError("type")}>
            <InputLabel>お茶の種類</InputLabel>
            <Select
              value={values.type}
              label="お茶の種類"
              onChange={(e) => handleChange("type", e.target.value)}
              onBlur={() => handleBlur("type")}
              aria-label="お茶の種類"
            >
              <MenuItem value="緑茶">緑茶</MenuItem>
              <MenuItem value="紅茶">紅茶</MenuItem>
              <MenuItem value="烏龍茶">烏龍茶</MenuItem>
              <MenuItem value="プーアル茶">プーアル茶</MenuItem>
              <MenuItem value="その他">その他</MenuItem>
            </Select>
            {hasFieldError("type") && (
              <Box component="span" id="type-error" role="alert">
                {getFieldError("type")}
              </Box>
            )}
          </FormControl>
        </Grid>

        <Grid item xs={12} sm={6}>
          <TextField
            fullWidth
            label="産地"
            name="origin"
            value={values.origin}
            onChange={(e) => handleChange("origin", e.target.value)}
            onBlur={() => handleBlur("origin")}
            error={hasFieldError("origin")}
            helperText={getFieldError("origin")}
            aria-label="産地"
            inputProps={{
              'aria-describedby': hasFieldError("origin") ? "origin-error" : undefined,
            }}
          />
        </Grid>

        <Grid item xs={12} sm={6}>
          <TextField
            fullWidth
            label="購入日"
            name="purchaseDate"
            type="date"
            value={values.purchaseDate}
            onChange={(e) => handleChange("purchaseDate", e.target.value)}
            onBlur={() => handleBlur("purchaseDate")}
            error={hasFieldError("purchaseDate")}
            helperText={getFieldError("purchaseDate")}
            InputLabelProps={{ shrink: true }}
            aria-label="購入日"
            inputProps={{
              'aria-describedby': hasFieldError("purchaseDate") ? "purchaseDate-error" : undefined,
            }}
          />
        </Grid>

        <Grid item xs={12} sm={6}>
          <FormControl fullWidth error={hasFieldError("status")}>
            <InputLabel>状態</InputLabel>
            <Select
              value={values.status}
              label="状態"
              onChange={(e) => handleChange("status", e.target.value)}
              onBlur={() => handleBlur("status")}
              aria-label="状態"
            >
              <MenuItem value="未開封">未開封</MenuItem>
              <MenuItem value="開封済み">開封済み</MenuItem>
              <MenuItem value="飲み終わり">飲み終わり</MenuItem>
            </Select>
            {hasFieldError("status") && (
              <Box component="span" id="status-error" role="alert">
                {getFieldError("status")}
              </Box>
            )}
          </FormControl>
        </Grid>

        <Grid item xs={12}>
          <TextField
            fullWidth
            label="説明"
            name="description"
            multiline
            rows={4}
            value={values.description}
            onChange={(e) => handleChange("description", e.target.value)}
            onBlur={() => handleBlur("description")}
            error={hasFieldError("description")}
            helperText={getFieldError("description")}
            aria-label="説明"
            inputProps={{
              'aria-describedby': hasFieldError("description") ? "description-error" : undefined,
            }}
          />
        </Grid>

        <Grid item xs={12} sm={6}>
          <TextField
            fullWidth
            label="価格"
            name="price"
            type="number"
            value={values.price}
            onChange={(e) => handleChange("price", e.target.value)}
            onBlur={() => handleBlur("price")}
            error={hasFieldError("price")}
            helperText={getFieldError("price")}
            InputProps={{ inputProps: { min: 0 } }}
            aria-label="価格"
            inputProps={{
              'aria-describedby': hasFieldError("price") ? "price-error" : undefined,
            }}
          />
        </Grid>

        <Grid item xs={12} sm={6}>
          <TextField
            fullWidth
            label="数量"
            name="quantity"
            type="number"
            value={values.quantity}
            onChange={(e) => handleChange("quantity", e.target.value)}
            onBlur={() => handleBlur("quantity")}
            error={hasFieldError("quantity")}
            helperText={getFieldError("quantity")}
            InputProps={{ inputProps: { min: 0 } }}
            aria-label="数量"
            inputProps={{
              'aria-describedby': hasFieldError("quantity") ? "quantity-error" : undefined,
            }}
          />
        </Grid>

        <Grid item xs={12} sm={6}>
          <FormControl fullWidth error={hasFieldError("unit")}>
            <InputLabel>単位</InputLabel>
            <Select
              value={values.unit}
              label="単位"
              onChange={(e) => handleChange("unit", e.target.value)}
              onBlur={() => handleBlur("unit")}
              aria-label="単位"
            >
              <MenuItem value="g">g</MenuItem>
              <MenuItem value="kg">kg</MenuItem>
              <MenuItem value="個">個</MenuItem>
              <MenuItem value="パック">パック</MenuItem>
            </Select>
            {hasFieldError("unit") && (
              <Box component="span" id="unit-error" role="alert">
                {getFieldError("unit")}
              </Box>
            )}
          </FormControl>
        </Grid>

        <Grid item xs={12}>
          <Box display="flex" gap={2} justifyContent="flex-end">
            <Button
              variant="outlined"
              onClick={onCancel}
              disabled={isSubmitting}
              aria-label="キャンセル"
            >
              キャンセル
            </Button>
            <Button
              type="submit"
              variant="contained"
              disabled={isSubmitting}
              aria-label="保存"
            >
              {isSubmitting ? <CircularProgress size={24} /> : "保存"}
            </Button>
          </Box>
        </Grid>
      </Grid>
    </Box>
  );
}); 