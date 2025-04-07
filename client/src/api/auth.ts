import axios from 'axios';
import { User } from '../types';
import { generatePasswordResetEmail } from '../utils/emailUtils';

const API_URL = import.meta.env.VITE_API_URL || 'http://localhost:9000';
const FRONTEND_URL = import.meta.env.VITE_FRONTEND_URL || 'http://localhost:5173';

interface EmailTemplate {
  subject: string;
  body: string;
  variables: string[];
}

interface EmailTemplateVersion extends EmailTemplate {
  id: string;
  version: number;
  createdAt: string;
  createdBy: string;
  isActive: boolean;
}

/**
 * 認証関連のAPIクライアント
 */
export const authApi = {
  /**
   * ログイン
   */
  login: async (username: string, password: string): Promise<User> => {
    const response = await axios.post(`${API_URL}/auth/login`, {
      username,
      password,
    });
    return response.data;
  },

  /**
   * ユーザー登録
   */
  register: async (username: string, email: string, password: string): Promise<User> => {
    const response = await axios.post(`${API_URL}/auth/register`, {
      username,
      email,
      password,
    });
    return response.data;
  },

  /**
   * ログアウト
   */
  logout: async (): Promise<void> => {
    await axios.post(`${API_URL}/auth/logout`);
  },

  /**
   * 現在のユーザー情報を取得
   */
  getCurrentUser: async (): Promise<User> => {
    const response = await axios.get(`${API_URL}/auth/me`);
    return response.data;
  },

  /**
   * ユーザー情報を更新
   */
  updateUser: async (userData: Partial<User>): Promise<User> => {
    const response = await axios.put(`${API_URL}/auth/me`, userData);
    return response.data;
  },

  /**
   * パスワードを更新
   */
  updatePassword: async (currentPassword: string, newPassword: string): Promise<void> => {
    await axios.put(`${API_URL}/auth/me/password`, {
      currentPassword,
      newPassword,
    });
  },

  /**
   * メールテンプレートを更新
   */
  updateEmailTemplate: async (
    templateType: 'password-reset' | 'welcome' | 'notification',
    template: EmailTemplate
  ): Promise<void> => {
    await axios.put(`${API_URL}/auth/email-templates/${templateType}`, template);
  },

  /**
   * メールテンプレートを取得
   */
  getEmailTemplate: async (
    templateType: 'password-reset' | 'welcome' | 'notification'
  ): Promise<EmailTemplate> => {
    const response = await axios.get(`${API_URL}/auth/email-templates/${templateType}`);
    return response.data;
  },

  /**
   * パスワードリセット用のメールを送信
   */
  requestPasswordReset: async (email: string): Promise<void> => {
    const resetToken = Math.random().toString(36).substring(2, 15);
    const resetUrl = `${FRONTEND_URL}/password-reset?token=${resetToken}&email=${encodeURIComponent(email)}`;
    
    // カスタムテンプレートを取得
    try {
      const template = await authApi.getEmailTemplate('password-reset');
      const emailHtml = generatePasswordResetEmail(
        'ユーザー',
        resetToken,
        resetUrl,
        template
      );

      await axios.post(`${API_URL}/auth/password-reset/request`, {
        email,
        resetToken,
        emailHtml,
      });
    } catch (error) {
      // カスタムテンプレートの取得に失敗した場合はデフォルトテンプレートを使用
      const emailHtml = generatePasswordResetEmail('ユーザー', resetToken, resetUrl);
      await axios.post(`${API_URL}/auth/password-reset/request`, {
        email,
        resetToken,
        emailHtml,
      });
    }
  },

  /**
   * リセットトークンを検証
   */
  verifyResetToken: async (email: string, token: string): Promise<void> => {
    await axios.post(`${API_URL}/auth/password-reset/verify`, { email, token });
  },

  /**
   * パスワードをリセット
   */
  resetPassword: async (email: string, token: string, newPassword: string): Promise<void> => {
    await axios.post(`${API_URL}/auth/password-reset/reset`, {
      email,
      token,
      newPassword,
    });
  },
}; 