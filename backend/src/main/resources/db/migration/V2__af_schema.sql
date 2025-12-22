-- 1) Types of agroforestry systems
CREATE TABLE IF NOT EXISTS agroforestry_types (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(150) NOT NULL UNIQUE,
    description TEXT,
    created_at  TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- 2) Operations 
CREATE TABLE IF NOT EXISTS operations (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(150) NOT NULL UNIQUE,
    description TEXT,
    created_at  TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- 3) Farming technology types 
CREATE TABLE IF NOT EXISTS farming_technology_type (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(100) NOT NULL UNIQUE,
    description TEXT
);

-- 4) Farming technology 
CREATE TABLE IF NOT EXISTS farming_technology (
    id                         BIGSERIAL PRIMARY KEY,
    name                       VARCHAR(150) NOT NULL UNIQUE,
    farming_technology_type_id BIGINT NOT NULL REFERENCES farming_technology_type(id),
    description                TEXT,
    created_at                 TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at                 TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- 5) Trees 
CREATE TABLE IF NOT EXISTS trees (
    id    BIGSERIAL PRIMARY KEY,
    name  VARCHAR(150) NOT NULL UNIQUE,
    notes TEXT
);

-- 6) Plants 
CREATE TABLE IF NOT EXISTS plants (
    id    BIGSERIAL PRIMARY KEY,
    name  VARCHAR(150) NOT NULL UNIQUE,
    notes TEXT
);

-- 7) Livestock 
CREATE TABLE IF NOT EXISTS livestock (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(150) NOT NULL UNIQUE,
    description TEXT
);

-- 8) Silvo-arable combinations: AF type + tree + plant + operation + farming technology
CREATE TABLE IF NOT EXISTS silvo_arable_combinations (
    id                    BIGSERIAL PRIMARY KEY,
    agroforestry_type_id  BIGINT NOT NULL REFERENCES agroforestry_types(id),
    tree_id               BIGINT NOT NULL REFERENCES trees(id),
    plant_id              BIGINT NOT NULL REFERENCES plants(id),
    operation_id          BIGINT NOT NULL REFERENCES operations(id),
    farming_technology_id BIGINT NOT NULL REFERENCES farming_technology(id),
    notes                 TEXT,
    UNIQUE (agroforestry_type_id, tree_id, plant_id, operation_id, farming_technology_id)
);

-- 9) Silvopastoral combinations: AF type + tree + livestock + operation + farming technology
CREATE TABLE IF NOT EXISTS silvo_pastoral_combinations (
    id                    BIGSERIAL PRIMARY KEY,
    agroforestry_type_id  BIGINT NOT NULL REFERENCES agroforestry_types(id),
    tree_id               BIGINT NOT NULL REFERENCES trees(id),
    livestock_id          BIGINT NOT NULL REFERENCES livestock(id),
    operation_id          BIGINT NOT NULL REFERENCES operations(id),
    farming_technology_id BIGINT NOT NULL REFERENCES farming_technology(id),
    notes                 TEXT,
    UNIQUE (agroforestry_type_id, tree_id, livestock_id, operation_id, farming_technology_id)
);

-- Helpful indexes 
CREATE INDEX IF NOT EXISTS idx_silvo_arable_af_type
    ON silvo_arable_combinations (agroforestry_type_id);

CREATE INDEX IF NOT EXISTS idx_silvo_pastoral_af_type
    ON silvo_pastoral_combinations (agroforestry_type_id);

CREATE INDEX IF NOT EXISTS idx_farming_technology_type
    ON farming_technology (farming_technology_type_id);
