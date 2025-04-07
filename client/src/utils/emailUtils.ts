import { renderToString } from 'react-dom/server';
import { PasswordResetEmail } from '../components/email/PasswordResetEmail';
import React from 'react';

interface EmailTemplate {
  subject: string;
  body: string;
  variables: string[];
}

/**
 * メールテンプレートをHTMLに変換する
 */
export const generateEmailHtml = (template: React.ReactElement): string => {
  const html = renderToString(template);
  return `
    <!DOCTYPE html>
    <html>
      <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>茶の成長記録</title>
        <style>
          body {
            margin: 0;
            padding: 0;
            font-family: Arial, sans-serif;
            line-height: 1.6;
            color: #333;
          }
          .container {
            max-width: 600px;
            margin: 0 auto;
            padding: 20px;
          }
          .button {
            display: inline-block;
            padding: 12px 24px;
            background-color: #2e7d32;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            margin: 20px 0;
          }
          .button:hover {
            background-color: #1b5e20;
          }
          .url-box {
            background-color: #f5f5f5;
            padding: 12px;
            border-radius: 4px;
            margin: 20px 0;
            word-break: break-all;
          }
          .footer {
            margin-top: 40px;
            padding-top: 20px;
            border-top: 1px solid #ddd;
            color: #666;
          }
        </style>
      </head>
      <body>
        ${html}
      </body>
    </html>
  `;
};

/**
 * カスタムテンプレートからHTMLを生成する
 */
export const generateCustomEmailHtml = (
  template: EmailTemplate,
  variables: Record<string, string>
): string => {
  let html = template.body;
  Object.entries(variables).forEach(([key, value]) => {
    html = html.replace(new RegExp(`{${key}}`, 'g'), value);
  });

  return `
    <!DOCTYPE html>
    <html>
      <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>${template.subject}</title>
        <style>
          body {
            margin: 0;
            padding: 0;
            font-family: Arial, sans-serif;
            line-height: 1.6;
            color: #333;
          }
          .container {
            max-width: 600px;
            margin: 0 auto;
            padding: 20px;
          }
          .button {
            display: inline-block;
            padding: 12px 24px;
            background-color: #2e7d32;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            margin: 20px 0;
          }
          .button:hover {
            background-color: #1b5e20;
          }
          .url-box {
            background-color: #f5f5f5;
            padding: 12px;
            border-radius: 4px;
            margin: 20px 0;
            word-break: break-all;
          }
          .footer {
            margin-top: 40px;
            padding-top: 20px;
            border-top: 1px solid #ddd;
            color: #666;
          }
        </style>
      </head>
      <body>
        <div class="container">
          ${html}
        </div>
      </body>
    </html>
  `;
};

/**
 * パスワードリセットメールのHTMLを生成する
 */
export const generatePasswordResetEmail = (
  username: string,
  resetToken: string,
  resetUrl: string,
  customTemplate?: EmailTemplate
): string => {
  if (customTemplate) {
    return generateCustomEmailHtml(customTemplate, {
      username,
      resetUrl,
      expiryTime: '1時間',
    });
  }

  const template = React.createElement(PasswordResetEmail, {
    username,
    resetToken,
    resetUrl,
  });
  return generateEmailHtml(template);
}; 