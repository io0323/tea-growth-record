/**
 * バリデーションルールの型定義
 */
export interface ValidationRule {
  required?: boolean;
  minLength?: number;
  maxLength?: number;
  pattern?: RegExp;
  custom?: (value: any) => boolean | string;
  message: string;
}

/**
 * バリデーションエラーの型定義
 */
export interface ValidationErrors {
  [key: string]: string[];
}

/**
 * フィールドのバリデーション
 */
export const validateField = (
  value: any,
  rules: ValidationRule[]
): string[] => {
  const errors: string[] = [];

  for (const rule of rules) {
    // 必須チェック
    if (rule.required && (!value || value.trim() === "")) {
      errors.push(rule.message);
      continue;
    }

    // 最小長チェック
    if (rule.minLength && value.length < rule.minLength) {
      errors.push(rule.message);
      continue;
    }

    // 最大長チェック
    if (rule.maxLength && value.length > rule.maxLength) {
      errors.push(rule.message);
      continue;
    }

    // パターンチェック
    if (rule.pattern && !rule.pattern.test(value)) {
      errors.push(rule.message);
      continue;
    }

    // カスタムバリデーション
    if (rule.custom) {
      const result = rule.custom(value);
      if (typeof result === "string") {
        errors.push(result);
      } else if (!result) {
        errors.push(rule.message);
      }
    }
  }

  return errors;
};

/**
 * フォーム全体のバリデーション
 */
export const validateForm = (
  values: Record<string, any>,
  rules: Record<string, ValidationRule[]>
): ValidationErrors => {
  const errors: ValidationErrors = {};

  for (const [field, fieldRules] of Object.entries(rules)) {
    const fieldErrors = validateField(values[field], fieldRules);
    if (fieldErrors.length > 0) {
      errors[field] = fieldErrors;
    }
  }

  return errors;
};

/**
 * よく使用するバリデーションルール
 */
export const commonRules = {
  required: (message: string): ValidationRule => ({
    required: true,
    message,
  }),
  minLength: (min: number, message: string): ValidationRule => ({
    minLength: min,
    message,
  }),
  maxLength: (max: number, message: string): ValidationRule => ({
    maxLength: max,
    message,
  }),
  pattern: (pattern: RegExp, message: string): ValidationRule => ({
    pattern,
    message,
  }),
  email: (message: string = "有効なメールアドレスを入力してください"): ValidationRule => ({
    pattern: /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i,
    message,
  }),
  password: (message: string = "パスワードは8文字以上で、英数字を含める必要があります"): ValidationRule => ({
    pattern: /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/,
    message,
  }),
  number: (message: string = "数値を入力してください"): ValidationRule => ({
    pattern: /^\d+$/,
    message,
  }),
  positiveNumber: (message: string = "0より大きい数値を入力してください"): ValidationRule => ({
    custom: (value) => Number(value) > 0,
    message,
  }),
  date: (message: string = "有効な日付を入力してください"): ValidationRule => ({
    pattern: /^\d{4}-\d{2}-\d{2}$/,
    message,
  }),
  url: (message: string = "有効なURLを入力してください"): ValidationRule => ({
    pattern: /^(https?:\/\/)?([\da-z.-]+)\.([a-z.]{2,6})([/\w .-]*)*\/?$/,
    message,
  }),
  priceRange: (min: number, max: number, message: string): ValidationRule => ({
    custom: (value) => {
      const num = Number(value);
      return num >= min && num <= max;
    },
    message,
  }),
  quantityRange: (min: number, max: number, message: string): ValidationRule => ({
    custom: (value) => {
      const num = Number(value);
      return num >= min && num <= max;
    },
    message,
  }),
}; 