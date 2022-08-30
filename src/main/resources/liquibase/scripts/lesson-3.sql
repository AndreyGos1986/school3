--liquibase formatted sql

--changeSet AndreyGos1986 : 1
CREATE INDEX student_name_index ON student (name);


--changeSet AndreyGos1986:2
CREATE INDEX faculty_name_and_color_index ON faculty (name, color);

--changeSet AndreyGos1986:3
drop index student_name_index;

--changeSet AndreyGos1986:4
drop index faculty_name_and_color_index;