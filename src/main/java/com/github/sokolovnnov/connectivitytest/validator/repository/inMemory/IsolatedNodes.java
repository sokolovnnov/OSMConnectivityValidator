package com.github.sokolovnnov.connectivitytest.validator.repository.inMemory;

import com.github.sokolovnnov.connectivitytest.site.model.SimpleNode;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class IsolatedNodes implements Serializable {

    public final Map<Integer, List<SimpleNode>> nodesMap = new ConcurrentHashMap<>();
}
