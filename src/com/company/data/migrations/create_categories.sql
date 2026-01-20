CREATE TABLE IF NOT EXISTS categories (
                                          id SERIAL PRIMARY KEY,
                                          user_id INT NULL,
                                          name VARCHAR(100) NOT NULL,
    type VARCHAR(10) NOT NULL CHECK (type IN ('INCOME', 'EXPENSE'))
    );
CREATE UNIQUE INDEX IF NOT EXISTS ux_categories_common
    ON categories (name, type)
    WHERE user_id IS NULL;

CREATE UNIQUE INDEX IF NOT EXISTS ux_categories_user
    ON categories (user_id, name, type)
    WHERE user_id IS NOT NULL;
