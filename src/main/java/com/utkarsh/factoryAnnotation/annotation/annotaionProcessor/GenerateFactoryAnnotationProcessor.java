package com.utkarsh.factoryAnnotation.annotation.annotaionProcessor;

import com.utkarsh.factoryAnnotation.annotation.GenerateFactory;
import com.utkarsh.factoryAnnotation.factory.GenericFactory;
import org.reflections.Reflections;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Predicate;

@Configuration
public class GenerateFactoryAnnotationProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(@NonNull ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;
        Reflections reflections = new Reflections("com.utkarsh.factoryAnnotation");

        Set<Class<?>> annotatedInterfaces = reflections.getTypesAnnotatedWith(GenerateFactory.class);
        annotatedInterfaces.removeIf(Predicate.not(Class::isInterface));

        for (Class<?> annotatedInterface : annotatedInterfaces) {
            GenericFactory<Object, Object> factory = new GenericFactory<>();

            GenerateFactory annotation = annotatedInterface.getAnnotation(GenerateFactory.class);
            String keyMethodName = annotation.keyMethod();

            if (keyMethodName.isEmpty()) {
                throw new IllegalArgumentException("KeyMethod not defined for GenerateFactory " + annotatedInterface.getName());
            }

            Set<?> implementingClasses = reflections.getSubTypesOf(annotatedInterface);
            for (Object implClass : implementingClasses) {
                try {
                    Object instance = beanFactory.getBean((Class<?>) implClass);
                    Method keyMethod = ((Class<?>) implClass).getMethod(keyMethodName);
                    Object key = keyMethod.invoke(instance);
                    factory.registerInstance(key, instance);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
            registerFactoryBean(annotatedInterface, factory, registry);
        }
    }

    private <K, T> void registerFactoryBean(Class<?> annotatedInterface, GenericFactory<K, T> factory, BeanDefinitionRegistry registry) {
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(GenericFactory.class);
        beanDefinition.setScope(BeanDefinition.SCOPE_SINGLETON);
        beanDefinition.setInstanceSupplier(() -> factory);
        String beanName = annotatedInterface.getSimpleName() + "Factory";
        registry.registerBeanDefinition(beanName, beanDefinition);
    }
}
