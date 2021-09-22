package com.github.sokolovnnov.connectivitytest.validator.service;

import com.github.sokolovnnov.connectivitytest.validator.StorageUtil;
import com.github.sokolovnnov.connectivitytest.validator.repository.WayRepository;
import org.springframework.stereotype.Service;
import com.github.sokolovnnov.connectivitytest.site.model.SimpleNode;
import com.github.sokolovnnov.connectivitytest.validator.model.ValidationResult;

import java.util.List;

@Service
public class NodeService {

    WayRepository repository;

    public NodeService(WayRepository repository) {
        this.repository = repository;
    }

    public void save(ValidationResult result) throws Exception {
        List<SimpleNode> nodes = StorageUtil.getIsolatedSimpleNodesByWays(result);
        repository.saveAll(nodes);
    }
}
