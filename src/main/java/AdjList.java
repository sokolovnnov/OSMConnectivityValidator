import org.alex73.osmemory.OsmWay;

import java.util.*;

public class AdjList {

    private HashMap<Long, MarkedNode> adjHashMap = new HashMap<>();
    int connectedComponentId = 1;

    public AdjList(ArrayList<OsmWay> osmWays) {
        System.out.println("osmWays.size() = " + osmWays.size());
        for (OsmWay osmWay : osmWays) {
            createAdjSetForWay(osmWay);
        }

        System.out.println(adjHashMap.size());
        System.out.println(adjHashMap.get(580334994L));

    }

    public void markConnectedComponent() {

      //  int connectedComponentId = 1;

        // пока не останется ни одной не посещенной ноды
        while (adjHashMap.values().stream().anyMatch(n -> !n.isVisited())) {

            // находим первую ноду !isVisited()
            MarkedNode node = adjHashMap
                    .values()
                    .stream()
                    .filter(n -> !n.isVisited())
                    .findFirst()
                    .orElse(new MarkedNode(0L, null, 0L)); //todo это нужно как-то заменить

            node.setVisited(true);
            node.setConnectedComponentId(connectedComponentId);

            int connectedComponentSize = 1;
            node.setConnectedComponentSize(connectedComponentSize);

            // стек непосещенных нод
            Stack<Long> stack = new Stack<>();
            for (long neighbour : node.getNeighbours()) {
                if (!adjHashMap.get(neighbour).isVisited()) stack.push(neighbour);
            }

            while (!stack.empty()) {
                MarkedNode nodeFromStack = adjHashMap.get(stack.pop());
                nodeFromStack.setVisited(true);
                nodeFromStack.setConnectedComponentId(connectedComponentId);
                nodeFromStack.setConnectedComponentSize(connectedComponentSize++);
                for (long neighbour : nodeFromStack.getNeighbours()) {
                    if (!adjHashMap.get(neighbour).isVisited()) stack.push(neighbour);
                }
            }
            connectedComponentId++;
        }

        System.out.println("количество графов: " + connectedComponentId);
        for (Long lon : adjHashMap.keySet()) {
            if (adjHashMap.get(lon).getConnectedComponentSize() < 1000) {
                adjHashMap.get(lon).setIsolated(true);
            }
        }
        System.out.println("проставлены изоляты если меньше 1000");
    }

    // Create new AdjList from ArrayList<OsmWay>
   /* public AdjList create(ArrayList<OsmWay> osmWays) {

        //ArrayList<OsmWay> osmWays =

        for (OsmWay osmWay : osmWays) {
            createAdjSetForWay(osmWay);
        }

        System.out.println(adjHashMap.size());
        System.out.println(adjHashMap.get(580334994L));

        return this;
    }*/


    HashSet[] getIsolatedWayIds(){
        //ArrayList<HashSet<Long>> isolatedWays = new ArrayList<>(1000);
        HashSet[] isolatedWays = new HashSet[1000];
//        OsmWay[][] osmWays = new OsmWay[connectedComponentId][];
//        for(int i = 1; i < connectedComponentId; i++){
//
//        }
        for (long node: adjHashMap.keySet()){
            if(adjHashMap.get(node).getConnectedComponentSize() > 1000) continue;
          //  isolatedWays.add(adjHashMap.get(node).getConnectedComponentId(), new HashSet<>());
            if (isolatedWays[adjHashMap.get(node).getConnectedComponentId()] == null) {
                isolatedWays[adjHashMap.get(node).getConnectedComponentId()] = new HashSet<Long>();
            }
//            if (isolatedWays.get(adjHashMap.get(node).getConnectedComponentId()) == null){
//                isolatedWays.add(adjHashMap.get(node).getConnectedComponentId(), new HashSet<>());
//            }
           // isolatedWays.get(adjHashMap.get(node).getConnectedComponentId()).add(adjHashMap.get(node).getWayId());
            isolatedWays[adjHashMap.get(node).getConnectedComponentId()].add(adjHashMap.get(node).getWayId());

        }
        return isolatedWays;
    }

    private void createAdjSetForWay(OsmWay osmWay) {

        if (osmWay.getNodeIds().length < 2) {
//            System.out.println("!");
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

                adjHashMap.merge(nodes[i], new MarkedNode(nodes[i], hs, osmWay.getId()),
                        (old, newv) -> {
                            old.getNeighbours().addAll(newv.getNeighbours());
                            return old;
                        });
            }

            if (i == 0) {
                hs.add(nodes[i + 1]);
                adjHashMap.merge(nodes[0], new MarkedNode(nodes[0], hs, osmWay.getId()),
                        (old, newv) -> {
                            old.getNeighbours().addAll(newv.getNeighbours());
                            return old;
                        });
            }

            if (i == nodes.length - 1) {
                hs.add(nodes[i - 1]);
                adjHashMap.merge(nodes[nodes.length - 1], new MarkedNode(nodes[nodes.length - 1], hs, osmWay.getId()),
                        (old, newv) -> {
                            old.getNeighbours().addAll(newv.getNeighbours());
                            return old;
                        });
            }

            i++;
        }
    }

    public Set<Long> getNodeSet() {
        return this.adjHashMap.keySet();
    }

    public MarkedNode getNode(long nodeId) {
        return this.adjHashMap.get(nodeId);
    }

    public boolean contains(long nodeId) {
        return this.adjHashMap.containsKey(nodeId);
    }


    public HashMap<Long, MarkedNode> getAdjHashMap() {
        return adjHashMap;
    }
}
