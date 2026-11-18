CREATE DATABASE employee_db;

USE employee_db;

CREATE TABLE employees
(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    department VARCHAR(100),
    salary DECIMAL(10,2)
);