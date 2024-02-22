package com.enigma.tokonyadia.controller;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
public class ControllerToko {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String sayHello(){
        return "Hello Batch 18!";
    }

    @GetMapping("/hai")
    public String sayHelloAgain(){
        return "Hello Batch 18!";
    }

    @GetMapping("/users/{id}")
    public String pathVar(@PathVariable String id){
        return "User Id: " + id;
    }

    @GetMapping("/users")
    public String reqParam(@RequestParam(name = "halaman", required = false) String page){
        return "Hasil req param: "+ page;
    }

    @PostMapping("/users")
    public String reqBody(@RequestBody HashMap<String, String> mapBody){
        return "Req body: " + mapBody;
    }

}
