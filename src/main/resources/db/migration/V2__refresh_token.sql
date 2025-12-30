CREATE TABLE refresh_tokens (
                                id BIGSERIAL PRIMARY KEY,
                                token VARCHAR(255) UNIQUE NOT NULL,
                                user_id BIGINT NOT NULL,
                                expiry_date TIMESTAMP NOT NULL,
                                created_at TIMESTAMP NOT NULL,
                                updated_at TIMESTAMP,
                                CONSTRAINT fk_refresh_user FOREIGN KEY (user_id) REFERENCES users(id)
);
