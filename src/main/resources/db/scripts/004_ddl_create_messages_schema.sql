CREATE TABLE messages (
    id SERIAL PRIMARY KEY,
    sender_id long NOT NULL,
    receiver_id long NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP,
    FOREIGN KEY (sender_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (receiver_id) REFERENCES users(id) ON DELETE CASCADE
);