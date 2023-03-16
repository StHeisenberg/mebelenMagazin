CREATE TABLE category
(
    category_id SERIAL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL
);

CREATE TABLE product
(
    product_id  SERIAL PRIMARY KEY,
    name        VARCHAR(255)   NOT NULL,
    price       NUMERIC(10, 2) NOT NULL,
    description TEXT,
    category_id INTEGER REFERENCES category (category_id) ON DELETE SET NULL
);

CREATE TABLE customer
(
    customer_id SERIAL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    email       VARCHAR(255),
    phone       VARCHAR(20),
    address     TEXT
);

CREATE TABLE orders
(
    order_id    SERIAL PRIMARY KEY,
    order_date  TIMESTAMP NOT NULL,
    customer_id INTEGER   REFERENCES customer (customer_id) ON DELETE SET NULL
);

CREATE TABLE order_item
(
    order_id   INTEGER NOT NULL,
    product_id INTEGER NOT NULL,
    quantity   INTEGER NOT NULL,
    PRIMARY KEY (order_id, product_id),
    FOREIGN KEY (order_id) REFERENCES orders (order_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES product (product_id) ON DELETE CASCADE
);
