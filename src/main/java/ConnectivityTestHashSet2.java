import org.alex73.osmemory.MemoryStorage;

import java.util.HashSet;

public class ConnectivityTestHashSet2 {
    private static long start;

    public static void main(String[] args) {
    start = System.nanoTime();



    BaseTest baseTest = new BaseTest();
    OsmRegion nn = baseTest.produceRegion(RussianRegion.NN);
//        OsmRegion y = baseTest.produceRegion(RussianRegion.YOSHKAR_OLA);
//        OsmRegion ch = baseTest.produceRegion(RussianRegion.CHEBOKSARY);
        OsmRegion ry = baseTest.produceRegion(RussianRegion.RYAZAN);
        OsmRegion mo = baseTest.produceRegion(RussianRegion.SARANSK);
//        OsmRegion vl = baseTest.produceRegion(RussianRegion.VLADIMIR);
//        OsmRegion iv = baseTest.produceRegion(RussianRegion.IVANOVO);
//        OsmRegion kos = baseTest.produceRegion(RussianRegion.KOSTROMA);
//        OsmRegion ki = baseTest.produceRegion(RussianRegion.KIROV);

//        Util.connectivityTwoRegion(nn, y);
//        Util.connectivityTwoRegion(nn, ch);
        Util.connectivityTwoRegion(nn, ry);
        Util.connectivityTwoRegion(nn, mo);
//        Util.connectivityTwoRegion(nn, vl);
//        Util.connectivityTwoRegion(nn, iv);
//        Util.connectivityTwoRegion(nn, kos);
//        Util.connectivityTwoRegion(nn, ki);
//        baseTest.outerConnectivityTest(nn);
        for (HashSet<Long> way: nn.adjList.getIsolatedWayIds()){

            System.out.println(way);
        }
        System.out.println((System.nanoTime() - start)/1_000_000_000 + " - time");


//1877290337

//        ArrayList<OsmWay> ss = connectivityTestHashSet.dataToWays(data);
//        HashMap<Long, MarkedNode> adjS = connectivityTestHashSet.createAdjHashMap(ss);
//        connectivityTestHashSet.createAdjTest(adjS);


    }





}
