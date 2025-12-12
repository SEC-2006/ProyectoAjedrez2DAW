// Función para insertar un movimiento
export async function insertarMovimiento(movimiento) {
    try {
        const response = await fetch('http://localhost:8080/api/movimientos', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                idPartida: movimiento.idPartida,
                numeroMovimiento: movimiento.numeroMovimiento,
                movimientoNotacion: movimiento.movimientoNotacion,
                fenInicial: movimiento.fenInicial,
                fenFinal: movimiento.fenFinal
            })
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        const data = await response.json();
        console.log('Movimiento insertado:', data);
        return data;
    } catch (error) {
        console.error('Error al insertar movimiento:', error);
        throw error;
    }
}

/* Ejemplo de uso
const miMovimiento = {
    idPartida: 1,
    numeroMovimiento: 1,
    movimientoNotacion: "e4",
    fenInicial: "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1",
    fenFinal: "rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1"
};

insertarMovimiento(miMovimiento);*/