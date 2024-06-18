CREATE TABLE users (
  id SERIAL PRIMARY KEY,
  user_name VARCHAR(50) UNIQUE NOT NULL,
  email VARCHAR(255) UNIQUE NOT NULL,
  first_name VARCHAR(50) NOT NULL,
  last_name VARCHAR(50) NOT NULL,
  other_name VARCHAR(50),
  address VARCHAR(50) NOT NULL,
  phone_number VARCHAR(50) NOT NULL,
  date_of_birth DATE NOT NULL,
  user_role VARCHAR(20) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT now(),
  updated_at TIMESTAMP NOT NULL DEFAULT now()

);

