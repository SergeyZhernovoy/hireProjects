DROP TABLE IF EXISTS users;
create TABLE IF NOT EXISTS users(
      ID SERIAL PRIMARY KEY,
      NAME VARCHAR (255) NOT NULL UNIQUE,
      PASSWORD VARCHAR (255) NOT NULL,
      EMAIL VARCHAR (255) NOT NULL UNIQUE,
      ENABLE BOOLEAN DEFAULT FALSE
);
DROP TABLE IF EXISTS books;
CREATE TABLE IF NOT EXISTS books (
       ID SERIAL PRIMARY KEY,
       AUTHOR VARCHAR (255) NOT NULL,
       TITLE VARCHAR (255) NOT NULL,
       USER_ID INTEGER REFERENCES users(ID),
       SHELF_ID INTEGER REFERENCES shelves(ID),
       BLOB BYTEA
);
DROP TABLE IF EXISTS shelves;
CREATE TABLE IF NOT EXISTS shelves (
       ID SERIAL PRIMARY KEY,
       NAME VARCHAR (255) NOT NULL
);
DROP TABLE IF EXISTS registration;
CREATE TABLE IF NOT EXISTS registration (
       ID SERIAL PRIMARY KEY,
       TOKEN VARCHAR (255) NOT NULL UNIQUE,
       USER_ID INTEGER REFERENCES users(ID),
       EXPIRE_DATE TIMESTAMP
);
DROP TABLE IF EXISTS filling_shelves;
DROP TABLE IF EXISTS request_response_book;
CREATE TABLE IF NOT EXISTS request_response_book (
       ID SERIAL PRIMARY KEY,
       MESSAGE VARCHAR (255),
       ANSWER VARCHAR (255),
       user_request_id INTEGER REFERENCES users(ID) NOT NULL,
       user_response_id INTEGER REFERENCES users(ID) NOT NULL,
       BOOK_ID INTEGER REFERENCES books(ID) NOT NULL,
       ENABLED BOOLEAN DEFAULT FALSE,
       expired TIMESTAMP
);
DROP TABLE IF EXISTS progress_reading;
CREATE TABLE IF NOT EXISTS progress_reading (
       ID SERIAL PRIMARY KEY,
       USER_ID INTEGER REFERENCES users(ID) NOT NULL,
       BOOK_ID INTEGER REFERENCES books(ID) NOT NULL,
       PAGE INTEGER NOT NULL
);