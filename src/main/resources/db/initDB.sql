DROP TABLE IF EXISTS regions;
DROP TABLE IF EXISTS nodes;
DROP TABLE IF EXISTS ways;

DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START WITH 100000;

CREATE TABLE nodes
(
    id           INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    osm_id       BIGINT  ,
    way_osm_id   INTEGER ,
    order_in_way INTEGER ,
    lat          float4 NOT NULL,
    lon          float4 NOT NULL
);
CREATE UNIQUE INDEX nodes_unique_wayid_order_idx ON nodes (way_osm_id, order_in_way, osm_id);

CREATE TABLE IF NOT EXISTS regions
(
    id           INTEGER PRIMARY KEY,
    name         VARCHAR,
    neighbors    INTEGER[],
    path         VARCHAR
);

INSERT INTO regions (id, name, neighbors, path)
VALUES (52, 'NN', '{12, 13, 21, 33, 37, 43, 44, 62}', 'RU-NIZ.o5m'),
       (33, 'VLA', '{37, 50, 52, 62, 76}', 'RU-VLA.o5m'),
       (37, 'IVA', '{33, 52, 44, 76}', 'RU-IVA.o5m'),
       (44, 'KOS', '{33, 37, 35, 43, 52, 76}', 'RU-KOS.o5m'),
       (43, 'KIR', '{11, 12, 16, 18, 29, 35, 44, 52, 59}', 'RU-KIR.o5m'),
       (12, 'ME', '{16, 21, 43, 52}', 'RU-ME.o5m'),
       (21, 'CU', '{12, 13, 16, 52, 73}', 'RU-CU.o5m'),
       (13, 'MO', '{21, 52, 58, 62, 73}', 'RU-MO.o5m'),
       (62, 'RYA', '{13, 33, 48, 50, 52, 58, 68, 71}', 'RU-RYA.o5m'),
       (16, 'TA', '{2, 12, 18, 21, 43, 56, 63, 73}', 'RU-TA.o5m'),
       (73, 'ULY', '{13, 16, 21, 58, 63, 64}', 'RU-ULY.o5m'),
       (58, 'PNZ', '{13, 62, 64, 68, 73}', 'RU-PNZ.o5m'),
       (35, 'VLG', '{10, 29, 43, 44, 47, 53, 69, 76}', 'RU-VLG.o5m'),
       (76, 'YAR', '{33, 35, 37, 44, 50, 69}', 'RU-YAR.o5m'),

       (51, 'MUR', '{10}', 'RU-MUR.o5m'),
       (10, 'KR', '{29, 35, 47, 51}', 'RU-KR.o5m'),
       (47, 'LEN', '{10, 35, 53, 60}', 'RU-LEN.o5m'),
       (60, 'PSK', '{47, 53, 67, 69}', 'RU-PSK.o5m'),
       (69, 'TVE', '{35, 50, 53, 60, 67, 76}', 'RU-TVE.o5m'),
       (53, 'NGR', '{35, 47, 60, 69}', 'RU-NGR.o5m'),
       (67, 'SMO', '{32, 40, 50, 60, 69}', 'RU-SMO.o5m'),
       (50, 'MOS', '{33, 40, 62, 67, 69, 71, 76}', 'RU-MOS.o5m'),
       (40, 'KLU', '{32, 50, 57, 67, 71}', 'RU-KLU.o5m'),
       (32, 'BRY', '{40, 46, 57, 67}', 'RU-BRY.o5m'),

       (71, 'TUL', '{40, 48, 50, 57, 62}', 'RU-TUL.o5m'),
       (57, 'ORL', '{32, 40, 46, 48, 71}', 'RU-ORL.o5m'),
       (46, 'KRS', '{31, 32, 36, 48, 57}', 'RU-KRS.o5m'),
       (31, 'BEL', '{36, 46}', 'RU-BEL.o5m'),
       (48, 'LIP', '{36, 46, 57, 62, 68, 71}', 'RU-LIP.o5m'),
       (68, 'TAM', '{36, 48, 58, 62, 64}', 'RU-TAM.o5m'),
       (36, 'VOR', '{31, 34, 46, 48, 61, 64, 68}', 'RU-VOR.o5m'),
       (64, 'SAR', '{34, 36, 58, 63, 68, 73}', 'RU-SAR.o5m'),

       (34, 'VGG', '{8, 30, 36, 61, 64}', 'RU-VGG.o5m'),
       (30, 'AST', '{8, 34}', 'RU-AST.o5m'),
       (8, 'KL', '{5, 26, 30, 34, 61}', 'RU-KL.o5m'),
       (61, 'ROS', '{8, 23, 26, 34, 36}', 'RU-ROS.o5m'),
       (92, 'SEV', '{82}', 'RU-SEV.o5m'),
       (82, 'CR', '{92, 23}', 'RU-CR.o5m'),
       (23, 'KDA', '{9, 26, 61, 82}', 'RU-KDA.o5m'),
       (26, 'STA', '{5, 7, 8, 9, 15, 20, 23, 61}', 'RU-STA.o5m'),
       (9, 'KC', '{7, 23, 26}', 'RU-KC.o5m'),

       (7, 'KB', '{9, 15, 26}', 'RU-KB.o5m'),
       (15, 'SE', '{6, 7, 20, 26}', 'RU-SE.o5m'),
       (6, 'IN', '{15, 20}', 'RU-IN.o5m'),
       (20, 'CE', '{5, 6, 15, 26}', 'RU-CE.o5m'),
       (5, 'DA', '{8, 20, 26}', 'RU-DA.o5m'),
       (63, 'SAM', '{16, 56, 64, 73}', 'RU-SAM.o5m'),
       (56, 'ORE', '{2, 16, 63, 64, 74}', 'RU-ORE.o5m'),
       (2, 'BA', '{16, 18, 56, 59, 66, 74}', 'RU-BA.o5m'),
       (74, 'CHE', '{2, 45, 56, 66}', 'RU-CHE.o5m'),
       (18, 'UD', '{2, 16, 43, 59}', 'RU-UD.o5m'),
       (59, 'PER', '{2, 11, 18, 43, 66}', 'RU-PER.o5m'),
       (66, 'SVE', '{2, 11, 45, 59, 72, 74, 86}', 'RU-SVE.o5m'),
       (11, 'KO', '{29, 43, 59, 66, 83, 86, 89}', 'RU-KO.o5m'),
       (86, 'KHM', '{11, 24, 55, 66, 70, 72, 89}', 'RU-KHM.o5m'),
       (89, 'YAN', '{11, 24, 83, 86}', 'RU-YAN.o5m'),
       (83, 'NEN', '{11, 29, 89}', 'RU-NEN.o5m'),
       (29, 'ARK', '{10, 11, 35, 43, 83}', 'RU-ARK.o5m');








