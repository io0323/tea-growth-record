'use client'

import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { Textarea } from "@/components/ui/textarea"
import { useState } from "react"
import { useRouter } from "next/navigation"
import { toast } from "sonner"
import { useAuth } from "@clerk/nextjs"
import DashboardLayout from "@/app/dashboard/layout"

export default function RegisterPage() {
  const router = useRouter()
  const [isSubmitting, setIsSubmitting] = useState(false)
  const { getToken } = useAuth()

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault()
    setIsSubmitting(true)

    const formData = new FormData(e.currentTarget)
    const purchaseDate = formData.get('purchaseDate') as string
    const formattedDate = purchaseDate.replace(/\//g, '-') // スラッシュをハイフンに変換

    const data = {
      name: formData.get('name'),
      purchaseDate: formattedDate,
      origin: formData.get('origin'),
      quantity: formData.get('quantity'),
      notes: formData.get('notes'),
    }

    try {
      const token = await getToken()
      if (!token) {
        throw new Error('認証が必要です')
      }

      const response = await fetch('/api/teas', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`,
        },
        body: JSON.stringify(data),
      })

      if (!response.ok) {
        const errorData = await response.json()
        throw new Error(errorData.error || 'データの保存に失敗しました')
      }

      toast.success('品種の記録を登録しました')
      router.push('/teas')
    } catch (error) {
      console.error('Error:', error)
      toast.error(error instanceof Error ? error.message : 'データの保存に失敗しました')
    } finally {
      setIsSubmitting(false)
    }
  }

  return (
    <DashboardLayout>
      <div className="container mx-auto py-8">
        <div className="max-w-2xl mx-auto">
          <h2 className="text-3xl font-bold text-gray-900">品種の新規登録</h2>
          <p className="text-gray-600 mt-2">
            新しい品種の情報を登録します
          </p>

          <form onSubmit={handleSubmit} className="mt-8">
            <Card>
              <CardHeader>
                <CardTitle>品種の情報</CardTitle>
                <CardDescription>品種の基本情報を入力してください</CardDescription>
              </CardHeader>
              <CardContent className="space-y-4">
                <div className="space-y-2">
                  <Label htmlFor="name">品種の名前</Label>
                  <Input
                    id="name"
                    name="name"
                    required
                    disabled={isSubmitting}
                  />
                </div>

                <div className="space-y-2">
                  <Label htmlFor="purchaseDate">購入日</Label>
                  <Input
                    id="purchaseDate"
                    name="purchaseDate"
                    type="date"
                    required
                    disabled={isSubmitting}
                  />
                </div>

                <div className="space-y-2">
                  <Label htmlFor="origin">産地</Label>
                  <Input
                    id="origin"
                    name="origin"
                    required
                    disabled={isSubmitting}
                  />
                </div>

                <div className="space-y-2">
                  <Label htmlFor="quantity">数量（g）</Label>
                  <Input
                    id="quantity"
                    name="quantity"
                    type="number"
                    required
                    disabled={isSubmitting}
                  />
                </div>

                <div className="space-y-2">
                  <Label htmlFor="notes">メモ</Label>
                  <Textarea
                    id="notes"
                    name="notes"
                    disabled={isSubmitting}
                  />
                </div>

                <div className="flex justify-end">
                  <Button type="submit" disabled={isSubmitting}>
                    {isSubmitting ? '登録中...' : '登録する'}
                  </Button>
                </div>
              </CardContent>
            </Card>
          </form>
        </div>
      </div>
    </DashboardLayout>
  )
} 