select * from student;
select * from  student where age>10 and age<25;
select name from student;
select*from student where age<student.id;
select *from student where name like '%o%';
select *from student order by age;
SELECT COUNT (*) FROM Student;
SELECT AVG(age) FROM Student;
SELECT * FROM Student ORDER BY id DESC LIMIT 5;