CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(200) NOT NULL,
    role VARCHAR(50) NOT NULL
);

CREATE TABLE orders (
    id SERIAL PRIMARY KEY,
    date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    user_id INT REFERENCES users(id),
    total DECIMAL(10, 2)
);

CREATE TABLE order_items (
    id SERIAL PRIMARY KEY,
    description VARCHAR(255),
    price DECIMAL(10, 2),
    quantity INT,
    order_id INT REFERENCES orders(id)
);
