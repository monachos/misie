
DROP TABLE IF EXISTS Grade;
DROP TABLE IF EXISTS Student_Subject;
DROP TABLE IF EXISTS Class;
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
    quantity INTEGER
);


CREATE TABLE Address(
    id INTEGER IDENTITY PRIMARY KEY,
    address_line_1 varchar,
    address_line_2 varchar,
);

CREATE TABLE SOS_User(
    id INTEGER IDENTITY PRIMARY KEY,
    student_id_number varchar,
    active boolean,
    deleted boolean,
    email varchar,
    password varchar,
    phone_2fa varchar,
    accepted_privacy_policy boolean,
    accepted_terms_of_use boolean,
    name varchar,
    surname varchar,
    registered_address INTEGER FOREIGN KEY REFERENCES ADDRESS,
    residential_address INTEGER FOREIGN KEY REFERENCES ADDRESS,
    correspondence_address INTEGER FOREIGN KEY REFERENCES ADDRESS,
    blocked_account boolean,
    block_time datetime
);

CREATE TABLE Payment(
                        id INTEGER IDENTITY PRIMARY KEY,
                        student_id INTEGER FOREIGN KEY REFERENCES SOS_User,
                        type varchar,
                        amount DECIMAL(10,2)
);

CREATE TABLE Reservation(
      id INTEGER IDENTITY PRIMARY KEY,
      reservation_dare date,
      reservation_status varchar,
      user_id INTEGER FOREIGN KEY REFERENCES SOS_User,
      library_item_id INTEGER FOREIGN KEY REFERENCES Library_Item,
);

CREATE TABLE Lending(
       id INTEGER IDENTITY PRIMARY KEY,
       lending_date date,
       return_date varchar,
       user_id INTEGER FOREIGN KEY REFERENCES SOS_User,
       library_item_id INTEGER FOREIGN KEY REFERENCES Library_Item,
);


CREATE TABLE Department(
    id INTEGER IDENTITY PRIMARY KEY,
    name varchar
)

CREATE TABLE SOS_Role(
    id INTEGER IDENTITY PRIMARY KEY,
    department_id INTEGER FOREIGN KEY REFERENCES Department,
    name varchar,
    is_admin BOOLEAN
);

CREATE TABLE Subject(
    id INTEGER IDENTITY PRIMARY KEY,
    time datetime,
    description varchar,
    title varchar,
    max_students INTEGER,
    is_active boolean,
    semester varchar,
    academic_year INTEGER,
    registration_start datetime,
    registration_end datetime,
    room_number INTEGER,
    lecturer_id INTEGER FOREIGN KEY REFERENCES SOS_User,
    department_id INTEGER FOREIGN KEY REFERENCES Department
)

CREATE TABLE Class(
    id INTEGER IDENTITY PRIMARY KEY,
    subject_id INTEGER FOREIGN KEY REFERENCES Subject,
    start_date datetime,
    end_date datetime
)

CREATE TABLE Student_Subject(
    id INTEGER IDENTITY PRIMARY KEY,
    student_id INTEGER FOREIGN KEY REFERENCES SOS_User,
    subject_id INTEGER FOREIGN KEY REFERENCES Subject,
    registration_time datetime
)


CREATE TABLE Grade(
    id INTEGER IDENTITY PRIMARY KEY,
    student_subject_id INTEGER FOREIGN KEY REFERENCES Student_Subject,
    grade DECIMAL(2,1),
    type varchar,
    grading_date datetime
    )

