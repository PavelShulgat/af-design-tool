import { defineStore } from "pinia";
import { login as loginApi, register as registerApi } from "../api";

export const useAuthStore = defineStore("auth", {
  state: () => ({
    accessToken: localStorage.getItem("accessToken") || null,
    loading: false,
    error: null,
  }),

  getters: {
    isAuthenticated: (s) => !!s.accessToken,
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
      localStorage.removeItem("accessToken");
    },
  },
});
