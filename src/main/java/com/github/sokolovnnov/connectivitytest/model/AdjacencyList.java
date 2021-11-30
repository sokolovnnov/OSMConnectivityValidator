package com.github.sokolovnnov.connectivitytest.model;

import org.alex73.osmemory.OsmWay;
import com.github.sokolovnnov.connectivitytest.O5mStorageUtils;

import java.io.FileNotFoundException;
//import java.io.Serial;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class AdjacencyList implements Serializable {

//    @Serial
    private static final long serialVersionUID = 1L;

    private final String regionName;
    private final ArrayList<ConnectedComponent> connectedComponents = new ArrayList<>();
    private HashMap<Long, MarkedNode> innerAdjList;

    public AdjacencyList(OsmRegion region) {
        regionName = region.getPath();
    }

    //fixme Exception
    public void calculate() throws FileNotFoundException {
        List<OsmWay> osmWays = O5mStorageUtils.readWays(regionName);
        innerAdjList = createAdjacencyListForWays(osmWays);
    }

    public void markComponents() {

        int connectedComponentId = 1;

        // пока не останется ни одной не посещенной ноды
        while (innerAdjList.values().stream().anyMatch(n -> !n.isVisited())) {

            // find first node !isVisited()
            MarkedNode markedNode = this.innerAdjList.values()
                    .stream()
                    .filter(n -> !n.isVisited())
                    .findFirst()
                    .orElse(new MarkedNode(0L, 0, new long[]{1L}));

            markedNode.setVisited(true);
            markedNode.setConnectedComponentId(connectedComponentId);

            int connectedComponentSize = 1;

            // стек непосещенных нод
            Stack<Long> notVisitedNodes = new Stack<>();
            for (long nodeId : markedNode.neighborNodeIds) {
                if (innerAdjList.get(nodeId).isVisited() == false) { //fixme долгая операция
                    notVisitedNodes.push(nodeId);
                }
            }

            while (notVisitedNodes.empty() == false) {
                MarkedNode nodeFromStack = innerAdjList.get(notVisitedNodes.pop());
                nodeFromStack.setVisited(true);
                nodeFromStack.setConnectedComponentId(connectedComponentId);
                connectedComponentSize++;
                for (long neighbour : nodeFromStack.neighborNodeIds) {
                    if (innerAdjList.get(neighbour).isVisited() == false) {
                        notVisitedNodes.push(neighbour);
                    }
                }
            }

            addConnectedComponent(new ConnectedComponent(connectedComponentId, connectedComponentSize));

            connectedComponentId++;
        }

        System.out.println("Количество ConnectedComponent: " + this.getConnectedComponents().size());
    }

    public ArrayList<ConnectedComponent> getConnectedComponents() {
        return connectedComponents;
    }

    public Set<Integer> getIsolatedComponentIds() {
        return connectedComponents.stream()
                .filter(ConnectedComponent::isIsolated)
                .map(ConnectedComponent::getId)
                .collect(Collectors.toSet());
    }

    private void addConnectedComponent(ConnectedComponent connectedComponent) {
        connectedComponents.add(connectedComponent);
    }

    private HashMap<Long, MarkedNode> createAdjacencyListForWays(List<OsmWay> osmWays) {

        HashMap<Long, MarkedNode> commonAdjMap = new HashMap<>();

        for (OsmWay osmWay : osmWays) {
            HashMap<Long, MarkedNode> adjacencyMapForWays = createAdjacencyListForWay(osmWay);
            for (Long nodeId : adjacencyMapForWays.keySet()) {
                commonAdjMap.merge(nodeId, adjacencyMapForWays.get(nodeId), (old, n) -> {
                    //объединяем wayId в один массив
                    old.wayIds = sumTwoArray(old.wayIds, n.wayIds);
                    //объединяем соседей в один массив
                    old.neighborNodeIds = sumTwoArray(old.neighborNodeIds, n.neighborNodeIds);
                    return old;
                });
            }
        }

        //    System.out.println("Количество узлов в списке связности: " + commonAdjMap.size());
        return commonAdjMap;
    }

    private HashMap<Long, MarkedNode> createAdjacencyListForWay(OsmWay osmWay) {
        //long = id ноды, MarkedNode = сама нода с заполненными соседями
        HashMap<Long, MarkedNode> adjMapForWay = new HashMap<>();
        if (osmWay.getNodeIds().length < 2) {
            throw new IllegalArgumentException("Вей " + osmWay.getId() + " имеет только 1 точку!");//fixme
        }

        long[] nodeIds = osmWay.getNodeIds();
        int i = 0;

        while (i < nodeIds.length) {

            // для не крайних в вее точек
            if (i > 0 & i < nodeIds.length - 1) {
                // merge потому что веи могут пересекать сами себя, и одна точка может входить несколько раз в один вей
                adjMapForWay.merge(nodeIds[i],
                        new MarkedNode(nodeIds[i], osmWay.getId(), new long[]{nodeIds[i - 1], nodeIds[i + 1]}),
                        (oldValue, newValue) -> {
                            newValue.neighborNodeIds = sumTwoArray(oldValue.neighborNodeIds,
                                    newValue.neighborNodeIds);
                            return newValue;
                        });

            } else

                // для первой точки
                if (i == 0) {
                    adjMapForWay.merge(nodeIds[i], new MarkedNode(nodeIds[i], osmWay.getId(), new long[]{nodeIds[1]}),
                            (oldValue, newValue) -> {
                                newValue.neighborNodeIds = sumTwoArray(oldValue.neighborNodeIds, newValue.neighborNodeIds);
                                return newValue;
                            });
                } else

                    // для последней точки
                    if (i == nodeIds.length - 1) {
                        adjMapForWay.merge(nodeIds[i],
                                new MarkedNode(nodeIds[i], osmWay.getId(), new long[]{nodeIds[nodeIds.length - 2]}),
                                (oldValue, newValue) -> {
                                    newValue.neighborNodeIds = sumTwoArray(oldValue.neighborNodeIds, newValue.neighborNodeIds);
                                    return newValue;
                                });
                    }
            i++;
        }
        return adjMapForWay;
    }

    private long[] sumTwoArray(long[] one, long[] two) {
        long[] result = new long[one.length + two.length];
        System.arraycopy(one, 0, result, 0, one.length);
        System.arraycopy(two, 0, result, one.length, two.length);
        return result;
    }

    public HashMap<Long, MarkedNode> getInnerAdjList() {
        return innerAdjList;
    }

    public Collection<MarkedNode> getAllMarkedNodes() {
        return innerAdjList.values();
    }

    public boolean containsMarkedNode(long id){
        return innerAdjList.containsKey(id);
    }

    public MarkedNode getMarkedNode(long id) { return innerAdjList.get(id); }

    public String getRegionName() {
        return regionName;
    }
}
