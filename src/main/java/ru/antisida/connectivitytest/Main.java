package ru.antisida.connectivitytest;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.antisida.connectivitytest.validator.StorageUtil;
import ru.antisida.connectivitytest.validator.Validator;
import ru.antisida.connectivitytest.validator.model.AdjacencyList;
import ru.antisida.connectivitytest.validator.model.OsmRegion;
import ru.antisida.connectivitytest.validator.model.ValidationResult;
import ru.antisida.connectivitytest.validator.service.NodeService;
import ru.antisida.connectivitytest.validator.service.RegionService;
import ru.antisida.connectivitytest.validator.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws Exception {

        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext(
                "spring/spring-appp.xml", "spring/spring-db.xml")) {

            NodeService service = appCtx.getBean(NodeService.class);
            Validator validator = appCtx.getBean(Validator.class);
            RegionService regionService = appCtx.getBean(RegionService.class);

            Map<Integer, OsmRegion> regions = regionService.getAll();

//            for (OsmRegion region: regions.values()){
//                AdjacencyList adjacencyList = validator.calculateAdjList(region);
//                StorageUtil.serializeAdjList(adjacencyList);
//            }

          /*  OsmRegion2 region = regions.get(66);
            List<OsmRegion2> neighbors = new ArrayList<>();
            for (Integer neighborsId: region.getNeighbors()) {
                OsmRegion2 neighbor = regions.get(neighborsId);
                if (neighbor != null) {
                    neighbors.add(neighbor);
                }
            }

            ConnectivityResult result = validator.connectivityValidate(region, neighbors);
            service.save(result);*/


            for (OsmRegion region: regions.values()){
                region.setAdjacencyList(StorageUtil.deSerializeAdjList(region.getPath()));
                List<OsmRegion> neighbors = new ArrayList<>();
                for (Integer neighborsId: region.getNeighbors()) {
                    OsmRegion neighbor = regions.get(neighborsId);
                    if (neighbor != null) {
                        neighbor.setAdjacencyList(StorageUtil.deSerializeAdjList(neighbor.getPath()));
                        neighbors.add(regions.get(neighborsId));
                    }
                }
                ValidationResult result = validator.connectivityValidate(region, neighbors);
                service.save(result);
            }
        }
    }
}
