import style from './menu.css?inline';
import html from './menu.html?raw';

class MenuComponent extends HTMLElement {
  connectedCallback() {
    this.render();
  }

  render() {
    const contentHTML = document.createElement('div');
    contentHTML.innerHTML = html;
    
    const styleElement = document.createElement('style');
    styleElement.innerText = style;
    
    this.append(styleElement, contentHTML.firstElementChild);
  }
}