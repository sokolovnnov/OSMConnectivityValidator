package com.github.sokolovnnov.connectivitytest.repository.inMemory;

import com.github.sokolovnnov.connectivitytest.model.SimpleNode;
import org.springframework.stereotype.Repository;
import com.github.sokolovnnov.connectivitytest.repository.WayRepository;

import java.util.List;

@Repository
public class InMemoryIsolatedWayRepository implements WayRepository {

    private final IsolatedNodeStorage isolatedNodeStorage;

    public InMemoryIsolatedWayRepository(IsolatedNodeStorage storage) {
        this.isolatedNodeStorage = storage;
    }

    @Override
    public void saveAll(List<SimpleNode> simpleNodes) {
        isolatedNodeStorage.simpleNodes.addAll(simpleNodes);
    }

    public IsolatedNodeStorage getIsolatedNodes() {
        return this.isolatedNodeStorage;
    }

}
