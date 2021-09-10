package ru.antisida.connectivitytest.validator.service;

import org.springframework.stereotype.Service;
import ru.antisida.connectivitytest.validator.model.OsmRegion;
import ru.antisida.connectivitytest.validator.repository.RegionJdbcRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RegionService {

    private final RegionJdbcRepository repository;

    public RegionService(RegionJdbcRepository repository) {
        this.repository = repository;
    }

    public OsmRegion get(int id){
        return repository.get(id);
    }

    public Map<Integer, OsmRegion> getAll(){
        List<OsmRegion> regionList = repository.getAll();
        return regionList.stream().collect(Collectors.toMap(region -> region.getId(), region -> region));
    }

    public List<Integer> getAllId(){
        return repository.getAllId();
    }
}
