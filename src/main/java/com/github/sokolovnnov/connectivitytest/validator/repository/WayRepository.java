package com.github.sokolovnnov.connectivitytest.validator.repository;

import com.github.sokolovnnov.connectivitytest.site.model.SimpleNode;

import java.util.List;

public interface WayRepository {

    void saveAll(List<SimpleNode> simpleNodes);
}
