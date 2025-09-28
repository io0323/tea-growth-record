import { useState, useCallback } from "react";
import { useLoadingState } from "./useLoadingState";
import { useErrorHandler } from "./useErrorHandler";

interface UseApiRequestOptions<T> {
  apiFunction: () => Promise<T>;
  loadingMessage?: string;
  successMessage?: string;
  onSuccess?: (data: T) => void;
  onError?: (error: Error) => void;
  autoHide?: boolean;
  delay?: number;
}

export const useApiRequest = <T>({
  apiFunction,
  loadingMessage,
  successMessage,
  onSuccess,
  onError,
  autoHide = true,
  delay = 0,
}: UseApiRequestOptions<T>) => {
  const [data, setData] = useState<T | null>(null);
  const { startLoading, stopLoading } = useLoadingState({
    message: loadingMessage,
    autoHide,
    delay,
  });
  const { handleError } = useErrorHandler();

  const execute = useCallback(async () => {
    try {
      startLoading();
      const result = await apiFunction();
      setData(result);
      if (onSuccess) {
        onSuccess(result);
      }
      if (successMessage) {
        // 成功メッセージを表示する処理を追加
        console.log(successMessage);
      }
    } catch (error) {
      handleError(error, {
        onError: (error) => {
          if (onError) {
            onError(error);
          }
        },
      });
    } finally {
      stopLoading();
    }
  }, [apiFunction, startLoading, stopLoading, handleError, onSuccess, onError, successMessage]);

  return {
    data,
    execute,
  };
}; 