import React from 'react';

interface PasswordResetEmailProps {
  username: string;
  resetToken: string;
  resetUrl: string;
}

/**
 * パスワードリセットメールのテンプレートコンポーネント
 */
export const PasswordResetEmail: React.FC<PasswordResetEmailProps> = ({
  username,
  resetToken,
  resetUrl,
}) => {
  return (
    <div className="container">
      <h1>パスワードリセットのご案内</h1>
      <p>{username}様</p>
      <p>
        パスワードリセットのリクエストを受け付けました。
        以下のリンクをクリックして、新しいパスワードを設定してください。
      </p>
      <div className="url-box">
        <a href={`${resetUrl}?token=${resetToken}`} className="button">
          パスワードをリセット
        </a>
      </div>
      <p>
        このリンクは1時間後に無効となります。
        リンクが期限切れの場合は、再度パスワードリセットをリクエストしてください。
      </p>
      <div className="footer">
        <p>
          ※このメールに心当たりがない場合は、このメールを破棄してください。
          <br />
          ※このメールは自動送信されています。返信はできませんのでご了承ください。
        </p>
      </div>
    </div>
  );
}; 