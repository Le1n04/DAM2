from sqlalchemy import create_engine, Column, Integer, String, Float, ForeignKey, func
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import relationship, sessionmaker

Base = declarative_base()

# tablas como clases ORM
class Autor(Base):
    __tablename__ = 'autor'
    id = Column(Integer, primary_key=True, autoincrement=True)
    nombre = Column(String, nullable=False)
    pais = Column(String, nullable=False)
    libros = relationship('Libro', back_populates='autor')  # uno a muchos

class Libro(Base):
    __tablename__ = 'libro'
    id = Column(Integer, primary_key=True, autoincrement=True)
    isbn = Column(String, nullable=False)
    titulo = Column(String, nullable=False)
    precio = Column(Float, nullable=False)
    id_autor = Column(Integer, ForeignKey('autor.id'))
    autor = relationship('Autor', back_populates='libros')  # relación inversa

# configurar conexión a base de datos
DATABASE_URL = "sqlite:///libreria.db"
engine = create_engine(DATABASE_URL)
Session = sessionmaker(bind=engine)
session = Session()

# crear tablas en la base de datos
Base.metadata.create_all(engine)

# insertar datos
autor = Autor(nombre="Gabriel García Márquez", pais="Colombia")
session.add(autor)
session.commit()

libro = Libro(isbn="123456789", titulo="Cien Años de Soledad", precio=25.99, autor=autor)
session.add(libro)
session.commit()

# consultar un libro
print("Consultar un libro:")
libro_consultado = session.query(Libro).filter(Libro.titulo == "Cien Años de Soledad").first()
if libro_consultado:
    print(f"Título: {libro_consultado.titulo}, Autor: {libro_consultado.autor.nombre}")

# actualizar el precio de un libro
print("Actualizar el precio de un libro:")
session.query(Libro).filter(Libro.titulo == "Cien Años de Soledad").update({Libro.precio: 30.99})
session.commit()

# contar libros
print("Número de libros:")
num_libros = session.query(func.count(Libro.id)).scalar()
print(f"Número total de libros: {num_libros}")

# consulta con join
print("Consulta con JOIN:")
libros_join = session.query(Libro).join(Autor).filter(Autor.pais == "Colombia").all()
for libro in libros_join:
    print(f"Título: {libro.titulo}, Autor: {libro.autor.nombre}")

# carga relacionada: lazy vs eager
from sqlalchemy.orm import joinedload

print("Usando carga Eager (joinedload):")
libro_eager = session.query(Libro).options(joinedload(Libro.autor)).first()
if libro_eager:
    print(f"Título: {libro_eager.titulo}, Autor: {libro_eager.autor.nombre}")

# lazy carga los datos solo cuando se acceden
# eager carga los datos en la consulta principal
