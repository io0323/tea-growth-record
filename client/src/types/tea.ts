/**
 * お茶の型定義
 */
export interface Tea {
  id?: number;
  name: string;
  type: string;
  origin: string;
  purchaseDate: string;
  status: string;
  description?: string;
  price?: number;
  quantity?: number;
  unit?: string;
  imageUrl?: string;
  createdAt?: string;
  updatedAt?: string;
}

/**
 * お茶作成リクエストの型定義
 */
export interface CreateTeaRequest {
  name: string;
  type: string;
  origin: string;
  purchaseDate: string;
  status: string;
  description?: string;
  price?: number;
  quantity?: number;
  unit?: string;
}

/**
 * お茶更新リクエストの型定義
 */
export interface UpdateTeaRequest extends CreateTeaRequest {
  id: number;
} 