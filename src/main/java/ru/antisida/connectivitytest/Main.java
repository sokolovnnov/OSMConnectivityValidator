package ru.antisida.connectivitytest;

import org.springframework.context.support.GenericXmlApplicationContext;
import ru.antisida.connectivitytest.validator.Validator;
import ru.antisida.connectivitytest.validator.model.OsmRegion;
import ru.antisida.connectivitytest.validator.model.ValidationResult;
import ru.antisida.connectivitytest.validator.service.NodeService;
import ru.antisida.connectivitytest.validator.service.RegionService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws Exception {

        try (GenericXmlApplicationContext appCtx = new GenericXmlApplicationContext()) {

            appCtx.getEnvironment().setActiveProfiles("heroku");
//            appCtx.getEnvironment().setActiveProfiles("postgres");
            appCtx.load("spring/spring-appp.xml", "spring/spring-db.xml");
            appCtx.refresh();

            NodeService service = appCtx.getBean(NodeService.class);
            Validator validator = appCtx.getBean(Validator.class);
            RegionService regionService = appCtx.getBean(RegionService.class);

            Map<Integer, OsmRegion> regions = regionService.getAll();

            for (OsmRegion region : regions.values()) {
                    region.calculateAdjList();
                    region.serializeAdjList();
            }

            for (OsmRegion region : regions.values()) {
                if (region.isRussian()) {
                    region.deSerializeAdjList();
                    List<OsmRegion> neighbors = new ArrayList<>();
                    for (Integer neighborsId : region.getNeighbors()) {
                        OsmRegion neighbor = regions.get(neighborsId);
                        if (neighbor != null) {
                            neighbor.deSerializeAdjList();
                            neighbors.add(neighbor);
                        }
                    }
                    ValidationResult result = validator.connectivityValidate(region, neighbors);
                    service.save(result);
                }
            }
        }
    }
}
