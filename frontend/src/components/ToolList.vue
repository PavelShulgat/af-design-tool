<template>
  <section v-if="tools.length" class="results">
    <h2>Recommended machines</h2>

    <ul class="tool-list">
      <li v-for="tool in tools" :key="tool.id">
        <router-link
          class="tool-card tool-link"
          :to="{ name: 'tool-details', params: { id: tool.id } }"
        >
          <h3>{{ tool.name }}</h3>
          <p v-if="tool.category" class="badge">{{ tool.category }}</p>
          <p class="desc">{{ tool.description || 'No description yet.' }}</p>
        </router-link>
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
  margin-top: 34px;
}

.results h2 {
  margin: 0 0 12px;
  font-size: 18px;
  font-weight: 800;
  color: #111;
}

.tool-list {
  list-style: none;
  padding: 0;
  margin: 0;
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(260px, 1fr));
  gap: 14px;
}

/* moved card styles to the router-link */
.tool-card {
  background: #fff;
  border-radius: 12px;
  padding: 14px;
  border: 1px solid #e5e7eb;
}

.tool-link {
  display: block;
  text-decoration: none;
  color: inherit;
  cursor: pointer;
  transition: transform 0.08s ease, box-shadow 0.08s ease;
}

.tool-link:hover {
  transform: translateY(-1px);
  box-shadow: 0 10px 22px rgba(0,0,0,0.06);
}

.tool-link:focus-visible {
  outline: 3px solid rgba(59,130,246,0.5);
  outline-offset: 3px;
}

.tool-card h3 {
  margin: 0 0 6px;
  font-size: 15px;
  font-weight: 800;
  color: #111;
}

.badge {
  display: inline-block;
  padding: 2px 10px;
  border-radius: 999px;
  border: 1px solid #d1d5db;
  color: #111;
  font-size: 12px;
  margin-bottom: 8px;
}

.desc {
  margin: 0;
  font-size: 13px;
  line-height: 1.5;
  color: #111;
  opacity: 0.75;
}
</style>
