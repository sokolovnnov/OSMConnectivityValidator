package com.github.sokolovnnov.connectivitytest;

import com.github.sokolovnnov.connectivitytest.model.AdjacencyList;
import com.github.sokolovnnov.connectivitytest.model.SimpleNode;
import com.github.sokolovnnov.connectivitytest.repository.inMemory.IsolatedNodeStorage;
import org.alex73.osmemory.*;
import com.github.sokolovnnov.connectivitytest.model.ValidationResult;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class O5mStorageUtils {

    private static final String PATH = "d:\\osmtmp\\";
    private static final float DIVIDER = 0.0000001F;

    public static ArrayList<OsmWay> readWays(String path) throws FileNotFoundException {
        MemoryStorage memoryStorage;
        try {
            memoryStorage = new O5MReader().read(new File(PATH + path));
        } catch (Exception e) {
            System.out.println("Файл .om5 не найден: " + path);
            throw new FileNotFoundException("Файл .om5 не найден:  " + path);
        }

        ArrayList<OsmWay> osmWays = new ArrayList<>();
        memoryStorage.byTag("highway", o -> {
            if (
//                    o.getTag("highway", memoryStorage).equals("service") ||
                    o.getTag("highway", memoryStorage).equals("unclassified") ||
                    o.getTag("highway", memoryStorage).equals("residential") ||
                    o.getTag("highway", memoryStorage).equals("tertiary") ||
                    o.getTag("highway", memoryStorage).equals("secondary") ||
                    o.getTag("highway", memoryStorage).equals("primary") ||
                    o.getTag("highway", memoryStorage).equals("motorway") ||
                    o.getTag("highway", memoryStorage).equals("trunk") ||
                    o.getTag("highway", memoryStorage).equals("living_street") ||
                    o.getTag("highway", memoryStorage).equals("motorway_link") ||
                    o.getTag("highway", memoryStorage).equals("trunk_link") ||
                    o.getTag("highway", memoryStorage).equals("primary_link") ||
                    o.getTag("highway", memoryStorage).equals("tertiary_link") ||
                    o.getTag("highway", memoryStorage).equals("secondary_link")
            )
                if (o instanceof OsmWay) osmWays.add((OsmWay) o);
        });
        memoryStorage.byTag("ferry", o -> {
            if (
                    o.getTag("ferry", memoryStorage).equals("unclassified") ||
                    o.getTag("ferry", memoryStorage).equals("residential") ||
                    o.getTag("ferry", memoryStorage).equals("tertiary") ||
                    o.getTag("ferry", memoryStorage).equals("secondary") ||
                    o.getTag("ferry", memoryStorage).equals("primary") ||
                    o.getTag("ferry", memoryStorage).equals("trunk")
            )
                if (o instanceof OsmWay) osmWays.add((OsmWay) o);
        });
        return osmWays;
    }

    public static Set<OsmWay> readIsolatedWays(AdjacencyList adjacencyList) throws FileNotFoundException {
        ArrayList<OsmWay> allOsmWays = O5mStorageUtils.readWays(adjacencyList.getRegionName());
        Map<Long, OsmWay> allOsmWaysMap = allOsmWays.stream().collect(Collectors.toMap(OsmBase::getId, osmWay -> osmWay,
                (osmWay1, osmWay2) -> osmWay1));

        Set<Integer> isolatedComponentIds = adjacencyList.getIsolatedComponentIds();

        //get osmIds of isolated way
        Set<Long> isolatedWayIds = adjacencyList.getInnerAdjList()
                .values()
                .stream()
                .filter(markedNode -> isolatedComponentIds.contains(markedNode.getConnectedComponentId()))
                .map(markedNode -> markedNode.getWayIds())
                .flatMapToLong(l -> LongStream.of(l))
                .boxed()
                .collect(Collectors.toSet());

        Set<OsmWay> isolatedWays = new HashSet<>();
        for (Long isolatedWayId : isolatedWayIds) {
            isolatedWays.add(allOsmWaysMap.get(isolatedWayId));
        }
        return isolatedWays;
    }

    public static List<SimpleNode> readIsolatedSimpleNodes(ValidationResult result) throws Exception {
        MemoryStorage memoryStorage = new O5MReader().read(new File(PATH + result.getPath()));

        List<SimpleNode> nodes = new ArrayList<>();
        for (OsmWay osmWay : result.getIsolatedWays()) {
            long[] nodeIds = osmWay.getNodeIds();
            for (int orderInWay = 0; orderInWay < nodeIds.length; orderInWay++) {
                IOsmNode osmNode = memoryStorage.getNodeById(nodeIds[orderInWay]);
                nodes.add(new SimpleNode(
//                        null,
                        osmNode.getId(),
                        osmWay.getId(),
                        result.getRegionId(),
                        orderInWay,
                        osmNode.getLat() * DIVIDER,
                        osmNode.getLon() * DIVIDER));
            }
        }
        return nodes;
    }
}
