import { defineStore } from "pinia";
import { login as loginApi, register as registerApi } from "../api";

function parseJwt(token) {
  try {
    const payload = token.split(".")[1];
    const base64 = payload.replace(/-/g, "+").replace(/_/g, "/");
    const json = decodeURIComponent(
      atob(base64)
        .split("")
        .map((c) => "%" + ("00" + c.charCodeAt(0).toString(16)).slice(-2))
        .join("")
    );
    return JSON.parse(json);
  } catch {
    return null;
  }
}

function extractRole(payload) {
  if (!payload) return null;

  // try common fields (your backend might use any of these)
  if (typeof payload.role === "string") return payload.role;
  if (typeof payload.roles?.[0] === "string") return payload.roles[0];
  if (typeof payload.authorities?.[0] === "string") return payload.authorities[0];

  // sometimes it's "scope": "ROLE_ADMIN ROLE_USER" etc.
  if (typeof payload.scope === "string") {
    if (payload.scope.includes("ADMIN")) return "ADMIN";
    if (payload.scope.includes("USER")) return "USER";
  }

  return null;
}

export const useAuthStore = defineStore("auth", {
  state: () => ({
    accessToken: localStorage.getItem("accessToken") || null,
    role: localStorage.getItem("role") || null,
    loading: false,
    error: null,
  }),

  getters: {
    isAuthenticated: (s) => !!s.accessToken,
    isAdmin: (s) => s.role === "ADMIN" || s.role === "ROLE_ADMIN",
  },

  actions: {
    async login(email, password) {
      this.loading = true;
      this.error = null;
      try {
        const data = await loginApi({ email, password });
        const token = data?.accessToken;
        if (!token) throw new Error("No accessToken returned");

        this.accessToken = token;
        localStorage.setItem("accessToken", token);

        // decode JWT and store role
        const payload = parseJwt(token);
        const role = extractRole(payload);
        this.role = role;
        if (role) localStorage.setItem("role", role);
        else localStorage.removeItem("role");

        return token;
      } catch (e) {
        this.error =
          e?.response?.data?.message ||
          e?.response?.data ||
          e?.message ||
          "Login failed";
        throw e;
      } finally {
        this.loading = false;
      }
    },

    async register(payload) {
      this.loading = true;
      this.error = null;
      try {
        return await registerApi(payload);
      } catch (e) {
        this.error =
          e?.response?.data?.message ||
          e?.response?.data ||
          e?.message ||
          "Register failed";
        throw e;
      } finally {
        this.loading = false;
      }
    },

    logout() {
      this.accessToken = null;
      this.role = null;
      localStorage.removeItem("accessToken");
      localStorage.removeItem("role");
    },

    // optional: call once on app startup to fill role if token exists
    hydrateFromToken() {
      if (!this.accessToken) return;
      const payload = parseJwt(this.accessToken);
      const role = extractRole(payload);
      this.role = role;
      if (role) localStorage.setItem("role", role);
    },
  },
});
