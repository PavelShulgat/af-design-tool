<template>
  <div class="page">
    <div class="top">
      <button class="back" @click="$router.back()">← Back</button>
    </div>

    <div v-if="loading" class="card">Loading…</div>
    <div v-else-if="error" class="card error">{{ error }}</div>

    <div v-else class="layout">
      <section class="left">
        <h1 class="title">{{ tool.name }}</h1>

        <div class="meta">
          <span class="pill">{{ tool.typeName || "Unknown type" }}</span>
        </div>

        <p class="desc" v-if="tool.description">{{ tool.description }}</p>

        <h3 class="section">Information</h3>
        <p class="info" v-if="tool.info">{{ tool.info }}</p>
        <p class="info muted" v-else>No extra info yet.</p>
      </section>

      <aside class="right" v-if="tool.image_path">
        <div class="hero">
          <img class="hero-img" :src="tool.image_path" :alt="tool.name" />
        </div>
      </aside>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from "vue";
import { api } from "../api";

const props = defineProps({
  id: { type: [String, Number], required: true },
});

const tool = ref(null);
const loading = ref(true);
const error = ref(null);

onMounted(async () => {
  try {
    const { data } = await api.get(`/api/tools/${props.id}`);
    tool.value = data;
  } catch (e) {
    error.value = e?.response?.data?.message || e.message || "Failed to load tool";
  } finally {
    loading.value = false;
  }
});
</script>

<style scoped>
.page {
  max-width: 1600px;
  margin: 0 auto;
  padding: 24px;
}

@media (min-width: 1200px) {
  .page { padding: 32px 48px; }
}

.top { margin-bottom: 16px; }
.back { border: 0; background: transparent; cursor: pointer; font-size: 14px; }

.card {
  background: white;
  border-radius: 16px;
  padding: 20px;
  box-shadow: 0 6px 20px rgba(0,0,0,0.06);
}

.error { color: #b00020; }

.layout {
  display: grid;
  grid-template-columns: 1.1fr 0.9fr; /* text slightly wider */
  gap: 24px;
  align-items: start;
  background: white;
  border-radius: 16px;
  padding: 20px;
  box-shadow: 0 6px 20px rgba(0,0,0,0.06);
}

.title { margin: 0 0 10px; font-size: 34px; }
.meta { display: flex; gap: 8px; margin-bottom: 16px; }
.pill { padding: 6px 10px; border-radius: 999px; font-size: 13px; background: #f1f3f5; }

.desc { margin: 0 0 16px; line-height: 1.6; }
.section { margin: 16px 0 8px; }
.info { margin: 0; line-height: 1.7; }
.muted { opacity: 0.6; }

.hero {
  border-radius: 14px;
  overflow: hidden;
  box-shadow: 0 0 10px rgba(0,0,0,0.01);
}

.hero-img {
  width: 100%;
  height: min(70vh, 720px);
  object-fit: contain; /* show full tool, no cropping */
  display: block;
}

/* Mobile: stack */
@media (max-width: 900px) {
  .layout { grid-template-columns: 1fr; }
  .hero-img { height: 320px; }
}

</style>
