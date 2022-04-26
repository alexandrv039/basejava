package com.urise.webapp;

import com.urise.webapp.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Resume r = new Resume();
        Class resumeClass = r.getClass();
        System.out.println(resumeClass.getMethod("toString").invoke(r));
    }
}
