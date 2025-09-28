import axios from 'axios';
import type { LoginForm, RegisterForm, Plant, User } from '../types';

const api = axios.create({
  baseURL: import.meta.env.VITE_API_URL || 'http://localhost:9000/api',
  withCredentials: true,
  headers: {
    'Content-Type': 'application/json',
  },
});

/**
 * CSRFトークンを取得する関数
 */
const getCsrfToken = async () => {
  try {
    const response = await api.get('/csrf');
    return response.data.token;
  } catch (error) {
    console.error('CSRFトークンの取得に失敗しました:', error);
    return null;
  }
};

// CSRFトークンを取得してヘッダーに設定
api.interceptors.request.use(async (config) => {
  if (config.url === '/csrf-token') {
    return config;
  }

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
});

/**
 * 認証関連のAPI
 */
export const authApi = {
  login: (data: LoginForm) => api.post('/auth/login', data),
  register: (data: RegisterForm) => api.post('/auth/register', data),
  logout: () => api.post('/auth/logout'),
  getCurrentUser: () => api.get<{ data: User }>('/auth/me'),
  requestPasswordReset: (email: string) => api.post('/auth/password-reset', { email }),
  updateUser: (data: Partial<User>) => api.put<User>('/auth/me', data),
};

/**
 * 植物関連のAPI
 */
export const plantApi = {
  list: () => api.get<Plant[]>('/plants'),
  get: (id: number) => api.get<Plant>(`/plants/${id}`),
  create: (data: Omit<Plant, 'id' | 'userId'>) => api.post<Plant>('/plants', data),
  update: (id: number, data: Partial<Plant>) => api.put<Plant>(`/plants/${id}`, data),
  delete: (id: number) => api.delete(`/plants/${id}`),
};

export default api; 