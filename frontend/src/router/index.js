import { createRouter, createWebHistory } from "vue-router";

import LoginView from "../views/LoginView.vue";
import RegisterView from "../views/RegisterView.vue";
import AgroforestryToolView from "../views/AgroforestryToolView.vue";
import ToolDetailView from "../views/ToolDetailView.vue";
import AdminView from "../views/AdminView.vue";

const routes = [
  { path: "/", name: "tool", component: AgroforestryToolView},

  { path: "/login", name: "login", component: LoginView },
  { path: "/register", name: "register", component: RegisterView },
  { path: "/tools/:id", name: "tool-details", component: ToolDetailView, props: true },
  { path: "/admin", name: "admin", component: AdminView, meta: { requiresAdmin: true }}, 

  { path: "/:pathMatch(.*)*", redirect: "/" },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

router.beforeEach((to) => {
  const token = localStorage.getItem("accessToken");

  if (to.meta.requiresAuth && !token) return "/login";
  if ((to.path === "/login" || to.path === "/register") && token) return "/";
});


export default router;
