-- !Ups

-- 既存のテーブルを一時テーブルにコピー
CREATE TABLE plants_temp (
  id SERIAL PRIMARY KEY,
  name VARCHAR NOT NULL,
  species VARCHAR NOT NULL,
  user_id INTEGER NOT NULL DEFAULT 1,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users(id)
);

-- データを一時テーブルにコピー
INSERT INTO plants_temp (id, name, species)
SELECT id, name, species FROM plants;

-- 既存のテーブルを削除
DROP TABLE plants;

-- 一時テーブルを正しい名前にリネーム
ALTER TABLE plants_temp RENAME TO plants;

ALTER TABLE teas
  ADD COLUMN type VARCHAR(255) NOT NULL,
  ADD COLUMN origin VARCHAR(255) NOT NULL,
  ADD COLUMN purchase_date TIMESTAMP NOT NULL,
  ADD COLUMN status VARCHAR(255) NOT NULL,
  ADD COLUMN price DECIMAL(10,2),
  ADD COLUMN quantity DECIMAL(10,2),
  ADD COLUMN unit VARCHAR(50),
  ADD COLUMN image_url VARCHAR(255);

-- !Downs

-- 既存のテーブルを一時テーブルにコピー
CREATE TABLE plants_temp (
  id SERIAL PRIMARY KEY,
  name VARCHAR NOT NULL,
  species VARCHAR NOT NULL
);

-- データを一時テーブルにコピー
INSERT INTO plants_temp (id, name, species)
SELECT id, name, species FROM plants;

-- 既存のテーブルを削除
DROP TABLE plants;

-- 一時テーブルを正しい名前にリネーム
ALTER TABLE plants_temp RENAME TO plants;

ALTER TABLE teas
  DROP COLUMN type,
  DROP COLUMN origin,
  DROP COLUMN purchase_date,
  DROP COLUMN status,
  DROP COLUMN price,
  DROP COLUMN quantity,
  DROP COLUMN unit,
  DROP COLUMN image_url; 