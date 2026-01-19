-- Add a long “info” text field for machine detail pages
ALTER TABLE farming_technology
ADD COLUMN IF NOT EXISTS info TEXT;

-- Populate info for existing rows (safe default: copy description)
UPDATE farming_technology
SET info = COALESCE(info, description)
WHERE info IS NULL;

UPDATE farming_technology
SET info = 'GPS-guided seeder is a precision planting machine that uses satellite positioning to place seeds in consistent rows and at controlled spacing. In agroforestry (especially silvo-arable / alley cropping), this helps align crop rows with tree lines so that machinery can operate between rows without damaging trees or compacting soil near the root zone.

Typical benefits include reduced overlap (less wasted seed), more uniform germination due to consistent depth, and better repeatability across seasons (you can reuse the same guidance lines). This is useful when the field layout must remain stable because of permanent tree rows. It also supports controlled traffic patterns, which can reduce random compaction and make later operations (spraying, weeding, harvesting) easier to plan.

Limitations: it requires calibration (seed rate, depth, opener pressure) and a stable GPS signal. On very small plots or irregular tree layouts, the setup time may outweigh benefits. It also assumes that the alley spacing and turning areas are designed for machinery access. Best used when the system is designed with machinery lanes and clear headlands.'
WHERE name = 'GPS-guided seeder';

UPDATE farming_technology
SET info = 'Hand hoe is a simple manual tool used for weed control and light soil disturbance around crop rows or young trees. In agroforestry systems, it is often used close to tree trunks where larger machinery cannot safely reach without risk of bark damage. It is also practical in early establishment years when trees are small and the layout may still be adjusted.

Its main advantage is precision: the operator can target weeds selectively and avoid disturbing desired plants. It has low cost, requires no fuel, and produces minimal soil compaction. For small-scale systems, demonstration plots, or sites with limited equipment access, hand tools remain a realistic management method.

Trade-offs: manual weeding is labour intensive and time-consuming, especially as weed pressure increases. Frequent hoeing can disturb soil structure and may increase evaporation if done too aggressively during dry periods. It is most suitable for small areas, maintenance around young trees, or as a complementary method alongside mulching, ground cover management, or mechanical tools used in the wider alleys.'
WHERE name = 'Hand hoe';

UPDATE farming_technology
SET info = 'Tractor-mounted sprayer is a field-scale application tool used to distribute liquids such as foliar nutrients, biological treatments, or (where permitted) herbicides and pesticides. In agroforestry, it is relevant for managing crops in alleys, controlling invasive weeds along access lanes, or supporting pasture management in silvopastoral systems.

A major benefit is coverage efficiency: large areas can be treated consistently with adjustable boom width, nozzle types, pressure, and flow rate. In well-designed systems with adequate alley width and turning space, tractor operations can be planned to follow the same traffic lanes to reduce soil compaction. Spraying can also be targeted seasonally to reduce weed competition in the establishment phase or to support crop health during sensitive growth stages.

Risks and constraints: spray drift is a key concern in mixed systems because trees and understory plants may be more sensitive. Weather conditions (wind speed, humidity) strongly influence safe use. Sprayer operation requires calibration and responsible handling to avoid over-application and environmental impact. In narrow tree rows, boom height and turning can be challenging. This tool fits best when the design includes machinery access and when application practices align with environmental guidelines and local regulation.'
WHERE name = 'Tractor-mounted sprayer';

UPDATE farming_technology
SET info = 'Drone sprayer is an aerial application technology designed for targeted spraying over specific zones. In agroforestry, it can be useful in situations where ground access is limited (wet soils, steep terrain) or where selective treatment is preferred, for example spot-treatment of weeds or localized nutrient application without driving heavy machinery through the field.

Operationally, drone spraying can reduce soil compaction because it does not require tractor passes. It may also improve precision for small or irregular areas, especially when tree rows create complex patterns. Some systems integrate mapping (e.g., identifying weed patches) and then apply treatment only where needed, which can reduce total chemical use when used responsibly.

Limitations: drone spraying has smaller tank capacity, so it is less suitable for large-area blanket applications. Battery life, payload, and flight conditions (wind, rain) restrict operational windows. Legal requirements can be strict (pilot certification, approved products, restricted zones), and safety procedures must be followed. It is best considered a complementary tool for targeted tasks rather than a full replacement for conventional sprayers in large-scale management.'
WHERE name = 'Drone sprayer';
