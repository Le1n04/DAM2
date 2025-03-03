from sqlalchemy.ext.asyncio import AsyncSession
from sqlalchemy.future import select
from models import Curso, Lector
import schemas

async def get_cursos(db: AsyncSession):
    result = await db.execute(select(Curso))
    return result.scalars().all()

async def create_curso(db: AsyncSession, curso: schemas.CursoCreate):
    db_curso = Curso(**curso.dict())
    db.add(db_curso)
    await db.commit()
    await db.refresh(db_curso)
    return db_curso

async def get_lectores(db: AsyncSession):
    result = await db.execute(select(Lector))
    return result.scalars().all()

async def create_lector(db: AsyncSession, lector: schemas.LectorCreate):
    db_lector = Lector(**lector.dict())
    db.add(db_lector)
    await db.commit()
    await db.refresh(db_lector)
    return db_lector
