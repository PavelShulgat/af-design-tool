ALTER TABLE farming_technology
ADD COLUMN IF NOT EXISTS image_path TEXT;

UPDATE farming_technology
SET image_path = '/images/tools/hand-hoe.jpg'
WHERE name = 'Hand hoe';

UPDATE farming_technology
SET image_path = '/images/tools/tractor-mounted-sprayer.jpg'
WHERE name = 'Tractor-mounted sprayer';

UPDATE farming_technology
SET image_path = '/images/tools/gps-guided-seeder.jpg'
WHERE name = 'GPS-guided seeder';

UPDATE farming_technology
SET image_path = '/images/tools/drone-sprayer.jpg'
WHERE name = 'Drone sprayer';
