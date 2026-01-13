<template>
  <div class="steps-wrap">
    <div class="form-grid">
      <!-- Step 1 -->
      <section class="step-card">
        <header class="step-head">
          <h2>Step 1</h2>
          <p>Agroforestry type</p>
        </header>

        <select v-model="localTypeId">
          <option disabled value="">Choose agroforestry type…</option>
          <option v-for="type in types" :key="type.id" :value="type.id">
            {{ type.name }}
          </option>
        </select>
      </section>

      <!-- Step 2 -->
      <section class="step-card">
        <header class="step-head">
          <h2>Step 2</h2>
          <p>Flora</p>
        </header>

        <p v-if="!hasType" class="hint">
          First select an agroforestry type in Step 1.
        </p>

        <label class="field">
          <span>Tree species</span>
          <select v-model="localTreeId" :disabled="!hasType">
            <option disabled value="">Choose tree…</option>
            <option v-for="tree in filteredTrees" :key="tree.id" :value="tree.id">
              {{ tree.name }}
            </option>
          </select>
        </label>

        <label v-if="isSilvoArable" class="field">
          <span>Crop / plant</span>
          <select v-model="localPlantId" :disabled="!hasType">
            <option disabled value="">Choose crop / plant…</option>
            <option v-for="plant in filteredPlants" :key="plant.id" :value="plant.id">
              {{ plant.name }}
            </option>
          </select>
        </label>

        <div v-else-if="isSilvopastoral" class="field placeholder">
          <span>Livestock</span>
          <div class="placeholder-box">
            Livestock selection will be added in the next phase.
            For now, recommendations are based on tree + system type.
          </div>
        </div>
      </section>

      <!-- Step 3 -->
      <section class="step-card">
        <header class="step-head">
          <h2>Step 3</h2>
          <p>Operation</p>
        </header>

        <p v-if="!hasType" class="hint">Select agroforestry type first.</p>

        <select v-model="localOperationId" :disabled="!hasType">
          <option disabled value="">Choose operation…</option>
          <option v-for="op in filteredOperations" :key="op.id" :value="op.id">
            {{ op.name }}
          </option>
        </select>
      </section>
    </div>

    <div class="actions">
      <button class="primary-btn" :disabled="!canSubmit || loading" @click="submit">
        {{ loading ? "Loading…" : "Show recommended machines" }}
      </button>
    </div>
  </div>
</template>

<script setup>
import { computed, ref, watch } from "vue";

const props = defineProps({
  types: { type: Array, default: () => [] },
  trees: { type: Array, default: () => [] },
  plants: { type: Array, default: () => [] },
  operations: { type: Array, default: () => [] },
  loading: { type: Boolean, default: false },
});

const emit = defineEmits(["submit"]);

const localTypeId = ref("");
const localTreeId = ref("");
const localPlantId = ref("");
const localOperationId = ref("");

const hasType = computed(() => !!localTypeId.value);

const selectedType = computed(() =>
  props.types.find((t) => t.id === localTypeId.value)
);

const isSilvoArable = computed(
  () => selectedType.value && selectedType.value.name.toLowerCase().includes("silvo-arable")
);

const isSilvopastoral = computed(
  () => selectedType.value && selectedType.value.name.toLowerCase().includes("silvopastoral")
);

const filteredTrees = computed(() => {
  if (!selectedType.value) return [];
  return props.trees.filter(
    (t) => !t.agroforestryTypeId || t.agroforestryTypeId === selectedType.value.id
  );
});

const filteredPlants = computed(() => {
  if (!selectedType.value) return [];
  return props.plants.filter(
    (p) => !p.agroforestryTypeId || p.agroforestryTypeId === selectedType.value.id
  );
});

const filteredOperations = computed(() => {
  if (!selectedType.value) return [];

  const byId = props.operations.filter(
    (o) => o.agroforestryTypeId === selectedType.value.id
  );
  if (byId.length) return byId;

  const typeName = selectedType.value.name?.toLowerCase() || "";
  return props.operations.filter(
    (o) => (o.agroforestryTypeName || "").toLowerCase() === typeName
  );
});

watch(localTypeId, () => {
  localTreeId.value = "";
  localPlantId.value = "";
  localOperationId.value = "";
});

const canSubmit = computed(() => {
  if (!hasType.value) return false;
  if (!localTreeId.value) return false;
  if (!localOperationId.value) return false;

  if (isSilvoArable.value) return !!localPlantId.value;
  return true;
});

function submit() {
  if (!canSubmit.value) return;

  emit("submit", {
    typeId: localTypeId.value,
    treeId: localTreeId.value,
    plantId: isSilvoArable.value ? localPlantId.value : null,
    operationId: localOperationId.value,
  });
}
</script>

<style scoped>
.steps-wrap {
  width: 100%;
  max-width: 1400px;
  margin: 0 auto;
}

/* ✅ equal width columns */
.form-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 22px;
  width: 100%;
  margin-top: 14px;
  align-items: stretch;
}

@media (max-width: 1050px) {
  .form-grid {
    grid-template-columns: 1fr;
  }
}

.step-card {
  border: 1px solid var(--color-border);
  border-radius: 14px;
  padding: 14px;
  background: #fff;
  min-width: 0;
}

.step-head {
  display: flex;
  align-items: baseline;
  justify-content: space-between;
  gap: 10px;
  margin-bottom: 12px;
  padding-bottom: 10px;
  border-bottom: 1px solid rgba(0,0,0,0.06);
}

.step-head h2 {
  margin: 0;
  font-size: 13px;
  font-weight: 900;
  text-transform: uppercase;
  letter-spacing: 0.08em;
}

.step-head p {
  margin: 0;
  font-size: 13px;
  color: var(--color-muted);
  font-weight: 600;
}

.field {
  display: flex;
  flex-direction: column;
  gap: 6px;
  margin-top: 10px;
}

.field span {
  font-size: 13px;
  font-weight: 700;
  color: var(--color-text);
}

select {
  width: 100%;
  height: 42px;
  padding: 0 12px;
  border-radius: 10px;
  border: 1px solid var(--color-border);
  font-size: 14px;
  background: #fff;
  color: var(--color-text);
  outline: none;
}

select:focus {
  border-color: rgba(0,0,0,0.35);
  box-shadow: 0 0 0 3px rgba(47, 143, 70, 0.12);
}

select:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.hint {
  margin: 2px 0 6px;
  font-size: 13px;
  color: var(--color-muted);
}

.placeholder-box {
  width: 100%;
  padding: 12px;
  border-radius: 10px;
  border: 1px dashed var(--color-border);
  background: #fff;
  font-size: 13px;
  color: var(--color-muted);
}

.actions {
  display: flex;
  justify-content: center;
  margin-top: 18px;
}

.primary-btn {
  height: 44px;
  padding: 0 18px;
  border-radius: 10px;
  border: 1px solid #111;
  background: #111;
  color: #fff;
  font-weight: 800;
  cursor: pointer;
  transition: opacity 0.15s ease, transform 0.08s ease;
}

.primary-btn:hover { opacity: 0.92; }
.primary-btn:active { transform: translateY(1px); }
.primary-btn:disabled { opacity: 0.45; cursor: not-allowed; }
</style>
