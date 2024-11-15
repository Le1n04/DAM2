interface Expediente {
    curso: string;
    notaMedia: number;
}

interface ModuloProfesional {
    nombre: string;
}

interface Alumno {
    nombre: string;
    apellidos: string;
    modulosProfesionales: ModuloProfesional[];
    expedientes: Expediente[];
}

const alumnos: Alumno[] = [
    {
        nombre: "Daniel",
        apellidos: "Janssen",
        modulosProfesionales: [
            { nombre: "mod1" },
            { nombre: "mod2" },
        ],
        expedientes: [
            { curso: "2022-2023", notaMedia: 8.5 },
            { curso: "2021-2022", notaMedia: 7.8 },
        ],
    },
    {
        nombre: "Josue",
        apellidos: "Gil",
        modulosProfesionales: [
            { nombre: "mod3" },
            { nombre: "mod4" },
        ],
        expedientes: [
            { curso: "2023-2024", notaMedia: 1.3 },
            { curso: "2021-2022", notaMedia: 5.2 },
        ],
    },
    {
        nombre: "Danielti",
        apellidos: "Nessnaj",
        modulosProfesionales: [
            { nombre: "mod5" },
            { nombre: "mod6" },
        ],
        expedientes: [
            { curso: "2024-2025", notaMedia: 2.5 },
            { curso: "2021-2022", notaMedia: 4.5 },
        ],
    },
];

const { nombre, apellidos, modulosProfesionales, expedientes } = alumnos[1];
const primerModulo = modulosProfesionales[0].nombre;
const { curso, notaMedia } = expedientes[1];

console.log(`Nombre: ${nombre} ${apellidos}`);
console.log(`Primer modulo profesional: ${primerModulo}`);
console.log(`Curso: ${curso}, Nota media: ${notaMedia}`);

export{};