public class BaseTest {
    static OsmRegion[] innerTestCompletedRegions = new OsmRegion[150];

    public OsmRegion produceRegion(RussianRegion russianRegion) {

        OsmRegion osmRegion = new OsmRegion(russianRegion);
        osmRegion.createAdjList();
        osmRegion.adjList.markConnectedComponent();
        innerTestCompletedRegions[russianRegion.id] = osmRegion;
        return osmRegion;
    }

    void outerConnectivityTest(OsmRegion osmRegion){
        for (int nbh: osmRegion.neighbors){
            OsmRegion osmRegion2;
            if(innerTestCompletedRegions[nbh] == null){
                RussianRegion russianRegion;
                for (RussianRegion russianR : RussianRegion.values()) {
                    if (nbh == russianR.id) {
                        russianRegion = russianR;
                        System.out.println("produceRegion: " + russianRegion.name());
                        produceRegion(russianRegion);
                        System.out.println(russianRegion);
                    }
                }

                Util.connectivityTwoRegion(osmRegion, innerTestCompletedRegions[nbh]);
            }
            else {
                osmRegion2 = innerTestCompletedRegions[nbh];
                System.out.println(osmRegion2.name);
                Util.connectivityTwoRegion(osmRegion, osmRegion2);
            }
        }
        System.out.println("outer test done");
//        for (long node: osmRegion.getAdjList().getAdjHashMap().keySet()){
//            if (osmRegion.getAdjList().getAdjHashMap().get(node).isIsolated()) {
//                System.out.println(osmRegion.getAdjList().getAdjHashMap().get(node).getConnectedComponentId() + ": " +
//                        + osmRegion.getAdjList().getAdjHashMap().get(node).getId());
//            }
//        }

    }


}
