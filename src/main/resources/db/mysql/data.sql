INSERT IGNORE INTO schoolforms (id, is_deleted, name) VALUES (6, 0, '6F');
INSERT IGNORE INTO schoolforms (id, is_deleted, name) VALUES (1, 0, '1A');
INSERT IGNORE INTO schoolforms (id, is_deleted, name) VALUES (2, 0, '2B');
INSERT IGNORE INTO schoolforms (id, is_deleted, name) VALUES (4, 0, '4D');
INSERT IGNORE INTO schoolforms (id, is_deleted, name) VALUES (5, 0, '5E');
INSERT IGNORE INTO schoolforms (id, is_deleted, name) VALUES (3, 0, '3C');


INSERT IGNORE INTO subjects (id, is_deleted, name) VALUES (3, 0, 'IT');
INSERT IGNORE INTO subjects (id, is_deleted, name) VALUES (6, 0, 'Geography');
INSERT IGNORE INTO subjects (id, is_deleted, name) VALUES (1, 0, 'Biology');
INSERT IGNORE INTO subjects (id, is_deleted, name) VALUES (2, 0, 'Physics');
INSERT IGNORE INTO subjects (id, is_deleted, name) VALUES (4, 0, 'Maths');
INSERT IGNORE INTO subjects (id, is_deleted, name) VALUES (5, 0, 'English');

INSERT IGNORE INTO students (id, is_deleted, address, birth_date, email, first_name, last_name, password, telephone, schoolform_id) VALUES ('4', 0, 'uuudddddd', '2002-04-01', 'd@wp.pl', 'Daria', 'Dickinson', 'ddd', '911111', '2');
INSERT IGNORE INTO students (id, is_deleted, address, birth_date, email, first_name, last_name, password, telephone, schoolform_id) VALUES ('3', 0, 'ccccccccc', '2001-04-01', 'c@wp.pl', 'Camille', 'Camel', 'ccc', '711111', '1');
INSERT IGNORE INTO students (id, is_deleted, address, birth_date, email, first_name, last_name, password, telephone, schoolform_id) VALUES ('1', 0, 'xxxaaaaaaaa', '2000-04-01', 'a@wp.pl', 'Anna', 'Angus', 'aaa', '111111', '1');
INSERT IGNORE INTO students (id, is_deleted, address, birth_date, email, first_name, last_name, password, telephone, schoolform_id) VALUES ('2', 0, 'bbbbbbbbb', '2004-04-01', 'b@wp.pl', 'Betty', 'Benneth', 'bbb', '411111', '1');
INSERT IGNORE INTO students (id, is_deleted, address, birth_date, email, first_name, last_name, password, telephone, schoolform_id) VALUES ('5', 0, 'oooeeeeeeee', '2000-04-01', 'e@wp.pl', 'Edith', 'Edmunds', 'eee', '111111', '2');
INSERT IGNORE INTO students (id, is_deleted, address, birth_date, email, first_name, last_name, password, telephone, schoolform_id) VALUES ('6', 0, 'fffffffff', '2006-04-01', 'f@wp.pl', 'Fuscia', 'Forest', 'fff', '311111', '3');
INSERT IGNORE INTO students (id, is_deleted, address, birth_date, email, first_name, last_name, password, telephone, schoolform_id) VALUES ('7', 0, 'wwwgggggg', '2000-04-01', 'g@wp.pl', 'Grace', 'Grog', 'ggg', '611111', '4');

INSERT IGNORE INTO teachers (id, is_deleted, address, birth_date, email, first_name, last_name, password, telephone) VALUES ('6', 0, 'fffffffff', '2000-04-01', 'f@wp.pl', 'Finn', 'Flowery', 'fff', '111111');
INSERT IGNORE INTO teachers (id, is_deleted, address, birth_date, email, first_name, last_name, password, telephone) VALUES ('7', 0, 'uuudddddd', '2000-04-01', 'g@wp.pl', 'Greg', 'Green', 'ggg', '411111');
INSERT IGNORE INTO teachers (id, is_deleted, address, birth_date, email, first_name, last_name, password, telephone) VALUES ('1', 0, 'wwwgggggg', '2000-04-01', 'a@wp.pl', 'Alfred', 'Albinoss', 'aaa', '511111');
INSERT IGNORE INTO teachers (id, is_deleted, address, birth_date, email, first_name, last_name, password, telephone) VALUES ('2', 0, 'bbbbbbbbb', '2000-04-01', 'b@wp.pl', 'Ben', 'Brown', 'bbb', '811111');
INSERT IGNORE INTO teachers (id, is_deleted, address, birth_date, email, first_name, last_name, password, telephone) VALUES ('3', 0, 'ccccccccc', '2000-04-01', 'c@wp.pl', 'Claude', 'Cherryred', 'ccc', '111111');
INSERT IGNORE INTO teachers (id, is_deleted, address, birth_date, email, first_name, last_name, password, telephone) VALUES ('4', 0, 'ddddddddd', '2000-04-01', 'd@wp.pl', 'Dick', 'Dark', 'ddd', '111111');
INSERT IGNORE INTO teachers (id, is_deleted, address, birth_date, email, first_name, last_name, password, telephone) VALUES ('5', 0, 'xxxaaaaaaaa', '2000-04-01', 'e@wp.pl', 'Ed', 'Emerald', 'eee', '211111');

