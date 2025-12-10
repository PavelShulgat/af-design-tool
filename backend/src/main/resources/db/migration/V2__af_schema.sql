-- Agroforestry Design Tool schema (aligned with ERD)

-- 1) Types of agroforestry systems
CREATE TABLE IF NOT EXISTS agroforestry_types (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(150) NOT NULL UNIQUE,
    description TEXT,
    created_at  TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- 2) Operations (per agroforestry type)
CREATE TABLE IF NOT EXISTS operations (
    id                   BIGSERIAL PRIMARY KEY,
    agroforestry_type_id BIGINT NOT NULL REFERENCES agroforestry_types(id),
    name                 VARCHAR(150) NOT NULL,
    description          TEXT,
    created_at           TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at           TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- 3) Tool categories (conventional / modern precision / ...)
CREATE TABLE IF NOT EXISTS tools_categories (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(100) NOT NULL UNIQUE,
    description TEXT
);

-- 4) Tools
CREATE TABLE IF NOT EXISTS tools (
    id                     BIGSERIAL PRIMARY KEY,
    name                   VARCHAR(150) NOT NULL UNIQUE,
    technology_category_id BIGINT NOT NULL REFERENCES tools_categories(id),
    operation_id           BIGINT REFERENCES operations(id),
    description            TEXT,
    created_at             TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at             TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- 5) Trees
CREATE TABLE IF NOT EXISTS trees (
    id                   BIGSERIAL PRIMARY KEY,
    agroforestry_type_id BIGINT REFERENCES agroforestry_types(id),
    name                 VARCHAR(150) NOT NULL UNIQUE,
    notes                TEXT
);

-- 6) Plants (non-tree crops)
CREATE TABLE IF NOT EXISTS plants (
    id                   BIGSERIAL PRIMARY KEY,
    agroforestry_type_id BIGINT REFERENCES agroforestry_types(id),
    name                 VARCHAR(150) NOT NULL UNIQUE,
    notes                TEXT
);

-- 7) Livestock
CREATE TABLE IF NOT EXISTS livestock (
    id                   BIGSERIAL PRIMARY KEY,
    agroforestry_type_id BIGINT REFERENCES agroforestry_types(id),
    name                 VARCHAR(150) NOT NULL UNIQUE,
    description          TEXT
);

-- 8) Soil types
CREATE TABLE IF NOT EXISTS soil_types (
    id                   BIGSERIAL PRIMARY KEY,
    agroforestry_type_id BIGINT REFERENCES agroforestry_types(id),
    name                 VARCHAR(150) NOT NULL UNIQUE,
    description          TEXT
);

-- 9) Many-to-many between operations and tools
CREATE TABLE IF NOT EXISTS operations_tools (
    operation_id BIGINT NOT NULL REFERENCES operations(id),
    tool_id      BIGINT NOT NULL REFERENCES tools(id),
    PRIMARY KEY (operation_id, tool_id)
);

-- 10) Silvo-arable combinations: AF type + soil + tree + plant
CREATE TABLE IF NOT EXISTS silvo_arable_combinations (
    agroforestry_type_id BIGINT NOT NULL REFERENCES agroforestry_types(id),
    soil_type_id         BIGINT NOT NULL REFERENCES soil_types(id),
    tree_id              BIGINT NOT NULL REFERENCES trees(id),
    plant_id             BIGINT NOT NULL REFERENCES plants(id),
    notes                TEXT,
    PRIMARY KEY (agroforestry_type_id, soil_type_id, tree_id, plant_id)
);

-- 11) Silvopastoral combinations: AF type + soil + tree + livestock
CREATE TABLE IF NOT EXISTS silvo_pastoral_combinations (
    agroforestry_type_id BIGINT NOT NULL REFERENCES agroforestry_types(id),
    soil_type_id         BIGINT NOT NULL REFERENCES soil_types(id),
    tree_id              BIGINT NOT NULL REFERENCES trees(id),
    livestock_id         BIGINT NOT NULL REFERENCES livestock(id),
    notes                TEXT,
    PRIMARY KEY (agroforestry_type_id, soil_type_id, tree_id, livestock_id)
);

