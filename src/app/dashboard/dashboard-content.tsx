'use client'

import { useUser, useAuth } from "@clerk/nextjs";
import { PlusCircle, Coffee, Settings, History } from "lucide-react";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card"
import Link from "next/link"
import { useEffect, useState } from "react"
import { format } from 'date-fns'
import { ja } from 'date-fns/locale'
import { toast } from "sonner"
import { Button } from "@/components/ui/button";
import { SignOutButton } from "@clerk/nextjs";
import { LogOut } from "lucide-react";

interface Tea {
  id: string
  name: string
  purchaseDate: string
  origin: string
  quantity: string
  notes: string | null
  createdAt: string
}

export default function DashboardContent() {
  const { user } = useUser();
  const { getToken } = useAuth();
  const [recentTeas, setRecentTeas] = useState<Tea[]>([])
  const [isLoading, setIsLoading] = useState(true)

  useEffect(() => {
    fetchRecentTeas()
  }, [])

  const fetchRecentTeas = async () => {
    try {
      const token = await getToken();
      if (!token) {
        throw new Error('認証が必要です')
      }

      const response = await fetch('/api/teas', {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`,
        },
      })
      if (!response.ok) {
        const errorData = await response.json()
        throw new Error(errorData.error || 'データの取得に失敗しました')
      }
      const data = await response.json()
      setRecentTeas(data.slice(0, 3))
    } catch (error) {
      console.error('Error fetching teas:', error)
      if (error instanceof Error) {
        toast.error(error.message)
      } else {
        toast.error('品種の一覧を取得できませんでした')
      }
    } finally {
      setIsLoading(false)
    }
  }

  const formatDate = (dateString: string) => {
    return format(new Date(dateString), 'yyyy年MM月dd日', { locale: ja })
  }

  return (
    <div className="min-h-screen bg-gray-50">
      {/* ヘッダー */}
      <header className="bg-white shadow-sm">
        <div className="max-w-7xl mx-auto px-4 py-4 sm:px-6 lg:px-8 flex justify-between items-center">
          <h1 className="text-2xl font-bold text-gray-900">Tea Growth Record</h1>
          <div className="flex items-center gap-4">
            <span className="text-sm text-gray-600">
              {user?.firstName || 'ゲスト'}さん
            </span>
            <SignOutButton>
              <Button variant="outline" size="sm">
                <LogOut className="mr-2 h-4 w-4" />
                ログアウト
              </Button>
            </SignOutButton>
          </div>
        </div>
      </header>

      {/* メインコンテンツ */}
      <main className="max-w-7xl mx-auto px-4 py-8 sm:px-6 lg:px-8">
        {/* アクションカード */}
        <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
          <Link href="/register" className="block">
            <div className="bg-white p-6 rounded-lg shadow-sm hover:shadow-md transition-shadow flex items-center gap-4">
              <PlusCircle className="w-8 h-8 text-green-500" />
              <div className="text-left">
                <h2 className="font-semibold text-gray-900">新規記録</h2>
                <p className="text-sm text-gray-600">品種の記録を追加</p>
              </div>
            </div>
          </Link>

          <Link href="/teas" className="block">
            <div className="bg-white p-6 rounded-lg shadow-sm hover:shadow-md transition-shadow flex items-center gap-4">
              <Coffee className="w-8 h-8 text-amber-600" />
              <div className="text-left">
                <h2 className="font-semibold text-gray-900">品種一覧</h2>
                <p className="text-sm text-gray-600">登録した品種を確認</p>
              </div>
            </div>
          </Link>

          <Link href="/settings" className="block">
            <div className="bg-white p-6 rounded-lg shadow-sm hover:shadow-md transition-shadow flex items-center gap-4">
              <Settings className="w-8 h-8 text-gray-600" />
              <div className="text-left">
                <h2 className="font-semibold text-gray-900">設定</h2>
                <p className="text-sm text-gray-600">アカウント設定</p>
              </div>
            </div>
          </Link>
        </div>

        {/* 最近の記録 */}
        <div className="bg-white rounded-lg shadow-sm p-6">
          <div className="flex items-center justify-between mb-6">
            <h2 className="text-xl font-semibold text-gray-900">最近の記録</h2>
            <Link href="/teas" className="text-sm text-gray-600 hover:text-gray-900 flex items-center gap-2">
              <History className="w-4 h-4" />
              すべて表示
            </Link>
          </div>

          {isLoading ? (
            <div className="text-center py-12">
              <p className="text-gray-500">読み込み中...</p>
            </div>
          ) : recentTeas.length === 0 ? (
            <div className="text-center py-12">
              <Coffee className="w-12 h-12 text-gray-400 mx-auto mb-4" />
              <p className="text-gray-600 mb-2">まだ記録がありません</p>
              <p className="text-sm text-gray-500">
                「新規記録」から最初の品種を記録してみましょう
              </p>
            </div>
          ) : (
            <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
              {recentTeas.map((tea) => (
                <Card key={tea.id} className="hover:shadow-lg transition-shadow">
                  <CardHeader>
                    <CardTitle>{tea.name}</CardTitle>
                    <CardDescription>
                      購入日: {formatDate(tea.purchaseDate)}
                    </CardDescription>
                  </CardHeader>
                  <CardContent>
                    <div className="space-y-2">
                      <div>
                        <span className="font-medium">産地:</span> {tea.origin}
                      </div>
                      <div>
                        <span className="font-medium">数量:</span> {tea.quantity}
                      </div>
                      {tea.notes && (
                        <div>
                          <span className="font-medium">メモ:</span> {tea.notes}
                        </div>
                      )}
                      <div className="text-sm text-gray-500">
                        登録日: {formatDate(tea.createdAt)}
                      </div>
                    </div>
                  </CardContent>
                </Card>
              ))}
            </div>
          )}
        </div>
      </main>
    </div>
  )
} 