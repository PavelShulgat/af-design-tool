<template>
  <AppHeader />
  <main class="app-main">
    <router-view />
  </main>
</template>

<script setup>
import AppHeader from "./components/layout/AppHeader.vue";
</script>

<style>
.app-main {
  flex: 1;
  width: 100%;
  min-height: 0;
  display: block;   
  padding: 0;         
  margin: 0;         
}
</style>