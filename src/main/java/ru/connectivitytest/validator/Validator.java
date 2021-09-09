package ru.connectivitytest.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.connectivitytest.validator.model.AdjacencyList;
import ru.connectivitytest.validator.model.ConnectedComponent;
import ru.connectivitytest.validator.model.MarkedNode;
import ru.connectivitytest.validator.model.OsmRegion;
import ru.connectivitytest.validator.model.ValidationResult;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class Validator {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final Set<Integer> notFoundRegion = new HashSet<>();

    public AdjacencyList calculateAdjList(OsmRegion region) {
        AdjacencyList adjacencyList = null;
        try {
            adjacencyList = region.calculateAdjList();
        } catch (IOException e) {//fixme
            notFoundRegion.add(region.getId());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return adjacencyList;
    }

    public ValidationResult connectivityValidate(OsmRegion region, List<OsmRegion> neighbors) throws IOException {

        for (OsmRegion neighbor : neighbors) {
            connectivityTwoRegion(region, neighbor);
            neighbor.setAdjacencyList(new AdjacencyList());
        }

        ValidationResult validationResult =
                new ValidationResult(region.getId(), region.getPath(), region.getIsolatedWaysFromO5M());
        region.setAdjacencyList(new AdjacencyList());
        return validationResult;
    }

    private void connectivityTwoRegion(OsmRegion one, OsmRegion two) {

        if (two == null || one.getAdjacencyList() == null || two.getAdjacencyList() == null) return;

        System.out.println("One = " + one.getName() + ", Two = " + two.getName());

        AdjacencyList adjacencyListOne = one.getAdjacencyList();
        AdjacencyList adjacencyListTwo = two.getAdjacencyList();

        //1. сет id всех изолированных компонентов региона one
        Set<Integer> isolatedComponentIds_One = one.getAdjacencyList().getIsolatedComponentIds();

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
