import React from "react";
import { render, screen, fireEvent, waitFor } from "@testing-library/react";
import { BrowserRouter } from "react-router-dom";
import { RegisterPage } from "../RegisterPage";
import { authApi } from "../../api/authApi";

// APIのモック
jest.mock("../../api/authApi");

describe("RegisterPage", () => {
  const mockUser = {
    id: "1",
    name: "テストユーザー",
    email: "test@example.com",
  };

  beforeEach(() => {
    (authApi.register as jest.Mock).mockResolvedValue(mockUser);
  });

  afterEach(() => {
    jest.clearAllMocks();
  });

  it("新規登録フォームが正しく表示される", () => {
    render(
      <BrowserRouter>
        <RegisterPage />
      </BrowserRouter>
    );

    expect(screen.getByText("新規登録")).toBeInTheDocument();
    expect(screen.getByLabelText("名前")).toBeInTheDocument();
    expect(screen.getByLabelText("メールアドレス")).toBeInTheDocument();
    expect(screen.getByLabelText("パスワード")).toBeInTheDocument();
    expect(screen.getByLabelText("パスワード（確認）")).toBeInTheDocument();
    expect(screen.getByText("登録する")).toBeInTheDocument();
  });

  it("新規登録が正しく動作する", async () => {
    render(
      <BrowserRouter>
        <RegisterPage />
      </BrowserRouter>
    );

    // フォームに入力
    fireEvent.change(screen.getByLabelText("名前"), {
      target: { value: "テストユーザー" },
    });
    fireEvent.change(screen.getByLabelText("メールアドレス"), {
      target: { value: "test@example.com" },
    });
    fireEvent.change(screen.getByLabelText("パスワード"), {
      target: { value: "password" },
    });
    fireEvent.change(screen.getByLabelText("パスワード（確認）"), {
      target: { value: "password" },
    });

    // 登録ボタンをクリック
    const registerButton = screen.getByText("登録する");
    fireEvent.click(registerButton);

    // APIが呼び出される
    await waitFor(() => {
      expect(authApi.register).toHaveBeenCalledWith({
        name: "テストユーザー",
        email: "test@example.com",
        password: "password",
      });
    });

    // URLが変更されることを確認
    expect(window.location.pathname).toBe("/dashboard");
  });

  it("バリデーションエラーが表示される", async () => {
    render(
      <BrowserRouter>
        <RegisterPage />
      </BrowserRouter>
    );

    // 登録ボタンをクリック
    const registerButton = screen.getByText("登録する");
    fireEvent.click(registerButton);

    // エラーメッセージが表示される
    expect(screen.getByText("名前を入力してください")).toBeInTheDocument();
    expect(screen.getByText("メールアドレスを入力してください")).toBeInTheDocument();
    expect(screen.getByText("パスワードを入力してください")).toBeInTheDocument();
    expect(screen.getByText("パスワード（確認）を入力してください")).toBeInTheDocument();

    // APIが呼び出されないことを確認
    expect(authApi.register).not.toHaveBeenCalled();
  });

  it("パスワード不一致エラーが表示される", async () => {
    render(
      <BrowserRouter>
        <RegisterPage />
      </BrowserRouter>
    );

    // フォームに入力（パスワード不一致）
    fireEvent.change(screen.getByLabelText("名前"), {
      target: { value: "テストユーザー" },
    });
    fireEvent.change(screen.getByLabelText("メールアドレス"), {
      target: { value: "test@example.com" },
    });
    fireEvent.change(screen.getByLabelText("パスワード"), {
      target: { value: "password" },
    });
    fireEvent.change(screen.getByLabelText("パスワード（確認）"), {
      target: { value: "different-password" },
    });

    // 登録ボタンをクリック
    const registerButton = screen.getByText("登録する");
    fireEvent.click(registerButton);

    // エラーメッセージが表示される
    expect(screen.getByText("パスワードが一致しません")).toBeInTheDocument();

    // APIが呼び出されないことを確認
    expect(authApi.register).not.toHaveBeenCalled();
  });

  it("APIエラーが表示される", async () => {
    // APIエラーのモック
    (authApi.register as jest.Mock).mockRejectedValue(new Error("登録に失敗しました"));

    render(
      <BrowserRouter>
        <RegisterPage />
      </BrowserRouter>
    );

    // フォームに入力
    fireEvent.change(screen.getByLabelText("名前"), {
      target: { value: "テストユーザー" },
    });
    fireEvent.change(screen.getByLabelText("メールアドレス"), {
      target: { value: "test@example.com" },
    });
    fireEvent.change(screen.getByLabelText("パスワード"), {
      target: { value: "password" },
    });
    fireEvent.change(screen.getByLabelText("パスワード（確認）"), {
      target: { value: "password" },
    });

    // 登録ボタンをクリック
    const registerButton = screen.getByText("登録する");
    fireEvent.click(registerButton);

    // エラーメッセージが表示される
    await waitFor(() => {
      expect(screen.getByText("登録に失敗しました")).toBeInTheDocument();
    });

    // URLが変更されないことを確認
    expect(window.location.pathname).toBe("/register");
  });

  it("ログインページへのリンクが正しく動作する", () => {
    render(
      <BrowserRouter>
        <RegisterPage />
      </BrowserRouter>
    );

    // ログインリンクをクリック
    const loginLink = screen.getByText("ログインはこちら");
    fireEvent.click(loginLink);

    // URLが変更されることを確認
    expect(window.location.pathname).toBe("/login");
  });
}); 