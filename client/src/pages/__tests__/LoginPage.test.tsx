import React from "react";
import { render, screen, fireEvent, waitFor } from "@testing-library/react";
import { BrowserRouter } from "react-router-dom";
import { LoginPage } from "../LoginPage";
import { authApi } from "../../api/authApi";

// APIのモック
jest.mock("../../api/authApi");

describe("LoginPage", () => {
  const mockUser = {
    id: "1",
    name: "テストユーザー",
    email: "test@example.com",
  };

  beforeEach(() => {
    (authApi.login as jest.Mock).mockResolvedValue(mockUser);
  });

  afterEach(() => {
    jest.clearAllMocks();
  });

  it("ログインフォームが正しく表示される", () => {
    render(
      <BrowserRouter>
        <LoginPage />
      </BrowserRouter>
    );

    expect(screen.getByText("ログイン")).toBeInTheDocument();
    expect(screen.getByLabelText("メールアドレス")).toBeInTheDocument();
    expect(screen.getByLabelText("パスワード")).toBeInTheDocument();
    expect(screen.getByText("ログインする")).toBeInTheDocument();
  });

  it("ログインが正しく動作する", async () => {
    render(
      <BrowserRouter>
        <LoginPage />
      </BrowserRouter>
    );

    // フォームに入力
    fireEvent.change(screen.getByLabelText("メールアドレス"), {
      target: { value: "test@example.com" },
    });
    fireEvent.change(screen.getByLabelText("パスワード"), {
      target: { value: "password" },
    });

    // ログインボタンをクリック
    const loginButton = screen.getByText("ログインする");
    fireEvent.click(loginButton);

    // APIが呼び出される
    await waitFor(() => {
      expect(authApi.login).toHaveBeenCalledWith({
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
        <LoginPage />
      </BrowserRouter>
    );

    // ログインボタンをクリック
    const loginButton = screen.getByText("ログインする");
    fireEvent.click(loginButton);

    // エラーメッセージが表示される
    expect(screen.getByText("メールアドレスを入力してください")).toBeInTheDocument();
    expect(screen.getByText("パスワードを入力してください")).toBeInTheDocument();

    // APIが呼び出されないことを確認
    expect(authApi.login).not.toHaveBeenCalled();
  });

  it("APIエラーが表示される", async () => {
    // APIエラーのモック
    (authApi.login as jest.Mock).mockRejectedValue(new Error("ログインに失敗しました"));

    render(
      <BrowserRouter>
        <LoginPage />
      </BrowserRouter>
    );

    // フォームに入力
    fireEvent.change(screen.getByLabelText("メールアドレス"), {
      target: { value: "test@example.com" },
    });
    fireEvent.change(screen.getByLabelText("パスワード"), {
      target: { value: "password" },
    });

    // ログインボタンをクリック
    const loginButton = screen.getByText("ログインする");
    fireEvent.click(loginButton);

    // エラーメッセージが表示される
    await waitFor(() => {
      expect(screen.getByText("ログインに失敗しました")).toBeInTheDocument();
    });

    // URLが変更されないことを確認
    expect(window.location.pathname).toBe("/login");
  });

  it("新規登録ページへのリンクが正しく動作する", () => {
    render(
      <BrowserRouter>
        <LoginPage />
      </BrowserRouter>
    );

    // 新規登録リンクをクリック
    const registerLink = screen.getByText("新規登録はこちら");
    fireEvent.click(registerLink);

    // URLが変更されることを確認
    expect(window.location.pathname).toBe("/register");
  });
}); 