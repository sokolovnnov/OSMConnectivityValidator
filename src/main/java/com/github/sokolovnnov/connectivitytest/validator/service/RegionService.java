package com.github.sokolovnnov.connectivitytest.validator.service;

import com.github.sokolovnnov.connectivitytest.validator.model.OsmRegion;
import org.springframework.stereotype.Service;
import com.github.sokolovnnov.connectivitytest.validator.repository.jdbc.region.RegionJdbcRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class RegionService {

    private final RegionJdbcRepository repository;

    public RegionService(RegionJdbcRepository repository) {
        this.repository = repository;
    }

    public List<OsmRegion> getAll(){
        List<OsmRegion> regionList = repository.getAll();
        Map<Integer, OsmRegion> regions = regionList.stream().collect(Collectors.toMap(region -> region.getId(),
                region -> region));
        return fillNeighbors(regions);
    }

    private List<OsmRegion> fillNeighbors(Map<Integer, OsmRegion> regions){
        List<OsmRegion> regionsList = new ArrayList<>(regions.values());
        for (OsmRegion region: regionsList){
            List<OsmRegion> neighbors = new ArrayList<>();
            for (Integer neighborId: region.getNeighborIds()) {
                neighbors.add(regions.get(neighborId));
            }
            region.setNeighbors(neighbors);
        }
        regionsList.removeIf(Objects::isNull);
        return regionsList;
    }
}
