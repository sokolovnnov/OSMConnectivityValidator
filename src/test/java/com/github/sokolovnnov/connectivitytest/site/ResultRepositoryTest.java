package com.github.sokolovnnov.connectivitytest.site;

import com.github.sokolovnnov.connectivitytest.site.repo.ResultRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;


@SpringJUnitConfig(locations = {
        "classpath:spring/spring-appp.xml",
        "classpath:spring/spring-mvc.xml",
        "classpath:spring/spring-db.xml"
})
//@ActiveProfiles(value = "postgres")
@Transactional
class ResultRepositoryTest {

    private final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    ResultRepository resultRepository;

    @Test
    void getAll() {
        Long start = System.nanoTime();
        System.out.println();
        System.out.println("1");
        resultRepository.getAll(1);
        log.info("Time getAll: " + (double)(System.nanoTime() - start)/1_000_000_000.0);
        System.out.println("2");
        Long start1 = System.nanoTime();
        resultRepository.getAll(1);
        log.info("Time getAll: " + (double)(System.nanoTime() - start1)/1_000_000_000.0);
        System.out.println("3");
        resultRepository.getAll(1);
        System.out.println("4");
        resultRepository.getAll(1);
        System.out.println("5");
        resultRepository.getAll(1);
        System.out.println("6");
        resultRepository.getAll(1);
        System.out.println("7");
        resultRepository.getAll(2);

    }
}