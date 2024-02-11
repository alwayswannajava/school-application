CREATE TABLE IF NOT EXISTS groups
(
    group_id   INT PRIMARY KEY,
    group_name VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS students
(
    student_id SERIAL PRIMARY KEY,
    group_id   INT         NOT NULL DEFAULT 0 REFERENCES groups ON UPDATE CASCADE ON DELETE CASCADE,
    first_name VARCHAR(30) NOT NULL,
    last_name  VARCHAR(30) NOT NULL,
    FOREIGN KEY (group_id) REFERENCES groups (group_id)
);

CREATE TABLE IF NOT EXISTS courses
(
    course_id          INT PRIMARY KEY,
    course_name        VARCHAR(30)  NOT NULL,
    course_description VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS students_courses
(
    student_id INT REFERENCES students (student_id) ON UPDATE CASCADE ON DELETE CASCADE,
    course_id  INT REFERENCES courses (course_id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT student_course_pkey PRIMARY KEY (student_id, course_id)
);