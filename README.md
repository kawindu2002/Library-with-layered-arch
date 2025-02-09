# LibraryProject

Hello guys! This is my library management project..

============================== START ==================================


-- Drop the database if it exists
DROP DATABASE IF EXISTS Library;

-- Create the Library database
CREATE DATABASE Library;

-- Switch to the Library database
USE Library;


============================ Member Table ============================00000


CREATE TABLE Member (
    Member_Id VARCHAR(30) PRIMARY KEY,
    Name VARCHAR(50),
    Address VARCHAR(50),
    Email VARCHAR(75),
    Contact VARCHAR(15),
    Reg_date DATE,
    State VARCHAR(30)
);


============================ Book Table ============================00000


CREATE TABLE Book (
    Book_Id VARCHAR(30) PRIMARY KEY,
    Title VARCHAR(50),
    ISBN VARCHAR(40),
    Reg_date DATE,
    Publisher_Id VARCHAR(30),
    Price DECIMAL(10, 2),
    State VARCHAR(30),
    Bookshelf_Id VARCHAR(30),
    FOREIGN KEY (Publisher_Id) REFERENCES Publisher(Publisher_Id)
        ON UPDATE CASCADE,
    FOREIGN KEY (Bookshelf_Id) REFERENCES Bookshelf(Bookshelf_Id)
        ON UPDATE CASCADE
);


============================ Author Table ============================00000


CREATE TABLE Author (
    Author_Id VARCHAR(30) PRIMARY KEY,
    Name VARCHAR(50),
    Biography VARCHAR(70),
    Reg_date DATE
);


============================ Author_Book Table ============================000000


CREATE TABLE Author_Book (
    Book_Id VARCHAR(30),
    Author_Id VARCHAR(30),
    FOREIGN KEY (Book_Id) REFERENCES Book(Book_Id)
        ON UPDATE CASCADE,
    FOREIGN KEY (Author_Id) REFERENCES Author(Author_Id)
        ON UPDATE CASCADE
);


============================ Publisher Table ============================00000


CREATE TABLE Publisher (
    Publisher_Id VARCHAR(30) PRIMARY KEY,
    Name VARCHAR(50),
    Address VARCHAR(70),
    Reg_date DATE
);

============================ Category Table ============================000000


CREATE TABLE Category (
    Category_Id VARCHAR(30) PRIMARY KEY,
    Description VARCHAR(50),
    Reg_date DATE
);

============================ Category_Book Table ============================00000

CREATE TABLE Category_Book (
    Book_Id VARCHAR(30),
    Category_Id VARCHAR(30),
    FOREIGN KEY (Book_Id) REFERENCES Book(Book_Id)
        ON UPDATE CASCADE,
    FOREIGN KEY (Category_Id) REFERENCES Category(Category_Id)
        ON UPDATE CASCADE
);

============================ Bookshelf Table ============================000000


CREATE TABLE Bookshelf (
    Bookshelf_Id VARCHAR(30) PRIMARY KEY,
    Category_Id VARCHAR(30),
    Capacity INT,
    Location VARCHAR(50),
    FOREIGN KEY (Category_Id) REFERENCES Category(Category_Id)
        ON UPDATE CASCADE
);



============================ Loan Table  ============================000000


CREATE TABLE Loan (
    Loan_Id VARCHAR(30) PRIMARY KEY,
    User_Id VARCHAR(30),
    Member_Id VARCHAR(30),
    Book_Id VARCHAR(30),
    Loan_Date DATE,
    Due_Date DATE,
    State VARCHAR(30),
    FOREIGN KEY (User_Id) REFERENCES User(User_Id)
        ON UPDATE CASCADE,
    FOREIGN KEY (Member_Id) REFERENCES Member(Member_Id)
        ON UPDATE CASCADE,
    FOREIGN KEY (Book_Id) REFERENCES Book(Book_Id)
        ON UPDATE CASCADE
);

============================ Returns Table ============================000000


CREATE TABLE Returns (
    Returns_Id VARCHAR(30) PRIMARY KEY,
    User_Id VARCHAR(30),
    Member_Id VARCHAR(30),
    Loan_Id VARCHAR(30),
    Book_Id VARCHAR(30),
    Returns_Date DATE,
    State VARCHAR(30),
    FOREIGN KEY (User_Id) REFERENCES User(User_Id)
        ON UPDATE CASCADE,
    FOREIGN KEY (Member_Id) REFERENCES Member(Member_Id)
        ON UPDATE CASCADE,
    FOREIGN KEY (Loan_Id) REFERENCES Loan(Loan_Id)
        ON UPDATE CASCADE,
    FOREIGN KEY (Book_Id) REFERENCES Book(Book_Id)
        ON UPDATE CASCADE
);


============================ Fine Table ============================000000


CREATE TABLE Fine (
    Fine_Id VARCHAR(30) PRIMARY KEY,
    User_Id VARCHAR(30),
    Member_Id VARCHAR(30),
    Loan_Id VARCHAR(30),
    Price DECIMAL(10, 2),
    Fine_date DATE,
    Reason VARCHAR(30),
    FOREIGN KEY (User_Id) REFERENCES User(User_Id)
        ON UPDATE CASCADE,
    FOREIGN KEY (Member_Id) REFERENCES Member(Member_Id)
        ON UPDATE CASCADE,
    FOREIGN KEY (Loan_Id) REFERENCES Loan(Loan_Id)
        ON UPDATE CASCADE
);


============================ Payment Table ============================000000


CREATE TABLE Payment (
    Payment_Id VARCHAR(30) PRIMARY KEY,
    Member_Id VARCHAR(30),
    Purpose VARCHAR(50),
    Price DECIMAL(10, 2),
    Payment_date DATE,
    User_Id VARCHAR(30),
    FOREIGN KEY (Member_Id) REFERENCES Member(Member_Id)
        ON UPDATE CASCADE,
    FOREIGN KEY (User_Id) REFERENCES User(User_Id)
        ON UPDATE CASCADE
);


============================ Reservation Table ============================00000


CREATE TABLE Reservation (
    Reservation_Id VARCHAR(30) PRIMARY KEY,
    Member_Id VARCHAR(30),
    Book_Id VARCHAR(30),
    User_Id VARCHAR(30),
    Reservation_date DATE,
    State VARCHAR(30),
    FOREIGN KEY (Member_Id) REFERENCES Member(Member_Id)
        ON UPDATE CASCADE,
    FOREIGN KEY (Book_Id) REFERENCES Book(Book_Id)
        ON UPDATE CASCADE,
    FOREIGN KEY (User_Id) REFERENCES User(User_Id)
        ON UPDATE CASCADE
);


============================ User Table ============================ 00000


CREATE TABLE User (
    User_Id VARCHAR(30) PRIMARY KEY,
    Name VARCHAR(50),
    Password VARCHAR(50),
    Role VARCHAR(40),
    Reg_date DATE,
    State VARCHAR(30)
);


============================ Table Descriptions ============================

DESCRIBE Member;
DESCRIBE Book;
DESCRIBE Author;
DESCRIBE Author_Book;
DESCRIBE Publisher;
DESCRIBE Category;
DESCRIBE Category_Book;
DESCRIBE Bookshelf;
DESCRIBE Loan;
DESCRIBE Returns;
DESCRIBE Fine;
DESCRIBE Payment;
DESCRIBE Reservation;
DESCRIBE User;

================================== END =====================================


