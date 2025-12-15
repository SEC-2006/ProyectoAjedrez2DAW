class HomePage extends HTMLElement {
    constructor() {
        super();
        this.attachShadow({ mode: 'open' }); // Ús d'Shadow DOM per encapsulació
    }

    connectedCallback() {
        this.render();
    }

    render() {
        // Estils bàsics (usant colors de plantilla, adaptables al vostre CSS global)
        this.shadowRoot.innerHTML = `
            <style>
                :host {
                    display: block;
                    padding: 40px 20px;
                    text-align: center;
                    background-color: var(--color-background-soft, #f4f7f6); /* Fons suau */
                    min-height: 80vh;
                    box-sizing: border-box;
                }
                
                .home-container {
                    max-width: 900px;
                    margin: 0 auto;
                }
                
                h1 {
                    color: var(--color-primary-dark, #2c3e50); /* Color fosc primari */
                    font-family: var(--font-header, sans-serif);
                    font-size: 3em;
                    margin-bottom: 0.2em;
                }
                
                p.subtitle {
                    color: var(--color-text-medium, #7f8c8d); /* Gris subtil */
                    font-size: 1.2em;
                    margin-bottom: 40px;
                    line-height: 1.5;
                }

                .link-section {
                    display: flex;
                    justify-content: center;
                    flex-wrap: wrap;
                    gap: 20px;
                    margin-top: 40px;
                }

                .nav-card {
                    background-color: var(--color-background-light, #ffffff);
                    border: 1px solid var(--color-border, #ecf0f1);
                    border-radius: 8px;
                    padding: 30px;
                    width: 250px;
                    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.05);
                    transition: transform 0.3s, box-shadow 0.3s;
                    text-decoration: none; /* Per si és un <a> */
                    display: block;
                    text-align: left;
                }

                .nav-card:hover {
                    transform: translateY(-5px);
                    box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1);
                }

                .nav-card h3 {
                    color: var(--color-primary, #3498db); /* Color primari (blau) */
                    margin-top: 0;
                    font-size: 1.5em;
                }

                .nav-card p {
                    color: var(--color-text-dark, #34495e);
                    font-size: 0.95em;
                }
            </style>

            <div class="home-container">
                <h1>♟ Projecte D'Escacs</h1>
                <p class="subtitle">
                    Plataforma per a la gestió de jugades d'escacs amb el fi d'utilitzar-les per entrenar una IA.
                </p>

                <div class="link-section">
                    <a href="/elements" class="nav-card" data-route>
                        <h3>📋 Jugar</h3>
                        <p>Jugar vs IA o vs un altra persona.</p>
                    </a>

                    <a href="/dashboard" class="nav-card" data-route>
                        <h3>📊 Tauler de Control</h3>
                        <p>Consulta les estadístiques i els indicadors de rendiment del sistema.</p>
                    </a>

                    <a href="/profile" class="nav-card" data-route>
                        <h3>👤 Configuració d'Usuari</h3>
                        <p>Accedeix i modifica la teva informació personal i preferències de l'aplicació.</p>
                    </a>
                </div>
            </div>
        `;
        
        // Assegurar la navegació amb el router (si s'utilitza un router client-side)
        this.shadowRoot.querySelectorAll('[data-route]').forEach(link => {
            link.addEventListener('click', this.handleNavigation.bind(this));
        });
    }
    
    // Mètode d'interfície per a la navegació (Router)
    handleNavigation(event) {
        // Evita la navegació per defecte (reload de pàgina)
        event.preventDefault(); 
        const path = event.currentTarget.getAttribute('href');
        
        // Simular l'emissió d'un esdeveniment de router o una crida global
        // El vostre router hauria d'escoltar un esdeveniment com 'navigate'
        this.dispatchEvent(new CustomEvent('navigate', { 
            detail: { path }, 
            bubbles: true, 
            composed: true 
        }));

        // Opcionalment, podríeu utilitzar history.pushState(null, '', path);
    }

    // Mètodes similars als d'altres components (per exemple, per actualitzar dades si fos necessari)
    updateData(newData) {
        // Lògica per actualitzar la visualització si l'estat global canvia
        console.log("Pàgina d'inici actualitzada amb noves dades:", newData);
    }
}

// 2. Definició del Web Component
customElements.define('home-page', HomePage);