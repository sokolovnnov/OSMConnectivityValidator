package ru.connectivitytest.site;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(locations = {
        "classpath:spring/spring-appp.xml",
        "classpath:spring/spring-db.xml"
})
class ResultRepositoryTest {

    @Autowired
    ResultRepository resultRepository;

    @Test
    void getAll() {

    }
}