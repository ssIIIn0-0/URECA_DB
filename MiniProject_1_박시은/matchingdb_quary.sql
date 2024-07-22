use matchingdb;
CREATE TABLE Users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    email VARCHAR(100),
    password VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE Interests (
    interest_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100)
);

CREATE TABLE User_Interests (
    user_id INT,
    interest_id INT,
    PRIMARY KEY (user_id, interest_id),
    FOREIGN KEY (user_id) REFERENCES Users(user_id),
    FOREIGN KEY (interest_id) REFERENCES Interests(interest_id)
);

CREATE TABLE Recommendations (
    user_id INT,
    recommended_user_id INT,
    similarity_score FLOAT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, recommended_user_id),
    FOREIGN KEY (user_id) REFERENCES Users(user_id),
    FOREIGN KEY (recommended_user_id) REFERENCES Users(user_id)
);

CREATE TABLE profile (
    userId INT NOT NULL,
    userName VARCHAR(255) NOT NULL,
    selfIntroduce TEXT,
    PRIMARY KEY (userId)
);

ALTER TABLE recommendations DROP FOREIGN KEY user_id;

ALTER TABLE recommendations ADD CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users(user_id);
ALTER TABLE recommendations ADD CONSTRAINT fk_recommended_user_id FOREIGN KEY (recommended_user_id) REFERENCES users(user_id);
