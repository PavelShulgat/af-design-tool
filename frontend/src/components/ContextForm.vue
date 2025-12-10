<template>
  <div class="form-grid">
    <!-- Step 1 -->
    <div class="form-block">
      <h2>Step 1 – System type</h2>
      <select v-model="localTypeId">
        <option disabled value="">Choose agroforestry type…</option>
        <option
          v-for="type in types"
          :key="type.id"
          :value="type.id"
        >
          {{ type.name }}
        </option>
      </select>
    </div>

    <!-- Step 2 -->
    <div class="form-block">
      <h2>Step 2 – Context</h2>

      <p v-if="!hasType" class="hint">
        First select a system type in Step&nbsp;1.
      </p>

      <label class="field">
        <span>Tree species</span>
        <select v-model="localTreeId" :disabled="!hasType">
          <option disabled value="">Choose tree…</option>
          <option
            v-for="tree in trees"
            :key="tree.id"
            :value="tree.id"
          >
            {{ tree.name }}
          </option>
        </select>
      </label>

      <!-- Silvo-arable: tree + crop/plant -->
      <label
        v-if="isSilvoArable"
        class="field"
      >
        <span>Crop / plant</span>
        <select v-model="localPlantId" :disabled="!hasType">
          <option disabled value="">Choose crop / plant…</option>
          <option
            v-for="plant in plants"
            :key="plant.id"
            :value="plant.id"
          >
            {{ plant.name }}
          </option>
        </select>
      </label>

      <!-- Silvopastoral: tree + livestock (future) -->
      <div
        v-else-if="isSilvopastoral"
        class="field placeholder"
      >
        <span>Livestock</span>
        <div class="placeholder-box">
          Livestock selection will be added in the next phase.  
          For now, recommendations are based on tree + system type.
        </div>
      </div>
    </div>
  </div>

  <button
    class="primary-btn"
    :disabled="!canSubmit || loading"
    @click="submit"
  >
    {{ loading ? 'Loading…' : 'Show recommended machines' }}
  </button>
</template>

<script setup>
import { computed, ref } from 'vue';

const props = defineProps({
  types: { type: Array, default: () => [] },
  trees: { type: Array, default: () => [] },
  plants: { type: Array, default: () => [] },
  loading: { type: Boolean, default: false }
});

const emit = defineEmits(['submit']);

const localTypeId = ref('');
const localTreeId = ref('');
const localPlantId = ref('');

const hasType = computed(() => !!localTypeId.value);

const selectedType = computed(() =>
  props.types.find(t => t.id === localTypeId.value)
);

const isSilvoArable = computed(() =>
  selectedType.value &&
  selectedType.value.name.toLowerCase().includes('silvo-arable')
);

const isSilvopastoral = computed(() =>
  selectedType.value &&
  selectedType.value.name.toLowerCase().includes('silvopastoral')
);

// For Silvo-arable: need type + tree + plant
// For Silvopastoral: need type + tree
const canSubmit = computed(() => {
  if (!hasType.value || !localTreeId.value) return false;
  if (isSilvoArable.value) {
    return !!localPlantId.value;
  }
  return true; // silvopastoral
});

function submit() {
  if (!canSubmit.value) return;

  emit('submit', {
    typeId: localTypeId.value,
    treeId: localTreeId.value,
    plantId: isSilvoArable.value ? localPlantId.value : null
  });
}
</script>

<style scoped>
.form-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(260px, 1fr));
  gap: 1.5rem;
  margin-bottom: 1.5rem;
}

.form-block h2 {
  font-size: 1.1rem;
  margin-bottom: 0.75rem;
}

.field {
  display: flex;
  flex-direction: column;
  margin-bottom: 0.75rem;
  font-size: 0.95rem;
}

.field span {
  margin-bottom: 0.25rem;
}

select {
  padding: 0.5rem 0.75rem;
  border-radius: 0.5rem;
  border: 1px solid #d1d5db;
  font-size: 0.95rem;
  background: white;
}

select:disabled {
  background: #f3f4f6;
  cursor: not-allowed;
}

.hint {
  margin: 0 0 0.5rem;
  font-size: 0.85rem;
  color: #9ca3af;
}

.placeholder-box {
  padding: 0.7rem 0.9rem;
  border-radius: 0.6rem;
  border: 1px dashed #d1d5db;
  background: #f9fafb;
  font-size: 0.85rem;
  color: #6b7280;
}

.primary-btn {
  margin-top: 0.5rem;
  padding: 0.6rem 1.4rem;
  border-radius: 999px;
  border: none;
  background: #16a34a;
  color: white;
  font-weight: 600;
  cursor: pointer;
}

.primary-btn:disabled {
  opacity: 0.5;
  cursor: default;
}
</style>
