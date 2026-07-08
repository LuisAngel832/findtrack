CREATE TABLE budgets (
    id           UUID        PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id      UUID        NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    category_id  UUID        NOT NULL REFERENCES categories(id) ON DELETE RESTRICT,
    limit_amount DECIMAL(12, 2) NOT NULL CHECK (limit_amount > 0),
    month   INT        NOT NULL CHECK (month >= 1 AND month <= 12),
    year     INT        NOT NULL,
    alert_threshold      DECIMAL(3, 2) NOT NULL CHECK (alert_threshold >= 0.01 AND alert_threshold <= 1.00) DEFAULT 0.80,

    CONSTRAINT uk_budgets_user_category_month_year UNIQUE (user_id, category_id, month, year)
);