from fastapi import APIRouter, Depends
from sqlalchemy.ext.asyncio import AsyncSession
import schemas, crud
from database import get_db

router = APIRouter()

@router.get("/cursos", response_model=list[schemas.CursoDB])
async def obtener_cursos(db: AsyncSession = Depends(get_db)):
    return await crud.get_cursos(db)

@router.post("/cursos", response_model=schemas.CursoDB)
async def crear_curso(curso: schemas.CursoCreate, db: AsyncSession = Depends(get_db)):
    return await crud.create_curso(db, curso)

@router.get("/lectores", response_model=list[schemas.LectorDB])
async def obtener_lectores(db: AsyncSession = Depends(get_db)):
    return await crud.get_lectores(db)

@router.post("/lectores", response_model=schemas.LectorDB)
async def crear_lector(lector: schemas.LectorCreate, db: AsyncSession = Depends(get_db)):
    return await crud.create_lector(db, lector)
