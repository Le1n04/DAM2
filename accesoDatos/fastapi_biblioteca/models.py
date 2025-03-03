from sqlalchemy import Column, Integer, String, Boolean, ForeignKey
from sqlalchemy.orm import relationship
from database import Base

class Curso(Base):
    __tablename__ = "cursos"

    id = Column(Integer, primary_key=True, index=True)
    descripcion = Column(String(255))

class Lector(Base):
    __tablename__ = "lectores"

    id = Column(Integer, primary_key=True, index=True)
    nombre = Column(String(100))
    nombrelogin = Column(String(50), unique=True, index=True)
    contraseña = Column(String(255))
    cod_curso = Column(Integer, ForeignKey("cursos.id"))

    curso = relationship("Curso")

class Libro(Base):
    __tablename__ = "libros"

    id = Column(Integer, primary_key=True, index=True)
    titulo = Column(String(255), index=True)
    autor = Column(String(100))
    paginas = Column(Integer)
    genero = Column(String(50))
    disponible = Column(Boolean, default=True)

class Prestamo(Base):
    __tablename__ = "prestamos"

    id = Column(Integer, primary_key=True, index=True)
    id_libro = Column(Integer, ForeignKey("libros.id"))
    id_lector = Column(Integer, ForeignKey("lectores.id"))
    fechaalta = Column(String(50))
    fechabaja = Column(String(50), nullable=True)

    libro = relationship("Libro")
    lector = relationship("Lector")
