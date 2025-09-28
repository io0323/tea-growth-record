import React from "react";
import { render, screen, fireEvent, waitFor } from "@testing-library/react";
import { BrowserRouter } from "react-router-dom";
import { ProfilePage } from "../ProfilePage";
import { authApi } from "../../api/authApi";

// APIのモック
jest.mock("../../api/authApi");

describe("ProfilePage", () => {
  const mockUser = {
    id: "1",
    name: "テストユーザー",
    email: "test@example.com",
  };

  beforeEach(() => {
    (authApi.getProfile as jest.Mock).mockResolvedValue(mockUser);
    (authApi.updateProfile as jest.Mock).mockResolvedValue(mockUser);
  });

  afterEach(() => {
    jest.clearAllMocks();
  });

  it("プロフィール情報が正しく表示される", async () => {
    render(
      <BrowserRouter>
        <ProfilePage />
      </BrowserRouter>
    );

    // ローディング中は表示されない
    expect(screen.queryByText("テストユーザー")).not.toBeInTheDocument();

    // データが読み込まれたら表示される
    await waitFor(() => {
      expect(screen.getByText("テストユーザー")).toBeInTheDocument();
      expect(screen.getByText("test@example.com")).toBeInTheDocument();
    });
  });

  it("プロフィール更新が正しく動作する", async () => {
    render(
      <BrowserRouter>
        <ProfilePage />
      </BrowserRouter>
    );

    // データが読み込まれるのを待つ
    await waitFor(() => {
      expect(screen.getByText("テストユーザー")).toBeInTheDocument();
    });

    // フォームを編集
    fireEvent.change(screen.getByLabelText("名前"), {
      target: { value: "更新ユーザー" },
    });
    fireEvent.change(screen.getByLabelText("メールアドレス"), {
      target: { value: "update@example.com" },
    });
    fireEvent.change(screen.getByLabelText("現在のパスワード"), {
      target: { value: "current-password" },
    });
    fireEvent.change(screen.getByLabelText("新しいパスワード"), {
      target: { value: "new-password" },
    });

    // 保存ボタンをクリック
    const saveButton = screen.getByText("保存");
    fireEvent.click(saveButton);

    // APIが呼び出される
    await waitFor(() => {
      expect(authApi.updateProfile).toHaveBeenCalledWith({
        name: "更新ユーザー",
        email: "update@example.com",
        currentPassword: "current-password",
        newPassword: "new-password",
      });
    });

    // 成功メッセージが表示される
    expect(screen.getByText("プロフィールを更新しました")).toBeInTheDocument();
  });

  it("パスワードなしでプロフィール更新が正しく動作する", async () => {
    render(
      <BrowserRouter>
        <ProfilePage />
      </BrowserRouter>
    );

    // データが読み込まれるのを待つ
    await waitFor(() => {
      expect(screen.getByText("テストユーザー")).toBeInTheDocument();
    });

    // フォームを編集
    fireEvent.change(screen.getByLabelText("名前"), {
      target: { value: "更新ユーザー" },
    });
    fireEvent.change(screen.getByLabelText("メールアドレス"), {
      target: { value: "update@example.com" },
    });

    // 保存ボタンをクリック
    const saveButton = screen.getByText("保存");
    fireEvent.click(saveButton);

    // APIが呼び出される
    await waitFor(() => {
      expect(authApi.updateProfile).toHaveBeenCalledWith({
        name: "更新ユーザー",
        email: "update@example.com",
      });
    });

    // 成功メッセージが表示される
    expect(screen.getByText("プロフィールを更新しました")).toBeInTheDocument();
  });

  it("バリデーションエラーが表示される", async () => {
    render(
      <BrowserRouter>
        <ProfilePage />
      </BrowserRouter>
    );

    // データが読み込まれるのを待つ
    await waitFor(() => {
      expect(screen.getByText("テストユーザー")).toBeInTheDocument();
    });

    // フォームを編集（無効なメールアドレス）
    fireEvent.change(screen.getByLabelText("メールアドレス"), {
      target: { value: "invalid-email" },
    });

    // 保存ボタンをクリック
    const saveButton = screen.getByText("保存");
    fireEvent.click(saveButton);

    // エラーメッセージが表示される
    expect(screen.getByText("有効なメールアドレスを入力してください")).toBeInTheDocument();

    // APIが呼び出されないことを確認
    expect(authApi.updateProfile).not.toHaveBeenCalled();
  });

  it("パスワード変更時にバリデーションエラーが表示される", async () => {
    render(
      <BrowserRouter>
        <ProfilePage />
      </BrowserRouter>
    );

    // データが読み込まれるのを待つ
    await waitFor(() => {
      expect(screen.getByText("テストユーザー")).toBeInTheDocument();
    });

    // フォームを編集（パスワード不一致）
    fireEvent.change(screen.getByLabelText("新しいパスワード"), {
      target: { value: "new-password" },
    });
    fireEvent.change(screen.getByLabelText("新しいパスワード（確認）"), {
      target: { value: "different-password" },
    });

    // 保存ボタンをクリック
    const saveButton = screen.getByText("保存");
    fireEvent.click(saveButton);

    // エラーメッセージが表示される
    expect(screen.getByText("パスワードが一致しません")).toBeInTheDocument();

    // APIが呼び出されないことを確認
    expect(authApi.updateProfile).not.toHaveBeenCalled();
  });

  it("APIエラーが表示される", async () => {
    // APIエラーのモック
    (authApi.updateProfile as jest.Mock).mockRejectedValue(new Error("更新に失敗しました"));

    render(
      <BrowserRouter>
        <ProfilePage />
      </BrowserRouter>
    );

    // データが読み込まれるのを待つ
    await waitFor(() => {
      expect(screen.getByText("テストユーザー")).toBeInTheDocument();
    });

    // フォームを編集
    fireEvent.change(screen.getByLabelText("名前"), {
      target: { value: "更新ユーザー" },
    });

    // 保存ボタンをクリック
    const saveButton = screen.getByText("保存");
    fireEvent.click(saveButton);

    // エラーメッセージが表示される
    await waitFor(() => {
      expect(screen.getByText("更新に失敗しました")).toBeInTheDocument();
    });
  });
}); 