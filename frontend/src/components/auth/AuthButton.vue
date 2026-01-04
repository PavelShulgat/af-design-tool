<template>
  <button class="btn" type="submit" :disabled="disabled">
    <slot />
  </button>
</template>

<script setup>
defineProps({
  disabled: { type: Boolean, default: false },
});
</script>
