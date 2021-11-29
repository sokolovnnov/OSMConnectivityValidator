package com.github.sokolovnnov.connectivitytest.validators;

import com.github.sokolovnnov.connectivitytest.SerializeUtils;
import com.github.sokolovnnov.connectivitytest.model.AdjacencyList;
import com.github.sokolovnnov.connectivitytest.model.ConnectedComponent;
import com.github.sokolovnnov.connectivitytest.model.MarkedNode;
import com.github.sokolovnnov.connectivitytest.model.OsmRegion;
import com.github.sokolovnnov.connectivitytest.O5mStorageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.github.sokolovnnov.connectivitytest.model.ValidationResult;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ConnectivityValidator {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final Set<Integer> notFoundRegion = new HashSet<>();

    public List<ValidationResult> validate(List<OsmRegion> regions) throws IOException, ClassNotFoundException {

        for (OsmRegion region: regions) {
            prepare(region);
        }

        List<ValidationResult> results = new ArrayList<>();
        for (OsmRegion region: regions) {
            if(region.getNeighbors() == null) continue;
            if(!region.isRussian()) continue;
            ValidationResult result = validateWithAllNeighbors(region);
            results.add(result);
        }

        return results;
    }

    //fixme Exception
    private void prepare(OsmRegion region) throws IOException {
        AdjacencyList adjacencyList = new AdjacencyList(region);
        adjacencyList.calculate();
        adjacencyList.markComponents();
        SerializeUtils.serializeAdjList(adjacencyList);
    }

    //fixme Exception
    private ValidationResult validateWithAllNeighbors(OsmRegion region) throws IOException, ClassNotFoundException {
        AdjacencyList adjacencyRegionList = SerializeUtils.deSerializeAdjList(region.getPath());

        System.out.println(region.getName() + " - соседей: " + region.getNeighbors().size());
        for (OsmRegion osmRegion: region.getNeighbors()) {
            if (osmRegion == null) continue;
            System.out.println(osmRegion.getName());
        }

        for (OsmRegion neighbor: region.getNeighbors()){
            if (neighbor == null) continue;
            AdjacencyList adjacencyNeighborList = SerializeUtils.deSerializeAdjList(neighbor.getPath());
            validateTwoAdjList(adjacencyRegionList, adjacencyNeighborList);
        }

        return new ValidationResult(region.getId(), region.getPath(), O5mStorageUtils.readIsolatedWays(adjacencyRegionList));
    }

    private void validateTwoAdjList(AdjacencyList regionAdjList, AdjacencyList neighborAdjList) {

        log.info("{}: with neighbor {}", regionAdjList.getRegionName(), neighborAdjList.getRegionName());

        //1. сет id всех изолированных компонентов региона
        Set<Integer> regionIsolatedComponentIds = regionAdjList.getIsolatedComponentIds();

        //2.сет id изолированных компонент региона с учетом соседа
        Set<Integer> filteredRegionIsolatedComponentIds = regionAdjList.getAllMarkedNodes()
                .stream()
                //если нода входит в состав изолированных компонент графа региона
                .filter(markedNode -> regionIsolatedComponentIds.contains(markedNode.getConnectedComponentId()))
                //если нода есть у соседа, и если у соседа она не входит в сет изолированных компонент у соседа
                .filter(markedNode -> neighborAdjList.containsMarkedNode(markedNode.getId()) &&
                                      !neighborAdjList.getIsolatedComponentIds().contains(
                                              neighborAdjList.getMarkedNode(markedNode.getId()).getConnectedComponentId()))
                //получаем айдишники всех изолированных компонент
                .collect(Collectors.groupingBy(MarkedNode::getConnectedComponentId))
                .keySet();

        //изменение компонентов графа: если компонент приконнекчен к соседу, то помечаем его как неизолированный
        List<ConnectedComponent> regionConnectedComponents = regionAdjList.getConnectedComponents();
        for (ConnectedComponent connectedComponent : regionConnectedComponents) {
            if (filteredRegionIsolatedComponentIds.contains(connectedComponent.getId())) {
                System.out.println("Граф " + connectedComponent.getId() + " setIsolated(false)");
                connectedComponent.setIsolated(false);
            }
        }
    }
}
