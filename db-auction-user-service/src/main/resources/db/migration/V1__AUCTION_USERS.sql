CREATE TABLE auction_user (
    user_id             UUID PRIMARY KEY NOT NULL,
    first_name          VARCHAR(100) NOT NULL,
    last_name           VARCHAR(100) NOT NULL,
    username            VARCHAR(100) NOT NULL,
    password            VARCHAR(100) NOT NULL,
    email               VARCHAR(100) NOT NULL,
    is_active           BOOLEAN NOT NULL DEFAULT TRUE,
    role                VARCHAR(20) NOT NULL DEFAULT 'APP_USER',
    created_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO auction_user (user_id, first_name, last_name, username, password, email, is_active, role, created_at, updated_at)
VALUES
(UUID(), 'Seller', 'One', 'seller1', '$2a$10$3CJqzTeX6oMAd72GD0vfW..cdMq7/eLGO/IKtGh8zhI1kmbSYG7ri', 'seller1@example.com', TRUE, 'ROLE_SELLER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(UUID(), 'Seller', 'Two', 'seller2', '$2a$10$3CJqzTeX6oMAd72GD0vfW..cdMq7/eLGO/IKtGh8zhI1kmbSYG7ri', 'seller2@example.com', TRUE, 'ROLE_SELLER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(UUID(), 'Seller', 'Three', 'seller3', '$2a$10$3CJqzTeX6oMAd72GD0vfW..cdMq7/eLGO/IKtGh8zhI1kmbSYG7ri', 'seller3@example.com', TRUE, 'ROLE_SELLER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(UUID(), 'Seller', 'Four', 'seller4', '$2a$10$3CJqzTeX6oMAd72GD0vfW..cdMq7/eLGO/IKtGh8zhI1kmbSYG7ri', 'seller4@example.com', TRUE, 'ROLE_SELLER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(UUID(), 'Bidder', 'One', 'bidder1', '$2a$10$3CJqzTeX6oMAd72GD0vfW..cdMq7/eLGO/IKtGh8zhI1kmbSYG7ri', 'bidder1@example.com', TRUE, 'ROLE_BIDDER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(UUID(), 'Bidder', 'Two', 'bidder2', '$2a$10$3CJqzTeX6oMAd72GD0vfW..cdMq7/eLGO/IKtGh8zhI1kmbSYG7ri', 'bidder2@example.com', TRUE, 'ROLE_BIDDER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(UUID(), 'Bidder', 'Three', 'bidder3', '$2a$10$3CJqzTeX6oMAd72GD0vfW..cdMq7/eLGO/IKtGh8zhI1kmbSYG7ri', 'bidder3@example.com', TRUE, 'ROLE_BIDDER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(UUID(), 'Bidder', 'Four', 'bidder4', '$2a$10$3CJqzTeX6oMAd72GD0vfW..cdMq7/eLGO/IKtGh8zhI1kmbSYG7ri', 'bidder4@example.com', TRUE, 'ROLE_BIDDER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(UUID(), 'Bidder', 'Five', 'bidder5', '$2a$10$3CJqzTeX6oMAd72GD0vfW..cdMq7/eLGO/IKtGh8zhI1kmbSYG7ri', 'bidder5@example.com', TRUE, 'ROLE_BIDDER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(UUID(), 'Admin', 'One', 'admin1', '$2a$10$3CJqzTeX6oMAd72GD0vfW..cdMq7/eLGO/IKtGh8zhI1kmbSYG7ri', 'admin1@example.com', TRUE, 'ROLE_ADMIN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
