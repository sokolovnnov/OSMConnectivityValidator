package com.github.sokolovnnov.connectivitytest;

import com.github.sokolovnnov.connectivitytest.validator.StorageUtil;
import com.github.sokolovnnov.connectivitytest.validator.model.OsmRegion;
import com.github.sokolovnnov.connectivitytest.validator.model.ValidationResult;
import com.github.sokolovnnov.connectivitytest.validator.repository.inMemory.IsolatedWayInMemoryRepository;
import com.github.sokolovnnov.connectivitytest.validator.service.NodeService;
import com.github.sokolovnnov.connectivitytest.validator.service.RegionService;
import com.github.sokolovnnov.connectivitytest.validator.validator.ConnectivityValidator;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {

        try (GenericXmlApplicationContext appCtx = new GenericXmlApplicationContext()) {

            appCtx.getEnvironment().setActiveProfiles("H2");
//            appCtx.getEnvironment().setActiveProfiles("heroku");
//            appCtx.getEnvironment().setActiveProfiles("postgres");
            appCtx.load("spring/spring-appp.xml", "spring/spring-db.xml");
            appCtx.refresh();

            NodeService nodeResultService = appCtx.getBean(NodeService.class);
            ConnectivityValidator connectivityValidator = appCtx.getBean(ConnectivityValidator.class);
            RegionService regionService = appCtx.getBean(RegionService.class);

            List<OsmRegion> regions = regionService.getAll();

//            int size = regions.size();
//            if (size > 20) {
//                regions.subList(0, size - 20).clear();
//            }

            List<ValidationResult> results = connectivityValidator.validate(regions);
            for (ValidationResult result: results){
                nodeResultService.save(result);
            }

            StorageUtil.serializeInMemoryRepository(IsolatedWayInMemoryRepository.getIsolatedNodes());

        }
    }
}
