package com.github.sokolovnnov.connectivitytest.service;

import com.github.sokolovnnov.connectivitytest.StorageUtil;
import com.github.sokolovnnov.connectivitytest.repository.WayRepository;
import com.github.sokolovnnov.validatorsite.model.SimpleNode;
import org.springframework.stereotype.Service;
import com.github.sokolovnnov.connectivitytest.model.ValidationResult;

import java.util.List;

@Service
public class NodeService {

    WayRepository repository;

    public NodeService(WayRepository repository) {
        this.repository = repository;
    }

    public void save(ValidationResult result) throws Exception {
        List<SimpleNode> nodes = StorageUtil.readIsolatedSimpleNodes(result);
        repository.saveAll(nodes);
    }
}