INSERT IGNORE INTO teachers_subjects_schoolforms (id, teacher_id, subject_id, schoolform_id) VALUES (9, 6, 2, 3);
INSERT IGNORE INTO teachers_subjects_schoolforms (id, teacher_id, subject_id, schoolform_id) VALUES (1, 1, 1, 1);
INSERT IGNORE INTO teachers_subjects_schoolforms (id, teacher_id, subject_id, schoolform_id) VALUES (2, 1, 6, 1);
INSERT IGNORE INTO teachers_subjects_schoolforms (id, teacher_id, subject_id, schoolform_id) VALUES (3, 2, 2, 1);
INSERT IGNORE INTO teachers_subjects_schoolforms (id, teacher_id, subject_id, schoolform_id) VALUES (4, 2, 3, 1);
INSERT IGNORE INTO teachers_subjects_schoolforms (id, teacher_id, subject_id, schoolform_id) VALUES (5, 2, 4, 2);
INSERT IGNORE INTO teachers_subjects_schoolforms (id, teacher_id, subject_id, schoolform_id) VALUES (6, 3, 6, 3);
INSERT IGNORE INTO teachers_subjects_schoolforms (id, teacher_id, subject_id, schoolform_id) VALUES (7, 4, 5, 4);
INSERT IGNORE INTO teachers_subjects_schoolforms (id, teacher_id, subject_id, schoolform_id) VALUES (8, 5, 4, 4);

INSERT IGNORE INTO grades (id, student_id, subject_id, grade_value) VALUES (1, 1, 1, '3');
INSERT IGNORE INTO grades (id, student_id, subject_id, grade_value) VALUES (2, 1, 1, '4');
INSERT IGNORE INTO grades (id, student_id, subject_id, grade_value) VALUES (3, 1, 1, '3');
INSERT IGNORE INTO grades (id, student_id, subject_id, grade_value) VALUES (4, 1, 2, '3');
INSERT IGNORE INTO grades (id, student_id, subject_id, grade_value) VALUES (5, 1, 2, '4');
INSERT IGNORE INTO grades (id, student_id, subject_id, grade_value) VALUES (6, 1, 6, '3');
INSERT IGNORE INTO grades (id, student_id, subject_id, grade_value) VALUES (7, 3, 1, '5');
INSERT IGNORE INTO grades (id, student_id, subject_id, grade_value) VALUES (8, 3, 2, '2');
INSERT IGNORE INTO grades (id, student_id, subject_id, grade_value) VALUES (9, 3, 3, '2.5');
INSERT IGNORE INTO grades (id, student_id, subject_id, grade_value) VALUES(10, 3, 4, '4');
INSERT IGNORE INTO grades (id, student_id, subject_id, grade_value) VALUES(11, 2, 1, '4.5');
INSERT IGNORE INTO grades (id, student_id, subject_id, grade_value) VALUES(12, 2, 3, '2');
INSERT IGNORE INTO grades (id, student_id, subject_id, grade_value) VALUES(13, 4, 3, '3.5');
INSERT IGNORE INTO grades (id, student_id, subject_id, grade_value) VALUES(14, 4, 3, '5');
INSERT IGNORE INTO grades (id, student_id, subject_id, grade_value) VALUES(15, 5, 2, '4');
INSERT IGNORE INTO grades (id, student_id, subject_id, grade_value) VALUES(16, 6, 2, '6');

INSERT IGNORE INTO users (id, username, password) VALUES(1, 'dev', '$2a$10$LTFSrszGtMGLKA4DfU1YhetB.VCFZELQKNL1xfVu1DvXUe/LZ3PQi');
INSERT IGNORE INTO users (id, username, password) VALUES(2, 'admin', '$2a$10$2VYRrT14AqU0mKMSE3yPqOGquk2/mEZJTgW0EZeZ3kJwDCG9NdSXK');
INSERT IGNORE INTO users (id, username, password) VALUES(3, 'schooladmin', '$2a$10$0ZL4ONWijMM7hqmKT7bhaOrhYjGgCMpEDFET91q3xKRHKyyWcFxIi');
INSERT IGNORE INTO users (id, username, password) VALUES(4, 'student', '$2a$10$oMXlsxw3RbVjedL4OWNNT.MbZJJ/NGZMZPXdS.4pjI1rW3tjSnTs2');
INSERT IGNORE INTO users (id, username, password) VALUES(5, 'teacher', '$2a$10$lZGjvuNWkgYRgkIwAfoY6u84LflGD7eu/zTKgbcZj51.imM6Cd066');
INSERT IGNORE INTO users (id, username, password) VALUES(6, 'parent', '$2a$10$ZpS3Ei1A0qIaR8Bbscec0eEkkvLECGDHzQRdnTP2UCl87NZo/EtCe');
INSERT IGNORE INTO users (id, username, password) VALUES(7, 'darek', '$2a$10$vcZOpdTlGMrG1EhxP.BLZez9hyZBfJYKfld3x9oMW3igd116xovf2');

INSERT IGNORE INTO authorities(id, username, authority) VALUES(1, 'dev', 'ROLE_DEV');
INSERT IGNORE INTO authorities(id, username, authority) VALUES(2, 'admin', 'ROLE_ADMIN');
INSERT IGNORE INTO authorities(id, username, authority) VALUES(3, 'schooladmin', 'ROLE_SCHOOLADMIN');
INSERT IGNORE INTO authorities(id, username, authority) VALUES(4, 'student', 'ROLE_STUDENT');
INSERT IGNORE INTO authorities(id, username, authority) VALUES(5, 'teacher', 'ROLE_TEACHER');
INSERT IGNORE INTO authorities(id, username, authority) VALUES(6, 'parent', 'ROLE_PARENT');
INSERT IGNORE INTO authorities(id, username, authority) VALUES(7, 'darek', 'ROLE_DEV');






