'use client'

import { Sidebar } from "@/components/ui/sidebar"
import { UserButton } from "@clerk/nextjs"

export default function SettingsLayout({
  children,
}: {
  children: React.ReactNode
}) {
  return (
    <div className="flex h-screen">
      <Sidebar />
      <main className="flex-1 overflow-y-auto">
        <div className="flex justify-end p-4">
          <UserButton afterSignOutUrl="/" />
        </div>
        {children}
      </main>
    </div>
  )
} 