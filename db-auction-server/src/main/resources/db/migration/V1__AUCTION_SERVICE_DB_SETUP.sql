CREATE TABLE products (
    product_id      UUID PRIMARY KEY,
    name            VARCHAR(255) NOT NULL,
    description     VARCHAR(255),
    price           DOUBLE PRECISION NOT NULL,
    quantity        INTEGER NOT NULL
);

CREATE TABLE auction (
    auction_id              UUID PRIMARY KEY,
    name                    VARCHAR(50) NOT NULL,
    description             VARCHAR(100),
    min_price               DOUBLE PRECISION NOT NULL,
    winning_price           DOUBLE PRECISION,
    start_date              TIMESTAMP NOT NULL,
    end_date                TIMESTAMP NOT NULL,
    seller_id               UUID NOT NULL,
    auction_winner_id       UUID NULL,
    is_completed            BOOLEAN,
    product_id              UUID NOT NULL
);

CREATE TABLE bid (
    bid_id              UUID PRIMARY KEY,
    amount              DOUBLE PRECISION NOT NULL,
    bidder_id           UUID NOT NULL,
    auction_id          UUID NOT NULL,
    product_id          UUID NOT NULL,
    created_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
