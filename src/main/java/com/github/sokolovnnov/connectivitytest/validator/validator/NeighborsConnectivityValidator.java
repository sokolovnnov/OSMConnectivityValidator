package com.github.sokolovnnov.connectivitytest.validator.validator;

import com.github.sokolovnnov.connectivitytest.validator.model.AdjacencyList;
import com.github.sokolovnnov.connectivitytest.validator.model.ConnectedComponent;
import com.github.sokolovnnov.connectivitytest.validator.model.MarkedNode;
import com.github.sokolovnnov.connectivitytest.validator.model.OsmRegion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.github.sokolovnnov.connectivitytest.validator.model.ValidationResult;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class NeighborsConnectivityValidator {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final Set<Integer> notFoundRegion = new HashSet<>();

    public ValidationResult allNeighborsValidate(OsmRegion region, List<OsmRegion> neighbors) throws IOException {
        for (OsmRegion neighbor : neighbors) {
            oneNeighborValidate(region, neighbor);
            neighbor.clearAdjacencyList();
        }

        ValidationResult validationResult =
                new ValidationResult(region.getId(), region.getPath(), region.getIsolatedWaysFromO5M());
        region.clearAdjacencyList();
        return validationResult;
    }

    private void oneNeighborValidate(OsmRegion region, OsmRegion neighbor) {

        log.info("{}: with neighbor {}", region.getName(), neighbor.getName());

        if (region.getAdjacencyList() == null || neighbor.getAdjacencyList() == null) return;

        AdjacencyList regionAdjacencyList = region.getAdjacencyList();
        AdjacencyList neighborAdjacencyList = neighbor.getAdjacencyList();

        //1. сет id всех изолированных компонентов региона
        Set<Integer> regionIsolatedComponentIds = regionAdjacencyList.getIsolatedComponentIds();

        //2.сет id изолированных компонент региона с учетом соседа
        Set<Integer> filteredRegionIsolatedComponentIds = regionAdjacencyList.getAllMarkedNodes()
                .stream()
                //если нода входит в состав изолированных компонент графа региона
                .filter(markedNode -> regionIsolatedComponentIds.contains(markedNode.getConnectedComponentId()))
                //если нода есть у соседа, и если у соседа она не входит в сет изолированных компонент у соседа
                .filter(markedNode -> neighborAdjacencyList.containsMarkedNode(markedNode.getId()) &&
                                       !neighborAdjacencyList.getIsolatedComponentIds().contains(
                                               neighborAdjacencyList.getMarkedNode(markedNode.getId()).getConnectedComponentId()))
                //получаем айдишники всех изолированных компонент
                .collect(Collectors.groupingBy(MarkedNode::getConnectedComponentId))
                .keySet();

        //изменение компонентов графа: если компонент приконнекчен к соседу, то помечаем его как неизолированный
        List<ConnectedComponent> regionConnectedComponents = regionAdjacencyList.getConnectedComponents();
        for (ConnectedComponent connectedComponent : regionConnectedComponents) {
            if (filteredRegionIsolatedComponentIds.contains(connectedComponent.getId())) {
                System.out.println("Граф " + connectedComponent.getId() + " setIsolated(false)");
                connectedComponent.setIsolated(false);
            }
        }
    }
}
