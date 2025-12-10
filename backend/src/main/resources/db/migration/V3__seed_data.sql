-- V3__seed_data.sql
-- Initial reference data for the Agroforestry Design Tool
-- Updated to match the relational data model (2025)

--------------------------------------------------------
-- 1) Agroforestry Types
--------------------------------------------------------
INSERT INTO agroforestry_types (name, description) VALUES
  ('Silvo-arable', 'Trees combined with crops'),
  ('Silvopastoral', 'Trees combined with livestock')
ON CONFLICT (name) DO NOTHING;

--------------------------------------------------------
-- 2) Operations (linked later via operations_tools)
--------------------------------------------------------
INSERT INTO operations (agroforestry_type_id, name, description)
VALUES
  ((SELECT id FROM agroforestry_types WHERE name = 'Silvo-arable'),
      'Planting', 'Planting crops or trees'),
  ((SELECT id FROM agroforestry_types WHERE name = 'Silvo-arable'),
      'Weed control', 'Controlling weeds between tree rows'),
  ((SELECT id FROM agroforestry_types WHERE name = 'Silvopastoral'),
      'Pruning', 'Managing tree canopy for grazing systems'),
  ((SELECT id FROM agroforestry_types WHERE name = 'Silvopastoral'),
      'Pasture management', 'Managing grass and forage')
ON CONFLICT DO NOTHING;

--------------------------------------------------------
-- 3) Tool Categories
--------------------------------------------------------
INSERT INTO tools_categories (name, description) VALUES
  ('Conventional', 'Traditional mechanical tools'),
  ('Modern precision', 'GPS-guided and automated tools')
ON CONFLICT (name) DO NOTHING;

--------------------------------------------------------
-- 4) Tools (does NOT assign operation yet → linking table later)
--------------------------------------------------------
INSERT INTO tools (name, technology_category_id, description)
VALUES
  ('Hand hoe',
     (SELECT id FROM tools_categories WHERE name = 'Conventional'),
     'Basic hand tool for weeding'),
  ('Tractor-mounted sprayer',
     (SELECT id FROM tools_categories WHERE name = 'Conventional'),
     'Used for spraying over large areas'),
  ('GPS-guided seeder',
     (SELECT id FROM tools_categories WHERE name = 'Modern precision'),
     'Precision seeding system'),
  ('Drone sprayer',
     (SELECT id FROM tools_categories WHERE name = 'Modern precision'),
     'Automated aerial spraying')
ON CONFLICT (name) DO NOTHING;

--------------------------------------------------------
-- 5) Trees
--------------------------------------------------------
INSERT INTO trees (agroforestry_type_id, name, notes)
VALUES
  ((SELECT id FROM agroforestry_types WHERE name = 'Silvo-arable'),
      'Apple', 'Common fruit tree'),
  ((SELECT id FROM agroforestry_types WHERE name = 'Silvo-arable'),
      'Walnut', 'Nut tree used in alley cropping'),
  ((SELECT id FROM agroforestry_types WHERE name = 'Silvopastoral'),
      'Chestnut', 'Provides shade and forage'),
  ((SELECT id FROM agroforestry_types WHERE name = 'Silvopastoral'),
      'Poplar', 'Fast-growing shade tree')
ON CONFLICT (name) DO NOTHING;

--------------------------------------------------------
-- 6) Plants (crops used in silvo-arable)
--------------------------------------------------------
INSERT INTO plants (agroforestry_type_id, name, notes)
VALUES
  ((SELECT id FROM agroforestry_types WHERE name = 'Silvo-arable'),
      'Wheat', 'Common annual crop'),
  ((SELECT id FROM agroforestry_types WHERE name = 'Silvo-arable'),
      'Clover', 'Nitrogen-fixing cover crop'),
  ((SELECT id FROM agroforestry_types WHERE name = 'Silvo-arable'),
      'Raspberry', 'Perennial berry crop')
ON CONFLICT (name) DO NOTHING;

