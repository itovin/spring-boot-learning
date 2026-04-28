CREATE TABLE orders(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_uuid BINARY(16) UNIQUE NOT NULL,
    customer_id BIGINT NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_customer_id
        FOREIGN KEY (customer_id)
        REFERENCES users(id)
);

CREATE TABLE order_items(
    id BIGINT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    price_at_purchase DECIMAL(10, 2) NOT NULL,
    quantity INT NOT NULL,
    total_price DECIMAL(10, 2),

    CONSTRAINT fk_order_id
        FOREIGN KEY (order_id)
        REFERENCES orders(id),
    CONSTRAINT fk_product_id
        FOREIGN KEY (product_id)
        REFERENCES products(id)
);