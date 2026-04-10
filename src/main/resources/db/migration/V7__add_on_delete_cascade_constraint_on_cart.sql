ALTER TABLE cart
DROP CONSTRAINT cart_ibfk_1;

ALTER TABLE cart
DROP CONSTRAINT cart_ibfk_2;

ALTER TABLE cart
ADD CONSTRAINT fk_cart_user_id
    FOREIGN KEY (user_id)
    REFERENCES users(id)
    ON DELETE CASCADE;

ALTER TABLE cart
ADD CONSTRAINT fk_cart_product_id
    FOREIGN KEY (product_id)
    REFERENCES products(id)
    ON DELETE CASCADE;