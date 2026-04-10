ALTER TABLE addresses
DROP CONSTRAINT fk_address;

ALTER TABLE addresses
ADD CONSTRAINT fk_addresses
    FOREIGN KEY (user_id)
    REFERENCES users(id)
    ON DELETE CASCADE;