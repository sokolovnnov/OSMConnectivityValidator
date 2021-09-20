DROP TABLE IF EXISTS regions;
DROP TABLE IF EXISTS nodes;
DROP TABLE IF EXISTS ways;

DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START WITH 100000;

CREATE TABLE nodes
(
    id           INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    osm_id       BIGINT,
    way_osm_id   INTEGER,
    order_in_way INTEGER,
    lat          float4 NOT NULL,
    lon          float4 NOT NULL
);
CREATE UNIQUE INDEX nodes_unique_wayid_order_idx ON nodes (way_osm_id, order_in_way, osm_id);

CREATE TABLE IF NOT EXISTS regions
(
    id        INTEGER PRIMARY KEY,
    name      VARCHAR,
    neighbors INTEGER[],
    path      VARCHAR
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

       (51, 'MUR', '{10, 100, 201}', 'RU-MUR.o5m'),                       --100 NOR 200 FIN
       (10, 'KR', '{29, 35, 47, 51, 202}', 'RU-KR.o5m'),
       (47, 'LEN', '{10, 35, 53, 60, 202, 300}', 'RU-LEN.o5m'),           --300 EST
       (60, 'PSK', '{47, 53, 67, 69, 300, 400, 501}', 'RU-PSK.o5m'),      --400 LAT 501 BY-VI
       (69, 'TVE', '{35, 50, 53, 60, 67, 76}', 'RU-TVE.o5m'),
       (53, 'NGR', '{35, 47, 60, 69}', 'RU-NGR.o5m'),
       (67, 'SMO', '{32, 40, 50, 60, 69, 501, 502}', 'RU-SMO.o5m'),       --502 BY-MO
       (50, 'MOS', '{33, 40, 62, 67, 69, 71, 76}', 'RU-MOS.o5m'),
       (40, 'KLU', '{32, 50, 57, 67, 71}', 'RU-KLU.o5m'),
       (32, 'BRY', '{40, 46, 57, 67, 502, 503, 674, 659}', 'RU-BRY.o5m'), --503 BY-HO 674 UA-74 659 UA-59

       (71, 'TUL', '{40, 48, 50, 57, 62}', 'RU-TUL.o5m'),
       (57, 'ORL', '{32, 40, 46, 48, 71}', 'RU-ORL.o5m'),
       (46, 'KRS', '{31, 32, 36, 48, 57, 659}', 'RU-KRS.o5m'),
       (31, 'BEL', '{36, 46, 659, 663, 609}', 'RU-BEL.o5m'),              --663 UA-63 609 UA-09
       (48, 'LIP', '{36, 46, 57, 62, 68, 71}', 'RU-LIP.o5m'),
       (68, 'TAM', '{36, 48, 58, 62, 64}', 'RU-TAM.o5m'),
       (36, 'VOR', '{31, 34, 46, 48, 61, 64, 68, 609}', 'RU-VOR.o5m'),
       (64, 'SAR', '{34, 36, 58, 63, 68, 73, 900}', 'RU-SAR.o5m'),

       (34, 'VGG', '{8, 30, 36, 61, 64, 900}', 'RU-VGG.o5m'),
       (30, 'AST', '{8, 34, 900}', 'RU-AST.o5m'),                         --900 KZ
       (8, 'KL', '{5, 26, 30, 34, 61}', 'RU-KL.o5m'),
       (61, 'ROS', '{8, 23, 26, 34, 36, 609, 614}', 'RU-ROS.o5m'),        --614 UA-14
       (92, 'SEV', '{82}', 'RU-SEV.o5m'),
       (82, 'CR', '{92, 23, 665}', 'RU-CR.o5m'),                          --665 UA-65
       (23, 'KDA', '{9, 26, 61, 82, 700}', 'RU-KDA.o5m'),                 --700 GE
       (26, 'STA', '{5, 7, 8, 9, 15, 20, 23, 61}', 'RU-STA.o5m'),
       (9, 'KC', '{7, 23, 26, 700}', 'RU-KC.o5m'),

       (7, 'KB', '{9, 15, 26, 700}', 'RU-KB.o5m'),
       (15, 'SE', '{6, 7, 20, 26, 700}', 'RU-SE.o5m'),
       (6, 'IN', '{15, 20, 700}', 'RU-IN.o5m'),
       (20, 'CE', '{5, 6, 15, 26, 700}', 'RU-CE.o5m'),
       (5, 'DA', '{8, 20, 26, 700, 800}', 'RU-DA.o5m'),                   --800 AZ
       (63, 'SAM', '{16, 56, 64, 73, 900}', 'RU-SAM.o5m'),
       (56, 'ORE', '{2, 16, 63, 64, 74, 900}', 'RU-ORE.o5m'),
       (2, 'BA', '{16, 18, 56, 59, 66, 74}', 'RU-BA.o5m'),
       (74, 'CHE', '{2, 145, 56, 66, 900}', 'RU-CHE.o5m'),  --FIXME 145
       (18, 'UD', '{2, 16, 43, 59}', 'RU-UD.o5m'),
       (59, 'PER', '{2, 11, 18, 43, 66}', 'RU-PER.o5m'),
       (66, 'SVE', '{2, 11, 145, 59, 172, 74, 86}', 'RU-SVE.o5m'), --FIXME 145 172
       (11, 'KO', '{29, 43, 59, 66, 83, 86, 89}', 'RU-KO.o5m'),
       (86, 'KHM', '{11, 24, 55, 66, 70, 172, 89}', 'RU-KHM.o5m'), --FIXME 172
       (89, 'YAN', '{11, 24, 83, 86}', 'RU-YAN.o5m'),
       (83, 'NEN', '{11, 29, 89}', 'RU-NEN.o5m'),
       (29, 'ARK', '{10, 11, 35, 43, 83}', 'RU-ARK.o5m'),

       (172, 'TYU', '{0}', 'RU-TYU.o5m'), --FIXME
       (145, 'KGN', '{0}', 'RU-KGN.o5m'), --FIXME


       (100, 'NOR', '{0}', 'norway-latest.o5m'),
       (201, 'FIN-MUR', '{0}', 'finland-latestMUR.o5m'),
       (202, 'FIN', '{0}', 'finland-latest.o5m'),

       (300, 'EST', '{47, 60, 400}', 'estonia-latest.o5m'),
       (400, 'LAT', '{0}', 'latvia-latest.o5m'),

       (501, 'BY-VI', '{0}', 'BY-VI.o5m'),
       (502, 'BY-MO', '{0}', 'BY-MO.o5m'),
       (503, 'BY-HO', '{0}', 'BY-HO.o5m'),

       (674, 'UA-74', '{0}', 'UA-74.o5m'),
       (659, 'UA-59', '{0}', 'UA-59.o5m'),
       (663, 'UA-63', '{0}', 'UA-63.o5m'),
       (609, 'UA-09', '{0}', 'UA-09.o5m'),
       (614, 'UA-14', '{0}', 'UA-14.o5m'),
       (665, 'UA-14', '{0}', 'UA-65.o5m'),

       (700, 'GE', '{0}', 'GE.o5m'),
       (800, 'AZ', '{0}', 'AZ.o5m'),
       (900, 'KZ', '{0}', 'KZ.o5m');







