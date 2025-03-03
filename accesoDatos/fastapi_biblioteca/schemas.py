from pydantic import BaseModel
from typing import Optional

class CursoBase(BaseModel):
    descripcion: str

class CursoCreate(CursoBase):
    pass

class CursoDB(CursoBase):
    id: int

    class Config:
        from_attributes = True

class LectorBase(BaseModel):
    nombre: str
    nombrelogin: str
    contraseña: str
    cod_curso: int

class LectorCreate(LectorBase):
    pass

class LectorDB(LectorBase):
    id: int

    class Config:
        from_attributes = True
