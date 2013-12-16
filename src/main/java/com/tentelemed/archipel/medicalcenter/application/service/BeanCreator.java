package com.tentelemed.archipel.medicalcenter.application.service;

import org.springframework.asm.Type;
import org.springframework.cglib.core.Signature;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.InterfaceMaker;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 16/12/13
 * Time: 16:54
 */
public class BeanCreator {

    public static <M> M createBean(final M o) {
        //Create a dynamice interface
        InterfaceMaker im = new InterfaceMaker();

        // recuperation de tous les Fields de l'objet
        Class currentClass = o.getClass();
        Map<String, Field> fieldNames = new HashMap<>();
        while (currentClass != null) {
            for (Field field : o.getClass().getDeclaredFields()) {
                String fieldName = field.getName();
                fieldNames.put(fieldName, field);
            }
            currentClass = currentClass.getSuperclass();
        }

        Class c = o.getClass();
        for (String fieldName : fieldNames.keySet()) {
            String getterName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            try {
                // si getter deja present, rien a faire
                c.getMethod(getterName);
            } catch (Exception e) {
                // no getter
                //Type[] parameters = new Type[] { Type.getType(String.class) };
                Type[] parameters = new Type[]{};
                Field field = fieldNames.get(fieldName);
                Signature signature = new Signature(getterName, Type.getType(field.getType()), parameters);
                im.add(signature, null);
            }
        }

        //Finish creating the interface
        Class myInterface = im.create();

        //Create a dynamic class that subclasses Employee
        //and add a method interceptor to handle setFirstName
        Enhancer e = new Enhancer();
        e.setSuperclass(o.getClass());
        e.setInterfaces(new Class[]{myInterface});
        e.setCallback(new MethodInterceptor() {
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                if (method.getName().startsWith("get") || method.getName().startsWith("is")) {
                    String fieldName;
                    if (method.getName().startsWith("get")) {
                        fieldName = method.getName().substring(3);
                        fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
                    } else {
                        fieldName = method.getName().substring(2);
                        fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
                    }
                    //Employee employee = (Employee) obj;
                    for (Field field : o.getClass().getDeclaredFields()) {
                        if (field.getName().equals(fieldName)) {
                            field.setAccessible(true);
                            return field.get(o);
                        }
                    }

                    return null;
                } else {
                    return proxy.invokeSuper(obj, args);
                }
            }
        });
        return (M) e.create();
    }
}
