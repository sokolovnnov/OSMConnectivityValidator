package ru.antisida.connectivitytest.validator.service;

import org.springframework.stereotype.Service;
import ru.antisida.connectivitytest.site.model.SimpleNode;
import ru.antisida.connectivitytest.validator.StorageUtil;
import ru.antisida.connectivitytest.validator.repository.IsolatedWayJdbcRepository;
import ru.antisida.connectivitytest.validator.model.ValidationResult;

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
