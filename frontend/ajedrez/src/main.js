import "./style.scss"
import { router } from "./router";

import { renderHeader, renderHeaderUser } from "./components/header"
import { renderFooter } from "./components/footer";

document.addEventListener("DOMContentLoaded",async ()=>{
  const appDiv = document.querySelector('#app');
  const headerDiv = document.querySelector('#header');
  const footerDiv = document.querySelector('#footer');

  if(localStorage.getItem("email") != null) {
    headerDiv.replaceChildren( renderHeaderUser() );
  } else {
    headerDiv.replaceChildren( renderHeader() );
  }
  footerDiv.innerHTML = renderFooter();
  await router(window.location.hash, appDiv);
  window.addEventListener("hashchange", async () => {
    await router(window.location.hash, appDiv);
  });
});