CREATE DATABASE IF NOT EXISTS codeforces;

USE codeforces;

CREATE TABLE IF NOT EXISTS problems (
    id INT PRIMARY KEY AUTO_INCREMENT,
    contest_id INT NOT NULL,
    problem_index VARCHAR(5) NOT NULL,
    problem_name VARCHAR(255) NOT NULL,
    rating INT,
    solution_file VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
