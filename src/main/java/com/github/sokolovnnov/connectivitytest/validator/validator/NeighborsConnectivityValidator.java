package com.github.sokolovnnov.connectivitytest.validator.validator;

import com.github.sokolovnnov.connectivitytest.validator.StorageUtil;
import com.github.sokolovnnov.connectivitytest.validator.model.AdjacencyList;
import com.github.sokolovnnov.connectivitytest.validator.model.ConnectedComponent;
import com.github.sokolovnnov.connectivitytest.validator.model.MarkedNode;
import com.github.sokolovnnov.connectivitytest.validator.model.OsmRegion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.github.sokolovnnov.connectivitytest.validator.model.ValidationResult;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class NeighborsConnectivityValidator {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final Set<Integer> notFoundRegion = new HashSet<>();

    public List<ValidationResult> validate(List<OsmRegion> regions) throws IOException, ClassNotFoundException {
//        for (OsmRegion region: regions) {
//            prepare(region);
//        }
        List<ValidationResult> results = new ArrayList<>();
        for (OsmRegion region: regions) {
            if(region.getNeighbors() == null) continue;
            if(!region.isRussian()) continue;
            ValidationResult result = validateOne(region);
            results.add(result);
        }
        return results;
    }

    //fixme Exception
    private void prepare(OsmRegion region) throws IOException {
        AdjacencyList adjacencyList = new AdjacencyList(region);
        adjacencyList.calculate();
        adjacencyList.markComponents();
        StorageUtil.serializeAdjList(adjacencyList);
    }

    //fixme Exception
    private ValidationResult validateOne(OsmRegion region) throws IOException, ClassNotFoundException {
        AdjacencyList adjacencyRegionList = StorageUtil.deSerializeAdjList(region.getPath());

        System.out.println(region.getName() + " - соседей: " + region.getNeighbors().size());
        for (OsmRegion osmRegion: region.getNeighbors()) {
            if (osmRegion == null) continue;
            System.out.println(osmRegion.getName());
        }

        for (OsmRegion neighbor: region.getNeighbors()){
            if (neighbor == null) continue;
            AdjacencyList adjacencyNeighborList = StorageUtil.deSerializeAdjList(neighbor.getPath());
            oneNeighborValidate(adjacencyRegionList, adjacencyNeighborList);
        }
        ValidationResult validationResult =
                new ValidationResult(region.getId(), region.getPath(), StorageUtil.getIsolatedWays(adjacencyRegionList));
        return validationResult;
    }

   /* public ValidationResult allNeighborsValidate(OsmRegion region, List<OsmRegion> neighbors) throws IOException {
        for (OsmRegion neighbor : neighbors) {
            oneNeighborValidate(region, neighbor);
            neighbor.clearAdjacencyList();
        }

        ValidationResult validationResult =
                new ValidationResult(region.getId(), region.getPath(), region.getIsolatedWaysFromO5M());
        region.clearAdjacencyList();
        return validationResult;
    }*/
    private void oneNeighborValidate(AdjacencyList regionAdjList, AdjacencyList neighborAdjList) {

        log.info("{}: with neighbor {}", regionAdjList.getRegionName(), neighborAdjList.getRegionName());

//        if (region.getAdjacencyList() == null || neighbor.getAdjacencyList() == null) return;

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

   /* private void oneNeighborValidate(OsmRegion region, OsmRegion neighbor) {

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
    }*/
}
