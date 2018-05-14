package edu.pwr.drozd;

import org.springframework.web.bind.annotation.*;

@RestController("/")
public class Controller {

    @RequestMapping(value = "nextMove", method = RequestMethod.POST, produces = "application/json")
    public int[][] nextMove() {
        return null;
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET, produces = "application/json")
    public String test() {
        return "Hello";
    }
}
