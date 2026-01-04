<template>
  <div class="page">
    <AuthCard title="Create account" subtitle="Fill in your details to sign up.">
      <form class="form" @submit.prevent="onSubmit">
        <AuthField
          id="firstName"
          label="First name"
          type="text"
          autocomplete="given-name"
          placeholder="John"
          v-model="firstName"
        />

        <AuthField
          id="lastName"
          label="Last name"
          type="text"
          autocomplete="family-name"
          placeholder="Doe"
          v-model="lastName"
        />

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
          autocomplete="new-password"
          placeholder="Create a password"
          v-model="password"
        />

        <AuthButton :disabled="auth.loading">
          {{ auth.loading ? "Creating..." : "Create account" }}
        </AuthButton>

        <p v-if="auth.error" class="error">{{ auth.error }}</p>
        <p v-if="success" class="success">Account created. You can sign in now.</p>
      </form>

      <div class="hint">
        Already have an account?
        <router-link class="link" to="/login">Sign in</router-link>
      </div>
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

import "../assets/auth.css";

const router = useRouter();
const auth = useAuthStore();

const firstName = ref("");
const lastName = ref("");
const email = ref("");
const password = ref("");
const success = ref(false);

const onSubmit = async () => {
  success.value = false;

  try {
    await auth.register({
      email: email.value,
      firstName: firstName.value,
      lastName: lastName.value,
      password: password.value,
    });

    success.value = true;

    // optional: auto-redirect to login after success
    setTimeout(() => router.push("/login"), 600);
  } catch {
    // auth.error already set
  }
};
</script>

<style scoped>
.success {
  margin: 0;
  font-size: 13px;
  text-align: center;
  color: #16a34a;
}
</style>
