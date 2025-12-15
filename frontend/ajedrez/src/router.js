/*import { renderLogin } from "./components/login";
import { renderProfile } from "./components/profile";*/
import "./components/menu.js";
import "./components/home.js";
import "./components/tablero_webcomponent.js";
import { insertarMovimiento } from "./conexionServidor/conexion.js";

import "https://unpkg.com/@chrisoakman/chessboard2@0.5.0/dist/chessboard2.min.js";

// Rutas ahora almacenan funciones que devuelven nodos DOM (elementos personalizados)
const routes = new Map([
    ['', () => document.createElement('chess-home')],
    ['#game', () => {
        const el = document.createElement('chess-board');
        el.id = 'board';
        return el;
    }],
    /*
    ['#login', () => renderLogin('login')],
    ['#register', () => renderLogin('register')],
    ['#profile', () => renderProfile()]
    */
]);

export function router(route, container) {
    if (routes.has(route)) {
        const node = routes.get(route)();
        container.replaceChildren(node);
            setTimeout(() => {
            const board = document.getElementById("board");
            
            if (!board) {
                console.error("El elemento board no existe");
                return;
            }

            // Asignar ID de partida
            board.setIdPartida(1); // Usar el ID de la partida

            // Escuchar movimientos válidos
            board.addEventListener('move-made', async (e) => {
                const { numeroMovimiento, movimientoNotacion, fenInicial, fenFinal } = e.detail;
                
                console.log('━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━');
                console.log(`✅ Movimiento ${numeroMovimiento}: ${movimientoNotacion}`);
                console.log(`📋 FEN Inicial: ${fenInicial}`);
                console.log(`📋 FEN Final:   ${fenFinal}`);
                console.log('━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n');
                
               const guardado = await board.guardarMovimiento();
            });

            // Escuchar jaques
            board.addEventListener('check', (e) => {
                console.log('⚠️ ¡Jaque!');
                
            });

            // Escuchar movimientos ilegales
            board.addEventListener('move-illegal', (e) => {
                console.warn(`⛔ Movimiento ilegal: ${e.detail.from} → ${e.detail.to}`);
            });

            // Escuchar fin de juego
            board.addEventListener('game-over', async (e) => {
                const { tipo, ganador, mensaje, pgn, movimientos } = e.detail;
                
                console.log('🏁🏁🏁 FIN DE JUEGO 🏁🏁🏁');
                console.log(`Tipo: ${tipo}`);
                console.log(`Ganador: ${ganador || 'Empate'}`);
                console.log(`Mensaje: ${mensaje}`);
                console.log(`Total movimientos: ${movimientos.length}`);
                console.log(`PGN: ${pgn}`);
                
                // Avisar al jugador
                alert(mensaje);
                
                // Guardar en la base de datos automáticamente
                // const guardado = await board.guardarPartida();
                
                if (guardado) {
                    console.log('✅ Partida guardada correctamente en la BD');
                } else {
                    console.error('❌ Error al guardar la partida');
                }
            });

        }, 125);
    } else {
        container.innerHTML = `<h2>404</h2>`;
    }
}