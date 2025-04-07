import axios from "axios";
import { Tea, CreateTeaRequest, UpdateTeaRequest } from "../types/tea";

const API_URL = import.meta.env.VITE_API_URL || "http://localhost:9000/api";

const api = axios.create({
  baseURL: API_URL,
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

export const teaApi = {
  /**
   * お茶一覧を取得
   */
  getTeas: async (): Promise<Tea[]> => {
    try {
      const response = await api.get<Tea[]>("/teas");
      return response.data;
    } catch (error) {
      if (axios.isAxiosError(error) && error.response?.status === 404) {
        return [];
      }
      throw error;
    }
  },

  /**
   * お茶の詳細を取得
   */
  getTea: async (id: string): Promise<Tea> => {
    try {
      const response = await api.get<Tea>(`/teas/${id}`);
      return response.data;
    } catch (error) {
      if (axios.isAxiosError(error) && error.response?.status === 404) {
        throw new Error("お茶が見つかりません");
      }
      throw error;
    }
  },

  /**
   * お茶を新規作成
   */
  createTea: async (data: CreateTeaRequest): Promise<Tea> => {
    try {
      const response = await api.post<Tea>("/teas", data);
      return response.data;
    } catch (error) {
      if (axios.isAxiosError(error) && error.response?.status === 422) {
        throw new Error("入力内容が正しくありません");
      }
      throw error;
    }
  },

  /**
   * お茶を更新
   */
  updateTea: async (id: string, data: UpdateTeaRequest): Promise<Tea> => {
    try {
      const response = await api.put<Tea>(`/teas/${id}`, data);
      return response.data;
    } catch (error) {
      if (axios.isAxiosError(error)) {
        if (error.response?.status === 404) {
          throw new Error("お茶が見つかりません");
        }
        if (error.response?.status === 422) {
          throw new Error("入力内容が正しくありません");
        }
      }
      throw error;
    }
  },

  /**
   * お茶を削除
   */
  deleteTea: async (id: string): Promise<void> => {
    try {
      await api.delete(`/teas/${id}`);
    } catch (error) {
      if (axios.isAxiosError(error) && error.response?.status === 404) {
        throw new Error("お茶が見つかりません");
      }
      throw error;
    }
  },

  /**
   * お茶の画像をアップロード
   */
  uploadImage: async (id: string, file: File): Promise<string> => {
    try {
      const formData = new FormData();
      formData.append("image", file);

      const response = await api.post<{ url: string }>(
        `/teas/${id}/image`,
        formData,
        {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        }
      );
      return response.data.url;
    } catch (error) {
      if (axios.isAxiosError(error)) {
        if (error.response?.status === 404) {
          throw new Error("お茶が見つかりません");
        }
        if (error.response?.status === 413) {
          throw new Error("ファイルサイズが大きすぎます");
        }
        if (error.response?.status === 415) {
          throw new Error("サポートされていないファイル形式です");
        }
      }
      throw error;
    }
  },
}; 