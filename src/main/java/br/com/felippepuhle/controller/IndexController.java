package br.com.felippepuhle.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class IndexController {

    @RequestMapping(method = RequestMethod.GET)
    public Boolean index() {
        return Boolean.TRUE;
    }
}
