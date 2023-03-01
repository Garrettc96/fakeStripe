CREATE DATABASE stripe;
CREATE TABLE IF NOT EXISTS stripe.users (
    user_id BIGSERIAL PRIMARY KEY,
    username text,
    password text,
    email text
    last_logged_in timestamp without timezone
)