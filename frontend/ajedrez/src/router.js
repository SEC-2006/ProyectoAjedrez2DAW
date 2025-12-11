/*import { renderLogin } from "./components/login";
import { renderProfile } from "./components/profile";*/
import "./components/menu.js";
import "./components/home.js";
import "./components/tablero_webcomponent.js";

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
                const boardElement = document.getElementById("board");

                if (boardElement) {
                    boardElement.addEventListener("chess-move", (e) => {
                    const { from, to } = e.detail;
                    console.log(`📢 Movimiento intentado (from: ${from}, to: ${to})`); // LÒGICA EXTERNA: Valida el moviment amb el FEN correcte.

                    const gameLogic = new Chess(boardElement.getFen());
                    const move = gameLogic.move({ from, to, promotion: "q" });

                    if (move) {
                        // ✅ Si és legal: Acceptem i actualitzem el FEN.
                        console.log(`✅ Movimiento legal. Aceptando: ${move.san}`);
                        boardElement.setPosition(gameLogic.fen());
                    } else {
                        // ⛔ Si és il·legal: El tauler ja ha revertit (snapback).
                        console.warn(`⛔ Movimiento ilegal. El tauler ja ha revertit.`);
                    }
                    });
                }
            }, 125);
    } else {
        container.innerHTML = `<h2>404</h2>`;
    }
}