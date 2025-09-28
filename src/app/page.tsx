'use client'

import { SignInButton, SignUpButton, SignedIn, SignedOut } from "@clerk/nextjs"
import { useRouter } from "next/navigation"

export default function Home() {
  const router = useRouter()

  return (
    <main className="flex min-h-screen flex-col items-center justify-between p-24">
      <div className="z-10 max-w-5xl w-full items-center justify-between font-mono text-sm">
        <h1 className="text-4xl font-bold mb-8">Tea Growth Record</h1>
        <p className="text-xl mb-4">品種の成長記録を管理するアプリケーション</p>
        
        <SignedOut>
          <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
            <SignInButton mode="modal">
              <button className="w-full p-4 border rounded-lg hover:bg-gray-100">
                ログイン
              </button>
            </SignInButton>
            <SignUpButton mode="modal">
              <button className="w-full p-4 border rounded-lg hover:bg-gray-100">
                新規登録
              </button>
            </SignUpButton>
          </div>
        </SignedOut>

        <SignedIn>
          <div className="text-center">
            <p className="text-xl mb-4">すでにログインしています</p>
            <button
              onClick={() => router.push('/dashboard')}
              className="w-full p-4 bg-green-600 text-white rounded-lg hover:bg-green-700"
            >
              ダッシュボードへ移動
            </button>
          </div>
        </SignedIn>
      </div>
    </main>
  )
} 