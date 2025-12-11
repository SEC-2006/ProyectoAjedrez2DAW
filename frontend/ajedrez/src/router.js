/*import { renderLogin } from "./components/login";
import { renderProfile } from "./components/profile";*/
import "./components/menu.js";
import "./components/tablero_webcomponent.js";

import "https://unpkg.com/@chrisoakman/chessboard2@0.5.0/dist/chessboard2.min.js";

// Rutas ahora almacenan funciones que devuelven nodos DOM (elementos personalizados)
const routes = new Map([
    ['', () => document.createElement('chess-menu')],
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
    } else {
        container.innerHTML = `<h2>404</h2>`;
    }
}