CREATE TABLE PRODUCT (
    product_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    price FLOAT NOT NULL,
    quantity_in_stock INTEGER NOT NULL,
    CONSTRAINT product_id UNIQUE(product_id)
);

INSERT INTO PRODUCT (product_id, name, price, quantity_in_stock) VALUES (1, 'product name', 10.97, 10);