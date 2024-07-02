package com.utkarsh.factoryAnnotation.service;

import com.utkarsh.factoryAnnotation.annotation.GenerateFactory;
import com.utkarsh.factoryAnnotation.constant.Operator;

@GenerateFactory(keyMethod = "getOperator")
public interface OperatorService {

    Operator getOperator();

    Integer performOperation(Integer value1, Integer value2);
}
