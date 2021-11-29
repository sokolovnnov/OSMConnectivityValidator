package com.github.sokolovnnov.connectivitytest.service;

import com.github.sokolovnnov.connectivitytest.O5mStorageUtils;
import com.github.sokolovnnov.connectivitytest.model.SimpleNode;
import com.github.sokolovnnov.connectivitytest.repository.WayRepository;
import org.springframework.stereotype.Service;
import com.github.sokolovnnov.connectivitytest.model.ValidationResult;

import java.util.List;

@Service
public class NodeService {

    private final WayRepository repository;

    public NodeService(WayRepository repository) {
        this.repository = repository;
    }

    public void save(ValidationResult result) throws Exception {
        List<SimpleNode> nodes = O5mStorageUtils.readIsolatedSimpleNodes(result);
        repository.saveAll(nodes);
    }
}
