package com.github.sokolovnnov.connectivitytest.validator.repository.inMemory;

import com.github.sokolovnnov.validatorsite.model.SimpleNode;
import com.github.sokolovnnov.validatorsite.repo.inmemory.IsolatedNodes;
import org.springframework.stereotype.Repository;
import com.github.sokolovnnov.connectivitytest.validator.repository.WayRepository;

import java.util.List;

@Repository
public class IsolatedWayInMemoryRepository implements WayRepository {

    private static IsolatedNodes isolatedNodes = new IsolatedNodes();

    @Override
    public void saveAll(List<SimpleNode> simpleNodes) {
        isolatedNodes.nodesMap.put(simpleNodes.get(0).getRegionId(), simpleNodes);
    }

    public static IsolatedNodes getIsolatedNodes() {
        return isolatedNodes;
    }

    public static void setIsolatedNodes(IsolatedNodes isolatedNodes) {
        IsolatedWayInMemoryRepository.isolatedNodes = isolatedNodes;
    }
}
