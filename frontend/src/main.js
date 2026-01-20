import { createApp } from "vue";
import { createPinia } from "pinia";
import router from "./router";
import App from "./App.vue";

import "./assets/base.css";
import { useAuthStore } from "./stores/auth";

const app = createApp(App);

const pinia = createPinia();
app.use(pinia);

const auth = useAuthStore(pinia);
auth.hydrateFromToken?.(); 

app.use(router);
app.mount("#app");
