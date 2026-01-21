UPDATE farming_technology_type
SET name = 'Mechanical'
WHERE name = 'Conventional';

UPDATE farming_technology_type
SET name = 'Smart'
WHERE name = 'Modern precision';

-- Optional: adjust descriptions to match the new wording
UPDATE farming_technology_type
SET description = 'Mechanical tools and machinery'
WHERE name = 'Mechanical';

UPDATE farming_technology_type
SET description = 'Smart / connected / automated technology'
WHERE name = 'Smart';