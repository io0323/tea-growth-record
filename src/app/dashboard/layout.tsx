'use client'

import { Sidebar } from "@/components/ui/sidebar"
import { UserButton } from "@clerk/nextjs"

export default function DashboardLayout({
  children,
}: {
  children: React.ReactNode
}) {
  return (
    <div className="h-full relative">
      <div className="hidden h-full md:flex md:w-72 md:flex-col md:fixed md:inset-y-0 z-[80] bg-gray-900">
        <Sidebar />
      </div>
      <main className="md:pl-72">
        <div className="flex items-center justify-end p-4 border-b">
          <UserButton afterSignOutUrl="/" />
        </div>
        {children}
      </main>
    </div>
  )
} 