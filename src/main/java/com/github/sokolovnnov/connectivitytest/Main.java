package com.github.sokolovnnov.connectivitytest;

import com.github.sokolovnnov.connectivitytest.validator.StorageUtil;
import com.github.sokolovnnov.connectivitytest.validator.model.OsmRegion;
import com.github.sokolovnnov.connectivitytest.validator.model.ValidationResult;
import com.github.sokolovnnov.connectivitytest.validator.repository.inMemory.IsolatedWayInMemoryRepository;
import com.github.sokolovnnov.connectivitytest.validator.service.NodeService;
import com.github.sokolovnnov.connectivitytest.validator.service.RegionService;
import com.github.sokolovnnov.connectivitytest.validator.validator.Validator;
import org.springframework.context.support.GenericXmlApplicationContext;

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

            NodeService nodeResultService = appCtx.getBean(NodeService.class);
            Validator validator = appCtx.getBean(Validator.class);
            RegionService regionService = appCtx.getBean(RegionService.class);

            Map<Integer, OsmRegion> regions = regionService.getAll();
            Map<Integer, OsmRegion> regionsForValidate = regionService.getAll();
//            for (OsmRegion region : regions.values()) {
//                region.calculateAdjList();
//                region.serializeAdjList();
//            }


//            OsmRegion estonia = regions.get(300);
//
//            estonia.deSerializeAdjList();
//            List<OsmRegion> neighbors = new ArrayList<>();
//            for (Integer neighborsId : estonia.getNeighbors()) {
//                OsmRegion neighbor = regions.get(neighborsId);
//                System.out.println(neighborsId);
//                if (neighbor != null) {
//                    neighbor.deSerializeAdjList();
//                    neighbors.add(neighbor);
//                }
//            }
//            ValidationResult result = validator.connectivityValidate(estonia, neighbors);
//            nodeResultService.save(result);



            //del region for heroku
            //regionsForValidate.remove(76);
            regionsForValidate.remove(10);
            regionsForValidate.remove(51);
            regionsForValidate.remove(10);
            regionsForValidate.remove(47);
            regionsForValidate.remove(60);
            regionsForValidate.remove(69);
            regionsForValidate.remove(53);
            regionsForValidate.remove(67);
            regionsForValidate.remove(50);
            regionsForValidate.remove(40);
            regionsForValidate.remove(32);
            regionsForValidate.remove(71);
            regionsForValidate.remove(57);
            regionsForValidate.remove(46);
            regionsForValidate.remove(31);
            regionsForValidate.remove(48);
            regionsForValidate.remove(68);
            regionsForValidate.remove(36);
            regionsForValidate.remove(64);
            regionsForValidate.remove(34);
            regionsForValidate.remove(30);
            regionsForValidate.remove(8);
            regionsForValidate.remove(61);
            regionsForValidate.remove(92);
            regionsForValidate.remove(82);
            regionsForValidate.remove(23);
            regionsForValidate.remove(26);
            regionsForValidate.remove(9);
            regionsForValidate.remove(7);
            regionsForValidate.remove(15);
            regionsForValidate.remove(6);
            regionsForValidate.remove(20);
            regionsForValidate.remove(5);
            regionsForValidate.remove(63);
            regionsForValidate.remove(56);
            regionsForValidate.remove(74);
            regionsForValidate.remove(18);
            regionsForValidate.remove(59);
            regionsForValidate.remove(66);
            regionsForValidate.remove(11);
            regionsForValidate.remove(86);
            regionsForValidate.remove(89);
            regionsForValidate.remove(83);
            regionsForValidate.remove(29);




            for (OsmRegion region : regionsForValidate.values()) {
                if (region.isRussian()) {
                    region.deSerializeAdjList();
                    List<OsmRegion> neighbors2 = new ArrayList<>();
                    for (Integer neighborsId : region.getNeighbors()) {
                        OsmRegion neighbor = regions.get(neighborsId);
                        if (neighbor != null) {
                            neighbor.deSerializeAdjList();
                            neighbors2.add(neighbor);
                        }
                    }
                    ValidationResult result2 = validator.connectivityValidate(region, neighbors2);
                    nodeResultService.save(result2);
                }
            }
            StorageUtil.serializeInMemoryRepository(IsolatedWayInMemoryRepository.getIsolatedNodes());
        }
    }
}
