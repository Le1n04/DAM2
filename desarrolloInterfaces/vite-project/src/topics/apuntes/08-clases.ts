export class Persona
{
    public nombre: string;
    public direccion: string;
    public altura?: number;
    private edad: number;

    constructor()
    {
        this.nombre = "Pessi";
        this.direccion = "Mayami";
        this.edad = 36;
    };

}

const pessi = new Persona();

console.log(pessi);