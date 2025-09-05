-- Test database connection
-- Run this in your MySQL service to verify it's working

-- Test 1: Check if we can connect
SELECT 'Database connection successful' as status;

-- Test 2: Check current user
SELECT USER() as current_user;

-- Test 3: Check database
SELECT DATABASE() as current_database;

-- Test 4: Check tables (should be empty initially)
SHOW TABLES;

-- Test 5: Check privileges
SHOW GRANTS FOR 'root'@'%';
