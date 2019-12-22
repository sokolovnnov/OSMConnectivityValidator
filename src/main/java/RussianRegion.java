enum RussianRegion {
  /*  NN(52, new int[]{12, 13, 21, 33, 37, 43, 44, 62}, "z:\\osmtmp\\RU-NIZ.o5m" ),
    VLADIMIR(33, new int[]{37, 50, 52, 62, 76}, "z:\\osmtmp\\RU-VLA.o5m" ),
    IVANOVO(37, new int[]{33, 52, 44, 76}, "z:\\osmtmp\\RU-IVA.o5m" ),
    KOSTROMA(44, new int[]{33, 37, 35, 43, 52, 76}, "z:\\osmtmp\\RU-KOS.o5m" ),
    KIROV(43, new int[]{11, 12, 16, 18, 29, 35, 44, 52, 59}, "z:\\osmtmp\\RU-KIR.o5m" ),
    YOSHKAR_OLA(12, new int[]{16, 21, 43, 52}, "z:\\osmtmp\\RU-ME.o5m" ),
    CHEBOKSARY(21, new int[]{12, 13, 16, 52, 73}, "z:\\osmtmp\\RU-CU.o5m" ),
    SARANSK(13, new int[]{21, 52, 58, 62, 73}, "z:\\osmtmp\\RU-MO.o5m" ),
    RYAZAN(62, new int[]{13, 33, 48, 50, 52, 58, 68, 71}, "z:\\osmtmp\\RU-RYA.o5m" ),
    KAZAN(16, new int[]{2, 12, 18, 21, 43, 56, 63, 73}, "z:\\osmtmp\\RU-TA.o5m" ),
    YLYANOVSK(73, new int[]{13, 16, 21, 58, 63, 64}, "z:\\osmtmp\\RU-ULY.o5m" ),
    PENZA(58, new int[]{13, 62, 64, 68, 73}, "z:\\osmtmp\\RU-PNZ.o5m" ),
    VOLOGDA(35, new int[]{10, 29, 43, 44, 47, 53, 69, 76}, "z:\\osmtmp\\RU-VLG.o5m" ),
    YAROSLAVL(76, new int[]{33, 35, 37, 44, 50, 69}, "z:\\osmtmp\\RU-YAR.o5m" ),

    MURMANSK(51, new int[]{10}, "z:\\osmtmp\\RU-MUR.o5m" ), // ДОБАВИТЬ НОРВЕГ, ФИН
    PETROZAVODSK(10, new int[]{29, 35, 47, 51}, "z:\\osmtmp\\RU-KR.o5m" ), //ДОБАВИТЬ ФИН
    SPB(47, new int[]{10, 35, 53, 60}, "z:\\osmtmp\\RU-LEN.o5m" ), //ДОБАВИТЬ ФИН, ЭСТ
    PSKOV(60, new int[]{47, 53, 67, 69}, "z:\\osmtmp\\RU-PSK.o5m" ), //ДОБАВИТЬ ЭСТ, ЛАТ, БЕЛАРУ
    TVER(69, new int[]{35, 50, 53, 60, 67, 76}, "z:\\osmtmp\\RU-TVE.o5m" ),
    NOVGOROD(53, new int[]{35, 47, 60, 69}, "z:\\osmtmp\\RU-NGR.o5m" ),
    SMOLENSK(67, new int[]{32, 40, 50, 60, 69}, "z:\\osmtmp\\RU-SMO.o5m" ), //ДОБАВИТЬ БЕЛАРУС
    MOSCOW(50, new int[]{33, 40, 62, 67, 69, 71, 76}, "z:\\osmtmp\\RU-MOS.o5m" ),
    KALUGA(40, new int[]{32, 50, 57, 67, 71}, "z:\\osmtmp\\RU-KLU.o5m" ),

    BRYANSK(32, new int[]{32, 46, 57, 67}, "z:\\osmtmp\\RU-BRY.o5m" ), //ДОБАВИТЬ УКР, БЕЛАРУС
    TULA(71, new int[]{40, 48, 50, 57 ,62}, "z:\\osmtmp\\RU-TUL.o5m" ),
    OREL(57, new int[]{32, 40, 46, 48, 71}, "z:\\osmtmp\\RU-ORL.o5m" ),
    KURSK(46, new int[]{31, 32, 36, 48, 57}, "z:\\osmtmp\\RU-KRS.o5m" ), //ДОБАВИТЬ УКР
    BELGOROD(31, new int[]{36, 46}, "z:\\osmtmp\\RU-BEL.o5m" ), //ДОБАВИТЬ УКР
    LIPETSK(48, new int[]{36, 46, 57, 62, 68, 71}, "z:\\osmtmp\\RU-LIP.o5m" ),
    TAMBOV(68, new int[]{36, 48, 58, 62, 64, }, "z:\\osmtmp\\RU-TAM.o5m" ),
    VORONEZH(36, new int[]{31, 34, 46, 48, 61, 64, 68}, "z:\\osmtmp\\RU-VOR.o5m" ), //ДОБАВИТЬ УКР
    SARATOV(64, new int[]{34, 36, 58, 63, 68, 73}, "z:\\osmtmp\\RU-SAR.o5m" ), //ДОБАВИТЬ КАЗА

    VOLGOGRAD(34, new int[]{8, 30, 36, 61, 64 }, "z:\\osmtmp\\RU-VGG.o5m" ),//ДОБАВИТЬ КАЗА
    ASTRAHAN(30, new int[]{8, 34}, "z:\\osmtmp\\RU-AST.o5m" ), //ДОБАВИТЬ КАЗА
    ELISTA(8, new int[]{5, 26, 30, 34, 61}, "z:\\osmtmp\\RU-KL.o5m" ),
    ROSTOV(61, new int[]{8, 23, 26, 34, 36, }, "z:\\osmtmp\\RU-ROS.o5m" ),//ДОБАВИТЬ УКР
    SEVASTOPOL(92, new int[]{82}, "z:\\osmtmp\\RU-SEV.o5m" ),
    SIMFEROPOL(82, new int[]{82, 23}, "z:\\osmtmp\\RU-CR.o5m" ), //ДОБАВИТЬ УКР
    KRASNODAR(23, new int[]{9, 26, 61, 82}, "z:\\osmtmp\\RU-KDA.o5m" ), //ДОБАВИТЬ АБХАЗИ
    STAVROPOL(26, new int[]{5, 7, 8, 9, 15, 20, 23, 61}, "z:\\osmtmp\\RU-STA.o5m" ),
    CHERKESSK(9, new int[]{7, 23, 26, }, "z:\\osmtmp\\RU-KC.o5m" ), // ДОБАВИТЬ АБХ, ГРУ

    NALCHIK(7, new int[]{9, 15, 26}, "z:\\osmtmp\\RU-KB.o5m" ), // ДОБАВИТЬ ГРУ
    VLADIKAVKAZ(15, new int[]{6, 7, 20, 26}, "z:\\osmtmp\\RU-SE.o5m" ), // ДОБАВИТЬ ГРУ, ОСЕТ
    NAZRAN(6, new int[]{15, 20}, "z:\\osmtmp\\RU-IN.o5m" ), // ДОБАВИТЬ ГРУЗ
    GROZNY(20, new int[]{5, 6, 15, 26}, "z:\\osmtmp\\RU-CE.o5m" ), // ДОБАВИТЬ ГРУ
    MAHACHKALA(5, new int[]{8, 20, 26}, "z:\\osmtmp\\RU-DA.o5m" ), // ДОБАВИТЬ ГРУ, АЗЕР
    SAMARA(63, new int[]{16, 56, 64, 73}, "z:\\osmtmp\\RU-SAM.o5m" ),/// ДОБАВИТЬ КАЗА
    ORENBURG(56, new int[]{2, 16, 63, 64, 74}, "z:\\osmtmp\\RU-ORE.o5m" ), // ДОБАВИТЬ КАЗА
    UFA(2, new int[]{16, 18, 56, 59, 66, 74}, "z:\\osmtmp\\RU-BA.o5m" ),
    CHELYABINSK(74, new int[]{2, 45, 56, 66}, "z:\\osmtmp\\RU-CHE.o5m" ), //ДОБАВИТЬ КАЗА

    IZHEVSK(18, new int[]{2, 16, 43, 59}, "z:\\osmtmp\\RU-UD.o5m" ),
    PERM(59, new int[]{2, 11, 18, 43, 66}, "z:\\osmtmp\\RU-PER.o5m" ),
    EKATERINBURG(66, new int[]{2, 11, 45, 59, 72, 74, 86 }, "z:\\osmtmp\\RU-SVE.o5m" ),
    SYKTYVKAR(11, new int[]{29, 43, 59, 66, 83, 86, 89}, "z:\\osmtmp\\RU-KO.o5m" ),
    HANTY_MAN(86, new int[]{11, 24, 55, 66, 70, 72, 89}, "z:\\osmtmp\\RU-KHM.o5m" ),
    YAMAL(89, new int[]{11, 24, 83, 86}, "z:\\osmtmp\\RU-YAN.o5m" ),
    NARYAN_MAR(83, new int[]{11, 29, 89}, "z:\\osmtmp\\RU-NEN.o5m" ),
    ARHANGELSK(29, new int[]{10, 11, 35, 43, 83}, "z:\\osmtmp\\RU-ARK.o5m" ),*/
//    YAROSLAVL(76, new int[]{33, 35, 37, 44, 50, 69}, "z:\\osmtmp\\RU-YAR.o5m" ),
    //    YAROSLAVL(76, new int[]{33, 35, 37, 44, 50, 69}, "z:\\osmtmp\\RU-YAR.o5m" ),

    NN(52, new int[]{12, 13, 21, 33, 37, 43, 44, 62}, "e:\\osmtmp\\RU-NIZ.o5m" ),
    VLADIMIR(33, new int[]{37, 50, 52, 62, 76}, "e:\\osmtmp\\RU-VLA.o5m" ),
    IVANOVO(37, new int[]{33, 52, 44, 76}, "e:\\osmtmp\\RU-IVA.o5m" ),
    KOSTROMA(44, new int[]{33, 37, 35, 43, 52, 76}, "e:\\osmtmp\\RU-KOS.o5m" ),
    KIROV(43, new int[]{11, 12, 16, 18, 29, 35, 44, 52, 59}, "e:\\osmtmp\\RU-KIR.o5m" ),
    YOSHKAR_OLA(12, new int[]{16, 21, 43, 52}, "e:\\osmtmp\\RU-ME.o5m" ),
    CHEBOKSARY(21, new int[]{12, 13, 16, 52, 73}, "e:\\osmtmp\\RU-CU.o5m" ),
    SARANSK(13, new int[]{21, 52, 58, 62, 73}, "e:\\osmtmp\\RU-MO.o5m" ),
    RYAZAN(62, new int[]{13, 33, 48, 50, 52, 58, 68, 71}, "e:\\osmtmp\\RU-RYA.o5m" ),
    KAZAN(16, new int[]{2, 12, 18, 21, 43, 56, 63, 73}, "e:\\osmtmp\\RU-TA.o5m" ),
    YLYANOVSK(73, new int[]{13, 16, 21, 58, 63, 64}, "e:\\osmtmp\\RU-ULY.o5m" ),
    PENZA(58, new int[]{13, 62, 64, 68, 73}, "e:\\osmtmp\\RU-PNZ.o5m" ),
    VOLOGDA(35, new int[]{10, 29, 43, 44, 47, 53, 69, 76}, "e:\\osmtmp\\RU-VLG.o5m" ),
    YAROSLAVL(76, new int[]{33, 35, 37, 44, 50, 69}, "e:\\osmtmp\\RU-YAR.o5m" ),

    MURMANSK(51, new int[]{10}, "e:\\osmtmp\\RU-MUR.o5m" ), // ДОБАВИТЬ НОРВЕГ, ФИН
    PETROZAVODSK(10, new int[]{29, 35, 47, 51}, "e:\\osmtmp\\RU-KR.o5m" ), //ДОБАВИТЬ ФИН
    SPB(47, new int[]{10, 35, 53, 60}, "e:\\osmtmp\\RU-LEN.o5m" ), //ДОБАВИТЬ ФИН, ЭСТ
    PSKOV(60, new int[]{47, 53, 67, 69}, "e:\\osmtmp\\RU-PSK.o5m" ), //ДОБАВИТЬ ЭСТ, ЛАТ, БЕЛАРУ
    TVER(69, new int[]{35, 50, 53, 60, 67, 76}, "e:\\osmtmp\\RU-TVE.o5m" ),
    NOVGOROD(53, new int[]{35, 47, 60, 69}, "e:\\osmtmp\\RU-NGR.o5m" ),
    SMOLENSK(67, new int[]{32, 40, 50, 60, 69}, "e:\\osmtmp\\RU-SMO.o5m" ), //ДОБАВИТЬ БЕЛАРУС
    MOSCOW(50, new int[]{33, 40, 62, 67, 69, 71, 76}, "e:\\osmtmp\\RU-MOS.o5m" ),
    KALUGA(40, new int[]{32, 50, 57, 67, 71}, "e:\\osmtmp\\RU-KLU.o5m" ),

    BRYANSK(32, new int[]{32, 46, 57, 67}, "e:\\osmtmp\\RU-BRY.o5m" ), //ДОБАВИТЬ УКР, БЕЛАРУС
    TULA(71, new int[]{40, 48, 50, 57 ,62}, "e:\\osmtmp\\RU-TUL.o5m" ),
    OREL(57, new int[]{32, 40, 46, 48, 71}, "e:\\osmtmp\\RU-ORL.o5m" ),
    KURSK(46, new int[]{31, 32, 36, 48, 57}, "e:\\osmtmp\\RU-KRS.o5m" ), //ДОБАВИТЬ УКР
    BELGOROD(31, new int[]{36, 46}, "e:\\osmtmp\\RU-BEL.o5m" ), //ДОБАВИТЬ УКР
    LIPETSK(48, new int[]{36, 46, 57, 62, 68, 71}, "e:\\osmtmp\\RU-LIP.o5m" ),
    TAMBOV(68, new int[]{36, 48, 58, 62, 64, }, "e:\\osmtmp\\RU-TAM.o5m" ),
    VORONEZH(36, new int[]{31, 34, 46, 48, 61, 64, 68}, "e:\\osmtmp\\RU-VOR.o5m" ), //ДОБАВИТЬ УКР
    SARATOV(64, new int[]{34, 36, 58, 63, 68, 73}, "e:\\osmtmp\\RU-SAR.o5m" ), //ДОБАВИТЬ КАЗА

    VOLGOGRAD(34, new int[]{8, 30, 36, 61, 64 }, "e:\\osmtmp\\RU-VGG.o5m" ),//ДОБАВИТЬ КАЗА
    ASTRAHAN(30, new int[]{8, 34}, "e:\\osmtmp\\RU-AST.o5m" ), //ДОБАВИТЬ КАЗА
    ELISTA(8, new int[]{5, 26, 30, 34, 61}, "e:\\osmtmp\\RU-KL.o5m" ),
    ROSTOV(61, new int[]{8, 23, 26, 34, 36, }, "e:\\osmtmp\\RU-ROS.o5m" ),//ДОБАВИТЬ УКР
    SEVASTOPOL(92, new int[]{82}, "e:\\osmtmp\\RU-SEV.o5m" ),
    SIMFEROPOL(82, new int[]{82, 23}, "e:\\osmtmp\\RU-CR.o5m" ), //ДОБАВИТЬ УКР
    KRASNODAR(23, new int[]{9, 26, 61, 82}, "e:\\osmtmp\\RU-KDA.o5m" ), //ДОБАВИТЬ АБХАЗИ
    STAVROPOL(26, new int[]{5, 7, 8, 9, 15, 20, 23, 61}, "e:\\osmtmp\\RU-STA.o5m" ),
    CHERKESSK(9, new int[]{7, 23, 26, }, "e:\\osmtmp\\RU-KC.o5m" ), // ДОБАВИТЬ АБХ, ГРУ

    NALCHIK(7, new int[]{9, 15, 26}, "e:\\osmtmp\\RU-KB.o5m" ), // ДОБАВИТЬ ГРУ
    VLADIKAVKAZ(15, new int[]{6, 7, 20, 26}, "e:\\osmtmp\\RU-SE.o5m" ), // ДОБАВИТЬ ГРУ, ОСЕТ
    NAZRAN(6, new int[]{15, 20}, "e:\\osmtmp\\RU-IN.o5m" ), // ДОБАВИТЬ ГРУЗ
    GROZNY(20, new int[]{5, 6, 15, 26}, "e:\\osmtmp\\RU-CE.o5m" ), // ДОБАВИТЬ ГРУ
    MAHACHKALA(5, new int[]{8, 20, 26}, "e:\\osmtmp\\RU-DA.o5m" ), // ДОБАВИТЬ ГРУ, АЗЕР
    SAMARA(63, new int[]{16, 56, 64, 73}, "e:\\osmtmp\\RU-SAM.o5m" ),/// ДОБАВИТЬ КАЗА
    ORENBURG(56, new int[]{2, 16, 63, 64, 74}, "e:\\osmtmp\\RU-ORE.o5m" ), // ДОБАВИТЬ КАЗА
    UFA(2, new int[]{16, 18, 56, 59, 66, 74}, "e:\\osmtmp\\RU-BA.o5m" ),
    CHELYABINSK(74, new int[]{2, 45, 56, 66}, "e:\\osmtmp\\RU-CHE.o5m" ), //ДОБАВИТЬ КАЗА

    IZHEVSK(18, new int[]{2, 16, 43, 59}, "e:\\osmtmp\\RU-UD.o5m" ),
    PERM(59, new int[]{2, 11, 18, 43, 66}, "e:\\osmtmp\\RU-PER.o5m" ),
    EKATERINBURG(66, new int[]{2, 11, 45, 59, 72, 74, 86 }, "e:\\osmtmp\\RU-SVE.o5m" ),
    SYKTYVKAR(11, new int[]{29, 43, 59, 66, 83, 86, 89}, "e:\\osmtmp\\RU-KO.o5m" ),
    HANTY_MAN(86, new int[]{11, 24, 55, 66, 70, 72, 89}, "e:\\osmtmp\\RU-KHM.o5m" ),
    YAMAL(89, new int[]{11, 24, 83, 86}, "e:\\osmtmp\\RU-YAN.o5m" ),
    NARYAN_MAR(83, new int[]{11, 29, 89}, "e:\\osmtmp\\RU-NEN.o5m" ),
    ARHANGELSK(29, new int[]{10, 11, 35, 43, 83}, "e:\\osmtmp\\RU-ARK.o5m" ),
;
    String path;
    int[] neighbors;
    int id;

    RussianRegion(int id, int[] neighbors, String path) {
        this.path = path;
        this.neighbors = neighbors;
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public int[] getNeighbors() {
        return neighbors;
    }

    public int getId() {
        return id;
    }
}

