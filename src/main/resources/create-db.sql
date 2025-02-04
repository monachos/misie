
DROP TABLE IF EXISTS Grade;

DROP TABLE IF EXISTS Student_Subject;
DROP TABLE IF EXISTS University_Class;
DROP TABLE IF EXISTS Subject;
DROP TABLE IF EXISTS SOS_Role;
DROP TABLE IF EXISTS Department;
DROP TABLE IF EXISTS Lending;
DROP TABLE IF EXISTS Reservation;
DROP TABLE IF EXISTS Payment;
DROP TABLE IF EXISTS SOS_User;
DROP TABLE IF EXISTS Address;
DROP TABLE IF EXISTS Library_Item;


CREATE TABLE Library_Item(
    id INTEGER IDENTITY PRIMARY KEY,
    quantity INTEGER NOT NULL
);


CREATE TABLE Address(
    id INTEGER IDENTITY PRIMARY KEY,
    address_line_1 varchar NOT NULL,
    address_line_2 varchar NOT NULL,
);

CREATE TABLE SOS_User(
    id INTEGER IDENTITY PRIMARY KEY,
    student_id_number varchar UNIQUE,
    active boolean NOT NULL,
    deleted boolean NOT NULL,
    email varchar NOT NULL,
    password varchar NOT NULL,
    phone_2fa varchar,
    accepted_privacy_policy boolean DEFAULT false NOT NULL ,
    accepted_terms_of_use boolean DEFAULT false NOT NULL ,
    name varchar NOT NULL,
    surname varchar NOT NULL,
    registered_address INTEGER NOT NULL FOREIGN KEY REFERENCES ADDRESS,
    residential_address INTEGER FOREIGN KEY REFERENCES ADDRESS,
    correspondence_address INTEGER FOREIGN KEY REFERENCES ADDRESS,
    blocked_account boolean DEFAULT false NOT NULL,
    block_time datetime
);

CREATE TABLE Payment(
                        id INTEGER IDENTITY PRIMARY KEY,
                        student_id INTEGER NOT NULL FOREIGN KEY REFERENCES SOS_User,
                        type varchar NOT NULL,
                        amount DECIMAL(10,2) NOT NULL
);

CREATE TABLE Reservation(
      id INTEGER IDENTITY PRIMARY KEY,
      reservation_dare date NOT NULL,
      reservation_status varchar NOT NULL,
      user_id INTEGER FOREIGN NOT NULL KEY REFERENCES SOS_User,
      library_item_id INTEGER NOT NULL FOREIGN KEY REFERENCES Library_Item,
);

CREATE TABLE Lending(
       id INTEGER IDENTITY PRIMARY KEY,
       lending_date date NOT NULL,
       return_date varchar,
       user_id INTEGER NOT NULL FOREIGN KEY REFERENCES SOS_User,
       library_item_id INTEGER NOT NULL FOREIGN KEY REFERENCES Library_Item,
);


CREATE TABLE Department(
    id INTEGER IDENTITY PRIMARY KEY,
    name varchar NOT NULL
)

CREATE TABLE SOS_Role(
    id INTEGER IDENTITY PRIMARY KEY,
    department_id INTEGER NOT NULL FOREIGN KEY REFERENCES Department,
    name varchar NOT NULL,
    is_admin BOOLEAN DEFAULT false NOT NULL
);

CREATE TABLE Subject(
    id INTEGER IDENTITY PRIMARY KEY,
    time datetime NOT NULL,
    description varchar NOT NULL,
    title varchar NOT NULL,
    max_students INTEGER NOT NULL,
    is_active boolean,
    semester varchar NOT NULL,
    academic_year INTEGER NOT NULL,
    registration_start datetime NOT NULL,
    registration_end datetime NOT NULL,
    room_number INTEGER NOT NULL,
    lecturer_id INTEGER FOREIGN KEY REFERENCES SOS_User,
    department_id INTEGER NOT NULL FOREIGN KEY REFERENCES Department
);

CREATE TABLE University_Class(
    id INTEGER IDENTITY PRIMARY KEY,
    subject_id INTEGER NOT NULL FOREIGN KEY REFERENCES Subject,
    start_date datetime NOT NULL,
    end_date datetime NOT NULL
);

CREATE TABLE Student_Subject(
    id INTEGER IDENTITY PRIMARY KEY,
    student_id INTEGER NOT NULL FOREIGN KEY REFERENCES SOS_User,
    subject_id INTEGER NOT NULL FOREIGN KEY REFERENCES Subject,
    registration_time datetime
);


CREATE TABLE Grade(
    id INTEGER IDENTITY PRIMARY KEY,
    student_subject_id INTEGER NOT NULL FOREIGN KEY REFERENCES Student_Subject,
    grade DECIMAL(2,1) NOT NULL,
    type varchar NOT NULL,
    grading_date datetime NOT NULL
    );

