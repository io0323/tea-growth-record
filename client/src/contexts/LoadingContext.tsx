import React, { createContext, useContext, useState, useCallback } from "react";
import { Backdrop, CircularProgress } from "@mui/material";

interface LoadingContextType {
  showLoading: (message?: string) => void;
  hideLoading: () => void;
  isLoading: boolean;
  loadingMessage: string | null;
}

const LoadingContext = createContext<LoadingContextType | undefined>(undefined);

export const useLoading = () => {
  const context = useContext(LoadingContext);
  if (!context) {
    throw new Error("useLoading must be used within a LoadingProvider");
  }
  return context;
};

interface LoadingProviderProps {
  children: React.ReactNode;
}

export const LoadingProvider: React.FC<LoadingProviderProps> = ({ children }) => {
  const [loadingCount, setLoadingCount] = useState(0);
  const [loadingMessage, setLoadingMessage] = useState<string | null>(null);

  const showLoading = useCallback((message?: string) => {
    setLoadingCount((prev) => prev + 1);
    if (message) {
      setLoadingMessage(message);
    }
  }, []);

  const hideLoading = useCallback(() => {
    setLoadingCount((prev) => {
      if (prev <= 1) {
        setLoadingMessage(null);
        return 0;
      }
      return prev - 1;
    });
  }, []);

  const isLoading = loadingCount > 0;

  return (
    <LoadingContext.Provider
      value={{
        showLoading,
        hideLoading,
        isLoading,
        loadingMessage,
      }}
    >
      {children}
      <Backdrop
        sx={{
          color: "#fff",
          zIndex: (theme) => theme.zIndex.drawer + 1,
          backgroundColor: "rgba(0, 0, 0, 0.5)",
        }}
        open={isLoading}
      >
        <CircularProgress color="inherit" />
        {loadingMessage && (
          <div
            style={{
              position: "absolute",
              bottom: "20%",
              color: "#fff",
              textAlign: "center",
              width: "100%",
              fontSize: "1.1rem",
            }}
          >
            {loadingMessage}
          </div>
        )}
      </Backdrop>
    </LoadingContext.Provider>
  );
}; 