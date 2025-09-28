import axios from 'axios';

const API_BASE_URL = import.meta.env.VITE_API_URL || 'http://localhost:9000/api';

// デフォルトのaxiosインスタンスを作成
const api = axios.create({
  baseURL: API_BASE_URL,
  withCredentials: true,
  headers: {
    'Content-Type': 'application/json',
  },
});

/**
 * CSRFトークンを取得する関数
 * @returns Promise<string | null> CSRFトークン
 */
const getCsrfToken = async () => {
  try {
    const response = await axios.get(`${API_BASE_URL}/csrf`, {
      withCredentials: true
    });
    return response.data.token;
  } catch (error) {
    console.error('CSRFトークンの取得に失敗しました:', error);
    throw error;
  }
};

/**
 * リクエストインターセプター
 * CSRFトークンを自動的に設定します
 */
api.interceptors.request.use(
  async (config) => {
    try {
      const token = await getCsrfToken();
      if (token) {
        config.headers['X-CSRF-Token'] = token;
      }
      return config;
    } catch (error) {
      console.error('リクエストの準備中にエラーが発生しました:', error);
      return Promise.reject(error);
    }
  },
  (error) => {
    return Promise.reject(error);
  }
);

/**
 * レスポンスインターセプター
 * エラーハンドリングを強化します
 */
api.interceptors.response.use(
  (response) => response,
  async (error) => {
    if (error.response?.status === 401) {
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

// 型定義
export interface User {
  id: number;
  email: string;
  name: string;
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface RegisterRequest {
  email: string;
  password: string;
  name: string;
}

/**
 * 認証関連のAPI
 */
export const auth = {
  /**
   * ユーザーログイン
   * @param data ログイン情報
   */
  login: async (data: LoginRequest) => {
    try {
      // ログイン処理を一時的にコメントアウト
      /*
      const response = await axios.post(`${API_BASE_URL}/api/auth/login`, {
        email,
        password
      }, {
        withCredentials: true
      })
      return response
      */
      return {
        data: {
          user: {
            id: 1,
            email: data.email,
            name: 'テストユーザー',
            role: 'user'
          },
          token: 'dummy-token'
        }
      }
    } catch (error) {
      console.error('ログインに失敗しました:', error)
      throw error
    }
  },

  /**
   * ユーザー登録
   * @param data 登録情報
   */
  register: async (data: RegisterRequest) => {
    const response = await api.post<{ message: string; user: User }>('/auth/register', data);
    return response.data.user;
  },

  /**
   * ログアウト
   */
  logout: async () => {
    const response = await api.post<{ success: boolean }>('/auth/logout');
    return response.data;
  },

  /**
   * 現在のユーザー情報を取得
   */
  getCurrentUser: async () => {
    try {
      // 現在のユーザー情報の取得を一時的にコメントアウト
      /*
      const response = await axios.get(`${API_BASE_URL}/api/users/me`, {
        withCredentials: true
      })
      return response.data
      */
      return {
        id: 1,
        email: 'test@example.com',
        name: 'テストユーザー',
        role: 'user'
      }
    } catch (error) {
      console.error('ユーザー情報の取得に失敗しました:', error)
      throw error
    }
  },
};

export { api, getCsrfToken }; 