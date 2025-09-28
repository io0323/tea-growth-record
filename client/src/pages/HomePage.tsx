import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Container, Typography, Box } from '@mui/material';
import { useAuth } from '../contexts/AuthContext';
import { useLoading } from '../contexts/LoadingContext';
import { useSnackbar } from '../contexts/SnackbarContext';
import { Tea } from '../types/tea';
import { api } from '../api';

/**
 * ホームページコンポーネント
 */
export const HomePage = () => {
  const [teas, setTeas] = useState<Tea[]>([]);
  const { user } = useAuth();
  const navigate = useNavigate();
  const { showLoading, hideLoading } = useLoading();
  const { showSnackbar } = useSnackbar();

  useEffect(() => {
    const fetchTeas = async () => {
      try {
        showLoading();
        const response = await api.get('/api/teas');
        setTeas(response.data);
      } catch (error) {
        console.error('お茶の取得に失敗しました:', error);
        showSnackbar('お茶の取得に失敗しました', 'error');
      } finally {
        hideLoading();
      }
    };

    fetchTeas();
  }, [showLoading, hideLoading, showSnackbar]);

  if (!user) {
    return (
      <div className="container mx-auto px-4 py-8">
        <h1 className="text-3xl font-bold mb-6">お茶の成長記録へようこそ</h1>
        <p className="text-lg mb-4">
          お茶の成長を記録し、管理するためのアプリケーションです。
          ログインして始めましょう。
        </p>
        <button
          onClick={() => navigate('/login')}
          className="bg-green-600 text-white px-6 py-2 rounded-lg hover:bg-green-700 transition-colors"
        >
          ログイン
        </button>
      </div>
    );
  }

  return (
    <div className="container mx-auto px-4 py-8">
      <h1 className="text-3xl font-bold mb-6">お茶の成長記録</h1>
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        {teas.map((tea) => (
          <div
            key={tea.id}
            className="bg-white rounded-lg shadow-md p-6 hover:shadow-lg transition-shadow"
          >
            <h2 className="text-xl font-semibold mb-2">{tea.name}</h2>
            <p className="text-gray-600 mb-4">{tea.description}</p>
            <div className="flex justify-between items-center">
              <span className="text-sm text-gray-500">
                作成日: {new Date(tea.createdAt).toLocaleDateString()}
              </span>
              <button
                onClick={() => navigate(`/teas/${tea.id}`)}
                className="text-green-600 hover:text-green-700"
              >
                詳細を見る
              </button>
            </div>
          </div>
        ))}
      </div>
      <button
        onClick={() => navigate('/teas/new')}
        className="mt-6 bg-green-600 text-white px-6 py-2 rounded-lg hover:bg-green-700 transition-colors"
      >
        新しいお茶を追加
      </button>
    </div>
  );
}; 