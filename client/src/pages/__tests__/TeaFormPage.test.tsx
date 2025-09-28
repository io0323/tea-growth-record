import React from "react";
import { render, screen, fireEvent, waitFor } from "@testing-library/react";
import { BrowserRouter } from "react-router-dom";
import { TeaFormPage } from "../TeaFormPage";
import { teaApi } from "../../api/teaApi";

// APIのモック
jest.mock("../../api/teaApi");

describe("TeaFormPage", () => {
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
    (teaApi.createTea as jest.Mock).mockResolvedValue(mockTea);
    (teaApi.updateTea as jest.Mock).mockResolvedValue(mockTea);
    (teaApi.getTea as jest.Mock).mockResolvedValue(mockTea);
  });

  afterEach(() => {
    jest.clearAllMocks();
  });

  it("新規登録フォームが正しく表示される", () => {
    render(
      <BrowserRouter>
        <TeaFormPage />
      </BrowserRouter>
    );

    expect(screen.getByText("お茶の新規登録")).toBeInTheDocument();
    expect(screen.getByLabelText("お茶の名前")).toBeInTheDocument();
    expect(screen.getByLabelText("種類")).toBeInTheDocument();
    expect(screen.getByLabelText("産地")).toBeInTheDocument();
    expect(screen.getByLabelText("購入日")).toBeInTheDocument();
    expect(screen.getByLabelText("状態")).toBeInTheDocument();
    expect(screen.getByLabelText("説明")).toBeInTheDocument();
    expect(screen.getByLabelText("価格")).toBeInTheDocument();
    expect(screen.getByLabelText("数量")).toBeInTheDocument();
    expect(screen.getByLabelText("単位")).toBeInTheDocument();
  });

  it("編集フォームが正しく表示される", async () => {
    render(
      <BrowserRouter>
        <TeaFormPage />
      </BrowserRouter>
    );

    // データが読み込まれるのを待つ
    await waitFor(() => {
      expect(screen.getByText("お茶の編集")).toBeInTheDocument();
      expect(screen.getByDisplayValue("龍井茶")).toBeInTheDocument();
      expect(screen.getByDisplayValue("緑茶")).toBeInTheDocument();
      expect(screen.getByDisplayValue("中国")).toBeInTheDocument();
      expect(screen.getByDisplayValue("在庫あり")).toBeInTheDocument();
      expect(screen.getByDisplayValue("中国浙江省産の高級緑茶")).toBeInTheDocument();
      expect(screen.getByDisplayValue("5000")).toBeInTheDocument();
      expect(screen.getByDisplayValue("100")).toBeInTheDocument();
      expect(screen.getByDisplayValue("g")).toBeInTheDocument();
    });
  });

  it("新規登録が正しく動作する", async () => {
    render(
      <BrowserRouter>
        <TeaFormPage />
      </BrowserRouter>
    );

    // フォームに入力
    fireEvent.change(screen.getByLabelText("お茶の名前"), {
      target: { value: "龍井茶" },
    });
    fireEvent.change(screen.getByLabelText("種類"), {
      target: { value: "緑茶" },
    });
    fireEvent.change(screen.getByLabelText("産地"), {
      target: { value: "中国" },
    });
    fireEvent.change(screen.getByLabelText("購入日"), {
      target: { value: "2024-03-01" },
    });
    fireEvent.change(screen.getByLabelText("状態"), {
      target: { value: "在庫あり" },
    });
    fireEvent.change(screen.getByLabelText("説明"), {
      target: { value: "中国浙江省産の高級緑茶" },
    });
    fireEvent.change(screen.getByLabelText("価格"), {
      target: { value: "5000" },
    });
    fireEvent.change(screen.getByLabelText("数量"), {
      target: { value: "100" },
    });
    fireEvent.change(screen.getByLabelText("単位"), {
      target: { value: "g" },
    });

    // 送信ボタンをクリック
    const submitButton = screen.getByText("保存");
    fireEvent.click(submitButton);

    // APIが呼び出される
    await waitFor(() => {
      expect(teaApi.createTea).toHaveBeenCalledWith({
        name: "龍井茶",
        type: "緑茶",
        origin: "中国",
        purchaseDate: "2024-03-01",
        status: "在庫あり",
        description: "中国浙江省産の高級緑茶",
        price: 5000,
        quantity: 100,
        unit: "g",
      });
    });

    // URLが変更されることを確認
    expect(window.location.pathname).toBe("/teas/1");
  });

  it("編集が正しく動作する", async () => {
    render(
      <BrowserRouter>
        <TeaFormPage />
      </BrowserRouter>
    );

    // データが読み込まれるのを待つ
    await waitFor(() => {
      expect(screen.getByText("お茶の編集")).toBeInTheDocument();
    });

    // フォームを編集
    fireEvent.change(screen.getByLabelText("お茶の名前"), {
      target: { value: "龍井茶（更新）" },
    });

    // 送信ボタンをクリック
    const submitButton = screen.getByText("保存");
    fireEvent.click(submitButton);

    // APIが呼び出される
    await waitFor(() => {
      expect(teaApi.updateTea).toHaveBeenCalledWith("1", {
        name: "龍井茶（更新）",
        type: "緑茶",
        origin: "中国",
        purchaseDate: "2024-03-01",
        status: "在庫あり",
        description: "中国浙江省産の高級緑茶",
        price: 5000,
        quantity: 100,
        unit: "g",
      });
    });

    // URLが変更されることを確認
    expect(window.location.pathname).toBe("/teas/1");
  });

  it("バリデーションエラーが表示される", async () => {
    render(
      <BrowserRouter>
        <TeaFormPage />
      </BrowserRouter>
    );

    // 送信ボタンをクリック
    const submitButton = screen.getByText("保存");
    fireEvent.click(submitButton);

    // エラーメッセージが表示される
    expect(screen.getByText("お茶の名前を入力してください")).toBeInTheDocument();
    expect(screen.getByText("種類を選択してください")).toBeInTheDocument();
    expect(screen.getByText("産地を入力してください")).toBeInTheDocument();
    expect(screen.getByText("購入日を入力してください")).toBeInTheDocument();
    expect(screen.getByText("状態を選択してください")).toBeInTheDocument();
    expect(screen.getByText("価格を入力してください")).toBeInTheDocument();
    expect(screen.getByText("数量を入力してください")).toBeInTheDocument();
    expect(screen.getByText("単位を入力してください")).toBeInTheDocument();

    // APIが呼び出されないことを確認
    expect(teaApi.createTea).not.toHaveBeenCalled();
    expect(teaApi.updateTea).not.toHaveBeenCalled();
  });

  it("キャンセルボタンが正しく動作する", async () => {
    render(
      <BrowserRouter>
        <TeaFormPage />
      </BrowserRouter>
    );

    // データが読み込まれるのを待つ
    await waitFor(() => {
      expect(screen.getByText("キャンセル")).toBeInTheDocument();
    });

    // キャンセルボタンをクリック
    const cancelButton = screen.getByText("キャンセル");
    fireEvent.click(cancelButton);

    // URLが変更されることを確認
    expect(window.location.pathname).toBe("/teas");
  });

  it("APIエラーが表示される", async () => {
    // APIエラーのモック
    (teaApi.createTea as jest.Mock).mockRejectedValue(new Error("保存に失敗しました"));

    render(
      <BrowserRouter>
        <TeaFormPage />
      </BrowserRouter>
    );

    // フォームに入力
    fireEvent.change(screen.getByLabelText("お茶の名前"), {
      target: { value: "龍井茶" },
    });
    fireEvent.change(screen.getByLabelText("種類"), {
      target: { value: "緑茶" },
    });
    fireEvent.change(screen.getByLabelText("産地"), {
      target: { value: "中国" },
    });
    fireEvent.change(screen.getByLabelText("購入日"), {
      target: { value: "2024-03-01" },
    });
    fireEvent.change(screen.getByLabelText("状態"), {
      target: { value: "在庫あり" },
    });
    fireEvent.change(screen.getByLabelText("価格"), {
      target: { value: "5000" },
    });
    fireEvent.change(screen.getByLabelText("数量"), {
      target: { value: "100" },
    });
    fireEvent.change(screen.getByLabelText("単位"), {
      target: { value: "g" },
    });

    // 送信ボタンをクリック
    const submitButton = screen.getByText("保存");
    fireEvent.click(submitButton);

    // エラーメッセージが表示される
    await waitFor(() => {
      expect(screen.getByText("保存に失敗しました")).toBeInTheDocument();
    });

    // URLが変更されないことを確認
    expect(window.location.pathname).toBe("/teas/new");
  });
}); 