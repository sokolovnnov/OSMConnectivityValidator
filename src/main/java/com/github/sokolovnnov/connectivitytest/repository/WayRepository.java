package com.github.sokolovnnov.connectivitytest.repository;


import com.github.sokolovnnov.connectivitytest.model.SimpleNode;

import java.util.List;

public interface WayRepository {

    void saveAll(List<SimpleNode> simpleNodes);
}
