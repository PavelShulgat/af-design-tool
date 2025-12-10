<template>
  <section v-if="tools.length" class="results">
    <h2>Recommended machines</h2>

    <ul class="tool-list">
      <li v-for="tool in tools" :key="tool.id" class="tool-card">
        <h3>{{ tool.name }}</h3>
        <p v-if="tool.category" class="badge">{{ tool.category }}</p>
        <p class="desc">{{ tool.description || 'No description yet.' }}</p>
      </li>
    </ul>
  </section>

  <section v-else-if="hasSearched" class="results">
    <h2>No machines found</h2>
    <p>
      Try selecting a different combination. The database currently contains only a small example dataset.
    </p>
  </section>
</template>

<script setup>
const props = defineProps({
  tools: { type: Array, default: () => [] },
  hasSearched: { type: Boolean, default: false }
});
</script>

<style scoped>
.results {
  margin-top: 2rem;
}

.hint {
  color: #6b7280;
  margin-bottom: 1rem;
}

.tool-list {
  list-style: none;
  padding: 0;
  margin: 0;
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(260px, 1fr));
  gap: 1rem;
}

.tool-card {
  background: #f9fafb;
  border-radius: 0.75rem;
  padding: 1rem;
  border: 1px solid #e5e7eb;
}

.tool-card h3 {
  margin: 0 0 0.25rem;
}

.badge {
  display: inline-block;
  padding: 0.1rem 0.6rem;
  border-radius: 999px;
  background: #e0f2fe;
  color: #0369a1;
  font-size: 0.75rem;
  margin-bottom: 0.4rem;
}

.desc {
  margin: 0;
  font-size: 0.9rem;
  color: #4b5563;
}
</style>
