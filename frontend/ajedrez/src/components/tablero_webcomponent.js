// =========================================================
// ⚙️ CLASSE DEL WEB COMPONENT (ChessBoardComponent)
// =========================================================

class ChessBoardComponent extends HTMLElement {
  constructor() {
    super(); // Verificación de seguridad
    if (typeof Chess === "undefined") {
      console.error(
        "❌ ERROR: La librería 'chess.js' no se ha cargado. Revisa el HTML."
      );
      return;
    } // Utilizamos 'Chess' internamente solo para inicializar el FEN y mantener el estado actual.
    this.game = new Chess();
    this.board = null;
  }

  connectedCallback() {
    // Contenedor que asegura la capacidad de respuesta (responsividad)
    this.innerHTML = `<div id="boardContainer" style="width:100%; max-width:500px;"></div>`;
    const container = this.querySelector("#boardContainer");

    if (typeof Chessboard2 === "undefined") {
      console.warn("❌ ERROR: La librería 'Chessboard2' no se ha cargado.");
      return;
    }

    this.board = Chessboard2(container, {
      position: this.game.fen(),
      draggable: true,
      showNotation: true, // ✅ LÒGICA SNAPBACK PER UX MILLORADA (ÚS DE CÒPIA TEMPORAL)
      onDrop: ({ source, target }) => {
        // 1. Simulem el moviment a una CÒPIA TEMPORAL per comprovar la legalitat visual.
        const tempGame = new Chess(this.game.fen());
        const move = tempGame.move({
          from: source,
          to: target,
          promotion: "q",
        }); // 2. Emetre l'esdeveniment SEMPRE (l'estat intern this.game no ha canviat).

        this.dispatchEvent(
          new CustomEvent("chess-move", {
            detail: { from: source, to: target },
          })
        );

        if (move === null) {
          // 3. Si és IL·LEGAL: Forcem el snapback instantani.
          return "snapback";
        } else {
          // 4. Si és LEGAL: No fem res amb l'estat intern (this.game).
          // Retornar false per mantenir la peça al destí fins a la confirmació externa.
          return false;
        }
      },
    });
  }  // --- MÉTODOS PRIVADOS DE CONVERSIÓN ---
  /**
   * Converteix una matriu 8x8 de peces a la notació FEN (parcial)
   * @param {Array<Array<string|null>>} matrix
   * @returns {string} FEN String (amb metadata preservada).
   */

  _matrixToFen(matrix) {
    let fen = "";
    for (let i = 0; i < 8; i++) {
      let emptyCount = 0;
      for (let j = 0; j < 8; j++) {
        const piece = matrix[i][j];
        if (piece === null || piece === "") {
          emptyCount++;
        } else {
          if (emptyCount > 0) {
            fen += emptyCount;
            emptyCount = 0;
          } // Les peces han de ser P, N, B, R, Q, K (blanc) o p, n, b, r, q, k (negre)
          fen += piece;
        }
      }
      if (emptyCount > 0) {
        fen += emptyCount;
      }
      if (i < 7) {
        fen += "/"; // Separador de files
      }
    } // Afegim la metadata FEN (torn, enroc, etc.) que preserva l'estat actual del joc.
    const parts = this.game.fen().split(" ");
    return `${fen} ${parts[1]} ${parts[2]} ${parts[3]} ${parts[4]} ${parts[5]}`;
  }  // --- MÉTODOS PÚBLICOS PARA MANIPULACIÓN EXTERNA ---
  /**
   * Actualiza la posición del tablero basándose en un FEN.
   * Este es el método usado por el exterior para aceptar o revertir un movimiento.
   * @param {string} fen - La nueva posición FEN para el tablero.
   */

  setPosition(fen) {
    if (!this.board) return;
    this.board.position(fen);
    this.game.load(fen); // Actualiza la lógica interna del componente
  }
  /**
   * Actualiza la posición del tablero basándose en una matriz 8x8.
   * @param {Array<Array<string|null>>} matrix - La nueva posición de las piezas.
   */

  setMatrix(matrix) {
    if (!this.board) return;
    try {
      const newFen = this._matrixToFen(matrix);
      this.setPosition(newFen);
    } catch (e) {
      console.error("Error al convertir la matriz a FEN:", e);
    }
  }
  /**
   * Obtiene la posición FEN actual del tablero.
   * @returns {string} FEN actual.
   */

  getFen() {
    if (!this.game) return "";
    return this.game.fen();
  }
}

customElements.define("chess-board", ChessBoardComponent);
/*
// =========================================================
// 🧪 CÓDIGO DE PRUEBA (Aplicació Externa - Listener)
// =========================================================

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
  } else {
    console.error("El elemento con id='board' no se encontró en el DOM.");
  }
}, 125);
*/ 

