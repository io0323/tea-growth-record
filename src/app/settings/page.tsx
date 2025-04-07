'use client'

import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { Switch } from "@/components/ui/switch"

export default function SettingsPage() {
  return (
    <div className="container mx-auto py-8">
      <div className="max-w-2xl mx-auto">
        <h2 className="text-3xl font-bold text-gray-900">設定</h2>
        <p className="text-gray-600 mt-2">
          アカウントの設定を管理します
        </p>

        <div className="mt-8 space-y-6">
          <Card>
            <CardHeader>
              <CardTitle>プロフィール</CardTitle>
              <CardDescription>アカウントの基本情報を設定します</CardDescription>
            </CardHeader>
            <CardContent className="space-y-4">
              <div className="space-y-2">
                <Label htmlFor="name">表示名</Label>
                <Input id="name" placeholder="品種好き" />
              </div>
              <div className="space-y-2">
                <Label htmlFor="email">メールアドレス</Label>
                <Input id="email" type="email" placeholder="example@example.com" />
              </div>
            </CardContent>
          </Card>

          <Card>
            <CardHeader>
              <CardTitle>通知設定</CardTitle>
              <CardDescription>アプリケーションからの通知を管理します</CardDescription>
            </CardHeader>
            <CardContent className="space-y-4">
              <div className="flex items-center justify-between">
                <div className="space-y-0.5">
                  <Label>記録通知</Label>
                  <p className="text-sm text-gray-500">品種の記録に関する通知を受け取る</p>
                </div>
                <Switch />
              </div>
              <div className="flex items-center justify-between">
                <div className="space-y-0.5">
                  <Label>リマインダー</Label>
                  <p className="text-sm text-gray-500">定期的な記録をリマインドする</p>
                </div>
                <Switch />
              </div>
            </CardContent>
          </Card>

          <Card>
            <CardHeader>
              <CardTitle>アプリケーション設定</CardTitle>
              <CardDescription>アプリケーションの動作に関する設定</CardDescription>
            </CardHeader>
            <CardContent className="space-y-4">
              <div className="flex items-center justify-between">
                <div className="space-y-0.5">
                  <Label>ダークモード</Label>
                  <p className="text-sm text-gray-500">画面の表示を暗くする</p>
                </div>
                <Switch />
              </div>
            </CardContent>
          </Card>
        </div>

        <div className="mt-8 flex justify-end">
          <Button className="bg-green-600 hover:bg-green-700">
            設定を保存
          </Button>
        </div>
      </div>
    </div>
  )
} 