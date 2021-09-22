package com.github.sokolovnnov.connectivitytest.site.web;

import com.github.sokolovnnov.connectivitytest.site.repo.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.github.sokolovnnov.connectivitytest.site.model.SimpleWay;

import java.util.List;

@RestController
@RequestMapping(value = ResultRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ResultRestController {
    static final String REST_URL = "/rest";

    @Autowired
    private ResultRepository repository;

    @GetMapping
//    @Cacheable("jsonSimpleWays")
    public List<SimpleWay> getAll() {
        return repository.getAll(1);
    }
}
