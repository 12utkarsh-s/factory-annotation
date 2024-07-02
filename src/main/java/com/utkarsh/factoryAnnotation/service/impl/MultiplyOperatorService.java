package com.utkarsh.factoryAnnotation.service.impl;

import com.utkarsh.factoryAnnotation.constant.Operator;
import com.utkarsh.factoryAnnotation.service.OperatorService;
import org.springframework.stereotype.Service;

@Service
public class MultiplyOperatorService implements OperatorService {

    @Override
    public Operator getOperator() {
        return Operator.MULTIPLY;
    }

    @Override
    public Integer performOperation(Integer value1, Integer value2) {
        return value1 * value2;
    }
}
