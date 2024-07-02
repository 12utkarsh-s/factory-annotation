package com.utkarsh.factoryAnnotation.factory;

import com.utkarsh.factoryAnnotation.constant.Operator;
import com.utkarsh.factoryAnnotation.service.OperatorService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OperatorServiceFactory {

    @Autowired
    private List<OperatorService> operatorServiceList;

    private Map<Operator, OperatorService> operatorServiceMap;

    @PostConstruct
    public void init() {
        operatorServiceMap = new HashMap<>();
        for (OperatorService operatorService : operatorServiceList) {
            operatorServiceMap.put(operatorService.getOperator(), operatorService);
        }
    }

    public OperatorService getOperatorService(Operator operator) {
        return operatorServiceMap.get(operator);
    }
}