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
        :operations="operations"
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
  fetchTools,
  // fetchOperations // <- add when backend endpoint exists
} from '../api';

import ContextForm from '../components/ContextForm.vue';
import ToolList from '../components/ToolList.vue';

const types = ref([]);
const trees = ref([]);
const plants = ref([]);
const operations = ref([]);   // NEW
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
      // fetchOperations()
    ]);

    types.value = tTypes;
    trees.value = tTrees;
    plants.value = tPlants;
    allTools.value = tTools;

    // TEMP demo operations (remove once you have fetchOperations())
    operations.value = [
      { id: 1, name: 'Planting', agroforestryTypeName: 'Silvo-arable' },
      { id: 2, name: 'Weed control', agroforestryTypeName: 'Silvo-arable' },
      { id: 3, name: 'Pruning', agroforestryTypeName: 'Silvopastoral' },
      { id: 4, name: 'Pasture management', agroforestryTypeName: 'Silvopastoral' }
    ];

  } catch (e) {
    console.error(e);
    error.value = 'Failed to load reference data from the backend.';
  } finally {
    loading.value = false;
  }
});

function onContextSubmit(payload) {
  hasSearched.value = true;

  const { typeId, treeId, plantId, operationId } = payload;

  const type = types.value.find(t => t.id === typeId);
  const tree = trees.value.find(t => t.id === treeId);
  const plant = plantId ? plants.value.find(p => p.id === plantId) : null;
  const operation = operations.value.find(o => o.id === operationId);

  if (!type || !tree || !operation) {
    recommendations.value = [];
    return;
  }

  // demo rule until you have a real endpoint:
  // - if operation is Weed control -> show weed tools
  // - if operation is Planting -> show seeding tools
  // - else show all
  const op = operation.name.toLowerCase();

  recommendations.value = allTools.value.filter(tool => {
    const name = (tool.name || '').toLowerCase();
    const desc = (tool.description || '').toLowerCase();

    if (op.includes('weed')) return name.includes('hoe') || name.includes('sprayer') || desc.includes('weed');
    if (op.includes('plant')) return name.includes('seeder') || desc.includes('seed') || desc.includes('plant');
    if (op.includes('prun')) return name.includes('prun') || desc.includes('prun');
    if (op.includes('pasture')) return desc.includes('pasture') || desc.includes('forage');

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
