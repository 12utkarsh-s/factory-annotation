package com.utkarsh.factoryAnnotation.controller;


import com.utkarsh.factoryAnnotation.constant.Operator;
import com.utkarsh.factoryAnnotation.factory.OperatorServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/operation")
public class TestController {

    @Autowired
    private OperatorServiceFactory factory;

    @GetMapping(value = "/add")
    public void add(@RequestParam int value1, @RequestParam int value2) {
        System.out.println(factory.getOperatorService(Operator.ADD).performOperation(value1, value2));
    }

    @GetMapping(value = "/multiply")
    public void multiply(@RequestParam int value1, @RequestParam int value2) {
        System.out.println(factory.getOperatorService(Operator.MULTIPLY).performOperation(value1, value2));

    }
}

