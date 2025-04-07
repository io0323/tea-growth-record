/**
 * ユーザー情報の型定義
 */
export interface User {
  id: number;
  username: string;
  email: string;
  createdAt: string;
  updatedAt: string;
}

/**
 * 植物情報の型定義
 */
export interface Plant {
  id: number;
  userId: number;
  name: string;
  species: string;
  plantedDate: string;
  lastWateredDate: string;
  notes?: string;
  createdAt: string;
  updatedAt: string;
}

/**
 * ログインフォームの型定義
 */
export interface LoginForm {
  username: string;
  password: string;
}

/**
 * 登録フォームの型定義
 */
export interface RegisterForm {
  username: string;
  email: string;
  password: string;
  confirmPassword: string;
} 