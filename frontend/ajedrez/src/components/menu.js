class NavMenu extends HTMLElement {
  constructor() {
    super();
    this.attachShadow({ mode: 'open' });
  }

  connectedCallback() {
    this.render();
  }

  render() {
    this.shadowRoot.innerHTML = `
      <style>
        nav {
          background: linear-gradient(135deg, #340c3dff 0%, #0e0111ff 100%);
          padding: 1rem;
        }

        .nav-container {
          display: flex;
          justify-content: space-between;
          align-items: center;
          max-width: 1200px;
          margin: 0 auto;
        }

        .nav-brand {
          color: #abffffff;
          font-size: 1.5rem;
          font-weight: bold;
          text-decoration: none;
        }

        .nav-links {
          display: flex;
          gap: 2rem;
          list-style: none;
          margin: 0;
          padding: 0;
        }

        .nav-link {
          color: #abffffff;
          text-decoration: none;
          transition: all 0.3s;
          display: inline-block;
        }

        .nav-link:hover {
            transform: scale(1.65);
            text-shadow:
            0 0 4px rgba(0,240,255,0.6),
            0 0 14px rgba(0,240,255,0.35),
            0 6px 40px rgba(0,0,0,0.6);
        }

        @media (max-width: 768px) {
          .nav-container {
            flex-direction: column;
            gap: 1rem;
          }

          .nav-links {
            flex-direction: column;
            gap: 1rem;
            text-align: center;
          }
        }
      </style>

      <nav>
        <div class="nav-container">
          <a href="#" class="nav-brand">Ajedrez</a>
          <ul class="nav-links">
            <li><a href="#" class="nav-link">Inicio</a></li>
            <li><a href="#game" class="nav-link">Juego</a></li>
            <li><a href="#login" class="nav-link">Login</a></li>
            <li><a href="#registro" class="nav-link">Registro</a></li>
          </ul>
        </div>
      </nav>
    `;
  }
}

customElements.define('chess-menu', NavMenu);