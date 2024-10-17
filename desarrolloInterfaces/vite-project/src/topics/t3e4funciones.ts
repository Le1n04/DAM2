// Sumar
function sumar(a?: number, b?: number): number
{
    a = a ?? 0; //si no hay se asume 0 
    b = b ?? 0;
    return a + b;
}

// Restar
function restar(a?: number, b?: number): number
{
    a = a ?? 0;
    b = b ?? 0;
    return a - b;
}

// Multiplicar
function multiplicar(a?: number, b?: number): number
{
    a = a ?? 1; // si no hay se asume 1
    b = b ?? 1;
    return a * b;
}

// Dividir
function dividir(a?: number, b?: number): number
{
    a = a ?? 0;
    if (b === 0 || b === undefined)
        throw new Error("No se puede dividir por 0");
    return a / b;
}

// Ejemplos
console.log(sumar());
console.log(sumar(5, 3));
console.log(restar(10));
console.log(multiplicar(5));
console.log(dividir(10, 2));
console.log(dividir(10));

export{};