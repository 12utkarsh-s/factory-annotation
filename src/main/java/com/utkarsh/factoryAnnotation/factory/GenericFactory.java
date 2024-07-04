package com.utkarsh.factoryAnnotation.factory;

import java.util.HashMap;
import java.util.Map;

public class GenericFactory<K, T> {

    private final Map<K, T> implementationMap;

    public GenericFactory() {
        this.implementationMap = new HashMap<>();
    }

    public T getInstance(K key) {
        return implementationMap.get(key);
    }

    public void registerInstance(K key, T instance) {
        implementationMap.put(key, instance);
    }
}

