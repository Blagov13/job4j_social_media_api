CREATE TABLE friend_requests (
    id SERIAL PRIMARY KEY,
    sender_id long NOT NULL,
    receiver_id long NOT NULL,
    status varchar NOT NULL,
    created_at TIMESTAMP,
    FOREIGN KEY (sender_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (receiver_id) REFERENCES users(id) ON DELETE CASCADE
);