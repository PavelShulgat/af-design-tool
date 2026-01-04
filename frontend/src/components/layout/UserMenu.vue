<template>
  <div class="user">
    <div class="pill">
      <span class="icon">👤</span>
      <span class="name">{{ name }}</span>
    </div>

    <button class="logout" type="button" @click="$emit('logout')">
      Log out
    </button>
  </div>
</template>

<script setup>
defineProps({
  name: { type: String, default: "User" },
});
defineEmits(["logout"]);
</script>

<style scoped>
.user {
  display: flex;
  align-items: center;
  gap: 10px;
}

.pill {
  display: flex;
  align-items: center;
  gap: 8px;
  border: 1px solid var(--border);
  border-radius: 12px;
  padding: 8px 12px;
  background: #fff;
}

.icon {
  font-size: 14px;
}
.name {
  font-weight: 800;
}

.logout {
  border: 0;
  border-radius: 12px;
  padding: 10px 14px;
  font-weight: 800;
  cursor: pointer;
  background: #ef4444;
  color: #fff;
}

.logout:hover {
  filter: brightness(0.95);
}
</style>
