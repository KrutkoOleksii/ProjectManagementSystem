DROP SCHEMA IF EXISTS hw2;
CREATE SCHEMA hw2;

DROP TABLE IF EXISTS hw2.companies;
CREATE TABLE hw2.companies (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(45) NOT NULL,
  code VARCHAR(10) NOT NULL,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS hw2.customers;
CREATE TABLE hw2.customers (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(45) NOT NULL,
  code VARCHAR(10) NOT NULL,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS hw2.developers;
CREATE TABLE hw2.developers (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(45) NOT NULL,
  age INT NOT NULL,
  gender VARCHAR(6) NULL,
  company_id INT NOT NULL,
  salary INT NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (company_id) REFERENCES  hw2.companies(id)
);

DROP TABLE IF EXISTS hw2.projects;
CREATE TABLE hw2.projects (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(45) NOT NULL,
  company_id INT NOT NULL,
  customer_id INT NOT NULL,
  start_date DATE,
  PRIMARY KEY (id),
  FOREIGN KEY (company_id) REFERENCES  hw2.companies(id),
  FOREIGN KEY (customer_id) REFERENCES  hw2.customers(id)
);

DROP TABLE IF EXISTS hw2.skills;
CREATE TABLE hw2.skills (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(45) NOT NULL,
  skill_level VARCHAR(45) NOT NULL,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS hw2.developer_skill;
CREATE TABLE hw2.developer_skill (
  skill_id INT NOT NULL,
  developer_id INT NOT NULL,
  PRIMARY KEY (skill_id,developer_id),
  FOREIGN KEY (skill_id) REFERENCES  hw2.skills(id),
  FOREIGN KEY (developer_id) REFERENCES  hw2.developers(id)
);

DROP TABLE IF EXISTS hw2.developer_project;
CREATE TABLE hw2.developer_project (
  project_id INT NOT NULL,
  developer_id INT NOT NULL,
  PRIMARY KEY (project_id,developer_id),
  FOREIGN KEY (project_id) REFERENCES  hw2.projects(id),
  FOREIGN KEY (developer_id) REFERENCES  hw2.developers(id)
);
