-- Drop existing tables (if any)
DROP TABLE IF EXISTS restaurant;

-- Create the restaurant table
CREATE TABLE restaurant (
    id INT PRIMARY KEY AUTO_INCREMENT,
    city VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    estimated_cost INT NOT NULL,
    average_rating VARCHAR(10) NOT NULL,
    votes INT NOT NULL
);

-- Insert sample data
INSERT INTO restaurant (city, name, estimated_cost, average_rating, votes) VALUES
('Miami', 'Byg Cheeseburger', 1600, '4.9', 16203),
('New York', 'Food Palace', 1800, '4.5', 12000),
('Los Angeles', 'Tasty Bites', 2000, '4.8', 15000);