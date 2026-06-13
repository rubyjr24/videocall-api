DROP DATABASE IF EXISTS videocall;
CREATE DATABASE videocall;
USE videocall;

CREATE TABLE AUTH(
	id INT AUTO_INCREMENT PRIMARY KEY,
    token VARCHAR(256) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    expired_at TIMESTAMP NULL
);

CREATE TABLE USERS(
	id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(256) NOT NULL,
    password VARCHAR(256) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    auth_id INT,
    
	CONSTRAINT fk_users_auth
		FOREIGN KEY (auth_id)
		REFERENCES AUTH(id)
		ON DELETE SET NULL
);

CREATE TABLE USER_FAVORITES (
    user_id INT NOT NULL,
    user_favorite_id INT NOT NULL,
	
    PRIMARY KEY (user_id, user_favorite_id),
    
    CONSTRAINT fk_user_fav_user
        FOREIGN KEY (user_id)
        REFERENCES USERS(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_user_fav_favorite
        FOREIGN KEY (user_favorite_id)
        REFERENCES USERS(id)
        ON DELETE CASCADE
);

CREATE TABLE ROOMS (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    finished_at TIMESTAMP NULL
);

CREATE TABLE ROOM_INVITATIONS (
    room_id INT NOT NULL,
    user_id INT NOT NULL,
    is_owner BOOLEAN NOT NULL,
    joined_at TIMESTAMP NULL,
    left_at TIMESTAMP NULL,

    PRIMARY KEY (room_id, user_id),

    CONSTRAINT fk_room_inv_room
        FOREIGN KEY (room_id)
        REFERENCES ROOMS(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_room_inv_user
        FOREIGN KEY (user_id)
        REFERENCES USERS(id)
        ON DELETE CASCADE
);