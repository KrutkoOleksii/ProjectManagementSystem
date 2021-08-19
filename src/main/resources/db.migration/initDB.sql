CREATE SCHEMA hw2;

CREATE TABLE hw2.companies (
  company_id INT NOT NULL,
  company_name VARCHAR(45) NOT NULL,
  company_code VARCHAR(10) NOT NULL,
  PRIMARY KEY (company_id)
);

CREATE TABLE hw2.customers (
  customer_id INT NOT NULL,
  customer_name VARCHAR(45) NOT NULL,
  customer_code VARCHAR(10) NOT NULL,
  PRIMARY KEY (customer_id)
);

CREATE TABLE hw2.developers (
  developer_id INT NOT NULL,
  developer_name VARCHAR(45) NULL,
  age INT NULL,
  sex VARCHAR(6) NULL,
  company_id INT NOT NULL,
  PRIMARY KEY (developer_id),
  FOREIGN KEY (company_id) REFERENCES  hw2.companies(company_id)
);

CREATE TABLE hw2.projects (
  project_id INT NOT NULL,
  project_name VARCHAR(45) NULL,
  company_id INT NOT NULL,
  customer_id INT NOT NULL,
  PRIMARY KEY (project_id),
  FOREIGN KEY (company_id) REFERENCES  hw2.companies(company_id),
  FOREIGN KEY (customer_id) REFERENCES  hw2.customers(customer_id)
);

CREATE TABLE hw2.skills (
  skill_id INT NOT NULL,
  skill_name VARCHAR(45) NOT NULL,
  skill_level VARCHAR(45) NOT NULL,
  PRIMARY KEY (skill_id)
);


CREATE TABLE hw2.developer_skill (
  skill_id INT NOT NULL,
  developer_id INT NOT NULL,
  PRIMARY KEY (skill_id,developer_id),
  FOREIGN KEY (skill_id) REFERENCES  hw2.skills(skill_id),
  FOREIGN KEY (developer_id) REFERENCES  hw2.developers(developer_id)
);

CREATE TABLE hw2.developer_project (
  project_id INT NOT NULL,
  developer_id INT NOT NULL,
  PRIMARY KEY (project_id,developer_id),
  FOREIGN KEY (project_id) REFERENCES  hw2.projects(project_id),
  FOREIGN KEY (developer_id) REFERENCES  hw2.developers(developer_id)
);
