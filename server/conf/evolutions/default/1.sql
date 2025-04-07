-- !Ups

-- ユーザーテーブル
CREATE TABLE users (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  email VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  name VARCHAR(255),
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- お茶の種類
CREATE TABLE tea_types (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL UNIQUE,
  description TEXT,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- お茶の状態
CREATE TABLE tea_status_types (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL UNIQUE,
  description TEXT,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- お茶テーブル
CREATE TABLE teas (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL,
  name VARCHAR(255) NOT NULL,
  tea_type VARCHAR(255) NOT NULL,
  purchase_date DATE NOT NULL,
  price DECIMAL(10,2),
  quantity DECIMAL(10,2),
  notes TEXT,
  status VARCHAR(255) NOT NULL,
  image_path VARCHAR(255),
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- お茶の状態の初期データ
INSERT INTO tea_status_types (name, description) VALUES
  ('New', '新規登録'),
  ('InUse', '使用中'),
  ('Empty', '空');

-- お茶の種類の初期データ
INSERT INTO tea_types (name, description) VALUES
  ('Green', '緑茶'),
  ('Black', '紅茶'),
  ('Oolong', 'ウーロン茶'),
  ('White', '白茶'),
  ('PuErh', 'プーアル茶'),
  ('Herbal', 'ハーブティー'),
  ('Other', 'その他');

-- !Downs

DROP TABLE IF EXISTS teas;
DROP TABLE IF EXISTS tea_status_types;
DROP TABLE IF EXISTS tea_types;
DROP TABLE IF EXISTS users; 