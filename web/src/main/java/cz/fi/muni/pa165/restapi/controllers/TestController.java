package cz.fi.muni.pa165.restapi.controllers;


import net.minidev.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/hello")
public class TestController {

    @RequestMapping(method = RequestMethod.GET)
    public JSONObject HelloWorld() {
        JSONObject res = new JSONObject();
        res.put("data", "Neznášam javu");
        res.put("errCode", 0);
        return res;
    }
}
