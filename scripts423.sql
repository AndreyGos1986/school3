SELECT student.name,student.age,faculty.name,faculty.color
FROM student
LEFT JOIN faculty ON student.faculty_id = faculty.id;

SELECT student_id,student.name,student.age FROM student
INNER JOIN avatar a ON student.id = a.student_id;