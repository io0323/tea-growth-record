# お茶の成長記録アプリ

お茶の品種や成長記録を管理するためのWebアプリケーションです。

## 機能

- お茶の品種の登録・管理
- 購入日、産地、数量などの基本情報の記録
- メモ機能による詳細情報の記録
- ユーザー認証によるセキュアなデータ管理

## 技術スタック

- **フロントエンド**
  - Next.js 14.2.25
  - React 18.2.0
  - TypeScript 5.2.2
  - Tailwind CSS
  - Shadcn/ui

- **バックエンド**
  - Prisma ORM
  - SQLite

- **認証**
  - Clerk

## セットアップ

1. リポジトリのクローン
```bash
git clone https://github.com/io0323/tea-growth-record.git
cd tea-growth-record
```

2. 依存関係のインストール
```bash
npm install
```

3. 環境変数の設定
`.env`ファイルを作成し、以下の環境変数を設定：
```env
NEXT_PUBLIC_CLERK_PUBLISHABLE_KEY=your_publishable_key
CLERK_SECRET_KEY=your_secret_key
```

4. データベースのセットアップ
```bash
npx prisma generate
npx prisma db push
```

5. 開発サーバーの起動
```bash
npm run dev
```

## 使用方法

1. アカウントを作成またはログイン
2. サイドメニューから「新規登録」を選択
3. お茶の品種情報を入力
4. 登録完了後、一覧画面で確認可能

## ライセンス

MIT 