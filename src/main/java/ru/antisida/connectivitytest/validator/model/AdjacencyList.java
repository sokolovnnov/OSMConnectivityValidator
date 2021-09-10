package ru.antisida.connectivitytest.validator.model;

import org.alex73.osmemory.OsmBase;
import org.alex73.osmemory.OsmWay;
import ru.antisida.connectivitytest.validator.StorageUtil;

import java.io.FileNotFoundException;
import java.io.Serial;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class AdjacencyList implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String regionName;
    private final ArrayList<ConnectedComponent> connectedComponents = new ArrayList<>();
    private HashMap<Long, MarkedNode> innerAjList;

    public AdjacencyList calculateFromO5M(String path) throws FileNotFoundException {
        regionName = path.substring(0, path.length() - 4);
        List<OsmWay> osmWays;
        osmWays = StorageUtil.readFromOM5toWays(path);
        innerAjList = createAdjacencyListForWays(osmWays);
        markAdjacentComponents();
        return this;
    }

    public Set<OsmWay> getIsolatedWaysFromO5M(String path) throws FileNotFoundException {

        ArrayList<OsmWay> allOsmWays = StorageUtil.readFromOM5toWays(path);
        Map<Long, OsmWay> allOsmWaysMap = allOsmWays.stream().collect(Collectors.toMap(OsmBase::getId, osmWay -> osmWay,
                (osmWay1, osmWay2) -> osmWay1));

        Set<Integer> isolatedComponentIds = getIsolatedComponentIds();

        //get osmIds of isolated way
        Set<Long> isolatedWayIds = getInnerAjList()
                .values()
                .stream()
                .filter(markedNode -> isolatedComponentIds.contains(markedNode.getConnectedComponentId()))
                .map(markedNode -> markedNode.wayIds)
                .flatMapToLong(l -> LongStream.of(l))
                .boxed()
                .collect(Collectors.toSet());

        Set<OsmWay> isolatedWays = new HashSet<>();
        for (Long isolatedWayId : isolatedWayIds) {
            isolatedWays.add(allOsmWaysMap.get(isolatedWayId));
        }
        return isolatedWays;
    }

    private void markAdjacentComponents() {

        int connectedComponentId = 1;

        // пока не останется ни одной не посещенной ноды
        while (this.innerAjList.values().stream().anyMatch(n -> !n.isVisited())) {

            // find first node !isVisited()
            MarkedNode markedNode = this.innerAjList.values()
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
                if (this.innerAjList.get(nodeId).isVisited() == false) { //fixme долгая операция
                    notVisitedNodes.push(nodeId);
                }
            }

            while (notVisitedNodes.empty() == false) {
                MarkedNode nodeFromStack = innerAjList.get(notVisitedNodes.pop());
                nodeFromStack.setVisited(true);
                nodeFromStack.setConnectedComponentId(connectedComponentId);
                connectedComponentSize++;
                for (long neighbour : nodeFromStack.neighborNodeIds) {
                    if (this.innerAjList.get(neighbour).isVisited() == false) {
                        notVisitedNodes.push(neighbour);
                    }
                }
            }

            this.addConnectedComponent(new ConnectedComponent(connectedComponentId,
                    connectedComponentSize));

            connectedComponentId++;
        }

        System.out.println("Количество ConnectedComponent: " + this.getConnectedComponents().size());
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

        System.out.println("Количество узлов в списке связности: " + commonAdjMap.size());
        return commonAdjMap;
    }

    private HashMap<Long, MarkedNode> createAdjacencyListForWay(OsmWay osmWay) {
        //long = id ноды, MarkedNode = сама нода с заполненными у соседями
        HashMap<Long, MarkedNode> adjMapForWay = new HashMap<>();
        if (osmWay.getNodeIds().length < 2) {
            throw new IllegalArgumentException("Вей " + osmWay.getId() + " имеет только 1 точку!");
        }

        long[] nodeIds = osmWay.getNodeIds();
        int i = 0;

        while (i < nodeIds.length) {

            // merge потому что веи могут пересекать сами себя, и одна точка может входить несколько раз в один вей
            // для не крайних в вее точек
            if (i > 0 & i < nodeIds.length - 1) {
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

    public void addConnectedComponent(ConnectedComponent connectedComponent) {
        connectedComponents.add(connectedComponent);
    }

    public ArrayList<ConnectedComponent> getConnectedComponents() {
        return connectedComponents;
    }

    public static long[] sumTwoArray(long[] one, long[] two) {
        long[] result = new long[one.length + two.length];
        System.arraycopy(one, 0, result, 0, one.length);
        System.arraycopy(two, 0, result, one.length, two.length);
        return result;
    }

    public Set<Integer> getIsolatedComponentIds() {
        return connectedComponents.stream()
                .filter(ConnectedComponent::isIsolated)
                .map(ConnectedComponent::getId)
                .collect(Collectors.toSet());
    }

    public HashMap<Long, MarkedNode> getInnerAjList() {
        return innerAjList;
    }

    public String getRegionName() {
        return regionName;
    }
}
