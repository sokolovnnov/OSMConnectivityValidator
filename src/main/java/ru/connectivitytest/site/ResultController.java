package ru.connectivitytest.site;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.connectivitytest.site.model.SimpleWay;

import java.util.List;
@Controller
@RequestMapping(value = "/")
public class ResultController {

    @Autowired
    private ResultRepository resultRepository;

    @GetMapping("/")
    public String getWays(Model model) {
        List<SimpleWay> simpleWays = resultRepository.getAll();
        model.addAttribute("ways", simpleWays);
        return "way";
    }
}
