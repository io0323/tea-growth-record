import React from "react";
import { render, screen, fireEvent, waitFor } from "@testing-library/react";
import { TeaForm } from "./TeaForm";

describe("TeaForm", () => {
  const mockOnSubmit = jest.fn();
  const mockOnCancel = jest.fn();

  beforeEach(() => {
    jest.clearAllMocks();
  });

  it("初期状態で正しくレンダリングされること", () => {
    render(<TeaForm onSubmit={mockOnSubmit} onCancel={mockOnCancel} />);

    expect(screen.getByLabelText("お茶の名前")).toBeInTheDocument();
    expect(screen.getByLabelText("お茶の種類")).toBeInTheDocument();
    expect(screen.getByLabelText("産地")).toBeInTheDocument();
    expect(screen.getByLabelText("購入日")).toBeInTheDocument();
    expect(screen.getByLabelText("状態")).toBeInTheDocument();
    expect(screen.getByLabelText("説明")).toBeInTheDocument();
    expect(screen.getByLabelText("価格")).toBeInTheDocument();
    expect(screen.getByLabelText("数量")).toBeInTheDocument();
    expect(screen.getByLabelText("単位")).toBeInTheDocument();
  });

  it("必須フィールドが空の場合にバリデーションエラーが表示されること", async () => {
    render(<TeaForm onSubmit={mockOnSubmit} onCancel={mockOnCancel} />);

    fireEvent.click(screen.getByText("保存"));

    await waitFor(() => {
      expect(screen.getByText("お茶の名前を入力してください")).toBeInTheDocument();
      expect(screen.getByText("お茶の種類を選択してください")).toBeInTheDocument();
      expect(screen.getByText("産地を入力してください")).toBeInTheDocument();
      expect(screen.getByText("購入日を入力してください")).toBeInTheDocument();
      expect(screen.getByText("価格を入力してください")).toBeInTheDocument();
      expect(screen.getByText("数量を入力してください")).toBeInTheDocument();
    });

    expect(mockOnSubmit).not.toHaveBeenCalled();
  });

  it("有効なデータで送信できること", async () => {
    render(<TeaForm onSubmit={mockOnSubmit} onCancel={mockOnCancel} />);

    fireEvent.change(screen.getByLabelText("お茶の名前"), {
      target: { value: "玉露" },
    });
    fireEvent.change(screen.getByLabelText("お茶の種類"), {
      target: { value: "緑茶" },
    });
    fireEvent.change(screen.getByLabelText("産地"), {
      target: { value: "静岡県" },
    });
    fireEvent.change(screen.getByLabelText("購入日"), {
      target: { value: "2024-03-20" },
    });
    fireEvent.change(screen.getByLabelText("価格"), {
      target: { value: "1000" },
    });
    fireEvent.change(screen.getByLabelText("数量"), {
      target: { value: "100" },
    });

    fireEvent.click(screen.getByText("保存"));

    await waitFor(() => {
      expect(mockOnSubmit).toHaveBeenCalledWith({
        name: "玉露",
        type: "緑茶",
        origin: "静岡県",
        purchaseDate: "2024-03-20",
        status: "未開封",
        description: "",
        price: "1000",
        quantity: "100",
        unit: "g",
      });
    });
  });

  it("キャンセルボタンが正しく動作すること", () => {
    render(<TeaForm onSubmit={mockOnSubmit} onCancel={mockOnCancel} />);

    fireEvent.click(screen.getByText("キャンセル"));
    expect(mockOnCancel).toHaveBeenCalled();
  });

  it("価格が範囲外の場合にエラーが表示されること", async () => {
    render(<TeaForm onSubmit={mockOnSubmit} onCancel={mockOnCancel} />);

    fireEvent.change(screen.getByLabelText("価格"), {
      target: { value: "1000001" },
    });

    fireEvent.click(screen.getByText("保存"));

    await waitFor(() => {
      expect(screen.getByText("価格は0円から1,000,000円の間で入力してください")).toBeInTheDocument();
    });

    expect(mockOnSubmit).not.toHaveBeenCalled();
  });

  it("数量が範囲外の場合にエラーが表示されること", async () => {
    render(<TeaForm onSubmit={mockOnSubmit} onCancel={mockOnCancel} />);

    fireEvent.change(screen.getByLabelText("数量"), {
      target: { value: "1001" },
    });

    fireEvent.click(screen.getByText("保存"));

    await waitFor(() => {
      expect(screen.getByText("数量は0から1,000の間で入力してください")).toBeInTheDocument();
    });

    expect(mockOnSubmit).not.toHaveBeenCalled();
  });
}); 