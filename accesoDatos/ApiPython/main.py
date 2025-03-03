from fastapi import FastAPI, HTTPException, Depends
from sqlalchemy import Column, Integer, String, Boolean, ForeignKey, func
from sqlalchemy.orm import relationship, Session
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.ext.asyncio import AsyncSession, create_async_engine
from sqlalchemy.orm import sessionmaker

DATABASE_URL = "sqlite+aiosqlite:///./library.db"
engine = create_async_engine(DATABASE_URL, echo=True)
SessionLocal = sessionmaker(bind=engine, class_=AsyncSession, expire_on_commit=False)
Base = declarative_base()

class Libro(Base):
    __tablename__ = "libros"
    id = Column(Integer, primary_key=True, index=True)
    titulo = Column(String, index=True)
    autor = Column(String)
    paginas = Column(Integer)
    genero = Column(String)
    disponible = Column(Boolean, default=True)

class Lector(Base):
    __tablename__ = "lectores"
    id = Column(Integer, primary_key=True, index=True)
    nombre = Column(String)
    nombrelogin = Column(String, unique=True, index=True)
    contraseña = Column(String)
    cod_curso = Column(Integer, ForeignKey("cursos.id"))

class Prestamo(Base):
    __tablename__ = "prestamos"
    id = Column(Integer, primary_key=True, index=True)
    id_libro = Column(Integer, ForeignKey("libros.id"))
    id_lector = Column(Integer, ForeignKey("lectores.id"))
    fechaalta = Column(String)
    fechabaja = Column(String, nullable=True)

async def get_db():
    async with SessionLocal() as session:
        yield session

app = FastAPI()

@app.patch("/lectores/{lector_id}")
async def actualizar_lector(lector_id: int, nombre: str, db: Session = Depends(get_db)):
    lector = await db.get(Lector, lector_id)
    if not lector:
        raise HTTPException(status_code=404, detail="Lector no encontrado")
    lector.nombre = nombre
    await db.commit()
    await db.refresh(lector)
    return lector

@app.get("/libros/prestamos")
async def obtener_libros_prestados(db: Session = Depends(get_db)):
    query = """
    SELECT libros.titulo, lectores.nombre, prestamos.fechaalta, prestamos.fechabaja
    FROM prestamos
    JOIN libros ON prestamos.id_libro = libros.id
    JOIN lectores ON prestamos.id_lector = lectores.id
    """
    result = await db.execute(query)
    return result.fetchall()

@app.get("/libros/mas_prestados")
async def libros_mas_prestados(db: Session = Depends(get_db)):
    query = """
    SELECT libros.titulo, COUNT(prestamos.id) as cantidad_prestamos
    FROM prestamos
    JOIN libros ON prestamos.id_libro = libros.id
    GROUP BY libros.titulo
    ORDER BY cantidad_prestamos DESC
    LIMIT 5
    """
    result = await db.execute(query)
    return result.fetchall()

@app.on_event("startup")
async def startup():
    async with engine.begin() as conn:
        await conn.run_sync(Base.metadata.create_all)
