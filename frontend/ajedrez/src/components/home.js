let bundledHtml = null;
let bundledCss = null;
try {
  bundledCss = (import('./home.css?inline')).default;
  bundledHtml = (await import('./home.html?raw')).default;
} catch (e) {
}

export class HomeComponent extends HTMLElement {
  connectedCallback() {
    this.render();
  }

  async _loadAssets() {
    if (bundledHtml !== null && bundledCss !== null) {
      return { html: bundledHtml, css: bundledCss };
    }

    const base = new URL('.', import.meta.url);
    const htmlUrl = new URL('home.html', base).href;
    const cssUrl = new URL('home.css', base).href;

    const [htmlResp, cssResp] = await Promise.all([
      fetch(htmlUrl),
      fetch(cssUrl),
    ]);

    if (!htmlResp.ok) throw new Error(`Failed to load ${htmlUrl}: ${htmlResp.status}`);
    if (!cssResp.ok) throw new Error(`Failed to load ${cssUrl}: ${cssResp.status}`);

    const [htmlText, cssText] = await Promise.all([htmlResp.text(), cssResp.text()]);
    return { html: htmlText, css: cssText };
  }

  async render() {
    let htmlText = '';
    let cssText = '';
    try {
      const assets = await this._loadAssets();
      htmlText = assets.html;
      cssText = assets.css;
    } catch (e) {
      console.error('Error loading home assets:', e);
      this.innerHTML = '<p>Error loading home</p>';
      return;
    }

    const contentHTML = document.createElement('div');
    contentHTML.innerHTML = htmlText;

    const styleElement = document.createElement('style');
    styleElement.innerText = cssText;

    this.innerHTML = '';
    this.append(styleElement, contentHTML.firstElementChild);
  }
}

customElements.define("chess-home", HomeComponent);