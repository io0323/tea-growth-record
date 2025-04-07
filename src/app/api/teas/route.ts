import { NextResponse } from "next/server"
import { auth } from "@clerk/nextjs/server"
import { prisma } from "@/lib/prisma"

export async function POST(req: Request) {
  try {
    console.log("POST request received")
    const { userId } = await auth()
    console.log("Current userId:", userId)

    if (!userId) {
      console.error("No userId found in auth")
      return new NextResponse(
        JSON.stringify({ error: '認証が必要です' }),
        { 
          status: 401,
          headers: {
            'Content-Type': 'application/json',
            'WWW-Authenticate': 'Bearer'
          }
        }
      )
    }

    const body = await req.json()
    console.log("Received data:", body)

    const { name, purchaseDate, origin, quantity, notes } = body

    // 必須フィールドのチェック
    if (!name || !purchaseDate || !origin || !quantity) {
      console.error("Missing required fields:", { name, purchaseDate, origin, quantity })
      return new NextResponse(
        JSON.stringify({ error: '必要な情報が不足しています' }),
        { status: 400 }
      )
    }

    // 日付形式の検証
    const dateRegex = /^\d{4}-\d{2}-\d{2}$/
    if (!dateRegex.test(purchaseDate)) {
      console.error("Invalid date format:", purchaseDate)
      return new NextResponse(
        JSON.stringify({ error: '購入日はYYYY-MM-DD形式で入力してください' }),
        { status: 400 }
      )
    }

    // 同じ名前の品種が既に存在するかチェック
    const existingTea = await prisma.tea.findFirst({
      where: {
        name: name,
        userId: userId,
      },
    })

    if (existingTea) {
      console.error("Tea already exists:", existingTea)
      return new NextResponse(
        JSON.stringify({ error: "既に同じ名前の品種が登録されています" }),
        { status: 400 }
      )
    }

    // 日付文字列をDateTimeオブジェクトに変換
    const purchaseDateTime = new Date(purchaseDate)

    console.log("Creating tea with data:", {
      name,
      purchaseDate: purchaseDateTime,
      origin,
      quantity,
      notes,
      userId,
    })

    try {
      const tea = await prisma.tea.create({
        data: {
          name,
          purchaseDate: purchaseDateTime,
          origin,
          quantity,
          notes,
          userId,
        },
      })

      console.log("Tea created successfully:", tea)
      return new NextResponse(JSON.stringify(tea), { status: 201 })
    } catch (prismaError) {
      console.error("Prisma error:", prismaError)
      if (prismaError instanceof Error) {
        console.error("Prisma error details:", {
          message: prismaError.message,
          stack: prismaError.stack,
        })
      }
      throw prismaError
    }
  } catch (error) {
    console.error("Error creating tea:", error)
    if (error instanceof Error) {
      console.error("Error details:", {
        message: error.message,
        stack: error.stack,
      })
    }
    return new NextResponse(
      JSON.stringify({ error: "品種の登録に失敗しました" }),
      { status: 500 }
    )
  }
}

export async function GET(req: Request) {
  try {
    const { userId } = await auth()
    console.log("Current userId:", userId)

    if (!userId) {
      return new NextResponse(
        JSON.stringify({ error: '認証が必要です' }),
        { 
          status: 401,
          headers: {
            'Content-Type': 'application/json',
            'WWW-Authenticate': 'Bearer'
          }
        }
      )
    }

    const teas = await prisma.tea.findMany({
      where: {
        userId: userId,
      },
      orderBy: {
        createdAt: 'desc',
      },
    })

    console.log("Found teas:", teas)

    return new NextResponse(
      JSON.stringify(teas),
      { 
        status: 200, 
        headers: { 
          'Content-Type': 'application/json',
          'Cache-Control': 'no-store'
        } 
      }
    )
  } catch (error) {
    console.error("Error fetching teas:", error)
    return new NextResponse(
      JSON.stringify({ error: '品種の取得に失敗しました' }),
      { 
        status: 500, 
        headers: { 
          'Content-Type': 'application/json',
          'Cache-Control': 'no-store'
        } 
      }
    )
  }
} 