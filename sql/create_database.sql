create database student_db default char set utf8;
use student_db;

create table student (
ID integer(10) not null auto_increment,
first_name varchar(20) not null,
second_name varchar(20) not null,
enter_year int(4) not null,
primary key(ID)
);

create table subject (
ID int(10) not null auto_increment,
student_id int(10) not null,
title varchar(50) not null,
primary key(ID),
foreign key(student_id) references student(ID)
);

create table mark (
ID int(10) not null auto_increment,
student_id int(10) not null,
subject_id int(10) not null,
mark int not null,
primary key(ID),
foreign key(student_id) references student(ID),
foreign key(subject_id) references subject(ID)
);