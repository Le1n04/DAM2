export interface Direccion
{
    calle: string;
    cp: string;
    localidad: string;
    provincia: string;
}

export interface Ensenanza
{
    nivel: string;
    obligatoria: boolean;
    familiaProfesional: string;
    modalidad: "Mañana" | "Tarde" | "Telemático";
}

export interface Alumno
{
    nombre: string;
    edad: number;
    sexo: "M" | "F";
    ensenanza: Ensenanza;
    direccion: Direccion;
}

export interface Instituto
{
    nombre: string;
    director: string;
    telefono: string;
    correo: string;
    direccion: Direccion;
    alumnos: Alumno[];
}

export function matricularAlumno(instituto: Instituto, alumno: Alumno): void
{
    instituto.alumnos.push(alumno);
}

export function mostrarAlumnos(instituto: Instituto): void
{
    instituto.alumnos.forEach(({ nombre, ensenanza: { nivel }, direccion: { localidad } }) =>
    {
        console.log(`Alumno: ${nombre}, Nivel: ${nivel}, Localidad: ${localidad}`);
    });
}

export function mostrarDireccionInstituto(instituto: Instituto): void
{
    const { direccion: { calle, cp, localidad, provincia } } = instituto;
    console.log(`Dirección del instituto: ${calle}, ${cp}, ${localidad}, ${provincia}`);
}
