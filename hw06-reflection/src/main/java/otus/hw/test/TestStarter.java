package otus.hw.test;

import otus.hw.test.annotation.After;
import otus.hw.test.annotation.Before;
import otus.hw.test.annotation.Test;
import otus.hw.test.helper.ReflectionHelper;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Main starter test
 */
public class TestStarter {
    public static void main(String[] args) throws ClassNotFoundException {
        if (args.length == 0)
            throw new RuntimeException("Error, args is null elements");

        String testClass = args[0];
        Class<?> aClass = Class.forName(TestStarter.class.getPackageName() + "." + testClass);

        List<Method> beforeMethods = new ArrayList<>();
        List<Method> afterMethods = new ArrayList<>();
        List<Method> testMethods = new ArrayList<>();

        for (Method method : aClass.getMethods()) {
            if (method.isAnnotationPresent(Before.class)) {
                beforeMethods.add(method);
            } else if (method.isAnnotationPresent(After.class)) {
                afterMethods.add(method);
            } else if (method.isAnnotationPresent(Test.class)) {
                testMethods.add(method);
            }
        }

        testMethods.forEach(method -> {
            try {
                new TestHolder(ReflectionHelper.instantiate(aClass), method, beforeMethods, afterMethods).run();
                printPassed(method.getName());
            } catch (Exception e) {
                printError(method.getName());
            }
        });
    }

    private static void printPassed(String methodName) {
        print("PASSED", methodName);
    }

    private static void printError(String methodName) {
        print("ERROR", methodName);
    }

    private static void print(String status, String methodName) {
        System.out.printf("method \"%s\" - %s%n", methodName, status);
    }
}