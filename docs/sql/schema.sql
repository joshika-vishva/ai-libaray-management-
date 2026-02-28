CREATE TABLE users (
  id BIGSERIAL PRIMARY KEY,
  email VARCHAR(255) UNIQUE NOT NULL,
  full_name VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  role VARCHAR(30) NOT NULL
);

CREATE TABLE books (
  id BIGSERIAL PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  author VARCHAR(255) NOT NULL,
  category VARCHAR(50) NOT NULL,
  description TEXT,
  total_copies INT NOT NULL,
  available_copies INT NOT NULL
);

CREATE TABLE borrow_transactions (
  id BIGSERIAL PRIMARY KEY,
  user_id BIGINT NOT NULL REFERENCES users(id),
  book_id BIGINT NOT NULL REFERENCES books(id),
  issue_date DATE NOT NULL,
  due_date DATE NOT NULL,
  return_date DATE,
  fine_amount NUMERIC(10,2) NOT NULL,
  returned BOOLEAN NOT NULL
);

CREATE TABLE transaction_logs (
  id BIGSERIAL PRIMARY KEY,
  borrow_transaction_id BIGINT REFERENCES borrow_transactions(id),
  type VARCHAR(30) NOT NULL,
  details VARCHAR(500) NOT NULL,
  created_at TIMESTAMP NOT NULL
);
