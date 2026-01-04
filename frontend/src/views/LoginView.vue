<template>
  <div class="page">
    <AuthCard title="Sign in" subtitle="Use your email and password to continue.">
      <form class="form" @submit.prevent="onSubmit">
        <AuthField
          id="email"
          label="Email"
          type="email"
          autocomplete="username"
          placeholder="you@example.com"
          v-model="email"
        />

        <AuthField
          id="password"
          label="Password"
          type="password"
          autocomplete="current-password"
          placeholder="password"
          v-model="password"
        />

        <AuthButton :disabled="auth.loading">
          {{ auth.loading ? "Signing in..." : "Sign in" }}
        </AuthButton>

        <p v-if="auth.error" class="error">{{ auth.error }}</p>
      </form>

      <AuthHint />
    </AuthCard>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { useAuthStore } from "../stores/auth";

import AuthCard from "../components/auth/AuthCard.vue";
import AuthField from "../components/auth/AuthField.vue";
import AuthButton from "../components/auth/AuthButton.vue";
import AuthHint from "../components/auth/AuthHint.vue";

import "../assets/auth.css";

const router = useRouter();
const auth = useAuthStore();

const email = ref("");
const password = ref("");

const onSubmit = async () => {
  try {
    await auth.login(email.value, password.value);
    router.push("/"); // later: dashboard
  } catch {
  }
};
</script>
