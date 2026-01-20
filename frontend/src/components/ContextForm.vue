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
          <option v-for="type in types" :key="type.id" :value="String(type.id)">
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
            <option v-for="tree in filteredTrees" :key="tree.id" :value="String(tree.id)">
              {{ tree.name }}
            </option>
          </select>
        </label>

        <!-- Silvo-arable -->
        <label v-if="isSilvoArable" class="field">
          <span>Crop / plant</span>
          <select v-model="localPlantId" :disabled="!hasType || !localTreeId">
            <option disabled value="">Choose crop / plant…</option>
            <option v-for="plant in filteredPlants" :key="plant.id" :value="String(plant.id)">
              {{ plant.name }}
            </option>
          </select>
        </label>

        <!-- Silvopastoral -->
        <label v-else-if="isSilvopastoral" class="field">
          <span>Livestock</span>
          <select v-model="localLivestockId" :disabled="!hasType || !localTreeId">
            <option disabled value="">Choose livestock…</option>
            <option v-for="l in filteredLivestock" :key="l.id" :value="String(l.id)">
              {{ l.name }}
            </option>
          </select>
        </label>
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
          <option v-for="op in filteredOperations" :key="op.id" :value="String(op.id)">
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
  livestock: { type: Array, default: () => [] }, // ✅ NEW
  operations: { type: Array, default: () => [] },
  loading: { type: Boolean, default: false },
});

const emit = defineEmits(["submit"]);

const localTypeId = ref("");
const localTreeId = ref("");
const localPlantId = ref("");
const localLivestockId = ref(""); // ✅ NEW
const localOperationId = ref("");

const hasType = computed(() => !!localTypeId.value);

const selectedType = computed(() =>
  props.types.find((t) => String(t.id) === String(localTypeId.value))
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
    (t) => !t.agroforestryTypeId || String(t.agroforestryTypeId) === String(selectedType.value.id)
  );
});

const filteredPlants = computed(() => {
  if (!selectedType.value) return [];
  return props.plants.filter(
    (p) => !p.agroforestryTypeId || String(p.agroforestryTypeId) === String(selectedType.value.id)
  );
});

// Most likely livestock is NOT type-filtered in DB yet, but we keep the same pattern.
const filteredLivestock = computed(() => {
  if (!selectedType.value) return [];
  return props.livestock.filter(
    (l) => !l.agroforestryTypeId || String(l.agroforestryTypeId) === String(selectedType.value.id)
  );
});

const filteredOperations = computed(() => {
  if (!selectedType.value) return [];

  const byId = props.operations.filter(
    (o) => String(o.agroforestryTypeId) === String(selectedType.value.id)
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
  localLivestockId.value = ""; // ✅ reset
  localOperationId.value = "";
});

watch(localTreeId, () => {
  localPlantId.value = "";
  localLivestockId.value = "";
});

const canSubmit = computed(() => {
  if (!hasType.value) return false;
  if (!localTreeId.value) return false;
  if (!localOperationId.value) return false;

  if (isSilvoArable.value) return !!localPlantId.value;
  if (isSilvopastoral.value) return !!localLivestockId.value;

  return false;
});

function submit() {
  if (!canSubmit.value) return;

  emit("submit", {
    typeId: Number(localTypeId.value),
    treeId: Number(localTreeId.value),
    plantId: isSilvoArable.value ? Number(localPlantId.value) : null,
    livestockId: isSilvopastoral.value ? Number(localLivestockId.value) : null,
    operationId: Number(localOperationId.value),
  });
}
</script>

<style scoped>
/* unchanged styles from your file */
.steps-wrap {
  width: 100%;
  max-width: 1400px;
  margin: 0 auto;
}

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
