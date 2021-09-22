package com.github.sokolovnnov.connectivitytest.validator.service;

import com.github.sokolovnnov.connectivitytest.validator.model.OsmRegion;
import org.springframework.stereotype.Service;
import com.github.sokolovnnov.connectivitytest.validator.repository.jdbc.region.RegionJdbcRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RegionService {

    private final RegionJdbcRepository repository;

    public RegionService(RegionJdbcRepository repository) {
        this.repository = repository;
    }

    public Map<Integer, OsmRegion> getAll(){
        List<OsmRegion> regionList = repository.getAll();
        return regionList.stream().collect(Collectors.toMap(region -> region.getId(), region -> region));
    }
}
