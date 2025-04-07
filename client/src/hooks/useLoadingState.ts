import { useState, useCallback, useEffect } from "react";
import { useLoading } from "../contexts/LoadingContext";

interface UseLoadingStateOptions {
  message?: string;
  autoHide?: boolean;
  delay?: number;
}

export const useLoadingState = (options: UseLoadingStateOptions = {}) => {
  const { message, autoHide = true, delay = 0 } = options;
  const { showLoading, hideLoading, isLoading } = useLoading();
  const [isLocalLoading, setIsLocalLoading] = useState(false);

  const startLoading = useCallback(() => {
    setIsLocalLoading(true);
    if (delay > 0) {
      setTimeout(() => {
        showLoading(message);
      }, delay);
    } else {
      showLoading(message);
    }
  }, [delay, message, showLoading]);

  const stopLoading = useCallback(() => {
    setIsLocalLoading(false);
    hideLoading();
  }, [hideLoading]);

  // コンポーネントのアンマウント時にローディングを停止
  useEffect(() => {
    return () => {
      if (isLocalLoading) {
        stopLoading();
      }
    };
  }, [isLocalLoading, stopLoading]);

  // 自動的にローディングを停止する
  useEffect(() => {
    if (autoHide && isLocalLoading) {
      const timer = setTimeout(() => {
        stopLoading();
      }, 10000); // 10秒後に自動的に停止

      return () => clearTimeout(timer);
    }
  }, [autoHide, isLocalLoading, stopLoading]);

  return {
    isLoading: isLocalLoading,
    startLoading,
    stopLoading,
  };
}; 