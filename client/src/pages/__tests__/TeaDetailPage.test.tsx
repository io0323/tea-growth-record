import React from "react";
import { render, screen, fireEvent, waitFor } from "@testing-library/react";
import { BrowserRouter } from "react-router-dom";
import { TeaDetailPage } from "../TeaDetailPage";
import { teaApi } from "../../api/teaApi";

// APIのモック
jest.mock("../../api/teaApi");

describe("TeaDetailPage", () => {
  const mockTea = {
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
  };

  beforeEach(() => {
    (teaApi.getTea as jest.Mock).mockResolvedValue(mockTea);
  });

  afterEach(() => {
    jest.clearAllMocks();
  });

  it("お茶の詳細が正しく表示される", async () => {
    render(
      <BrowserRouter>
        <TeaDetailPage />
      </BrowserRouter>
    );

    // ローディング中は表示されない
    expect(screen.queryByText("龍井茶")).not.toBeInTheDocument();

    // データが読み込まれたら表示される
    await waitFor(() => {
      expect(screen.getByText("龍井茶")).toBeInTheDocument();
      expect(screen.getByText("緑茶")).toBeInTheDocument();
      expect(screen.getByText("中国")).toBeInTheDocument();
      expect(screen.getByText("在庫あり")).toBeInTheDocument();
      expect(screen.getByText("中国浙江省産の高級緑茶")).toBeInTheDocument();
      expect(screen.getByText("¥5,000")).toBeInTheDocument();
      expect(screen.getByText("100 g")).toBeInTheDocument();
    });
  });

  it("編集ボタンが正しく動作する", async () => {
    render(
      <BrowserRouter>
        <TeaDetailPage />
      </BrowserRouter>
    );

    // データが読み込まれるのを待つ
    await waitFor(() => {
      expect(screen.getByText("龍井茶")).toBeInTheDocument();
    });

    // 編集ボタンをクリック
    const editButton = screen.getByText("編集");
    fireEvent.click(editButton);

    // URLが変更されることを確認
    expect(window.location.pathname).toBe("/teas/1/edit");
  });

  it("削除ボタンが正しく動作する", async () => {
    // window.confirmのモック
    const mockConfirm = jest.fn(() => true);
    window.confirm = mockConfirm;

    (teaApi.deleteTea as jest.Mock).mockResolvedValue(undefined);

    render(
      <BrowserRouter>
        <TeaDetailPage />
      </BrowserRouter>
    );

    // データが読み込まれるのを待つ
    await waitFor(() => {
      expect(screen.getByText("龍井茶")).toBeInTheDocument();
    });

    // 削除ボタンをクリック
    const deleteButton = screen.getByText("削除");
    fireEvent.click(deleteButton);

    // 確認ダイアログが表示される
    expect(mockConfirm).toHaveBeenCalledWith("このお茶を削除してもよろしいですか？");

    // APIが呼び出される
    await waitFor(() => {
      expect(teaApi.deleteTea).toHaveBeenCalledWith("1");
    });

    // URLが変更されることを確認
    expect(window.location.pathname).toBe("/teas");
  });

  it("削除がキャンセルされた場合、APIは呼び出されない", async () => {
    // window.confirmのモック
    const mockConfirm = jest.fn(() => false);
    window.confirm = mockConfirm;

    render(
      <BrowserRouter>
        <TeaDetailPage />
      </BrowserRouter>
    );

    // データが読み込まれるのを待つ
    await waitFor(() => {
      expect(screen.getByText("龍井茶")).toBeInTheDocument();
    });

    // 削除ボタンをクリック
    const deleteButton = screen.getByText("削除");
    fireEvent.click(deleteButton);

    // 確認ダイアログが表示される
    expect(mockConfirm).toHaveBeenCalledWith("このお茶を削除してもよろしいですか？");

    // APIが呼び出されないことを確認
    expect(teaApi.deleteTea).not.toHaveBeenCalled();
  });

  it("お茶が見つからない場合、メッセージが表示される", async () => {
    // APIエラーのモック
    (teaApi.getTea as jest.Mock).mockRejectedValue(new Error("お茶が見つかりません"));

    render(
      <BrowserRouter>
        <TeaDetailPage />
      </BrowserRouter>
    );

    // エラーメッセージが表示される
    await waitFor(() => {
      expect(screen.getByText("お茶が見つかりません")).toBeInTheDocument();
    });
  });

  it("お茶一覧に戻るボタンが正しく動作する", async () => {
    render(
      <BrowserRouter>
        <TeaDetailPage />
      </BrowserRouter>
    );

    // データが読み込まれるのを待つ
    await waitFor(() => {
      expect(screen.getByText("お茶一覧に戻る")).toBeInTheDocument();
    });

    // お茶一覧に戻るボタンをクリック
    const backButton = screen.getByText("お茶一覧に戻る");
    fireEvent.click(backButton);

    // URLが変更されることを確認
    expect(window.location.pathname).toBe("/teas");
  });
}); 