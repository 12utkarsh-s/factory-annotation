package com.utkarsh.factoryAnnotation.factory;

import java.util.HashMap;
import java.util.Map;

public class GenericFactory<K, T> {

    private Map<K, T> implementationMap =  new HashMap<>();

    T getInstance(K key) {
        return implementationMap.get(key);
    }
}

