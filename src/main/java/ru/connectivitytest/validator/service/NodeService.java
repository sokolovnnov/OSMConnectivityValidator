package ru.connectivitytest.validator.service;

import org.springframework.stereotype.Service;
import ru.connectivitytest.validator.StorageUtil;
import ru.connectivitytest.site.model.SimpleNode;
import ru.connectivitytest.validator.repository.IsolatedWayJdbcRepository;
import ru.connectivitytest.validator.model.ValidationResult;

import java.util.List;

@Service
public class NodeService {

    IsolatedWayJdbcRepository repository;

    public NodeService(IsolatedWayJdbcRepository repository) {
        this.repository = repository;
    }

    public void save(ValidationResult result) throws Exception {
        List<SimpleNode> nodes = StorageUtil.getIsolatedSimpleNodesByWays(result);
        repository.saveAll(nodes);
    }
}
