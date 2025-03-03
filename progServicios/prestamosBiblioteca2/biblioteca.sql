-- Crear la base de datos
CREATE DATABASE biblioteca;

-- Usar la base de datos
USE biblioteca;

-- Crear la tabla de libros
CREATE TABLE libros (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    autor VARCHAR(100) NOT NULL,
    paginas INT NOT NULL,
    genero VARCHAR(100) NOT NULL,
    disponible BOOLEAN DEFAULT TRUE
);

-- Crear la tabla de lectores
CREATE TABLE lectores (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    nombreLogin VARCHAR(20) NOT NULL UNIQUE,
    contraseña VARCHAR(100) NOT NULL,
    cod_curso INT
);

-- Crear la tabla de cursos
CREATE TABLE cursos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    descripcion VARCHAR(100) NOT NULL
);

-- Crear la tabla de préstamos
CREATE TABLE prestamos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_libro INT NOT NULL,
    id_lector INT NOT NULL,
    fechaAlta DATE NOT NULL,
    fechaBaja DATE,
    FOREIGN KEY (id_libro) REFERENCES libros(id) ON DELETE CASCADE,
    FOREIGN KEY (id_lector) REFERENCES lectores(id) ON DELETE CASCADE
);

-- Insertar datos
INSERT INTO libros (titulo, autor, paginas, genero, disponible) VALUES 
('El Quijote de la Mancha', 'Miguel de Cervantes', 863, 'Novela', true),
('Las aventuras del Quijote', 'Anónimo', 500, 'Ficción', true),
('Quijote y Sancho en la luna', 'Luis Martín', 300, 'Ciencia ficción', false),
('Cien años de soledad', 'Gabriel García Márquez', 417, 'Realismo mágico', true),
('Donde los árboles cantan', 'Laura Gallego', 340, 'Fantasía', true),
('1984', 'George Orwell', 328, 'Distopía', true),
('Los pilares de la Tierra', 'Ken Follett', 1040, 'Histórica', true),
('La sombra del viento', 'Carlos Ruiz Zafón', 576, 'Misterio', true),
('El señor de los anillos', 'J.R.R. Tolkien', 1200, 'Fantasía', true),
('Harry Potter y la piedra filosofal', 'J.K. Rowling', 309, 'Fantasía', false);

INSERT INTO cursos (id, descripcion) VALUES 
(1, 'curso de literatura'),
(2, 'curso de matematicas'),
(3, 'curso de programacion');
