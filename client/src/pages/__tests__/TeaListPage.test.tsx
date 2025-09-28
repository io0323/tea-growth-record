import React from "react";
import { render, screen, fireEvent, waitFor } from "@testing-library/react";
import { BrowserRouter } from "react-router-dom";
import { TeaListPage } from "../TeaListPage";
import { teaApi } from "../../api/teaApi";

// APIのモック
jest.mock("../../api/teaApi");

describe("TeaListPage", () => {
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

  it("お茶一覧が正しく表示される", async () => {
    render(
      <BrowserRouter>
        <TeaListPage />
      </BrowserRouter>
    );

    // ローディング中は表示されない
    expect(screen.queryByText("龍井茶")).not.toBeInTheDocument();

    // データが読み込まれたら表示される
    await waitFor(() => {
      expect(screen.getByText("龍井茶")).toBeInTheDocument();
      expect(screen.getByText("アッサム")).toBeInTheDocument();
    });
  });

  it("検索機能が正しく動作する", async () => {
    render(
      <BrowserRouter>
        <TeaListPage />
      </BrowserRouter>
    );

    // データが読み込まれるのを待つ
    await waitFor(() => {
      expect(screen.getByText("龍井茶")).toBeInTheDocument();
    });

    // 検索ボックスに入力
    const searchInput = screen.getByLabelText("お茶を検索...");
    fireEvent.change(searchInput, { target: { value: "龍井" } });

    // 検索結果が表示される
    expect(screen.getByText("龍井茶")).toBeInTheDocument();
    expect(screen.queryByText("アッサム")).not.toBeInTheDocument();
  });

  it("新規登録ボタンが正しく動作する", async () => {
    render(
      <BrowserRouter>
        <TeaListPage />
      </BrowserRouter>
    );

    // データが読み込まれるのを待つ
    await waitFor(() => {
      expect(screen.getByText("新規登録")).toBeInTheDocument();
    });

    // 新規登録ボタンをクリック
    const addButton = screen.getByText("新規登録");
    fireEvent.click(addButton);

    // URLが変更されることを確認
    expect(window.location.pathname).toBe("/teas/new");
  });

  it("お茶カードをクリックすると詳細ページに遷移する", async () => {
    render(
      <BrowserRouter>
        <TeaListPage />
      </BrowserRouter>
    );

    // データが読み込まれるのを待つ
    await waitFor(() => {
      expect(screen.getByText("龍井茶")).toBeInTheDocument();
    });

    // お茶カードをクリック
    const teaCard = screen.getByText("龍井茶").closest("div");
    fireEvent.click(teaCard!);

    // URLが変更されることを確認
    expect(window.location.pathname).toBe("/teas/1");
  });

  it("検索結果が0件の場合、メッセージが表示される", async () => {
    render(
      <BrowserRouter>
        <TeaListPage />
      </BrowserRouter>
    );

    // データが読み込まれるのを待つ
    await waitFor(() => {
      expect(screen.getByText("龍井茶")).toBeInTheDocument();
    });

    // 検索ボックスに入力（存在しないお茶）
    const searchInput = screen.getByLabelText("お茶を検索...");
    fireEvent.change(searchInput, { target: { value: "存在しないお茶" } });

    // メッセージが表示される
    expect(screen.getByText("お茶が見つかりません")).toBeInTheDocument();
  });

  it("APIエラーが表示される", async () => {
    // APIエラーのモック
    (teaApi.getTeas as jest.Mock).mockRejectedValue(new Error("データの取得に失敗しました"));

    render(
      <BrowserRouter>
        <TeaListPage />
      </BrowserRouter>
    );

    // エラーメッセージが表示される
    await waitFor(() => {
      expect(screen.getByText("データの取得に失敗しました")).toBeInTheDocument();
    });
  });
}); 