-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
-- insert into myentity (id, field) values(1, 'field-1');
-- insert into myentity (id, field) values(2, 'field-2');
-- insert into myentity (id, field) values(3, 'field-3');
-- alter sequence myentity_seq restart with 4;

insert into subject (id, credits, description, name)
values(1, 3, 'la base de las matematicas', 'Matematicas generales')
ON CONFLICT (id) DO NOTHING;;


-- Professors
insert into professor (id, name, lastName, identification, email, birthDate, phoneNumber)
values(215, 'Juan', 'Garcia', '3698754', 'juan@gmail.com', '2000-09-19', '3669554875')
ON CONFLICT (id) DO NOTHING;


insert into professor (id, name, lastName, identification, email, birthDate, phoneNumber)
values(10, 'Mario', 'Morales', '369823754', 'mario@gmail.com', '1998-02-15', '65988888')
ON CONFLICT (id) DO NOTHING;


insert into professor (id, name, lastName, identification, email, birthDate, phoneNumber)
values(102, 'Fernando', 'Arias', '102', 'fernando@gmail.com', '1998-02-15', '651988888')
ON CONFLICT (id) DO NOTHING;


insert into professor (id, name, lastName, identification, email, birthDate, phoneNumber)
values(105, 'stiven', 'Carmelo', '10242', 'stiven@gmail.com', '2002-02-15', '6088888')
ON CONFLICT (id) DO NOTHING;


-- Students
insert into students (id, name, lastname, identification, email, birthdate, semester)
values(123, 'andres', 'Alzate', '102422', 'andres@gmail.com', '2000-02-15', 3)
ON CONFLICT (id) DO NOTHING;

insert into students (id, name, lastname, identification, email, birthdate, semester)
values(321, 'maria', 'Alzate', '1022422', 'marias@gmail.com', '1999-02-15', 3)
ON CONFLICT (id) DO NOTHING;

insert into students (id, name, lastname, identification, email, birthdate, semester)
values(428, 'andres', 'Garcia', '1024222', 'andresgar@gmail.com', '2004-02-15', 2)
ON CONFLICT (id) DO NOTHING;

insert into students (id, name, lastname, identification, email, birthdate, semester)
values(333, 'pedro', 'Zuluaga', '333', 'pedro@gmail.com', '2002-02-15', 1)
ON CONFLICT (id) DO NOTHING;

-- Subjects

INSERT INTO subject (id, name, description, credits)
VALUES (555, 'Programación I', 'Introducción a la programación con Java', 3)
ON CONFLICT (id) DO NOTHING;

INSERT INTO subject (id, name, description, credits)
VALUES (466, 'Estructuras de Datos', 'Estudio de estructuras fundamentales como listas, pilas y árboles', 4)
ON CONFLICT (id) DO NOTHING;

INSERT INTO subject (id, name, description, credits)
VALUES (35, 'Algoritmos y Estructuras de Datos', 'Estudio de algoritmos fundamentales y estructuras de datos avanzadas', 4)
ON CONFLICT (id) DO NOTHING;



insert into students (id, name, lastname, identification, email, birthdate, semester)
values(1, 'Juan', 'Miguel', '444', 'juanmiguel@gmail.com', '2002-02-15', 1)
ON CONFLICT (id) DO NOTHING;

INSERT INTO programs (id, title, description, code, student_id) VALUES (
1,
'prueba',
'Hello world',
'public class H{public static void main(String[]a){System.out.println("Hello, World!");}}',
1
);

INSERT INTO programs (id, title, description, code, student_id) VALUES (
2,
'prueba',
'suma',
'public class Suma{public static void main(String[]a){System.out.println(3+5);}}',
1
);

INSERT INTO programs (id, title, description, code, student_id) VALUES (
3,
'prueba',
'fecha',
'public class Fecha{public static void main(String[]a){System.out.println(java.time.LocalDate.now());}}',
1
);


INSERT INTO programs (id, title, description, code, student_id) VALUES (
4,
'prueba',
'Aleatorio',
'public class Aleatorio{public static void main(String[]a){System.out.println(Math.random());}}',
1
);


