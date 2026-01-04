import { createApp } from 'vue'
import { createPinia } from 'pinia'
/*import './style.css'*/
import router from "./router";
import App from './App.vue'

import "./assets/base.css";

createApp(App)
  .use(createPinia())
  .use(router)
  .mount("#app");