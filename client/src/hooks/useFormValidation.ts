import { useState, useCallback } from "react";
import { ValidationRule, ValidationErrors, validateForm } from "../utils/validation";

interface UseFormValidationOptions<T> {
  initialValues: T;
  validationRules: Record<keyof T, ValidationRule[]>;
  onSubmit: (values: T) => Promise<void> | void;
  onSuccess?: () => void;
  onError?: (errors: ValidationErrors) => void;
}

export const useFormValidation = <T extends Record<string, any>>({
  initialValues,
  validationRules,
  onSubmit,
  onSuccess,
  onError,
}: UseFormValidationOptions<T>) => {
  const [values, setValues] = useState<T>(initialValues);
  const [errors, setErrors] = useState<ValidationErrors>({});
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [touched, setTouched] = useState<Record<keyof T, boolean>>(
    Object.keys(initialValues).reduce(
      (acc, key) => ({ ...acc, [key]: false }),
      {} as Record<keyof T, boolean>
    )
  );

  const handleChange = useCallback(
    (field: keyof T, value: any) => {
      setValues((prev) => ({ ...prev, [field]: value }));
      setTouched((prev) => ({ ...prev, [field]: true }));

      // フィールドが変更されたら、そのフィールドのバリデーションを実行
      const fieldErrors = validateForm(
        { [field]: value },
        { [field]: validationRules[field] }
      );
      setErrors((prev) => ({
        ...prev,
        [field]: fieldErrors[field] || [],
      }));
    },
    [validationRules]
  );

  const handleBlur = useCallback(
    (field: keyof T) => {
      setTouched((prev) => ({ ...prev, [field]: true }));
    },
    []
  );

  const validate = useCallback(() => {
    const newErrors = validateForm(values, validationRules);
    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  }, [values, validationRules]);

  const handleSubmit = useCallback(async () => {
    // すべてのフィールドをtouchedとしてマーク
    setTouched(
      Object.keys(values).reduce(
        (acc, key) => ({ ...acc, [key]: true }),
        {} as Record<keyof T, boolean>
      )
    );

    // バリデーションを実行
    const isValid = validate();
    if (!isValid) {
      onError?.(errors);
      return;
    }

    try {
      setIsSubmitting(true);
      await onSubmit(values);
      onSuccess?.();
    } catch (error) {
      onError?.(errors);
    } finally {
      setIsSubmitting(false);
    }
  }, [values, validate, onSubmit, onSuccess, onError, errors]);

  const resetForm = useCallback(() => {
    setValues(initialValues);
    setErrors({});
    setTouched(
      Object.keys(initialValues).reduce(
        (acc, key) => ({ ...acc, [key]: false }),
        {} as Record<keyof T, boolean>
      )
    );
  }, [initialValues]);

  const getFieldError = useCallback(
    (field: keyof T): string | undefined => {
      return touched[field] ? errors[field]?.[0] : undefined;
    },
    [errors, touched]
  );

  const hasFieldError = useCallback(
    (field: keyof T): boolean => {
      return touched[field] && !!errors[field]?.length;
    },
    [errors, touched]
  );

  const isFieldTouched = useCallback(
    (field: keyof T): boolean => {
      return touched[field];
    },
    [touched]
  );

  return {
    values,
    errors,
    touched,
    isSubmitting,
    handleChange,
    handleBlur,
    handleSubmit,
    resetForm,
    getFieldError,
    hasFieldError,
    isFieldTouched,
    validate,
  };
}; 