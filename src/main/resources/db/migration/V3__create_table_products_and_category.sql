CREATE TABLE categories(
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE products(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2),
    description VARCHAR(255),
    status VARCHAR(255),
    category_id INT,

    CONSTRAINT fk_products
        FOREIGN KEY (category_id)
        REFERENCES categories(id)
);