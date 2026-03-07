CREATE TABLE addresses(
    id BIGINT AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    address1 VARCHAR(255),
    address2 VARCHAR(255),
    city VARCHAR(255) NOT NULL,
    province VARCHAR(255) NOT NULL,
    zip_code VARCHAR(20),

    CONSTRAINT pk_address PRIMARY KEY (id),
    CONSTRAINT fk_address
        FOREIGN KEY (user_id)
        REFERENCES users(id)
);