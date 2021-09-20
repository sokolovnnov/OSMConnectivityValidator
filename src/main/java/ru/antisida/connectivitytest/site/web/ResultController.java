package ru.antisida.connectivitytest.site.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.antisida.connectivitytest.site.model.SimpleWay;
import ru.antisida.connectivitytest.site.repo.ResultRepository;

import java.util.List;

@Controller
@RequestMapping(value = "/")
public class ResultController {
    private final Logger log = LoggerFactory.getLogger(getClass());


    @Autowired
    private ResultRepository resultRepository;

    @GetMapping("/")
//    @Cacheable("SimpleWay")
    public String getWays(Model model) {




        List<SimpleWay> simpleWays = resultRepository.getAll(1);
//        List<SimpleWay> simpleWays1 = resultRepository.getAll(2);
//        simpleWays1.remove(1);
//        simpleWays.addAll(simpleWays1);
//        simpleWays.addAll(resultRepository.getAll(1));
        Long start = System.nanoTime();
        model.addAttribute("ways", simpleWays);
        log.info("Time getAll: " + (double)(System.nanoTime() - start)/1_000_000_000.0);
        return "way";
    }
}
