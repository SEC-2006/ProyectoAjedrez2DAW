//import "./style.scss"
import { router } from "./router.js";

document.addEventListener("DOMContentLoaded",()=>{
  const appDiv = document.querySelector('#app');
  /*const headerDiv = document.querySelector('#header');
  const footerDiv = document.querySelector('#footer');*

  //headerDiv.innerHTML = renderHeader();
 // headerDiv.innerHTML = `<game-header></game-header>`
  footerDiv.innerHTML = renderFooter();*/



  router(window.location.hash, appDiv);
  window.addEventListener("hashchange", () => {
    router(window.location.hash, appDiv);
  });
  
});