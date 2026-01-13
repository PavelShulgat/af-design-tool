<template>
  <main class="page">
    <div class="hero">
        <h1>Agroforestry Design Tool</h1>
      </div>
    <section class="panel">
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
  width: 100%;
  background: #fff;
  padding: 18px 0 80px;
}

.panel {
  width: 100%;
  max-width: 1920px;
  margin: 0 auto;
  padding: 0 24px;
}

/* Title stays near top */
.hero {
  margin: 6px 0 18px;
  text-align: center;
}

.hero h1 {
  margin: 0;
  font-size: 56px;
  line-height: 1.02;
  letter-spacing: -0.9px;
  color: var(--color-text);
}
</style>


