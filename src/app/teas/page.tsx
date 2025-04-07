'use client'

import { useEffect, useState } from 'react'
import { useRouter } from 'next/navigation'
import { toast } from 'sonner'
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { Button } from "@/components/ui/button"
import { format } from 'date-fns'
import { ja } from 'date-fns/locale'

interface Tea {
  id: string
  name: string
  purchaseDate: string
  origin: string
  quantity: string
  notes: string | null
  createdAt: string
}

export default function TeasPage() {
  const router = useRouter()
  const [teas, setTeas] = useState<Tea[]>([])
  const [isLoading, setIsLoading] = useState(true)
  const [searchQuery, setSearchQuery] = useState('')

  useEffect(() => {
    fetchTeas()
  }, [])

  const fetchTeas = async () => {
    try {
      const response = await fetch('/api/teas')
      if (!response.ok) {
        throw new Error('データの取得に失敗しました')
      }
      const data = await response.json()
      setTeas(data)
    } catch (error) {
      console.error('Error fetching teas:', error)
      toast.error('品種の一覧を取得できませんでした')
    } finally {
      setIsLoading(false)
    }
  }

  const formatDate = (dateString: string) => {
    return format(new Date(dateString), 'yyyy年MM月dd日', { locale: ja })
  }

  const filteredTeas = teas.filter(tea =>
    tea.name.toLowerCase().includes(searchQuery.toLowerCase()) ||
    tea.origin.toLowerCase().includes(searchQuery.toLowerCase())
  )

  return (
    <div className="container mx-auto py-8">
      <div className="max-w-4xl mx-auto">
        <div className="flex justify-between items-center mb-8">
          <div>
            <h2 className="text-3xl font-bold text-gray-900">品種の記録一覧</h2>
            <p className="text-gray-600 mt-2">
              登録された品種の記録を表示します
            </p>
          </div>
          <Button onClick={() => router.push('/register')}>
            新規登録
          </Button>
        </div>

        <div className="mb-6">
          <Label htmlFor="search">検索</Label>
          <Input
            id="search"
            type="text"
            placeholder="品種名や産地で検索..."
            value={searchQuery}
            onChange={(e) => setSearchQuery(e.target.value)}
            className="mt-1"
          />
        </div>

        {isLoading ? (
          <div className="text-center py-12">
            <p className="text-gray-500">読み込み中...</p>
          </div>
        ) : filteredTeas.length === 0 ? (
          <div className="text-center py-12">
            <p className="text-gray-500">登録された品種の記録がありません</p>
          </div>
        ) : (
          <div className="grid gap-6">
            {filteredTeas.map((tea) => (
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
    </div>
  )
} 