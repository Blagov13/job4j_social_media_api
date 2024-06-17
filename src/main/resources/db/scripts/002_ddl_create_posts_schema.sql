CREATE TABLE posts (
     id SERIAL PRIMARY KEY,
    title varchar NOT NULL,
    text TEXT NOT NULL,
    image_url varchar,
    created_at TIMESTAMP,
    author_id BIGINT NOT NULL,
    FOREIGN KEY (author_id) REFERENCES users(id) ON DELETE CASCADE
);