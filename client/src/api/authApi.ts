import axios from "axios";
import { User, LoginRequest, RegisterRequest, UpdateProfileRequest } from "../types/user";

const API_BASE_URL = import.meta.env.VITE_API_URL || 'http://localhost:9000/api';

const api = axios.create({
  baseURL: API_BASE_URL,
  withCredentials: true,
  headers: {
    "Content-Type": "application/json",
  },
});

// リクエストインターセプター
api.interceptors.request.use(
  (config) => {
    // CSRFトークンの設定
    const token = document.cookie
      .split("; ")
      .find((row) => row.startsWith("XSRF-TOKEN="))
      ?.split("=")[1];
    if (token) {
      config.headers["X-XSRF-TOKEN"] = token;
    }
    return config;
  },
  (error) => {
    console.error('Request error:', error);
    return Promise.reject(error);
  }
);

// レスポンスインターセプター
api.interceptors.response.use(
  (response) => response,
  async (error) => {
    if (error.response?.status === 401) {
      // 認証エラーの場合、ログインページにリダイレクト
      window.location.href = "/login";
    }
    return Promise.reject(error);
  }
);

export interface AuthResponse {
  success: boolean;
  message: string;
  user?: {
    id: number;
    name: string;
    email: string;
  };
}

export const authApi = {
  /**
   * ログイン
   */
  login: async (data: LoginRequest): Promise<AuthResponse> => {
    try {
      console.log('Attempting login with:', data);
      const response = await api.post<AuthResponse>("/auth/login", data);
      console.log('Login response:', response.data);
      return response.data;
    } catch (error) {
      console.error('Login error:', error);
      if (axios.isAxiosError(error)) {
        console.error('Response data:', error.response?.data);
        console.error('Response status:', error.response?.status);
      }
      throw error;
    }
  },

  /**
   * 新規登録
   */
  register: async (data: RegisterRequest): Promise<AuthResponse> => {
    try {
      const response = await api.post<AuthResponse>("/auth/register", data);
      return response.data;
    } catch (error) {
      console.error('Register error:', error);
      throw error;
    }
  },

  /**
   * ログアウト
   */
  logout: async (): Promise<void> => {
    try {
      await api.post("/auth/logout");
    } catch (error) {
      console.error('Logout error:', error);
      throw error;
    }
  },

  /**
   * プロフィール取得
   */
  getProfile: async (): Promise<User> => {
    try {
      const response = await api.get<User>("/auth/profile");
      return response.data;
    } catch (error) {
      if (axios.isAxiosError(error) && error.response?.status === 401) {
        throw new Error("認証が必要です");
      }
      throw error;
    }
  },

  /**
   * プロフィール更新
   */
  updateProfile: async (data: UpdateProfileRequest): Promise<User> => {
    try {
      const response = await api.put<User>("/auth/profile", data);
      return response.data;
    } catch (error) {
      if (axios.isAxiosError(error)) {
        if (error.response?.status === 401) {
          throw new Error("認証が必要です");
        }
        if (error.response?.status === 409) {
          throw new Error("このメールアドレスは既に使用されています");
        }
        if (error.response?.status === 422) {
          throw new Error("入力内容が正しくありません");
        }
      }
      throw error;
    }
  },

  /**
   * パスワード変更
   */
  changePassword: async (data: {
    currentPassword: string;
    newPassword: string;
  }): Promise<void> => {
    try {
      await api.put("/auth/password", data);
    } catch (error) {
      if (axios.isAxiosError(error)) {
        if (error.response?.status === 401) {
          throw new Error("現在のパスワードが正しくありません");
        }
        if (error.response?.status === 422) {
          throw new Error("新しいパスワードの形式が正しくありません");
        }
      }
      throw error;
    }
  },

  /**
   * パスワードリセットメール送信
   */
  sendPasswordResetEmail: async (email: string): Promise<void> => {
    try {
      await api.post("/auth/password-reset", { email });
    } catch (error) {
      if (axios.isAxiosError(error) && error.response?.status === 404) {
        throw new Error("このメールアドレスは登録されていません");
      }
      throw error;
    }
  },

  /**
   * パスワードリセット
   */
  resetPassword: async (data: {
    token: string;
    password: string;
  }): Promise<void> => {
    try {
      await api.post("/auth/password-reset/confirm", data);
    } catch (error) {
      if (axios.isAxiosError(error)) {
        if (error.response?.status === 400) {
          throw new Error("無効または期限切れのトークンです");
        }
        if (error.response?.status === 422) {
          throw new Error("パスワードの形式が正しくありません");
        }
      }
      throw error;
    }
  },
}; 