from fastapi import FastAPI
from database import engine, Base
import routes

app = FastAPI()

app.include_router(routes.router)

@app.on_event("startup")
async def startup():
    async with engine.begin() as conn:
        await conn.run_sync(Base.metadata.create_all)

@app.get("/")
def read_root():
    return {"message": "¡Bienvenido a la API de la biblioteca con MySQL!"}
