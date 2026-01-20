<template>
  <div class="dropdown" ref="root">
    <button class="btn" :class="variantClass" type="button" @click="open = !open">
      {{ title }}
      <span class="chev" :class="{ up: open }">▾</span>
    </button>

    <div v-if="open" class="menu">
      <button
        v-for="item in items"
        :key="item.to"
        class="item"
        type="button"
        @click="go(item.to)"
      >
        {{ item.label }}
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount, computed } from "vue";
import { useRouter } from "vue-router";

const props = defineProps({
  title: { type: String, default: "Menu" },
  items: { type: Array, default: () => [] }, 
  variant: { type: String, default: "solid" },
});

const variantClass = computed(() =>
  props.variant === "ghost" ? "btn-ghost" : "btn-solid"
);

const router = useRouter();
const open = ref(false);
const root = ref(null);

const go = (to) => {
  open.value = false;
  router.push(to);
};

const onClickOutside = (e) => {
  if (!root.value) return;
  if (!root.value.contains(e.target)) open.value = false;
};

onMounted(() => document.addEventListener("click", onClickOutside));
onBeforeUnmount(() => document.removeEventListener("click", onClickOutside));
</script>

<style scoped>
.dropdown {
  position: relative;
}

.btn {
  border-radius: var(--radius);
  padding: 10px 14px;
  font-weight: 800;
  border: 1px solid var(--color-border);
  background: #fff;
  color: #111;
  cursor: pointer;
  display: flex;
  gap: 8px;
  align-items: center;
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

.chev {
  transition: transform 0.15s ease;
}
.chev.up {
  transform: rotate(180deg);
}

.menu {
  position: absolute;
  top: calc(100% + 10px);
  left: 0;
  min-width: 280px;
  background: #fff;
  border: 1px solid var(--border);
  border-radius: 12px;
  box-shadow: 0 18px 40px rgba(17, 24, 39, 0.14);
  padding: 10px;
  z-index: 20;
}

.item {
  width: 100%;
  text-align: left;
  border: 0;
  background: transparent;
  padding: 10px 10px;
  border-radius: 10px;
  font-weight: 700;
  cursor: pointer;
}

.item:hover {
  background: rgba(46, 204, 113, 0.12);
}
</style>
