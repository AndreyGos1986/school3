SELECT student.name,student.age,faculty.name,faculty.color
FROM student
INNER JOIN faculty ON student.faculty_id = faculty.id;

SELECT student_id,student.name,student.age FROM student
JOIN avatar a ON student.id = a.student_id;