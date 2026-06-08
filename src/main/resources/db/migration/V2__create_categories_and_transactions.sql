CREATE TABLE categories (
    id           UUID        PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id      UUID        REFERENCES users(id) ON DELETE CASCADE,
    name         VARCHAR(100) NOT NULL,
    icon         VARCHAR(50),
    type         VARCHAR(10) NOT NULL CHECK (type IN ('income', 'expense')),
    is_default   BOOLEAN     NOT NULL DEFAULT FALSE
);


CREATE TABLE transactions (
    id           UUID        PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id      UUID        NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    category_id  UUID        NOT NULL REFERENCES categories(id) ON DELETE RESTRICT,
    amount       DECIMAL(12, 2) NOT NULL CHECK (amount > 0),
    type         VARCHAR(10) NOT NULL CHECK (type IN ('income', 'expense')),
    description  VARCHAR(255) DEFAULT NULL,
    date         DATE        NOT NULL,
    created_at   TIMESTAMP    NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_transactions_user_id  ON transactions(user_id);

CREATE INDEX idx_transactions_date  ON transactions(date);

CREATE INDEX idx_transactions_category_id  ON transactions(category_id);

CREATE INDEX idx_transactions_user_id_date ON transactions(user_id, date);

CREATE INDEX idx_categories_user_id ON categories(user_id);

