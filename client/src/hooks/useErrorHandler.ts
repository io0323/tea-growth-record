import { useState, useCallback } from "react";
import { useSnackbar } from "notistack";
import axios from "axios";

export const useErrorHandler = () => {
  const { enqueueSnackbar } = useSnackbar();
  const [errors, setErrors] = useState<Record<string, string[]>>({});

  const handleError = useCallback((error: any) => {
    if (axios.isAxiosError(error)) {
      const response = error.response;

      // バリデーションエラー
      if (response?.status === 422) {
        const validationErrors = response.data.errors;
        setErrors(validationErrors);
        Object.values(validationErrors).forEach((messages) => {
          messages.forEach((message) => {
            enqueueSnackbar(message, { variant: "error" });
          });
        });
        return;
      }

      // 認証エラー
      if (response?.status === 401) {
        enqueueSnackbar("認証に失敗しました。再度ログインしてください。", {
          variant: "error",
        });
        // ログインページにリダイレクト
        window.location.href = "/login";
        return;
      }

      // 権限エラー
      if (response?.status === 403) {
        enqueueSnackbar("この操作を行う権限がありません。", {
          variant: "error",
        });
        return;
      }

      // リソースが見つからない
      if (response?.status === 404) {
        enqueueSnackbar("リソースが見つかりません。", {
          variant: "error",
        });
        return;
      }

      // サーバーエラー
      if (response?.status === 500) {
        enqueueSnackbar("サーバーでエラーが発生しました。", {
          variant: "error",
        });
        return;
      }

      // その他のエラー
      enqueueSnackbar(
        response?.data?.message || "予期せぬエラーが発生しました。",
        { variant: "error" }
      );
    } else {
      // その他のエラー
      enqueueSnackbar("予期せぬエラーが発生しました。", {
        variant: "error",
      });
    }
  }, [enqueueSnackbar]);

  const clearErrors = useCallback(() => {
    setErrors({});
  }, []);

  return {
    errors,
    handleError,
    clearErrors,
  };
}; 