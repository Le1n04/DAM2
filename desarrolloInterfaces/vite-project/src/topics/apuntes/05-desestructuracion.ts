interface ReproductorAudio
{
    volumenAudio: number,
    duracionCancion: number,
    cancion: string,
    detalles?: Detalles;
}

interface Detalles 
{
    autor: string,
    anio: number
}

const reproductorAudio : ReproductorAudio = 
{
    volumenAudio : 0,
    duracionCancion : 0,
    cancion: "",
}

