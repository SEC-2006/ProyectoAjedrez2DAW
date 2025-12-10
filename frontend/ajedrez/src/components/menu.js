// MenuComponent: compatible with plain static server (lite-server) and bundlers.
// It attempts to use bundler raw imports when available, otherwise fetches
// the HTML and CSS at runtime using URLs relative to this module.

let bundledHtml = null;
let bundledCss = null;
try {
  // These imports will work when a bundler (Vite, Rollup) provides the ?raw/?inline handlers.
  // They will throw in a plain browser environment, so we keep them in a try/catch.
  // eslint-disable-next-line import/no-unresolved
  // @ts-ignore
  bundledCss = (await import('./menu.css?inline')).default;
  // eslint-disable-next-line import/no-unresolved
  // @ts-ignore
  bundledHtml = (await import('./menu.html?raw')).default;
} catch (e) {
  // Ignore: we'll fallback to fetch below.
}

export class MenuComponent extends HTMLElement {
  connectedCallback() {
    this.render();
  }

  async _loadAssets() {
    if (bundledHtml !== null && bundledCss !== null) {
      return { html: bundledHtml, css: bundledCss };
    }

    // Fallback: fetch files relative to this module's location so it works with lite-server
    const base = new URL('.', import.meta.url);
    const htmlUrl = new URL('menu.html', base).href;
    const cssUrl = new URL('menu.css', base).href;

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
      console.error('Error loading menu assets:', e);
      this.innerHTML = '<p>Error loading menu</p>';
      return;
    }

    const contentHTML = document.createElement('div');
    contentHTML.innerHTML = htmlText;

    const styleElement = document.createElement('style');
    styleElement.innerText = cssText;

    // Clear previous content and append
    this.innerHTML = '';
    this.append(styleElement, contentHTML.firstElementChild);
  }
}

customElements.define("chess-menu", MenuComponent);