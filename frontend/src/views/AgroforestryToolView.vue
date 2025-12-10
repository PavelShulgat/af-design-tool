<template>
  <main class="page">
    <section class="panel">
      <h1>Agroforestry tool</h1>
      <p class="subtitle">
        Select system context and explore which machines are available from the knowledge base.
      </p>

      <ContextForm
        :types="types"
        :trees="trees"
        :plants="plants"
        :loading="loading"
        @submit="onContextSubmit"
      />

      <ToolList
        :tools="recommendations"
        :hasSearched="hasSearched"
      />

      <p v-if="error" class="error">{{ error }}</p>
    </section>
  </main>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import {
  fetchAgroforestryTypes,
  fetchTrees,
  fetchPlants,
  fetchTools
} from '../api';

import ContextForm from '../components/ContextForm.vue';
import ToolList from '../components/ToolList.vue';

const types = ref([]);
const trees = ref([]);
const plants = ref([]);
const allTools = ref([]);

const recommendations = ref([]);
const hasSearched = ref(false);
const loading = ref(false);
const error = ref('');

onMounted(async () => {
  try {
    loading.value = true;
    const [tTypes, tTrees, tPlants, tTools] = await Promise.all([
      fetchAgroforestryTypes(),
      fetchTrees(),
      fetchPlants(),
      fetchTools()
    ]);
    types.value = tTypes;
    trees.value = tTrees;
    plants.value = tPlants;
    allTools.value = tTools;
  } catch (e) {
    console.error(e);
    error.value = 'Failed to load reference data from the backend.';
  } finally {
    loading.value = false;
  }
});

function onContextSubmit(payload) {
  hasSearched.value = true;
  const { typeId, treeId, plantId } = payload;

  const type = types.value.find(t => t.id === typeId);
  const tree = trees.value.find(t => t.id === treeId);
  const plant = plants.value.find(p => p.id === plantId);

  if (!type || !tree || !plant) {
    recommendations.value = [];
    return;
  }

  // simple demo rule; later replace by real /recommendations endpoint
  const isCropSystem = type.name.toLowerCase().includes('silvo')
    && !!plant;

  recommendations.value = allTools.value.filter(tool => {
    if (!tool.category) return true;
    if (isCropSystem) {
      return tool.category.toLowerCase().includes('precision');
    }
    return true;
  });
}
</script>

<style scoped>
.page {
  min-height: 100vh;
  background: #f3f4f6;
  padding: 2rem;
  display: flex;
  justify-content: center;
}

.panel {
  width: 100%;
  max-width: 960px;
  background: #ffffff;
  border-radius: 1rem;
  padding: 2rem 2.5rem;
  box-shadow: 0 12px 30px rgba(15, 23, 42, 0.08);
}

h1 {
  margin: 0 0 0.5rem;
}

.subtitle {
  margin: 0 0 1.5rem;
  color: #6b7280;
}

.error {
  margin-top: 0.75rem;
  color: #b91c1c;
}
</style>
