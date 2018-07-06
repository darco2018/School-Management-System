
INSERT IGNORE INTO users (id, username, email, password, enabled) VALUES(1, 'dev', 'dev@gmail.com', '$2a$10$LTFSrszGtMGLKA4DfU1YhetB.VCFZELQKNL1xfVu1DvXUe/LZ3PQi', true);
INSERT IGNORE INTO users (id, username, email, password, enabled) VALUES(2, 'admin', 'admin@gmail.com', '$2a$10$2VYRrT14AqU0mKMSE3yPqOGquk2/mEZJTgW0EZeZ3kJwDCG9NdSXK', true);
INSERT IGNORE INTO users (id, username, email, password, enabled) VALUES(4, 'schooladmin', 'schooladmin@gmail.com', '$2a$10$0ZL4ONWijMM7hqmKT7bhaOrhYjGgCMpEDFET91q3xKRHKyyWcFxIi', true);
INSERT IGNORE INTO users (id, username, email, password, enabled) VALUES(5, 'student', 'student@gmail.com', '$2a$10$oMXlsxw3RbVjedL4OWNNT.MbZJJ/NGZMZPXdS.4pjI1rW3tjSnTs2', true);
INSERT IGNORE INTO users (id, username, email, password, enabled) VALUES(6, 'teacher', 'teacher@gmail.com', '$2a$10$lZGjvuNWkgYRgkIwAfoY6u84LflGD7eu/zTKgbcZj51.imM6Cd066', true);
INSERT IGNORE INTO users (id, username, email, password, enabled) VALUES(7, 'parent', 'parent@gmail.com', '$2a$10$ZpS3Ei1A0qIaR8Bbscec0eEkkvLECGDHzQRdnTP2UCl87NZo/EtCe', true);

INSERT IGNORE INTO user_roles(id, username, user_role) VALUES(1, 'dev', 'ROLE_DEVELOPER');
INSERT IGNORE INTO user_roles(id, username, user_role) VALUES(2, 'dev', 'ROLE_ADMIN');
INSERT IGNORE INTO user_roles(id, username, user_role) VALUES(3, 'dev', 'ROLE_SCHOOLADMIN');
INSERT IGNORE INTO user_roles(id, username, user_role) VALUES(4, 'dev', 'ROLE_TEACHER');
INSERT IGNORE INTO user_roles(id, username, user_role) VALUES(5, 'dev', 'ROLE_STUDENT');
INSERT IGNORE INTO user_roles(id, username, user_role) VALUES(6, 'dev', 'ROLE_PARENT');

INSERT IGNORE INTO user_roles(id, username, user_role) VALUES(7, 'admin', 'ROLE_ADMIN');
INSERT IGNORE INTO user_roles(id, username, user_role) VALUES(8, 'admin', 'ROLE_SCHOOLADMIN');
INSERT IGNORE INTO user_roles(id, username, user_role) VALUES(9, 'admin', 'ROLE_TEACHER');
INSERT IGNORE INTO user_roles(id, username, user_role) VALUES(10, 'admin', 'ROLE_STUDENT');
INSERT IGNORE INTO user_roles(id, username, user_role) VALUES(11, 'admin', 'ROLE_PARENT');

INSERT IGNORE INTO user_roles(id, username, user_role) VALUES(12, 'schooladmin', 'ROLE_SCHOOLADMIN');
INSERT IGNORE INTO user_roles(id, username, user_role) VALUES(13, 'chooladmin', 'ROLE_TEACHER');
INSERT IGNORE INTO user_roles(id, username, user_role) VALUES(14, 'schooladmin', 'ROLE_STUDENT');
INSERT IGNORE INTO user_roles(id, username, user_role) VALUES(15, 'schooladmin', 'ROLE_PARENT');

INSERT IGNORE INTO user_roles(id, username, user_role) VALUES(16, 'teacher', 'ROLE_TEACHER');
INSERT IGNORE INTO user_roles(id, username, user_role) VALUES(17, 'student', 'ROLE_STUDENT');
INSERT IGNORE INTO user_roles(id, username, user_role) VALUES(18, 'parent', 'ROLE_PARENT');
