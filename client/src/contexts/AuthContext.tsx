import React, { createContext, useContext, useState, useEffect } from "react";

interface User {
  id: string;
  name: string;
  email: string;
}

interface AuthContextType {
  user: User | null;
  loading: boolean;
  login: (email: string, password: string) => Promise<void>;
  logout: () => Promise<void>;
  register: (name: string, email: string, password: string) => Promise<void>;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error("useAuth must be used within an AuthProvider");
  }
  return context;
};

export const AuthProvider: React.FC<{ children: React.ReactNode }> = ({
  children,
}) => {
  const [user, setUser] = useState<User | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    // 初期状態を未認証に設定
    setLoading(false);
  }, []);

  const login = async (email: string, password: string) => {
    // ダミーデータを使用
    const dummyUser = {
      id: "1",
      name: "テストユーザー",
      email: email,
    };
    setUser(dummyUser);
  };

  const logout = async () => {
    setUser(null);
  };

  const register = async (name: string, email: string, password: string) => {
    // ダミーデータを使用
    const dummyUser = {
      id: "1",
      name: name,
      email: email,
    };
    setUser(dummyUser);
  };

  return (
    <AuthContext.Provider
      value={{
        user,
        loading,
        login,
        logout,
        register,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
}; 