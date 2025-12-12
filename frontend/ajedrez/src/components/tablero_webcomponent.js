// =========================================================
// ⚙️ OPCIÓN A: LÓGICA COMPLETA DENTRO DEL COMPONENTE
// =========================================================

class ChessBoardComponent extends HTMLElement {
  constructor() {
    super();
    
    if (typeof Chess === "undefined") {
      console.error("❌ ERROR: chess.js no cargado");
      return;
    }
    
    this.game = new Chess();
    this.board = null;
    this.movimientos = []; 
    this.idPartida = null;
  }

  connectedCallback() {
    this.innerHTML = `<div id="boardContainer" style="width:100%; max-width:500px;"></div>`;
    const container = this.querySelector("#boardContainer");

    if (typeof Chessboard2 === "undefined") {
      console.warn("❌ ERROR: Chessboard2 no cargado");
      return;
    }

    this.board = Chessboard2(container, {
      position: this.game.fen(),
      draggable: true,
      showNotation: true,
      onDrop: ({ source, target }) => {
        return this._handleMove(source, target);
      },
    });
  }

  // ==========================================
  // 🎯 MANEJO DE MOVIMIENTOS (SIMPLIFICADO)
  // ==========================================
  
  _handleMove(source, target) {
    const fenAntes = this.game.fen();
    
    const move = this.game.move({
      from: source,
      to: target,
      promotion: 'q'
    });

    if (move === null) {
      this.dispatchEvent(new CustomEvent('move-illegal', {
        detail: { from: source, to: target }
      }));
      return 'snapback';
    }

    const fenDespues = this.game.fen();
    
    const movimientoData = {
      numeroMovimiento: this.movimientos.length + 1,
      movimientoNotacion: move.san,
      fenInicial: fenAntes,
      fenFinal: fenDespues,
      from: source,
      to: target
    };
    
    this.movimientos.push(movimientoData);

    this.dispatchEvent(new CustomEvent('move-made', {
      detail: movimientoData
    }));

    this._checkGameOver();

    return true;
  }

  // ==========================================
  // 🏁 DETECCIÓN DE FIN DE JUEGO (ADAPTADA A 0.10.3)
  // ==========================================
  
  _checkGameOver() {

    // ⛔ En 0.10.3, el método es game_over()
    if (!this.game.game_over()) {

      // ⛔ En 0.10.3, el método es in_check()
      if (this.game.in_check()) {
        this.dispatchEvent(new CustomEvent('check', {
          detail: { player: this.game.turn() }
        }));
      }
      return;
    }

    let resultado = {};

    // Checkmate
    if (this.game.in_checkmate()) {
      const ganador = this.game.turn() === 'w' ? 'negras' : 'blancas';
      resultado = {
        tipo: 'checkmate',
        ganador,
        mensaje: `¡Jaque mate! Ganan las ${ganador}`
      };
    }

    // Tablas generales
    else if (this.game.in_draw()) {
      resultado = {
        tipo: 'draw',
        ganador: null,
        mensaje: '¡Empate!'
      };
    }

    // Ahogado
    else if (this.game.in_stalemate()) {
      resultado = {
        tipo: 'stalemate',
        ganador: null,
        mensaje: '¡Ahogado! Empate'
      };
    }

    // Material insuficiente
    else if (this.game.insufficient_material()) {
      resultado = {
        tipo: 'insufficient_material',
        ganador: null,
        mensaje: '¡Empate por material insuficiente!'
      };
    }

    // Triple repetición
    else if (this.game.in_threefold_repetition()) {
      resultado = {
        tipo: 'threefold_repetition',
        ganador: null,
        mensaje: '¡Empate por triple repetición!'
      };
    }

    resultado.pgn = this.game.pgn();
    resultado.fenFinal = this.game.fen();
    resultado.movimientos = this.movimientos;

    this.dispatchEvent(new CustomEvent('game-over', {
      detail: resultado
    }));
  }

  // ==========================================
  // 💾 GUARDAR EN BD
  // ==========================================
  
  async guardarPartida() {
    if (!this.idPartida) {
      console.error('❌ No hay ID de partida asignado');
      return;
    }

    try {
      for (const mov of this.movimientos) {
        await fetch('http://localhost:8080/api/movimientos', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({
            idPartida: this.idPartida,
            numeroMovimiento: mov.numeroMovimiento,
            movimientoNotacion: mov.movimientoNotacion,
            fenInicial: mov.fenInicial,
            fenFinal: mov.fenFinal
          })
        });
      }
      
      console.log('✅ Partida guardada correctamente');
      return true;
    } catch (error) {
      console.error('❌ Error al guardar:', error);
      return false;
    }
  }

  // ==========================================
  // 📋 MÉTODOS PÚBLICOS
  // ==========================================
  
  setIdPartida(id) {
    this.idPartida = id;
  }

  setPosition(fen) {
    if (!this.board) return;
    this.board.position(fen);
    this.game.load(fen);
  }

  getFen() {
    return this.game.fen();
  }

  getMovimientos() {
    return this.movimientos;
  }

  resetGame() {
    this.game.reset();
    this.board.position('start');
    this.movimientos = [];
  }
}

customElements.define("chess-board", ChessBoardComponent);

// =========================================================
// 🧪 EJEMPLO DE USO SIMPLIFICADO
// =========================================================

/*
setTimeout(() => {
  const board = document.getElementById("board");
  
  // Asignar ID de partida
  board.setIdPartida(1);

  // Escuchar movimientos válidos
  board.addEventListener('move-made', (e) => {
    console.log('✅ Movimiento:', e.detail.movimientoNotacion);
  });

  // Escuchar jaques
  board.addEventListener('check', (e) => {
    console.log('⚠️ ¡Jaque!');
  });

  // Escuchar fin de juego
  board.addEventListener('game-over', async (e) => {
    console.log('🏁 Fin de juego:', e.detail.mensaje);
    console.log('Tipo:', e.detail.tipo);
    console.log('Ganador:', e.detail.ganador);
    
    // Guardar en BD automáticamente
    await board.guardarPartida();
    
    // Mostrar mensaje al usuario
    alert(e.detail.mensaje);
  });

}, 100);
*/