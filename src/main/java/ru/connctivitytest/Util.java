package ru.connctivitytest;

import org.alex73.osmemory.MemoryStorage;
import org.alex73.osmemory.OsmWay;

import java.util.ArrayList;

public class Util {

    public static void connectivityTwoRegion(OsmRegion regionOne, OsmRegion regionTwo) {

        for (long nodeId : regionOne.adjList.getNodeSet()) {
            if (regionOne.adjList.getNode(nodeId).isIsolated()) {
                if (regionTwo.adjList.contains(nodeId)) {
                    int rOneConnectedComponentId = regionOne.adjList.getNode(nodeId).getConnectedComponentId();
                    System.out.println("r1: " + regionOne.name + ", r2: " + regionTwo.name);
                    System.out.println("граф " + rOneConnectedComponentId + " соединен с соседом, точка: " + nodeId);

                    //add setIsolated(false) to all nodes of found ConnectedComponent
                    for (long ndId : regionOne.adjList.getNodeSet()) {
                        if (regionOne.adjList.getNode(ndId).getConnectedComponentId() == rOneConnectedComponentId){
                            regionOne.adjList.getNode(ndId).setIsolated(false);
                        }
                    }
                }
            }
        }
    }





   /* public static HashMap<Long, MarkedNode> innerConnectivityTest(MemoryStorage memoryStorage){
        return createAdjTest(createAdjHashMap(dataToWays(memoryStorage)));
    }*/


    /*private static HashMap<Long, MarkedNode> createAdjTest(HashMap<Long, MarkedNode> adjSet) {

        int subgraphId = 1;

        while (adjSet.values().stream().anyMatch(n -> !n.isVisited())) {

            int sizeSubgraph = 1;
            MarkedNode smn = adjSet
                    .values()
                    .stream()
                    .filter(n -> !n.isVisited())
                    .findFirst()
                    .orElse(new MarkedNode(0L, null, 0L ));

            smn.setVisited(true);
            smn.setSubgraphId(subgraphId);
            smn.setSizeSubgraph(sizeSubgraph);

            long lng = smn.getId();
            Stack<Long> stack = new Stack<>();
            for (long l: smn.getNeighbour()) {
                if (!adjSet.get(l).isVisited()) stack.push(l);
            }

            while (!stack.empty()){
                MarkedNode smNodeFromStack = adjSet.get(stack.pop());
                smNodeFromStack.setVisited(true);
                smNodeFromStack.setSubgraphId(subgraphId);
                smNodeFromStack.setSizeSubgraph(sizeSubgraph++);
                for (long ll: smNodeFromStack.getNeighbour()){
                    if (!adjSet.get(ll).isVisited()) stack.push(ll);
                }
            }
            subgraphId++;
        }

        System.out.println("количество графов: " + subgraphId);
        for (Long lon : adjSet.keySet()){
            if (adjSet.get(lon).getSizeSubgraph() < 1000) {
                adjSet.get(lon).setIsolated(true);
            }
        }
        System.out.println("проставлены изоляты если меньше 1000");
        return adjSet;
    }*/


 /*   private static HashMap<Long, MarkedNode> createAdjHashMap(ArrayList<OsmWay> osmWays) {

        HashMap<Long, MarkedNode> adjHashMap = new HashMap<>();

        for (OsmWay osmWay : osmWays) {
            createAdjSetForWay(osmWay, adjHashMap);
        }

        System.out.println(adjHashMap.size());
        System.out.println(adjHashMap.get(580334994L));

        return adjHashMap;
    }*/


    /*private static void createAdjSetForWay(OsmWay osmWay, HashMap<Long, MarkedNode> adjSet) {

        if (osmWay.getNodeIds().length < 2) {
            System.out.println("!");
            return;
        }

        long[] nodes = osmWay.getNodeIds();
        int i = 0;
        while (i < nodes.length) {
            //создаем ноду с id и с ссылкой на вей, к которому она относится
            HashSet<Long> hs = new HashSet<>();

            if (i > 0 & i < nodes.length - 1) {
                hs.add(nodes[i - 1]);
                hs.add(nodes[i + 1]);

                adjSet.merge(nodes[i], new MarkedNode(nodes[i], hs, osmWay.getId()),
                        (old, newv) -> {
                            old.getNeighbour().addAll(newv.getNeighbour());
                            return old;
                        });
            }

            if (i == 0) {
                hs.add(nodes[i + 1]);
                adjSet.merge(nodes[0], new MarkedNode(nodes[0], hs, osmWay.getId()),
                        (old, newv) -> {
                            old.getNeighbour().addAll(newv.getNeighbour());
                            return old;
                        });
            }

            if (i == nodes.length - 1) {
                hs.add(nodes[i - 1]);
                adjSet.merge(nodes[nodes.length - 1], new MarkedNode(nodes[nodes.length - 1], hs, osmWay.getId()),
                        (old, newv) -> {
                            old.getNeighbour().addAll(newv.getNeighbour());
                            return old;
                        });
            }

            i++;
        }
    }*/


    /*  Метод возвращающий ArrayList всех значащих веев */
    public static ArrayList<OsmWay> memoryStorageToWays(MemoryStorage data) {
        ArrayList<OsmWay> ss = new ArrayList<>();
        data.byTag("highway", o -> {
            if (
                            o.getTag("highway", data).equals("service") ||
                            o.getTag("highway", data).equals("unclassified") ||
                            o.getTag("highway", data).equals("residential") ||
                            o.getTag("highway", data).equals("tertiary") ||
                            o.getTag("highway", data).equals("secondary") ||
                            o.getTag("highway", data).equals("primary") ||
                            o.getTag("highway", data).equals("motorway") ||
                            o.getTag("highway", data).equals("trunk") ||
                            o.getTag("highway", data).equals("living_street") ||
                            o.getTag("highway", data).equals("motorway_link") ||
                            o.getTag("highway", data).equals("trunk_link") ||
                            o.getTag("highway", data).equals("primary_link") ||
                            o.getTag("highway", data).equals("tertiary_link") ||
                            o.getTag("highway", data).equals("secondary_link")
            )
                if (o instanceof OsmWay) ss.add((OsmWay) o);
        });
        return ss;
    }
}