--------------------------------------------------------
-- 7) Livestock (for silvopastoral)
--------------------------------------------------------
INSERT INTO livestock (agroforestry_type_id, name, description)
VALUES
  ((SELECT id FROM agroforestry_types WHERE name = 'Silvopastoral'),
      'Cattle', 'Grazing livestock'),
  ((SELECT id FROM agroforestry_types WHERE name = 'Silvopastoral'),
      'Sheep', 'Light grazing livestock'),
  ((SELECT id FROM agroforestry_types WHERE name = 'Silvopastoral'),
      'Poultry', 'Free-range birds under trees')
ON CONFLICT (name) DO NOTHING;

--------------------------------------------------------
-- 8) Soil Types
--------------------------------------------------------
INSERT INTO soil_types (agroforestry_type_id, name, description)
VALUES
  ((SELECT id FROM agroforestry_types WHERE name = 'Silvo-arable'),
      'Sandy', 'Well-drained soil'),
  ((SELECT id FROM agroforestry_types WHERE name = 'Silvo-arable'),
      'Loam', 'Balanced fertile soil'),
  ((SELECT id FROM agroforestry_types WHERE name = 'Silvopastoral'),
      'Clay', 'Nutrient-rich but heavy soil'),
  ((SELECT id FROM agroforestry_types WHERE name = 'Silvopastoral'),
      'Peat', 'Organic-rich soil')
ON CONFLICT (name) DO NOTHING;

--------------------------------------------------------
-- 9) Tools ↔ Operations (many-to-many)
--------------------------------------------------------
INSERT INTO operations_tools (operation_id, tool_id)
VALUES
  ((SELECT id FROM operations WHERE name = 'Planting'),
      (SELECT id FROM tools WHERE name = 'GPS-guided seeder')),
  ((SELECT id FROM operations WHERE name = 'Weed control'),
      (SELECT id FROM tools WHERE name = 'Hand hoe')),
  ((SELECT id FROM operations WHERE name = 'Weed control'),
      (SELECT id FROM tools WHERE name = 'Tractor-mounted sprayer')),
  ((SELECT id FROM operations WHERE name = 'Pruning'),
      (SELECT id FROM tools WHERE name = 'Drone sprayer'))
ON CONFLICT DO NOTHING;

--------------------------------------------------------
-- 10) Silvo-arable combinations (AF + soil + tree + plant)
--------------------------------------------------------
INSERT INTO silvo_arable_combinations (agroforestry_type_id, soil_type_id, tree_id, plant_id, notes)
VALUES
(
    (SELECT id FROM agroforestry_types WHERE name = 'Silvo-arable'),
    (SELECT id FROM soil_types WHERE name = 'Loam'),
    (SELECT id FROM trees      WHERE name = 'Apple'),
    (SELECT id FROM plants     WHERE name = 'Wheat'),
    'Apple + Wheat alley cropping on loam soil'
),
(
    (SELECT id FROM agroforestry_types WHERE name = 'Silvo-arable'),
    (SELECT id FROM soil_types WHERE name = 'Sandy'),
    (SELECT id FROM trees      WHERE name = 'Walnut'),
    (SELECT id FROM plants     WHERE name = 'Clover'),
    'Walnut + Clover combination for nitrogen fixing'
)
ON CONFLICT DO NOTHING;

--------------------------------------------------------
-- 11) Silvopastoral combinations (AF + soil + tree + livestock)
--------------------------------------------------------
INSERT INTO silvo_pastoral_combinations (agroforestry_type_id, soil_type_id, tree_id, livestock_id, notes)
VALUES
(
    (SELECT id FROM agroforestry_types WHERE name = 'Silvopastoral'),
    (SELECT id FROM soil_types WHERE name = 'Clay'),
    (SELECT id FROM trees      WHERE name = 'Chestnut'),
    (SELECT id FROM livestock  WHERE name = 'Cattle'),
    'Chestnut + Cattle for shade and fodder'
),
(
    (SELECT id FROM agroforestry_types WHERE name = 'Silvopastoral'),
    (SELECT id FROM soil_types WHERE name = 'Peat'),
    (SELECT id FROM trees      WHERE name = 'Poplar'),
    (SELECT id FROM livestock  WHERE name = 'Sheep'),
    'Poplar + Sheep system adapted to wet soils'
)
ON CONFLICT DO NOTHING;
