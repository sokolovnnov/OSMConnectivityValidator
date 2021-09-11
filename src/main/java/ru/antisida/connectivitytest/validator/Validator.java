package ru.antisida.connectivitytest.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.antisida.connectivitytest.validator.model.AdjacencyList;
import ru.antisida.connectivitytest.validator.model.ConnectedComponent;
import ru.antisida.connectivitytest.validator.model.MarkedNode;
import ru.antisida.connectivitytest.validator.model.OsmRegion;
import ru.antisida.connectivitytest.validator.model.ValidationResult;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class Validator {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final Set<Integer> notFoundRegion = new HashSet<>();

    public ValidationResult connectivityValidate(OsmRegion region, List<OsmRegion> neighbors) throws IOException {
        for (OsmRegion neighbor : neighbors) {
            connectivityTwoRegion(region, neighbor);
            neighbor.setEmptyAdjacencyList();
        }

        ValidationResult validationResult =
                new ValidationResult(region.getId(), region.getPath(), region.getIsolatedWaysFromO5M());
        region.setEmptyAdjacencyList();
        return validationResult;
    }

    private void connectivityTwoRegion(OsmRegion region, OsmRegion neighbor) {

        log.info("{}: with neighbor {}", region.getName(), neighbor.getName());

        if (region.getAdjacencyList() == null || neighbor.getAdjacencyList() == null) return;

        AdjacencyList adjacencyListOne = region.getAdjacencyList();
        AdjacencyList adjacencyListTwo = neighbor.getAdjacencyList();

        //1. сет id всех изолированных компонентов региона region
        Set<Integer> isolatedComponentIds_One = region.getAdjacencyList().getIsolatedComponentIds();

        //2.сет id измененных компонентов
        Set<Integer> conComNotIsolatedIds = adjacencyListOne.getInnerAjList().values()
                .stream()
                .filter(markedNode -> isolatedComponentIds_One.contains(markedNode.getConnectedComponentId()))
                .filter(markedNode -> (adjacencyListTwo.getInnerAjList().containsKey(markedNode.getId()) &&
                                       !adjacencyListTwo.getIsolatedComponentIds().contains(
                                               adjacencyListTwo.getInnerAjList().get(markedNode.getId()).getConnectedComponentId())))
                .collect(Collectors.groupingBy(MarkedNode::getConnectedComponentId))
                .keySet();

        //изменение компонентов
        List<ConnectedComponent> conComOne = adjacencyListOne.getConnectedComponents();
        for (ConnectedComponent connectedComponent : conComOne) {
            if (conComNotIsolatedIds.contains(connectedComponent.getId())) {
                System.out.println("Граф " + connectedComponent.getId() + " setIsolated(false)");
                connectedComponent.setIsolated(false);
            }
        }
    }
}
