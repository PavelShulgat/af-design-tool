<template>
  <header class="hdr">
    <div class="container hdr-inner">
      <button class="brand" type="button" @click="goHome">
        AF Design Tool
      </button>

      <div class="right">
        <template v-if="auth.isAuthenticated">
          <button class="btn btn-ghost" type="button">
            User
          </button>
          <button class="btn btn-solid" type="button" @click="logout">
            Log out
          </button>
        </template>

        <template v-else>
          <button class="btn btn-ghost" type="button" @click="goLogin">
            Log in
          </button>
        </template>
      </div>
    </div>

    <div class="accent" />
  </header>
</template>

<script setup>
import { useRouter } from "vue-router";
import { useAuthStore } from "../../stores/auth";

const router = useRouter();
const auth = useAuthStore();

const goHome = () => router.push("/");
const goLogin = () => router.push("/login");

const logout = () => {
  auth.logout();
  router.push("/");
};
</script>

<style scoped>
.hdr {
  position: relative;
  z-index: 50;        
  background: #fff;
}

.hdr-inner {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 16px;
  padding-top: 12px;
}

.brand {
  border: 0;
  background: transparent;
  font-size: 20px;
  font-weight: 900;
  letter-spacing: 0.2px;
  color: var(--color-text);
  cursor: pointer;
  padding: 6px 0;
}

.right {
  display: flex;
  align-items: center;
  gap: 10px;
}

.btn {
  border-radius: var(--radius);
  padding: 10px 14px;
  font-weight: 800;
  border: 1px solid var(--color-border);
  background: #fff;
  color: #111;
  cursor: pointer;
}

.btn-ghost:hover {
  border-color: rgba(0, 0, 0, 0.18);
}

.btn-solid {
  background: #111;
  color: #fff;
  border-color: #111;
}

.btn-solid:hover {
  opacity: 0.92;
}

.accent {
  margin-top: 14px;
  height: 3px;
  width: 100%;
  background: var(--color-primary);
}
</style>
