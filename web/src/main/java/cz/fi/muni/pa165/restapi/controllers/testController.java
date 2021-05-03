package cz.fi.muni.pa165.restapi.controllers;


import net.minidev.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/*")
public class testController {

    @RequestMapping(method = RequestMethod.GET)
    public JSONObject HelloWorld() {
        JSONObject res = new JSONObject();
        res.put("data", "hello world!");
        res.put("errCode", 0);
        return res;
    }
}
