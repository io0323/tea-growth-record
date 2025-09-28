import React from "react";
import { render, screen, fireEvent, waitFor } from "@testing-library/react";
import { BrowserRouter } from "react-router-dom";
import { DashboardPage } from "../DashboardPage";
import { teaApi } from "../../api/teaApi";
import { AuthProvider } from "../../contexts/AuthContext";

// APIのモック
jest.mock("../../api/teaApi");

describe("DashboardPage", () => {
  const mockUser = {
    id: "1",
    name: "テストユーザー",
    email: "test@example.com",
  };

  const mockTeas = [
    {
      id: "1",
      name: "龍井茶",
      type: "緑茶",
      origin: "中国",
      purchaseDate: "2024-03-01",
      status: "在庫あり",
      description: "中国浙江省産の高級緑茶",
      price: 5000,
      quantity: 100,
      unit: "g",
    },
    {
      id: "2",
      name: "アッサム",
      type: "紅茶",
      origin: "インド",
      purchaseDate: "2024-03-15",
      status: "在庫あり",
      description: "インド産の紅茶",
      price: 3000,
      quantity: 200,
      unit: "g",
    },
  ];

  beforeEach(() => {
    (teaApi.getTeas as jest.Mock).mockResolvedValue(mockTeas);
  });

  afterEach(() => {
    jest.clearAllMocks();
  });

  it("ダッシュボードが正しく表示される", async () => {
    render(
      <BrowserRouter>
        <AuthProvider>
          <DashboardPage />
        </AuthProvider>
      </BrowserRouter>
    );

    // ローディング中は表示されない
    expect(screen.queryByText("テストユーザーさん、ようこそ")).not.toBeInTheDocument();

    // データが読み込まれたら表示される
    await waitFor(() => {
      expect(screen.getByText("テストユーザーさん、ようこそ")).toBeInTheDocument();
      expect(screen.getByText("お茶の新規登録")).toBeInTheDocument();
      expect(screen.getByText("お茶一覧")).toBeInTheDocument();
      expect(screen.getByText("登録済みのお茶")).toBeInTheDocument();
      expect(screen.getByText("今月の記録")).toBeInTheDocument();
      expect(screen.getByText("最近の記録")).toBeInTheDocument();
    });
  });

  it("お茶の新規登録ボタンが正しく動作する", async () => {
    render(
      <BrowserRouter>
        <AuthProvider>
          <DashboardPage />
        </AuthProvider>
      </BrowserRouter>
    );

    // データが読み込まれるのを待つ
    await waitFor(() => {
      expect(screen.getByText("お茶の新規登録")).toBeInTheDocument();
    });

    // 新規登録ボタンをクリック
    const addButton = screen.getByText("お茶の新規登録");
    fireEvent.click(addButton);

    // URLが変更されることを確認
    expect(window.location.pathname).toBe("/teas/new");
  });

  it("お茶一覧ボタンが正しく動作する", async () => {
    render(
      <BrowserRouter>
        <AuthProvider>
          <DashboardPage />
        </AuthProvider>
      </BrowserRouter>
    );

    // データが読み込まれるのを待つ
    await waitFor(() => {
      expect(screen.getByText("お茶一覧")).toBeInTheDocument();
    });

    // 一覧ボタンをクリック
    const listButton = screen.getByText("お茶一覧");
    fireEvent.click(listButton);

    // URLが変更されることを確認
    expect(window.location.pathname).toBe("/teas");
  });

  it("お茶の統計情報が正しく表示される", async () => {
    render(
      <BrowserRouter>
        <AuthProvider>
          <DashboardPage />
        </AuthProvider>
      </BrowserRouter>
    );

    // データが読み込まれるのを待つ
    await waitFor(() => {
      expect(screen.getByText("登録済みのお茶")).toBeInTheDocument();
    });

    // 統計情報が表示される
    expect(screen.getByText("2")).toBeInTheDocument(); // 登録済みのお茶の数
  });

  it("最近の記録が正しく表示される", async () => {
    render(
      <BrowserRouter>
        <AuthProvider>
          <DashboardPage />
        </AuthProvider>
      </BrowserRouter>
    );

    // データが読み込まれるのを待つ
    await waitFor(() => {
      expect(screen.getByText("最近の記録")).toBeInTheDocument();
    });

    // 記録がない場合のメッセージが表示される
    expect(screen.getByText("記録がありません")).toBeInTheDocument();
  });

  it("APIエラーが表示される", async () => {
    // APIエラーのモック
    (teaApi.getTeas as jest.Mock).mockRejectedValue(new Error("データの取得に失敗しました"));

    render(
      <BrowserRouter>
        <AuthProvider>
          <DashboardPage />
        </AuthProvider>
      </BrowserRouter>
    );

    // エラーメッセージが表示される
    await waitFor(() => {
      expect(screen.getByText("データの取得に失敗しました")).toBeInTheDocument();
    });
  });
}); 