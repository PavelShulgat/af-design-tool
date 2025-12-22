-- V3__seed_data.sql
-- Initial reference data for the Agroforestry Design Tool
-- Updated to match the updated ERD (no soils, no operations_tools, direct tech in combinations)

--------------------------------------------------------
-- 1) Agroforestry Types
--------------------------------------------------------
INSERT INTO agroforestry_types (name, description) VALUES
  ('Silvo-arable', 'Trees combined with crops'),
  ('Silvopastoral', 'Trees combined with livestock')
ON CONFLICT (name) DO NOTHING;

--------------------------------------------------------
-- 2) Operations
--------------------------------------------------------
INSERT INTO operations (name, description) VALUES
  ('Planting', 'Planting crops or trees'),
  ('Weed control', 'Controlling weeds between tree rows'),
  ('Pruning', 'Managing tree canopy'),
  ('Pasture management', 'Managing grass and forage for grazing')
ON CONFLICT (name) DO NOTHING;

--------------------------------------------------------
-- 3) Farming technology types
--------------------------------------------------------
INSERT INTO farming_technology_type (name, description) VALUES
  ('Conventional', 'Traditional mechanical tools'),
  ('Modern precision', 'GPS-guided and automated tools')
ON CONFLICT (name) DO NOTHING;

--------------------------------------------------------
-- 4) Farming technology 
--------------------------------------------------------
INSERT INTO farming_technology (name, farming_technology_type_id, description)
VALUES
  ('Hand hoe',
     (SELECT id FROM farming_technology_type WHERE name = 'Conventional'),
     'Basic hand tool for weeding'),
  ('Tractor-mounted sprayer',
     (SELECT id FROM farming_technology_type WHERE name = 'Conventional'),
     'Used for spraying over large areas'),
  ('GPS-guided seeder',
     (SELECT id FROM farming_technology_type WHERE name = 'Modern precision'),
     'Precision seeding system'),
  ('Drone sprayer',
     (SELECT id FROM farming_technology_type WHERE name = 'Modern precision'),
     'Automated aerial spraying')
ON CONFLICT (name) DO NOTHING;

--------------------------------------------------------
-- 5) Trees 
--------------------------------------------------------
INSERT INTO trees (name, notes) VALUES
  ('Apple', 'Common fruit tree'),
  ('Walnut', 'Nut tree used in alley cropping'),
  ('Chestnut', 'Provides shade and forage'),
  ('Poplar', 'Fast-growing shade tree')
ON CONFLICT (name) DO NOTHING;

--------------------------------------------------------
-- 6) Plants 
--------------------------------------------------------
INSERT INTO plants (name, notes) VALUES
  ('Wheat', 'Common annual crop'),
  ('Clover', 'Nitrogen-fixing cover crop'),
  ('Raspberry', 'Perennial berry crop')
ON CONFLICT (name) DO NOTHING;

--------------------------------------------------------
-- 7) Livestock 
--------------------------------------------------------
INSERT INTO livestock (name, description) VALUES
  ('Cattle', 'Grazing livestock'),
  ('Sheep', 'Light grazing livestock'),
  ('Poultry', 'Free-range birds under trees')
ON CONFLICT (name) DO NOTHING;

--------------------------------------------------------
-- 8) Silvo-arable combinations
-- AF type + tree + plant + operation + farming technology
--------------------------------------------------------
INSERT INTO silvo_arable_combinations (
    agroforestry_type_id,
    tree_id,
    plant_id,
    operation_id,
    farming_technology_id,
    notes
)
VALUES
(
    (SELECT id FROM agroforestry_types WHERE name = 'Silvo-arable'),
    (SELECT id FROM trees  WHERE name = 'Apple'),
    (SELECT id FROM plants WHERE name = 'Wheat'),
    (SELECT id FROM operations WHERE name = 'Planting'),
    (SELECT id FROM farming_technology WHERE name = 'GPS-guided seeder'),
    'Apple + Wheat alley cropping: planting cereals between tree rows with GPS-guided seeder.'
),
(
    (SELECT id FROM agroforestry_types WHERE name = 'Silvo-arable'),
    (SELECT id FROM trees  WHERE name = 'Walnut'),
    (SELECT id FROM plants WHERE name = 'Clover'),
    (SELECT id FROM operations WHERE name = 'Weed control'),
    (SELECT id FROM farming_technology WHERE name = 'Hand hoe'),
    'Walnut + Clover: weed control in young alley cropping systems using conventional hand hoe.'
)
ON CONFLICT DO NOTHING;

--------------------------------------------------------
-- 9) Silvopastoral combinations
-- AF type + tree + livestock + operation + farming technology
--------------------------------------------------------
INSERT INTO silvo_pastoral_combinations (
    agroforestry_type_id,
    tree_id,
    livestock_id,
    operation_id,
    farming_technology_id,
    notes
)
VALUES
(
    (SELECT id FROM agroforestry_types WHERE name = 'Silvopastoral'),
    (SELECT id FROM trees WHERE name = 'Chestnut'),
    (SELECT id FROM livestock WHERE name = 'Cattle'),
    (SELECT id FROM operations WHERE name = 'Pasture management'),
    (SELECT id FROM farming_technology WHERE name = 'Tractor-mounted sprayer'),
    'Chestnut + Cattle: pasture management in silvopastoral systems using conventional machinery.'
),
(
    (SELECT id FROM agroforestry_types WHERE name = 'Silvopastoral'),
    (SELECT id FROM trees WHERE name = 'Poplar'),
    (SELECT id FROM livestock WHERE name = 'Sheep'),
    (SELECT id FROM operations WHERE name = 'Pruning'),
    (SELECT id FROM farming_technology WHERE name = 'Drone sprayer'),
    'Poplar + Sheep: pruning/maintenance supported by modern precision technology (example entry).'
)
ON CONFLICT DO NOTHING;
