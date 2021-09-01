package otus.hw.test;

import otus.hw.test.annotation.After;
import otus.hw.test.annotation.Before;
import otus.hw.test.annotation.Test;
import otus.hw.test.helper.ReflectionHelper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class TestRunner {
    private final Logger logger = Logger.getLogger(TestStarter.class.getName());

    private final Class<?> testClass;

    public TestRunner(String testClassName) {
        try {
            testClass = Class.forName(TestStarter.class.getPackageName() + "." + testClassName);
        } catch (ClassNotFoundException e) {
            logger.log(Level.WARNING, "Error start testing", e);
            throw new RuntimeException("Error defining the class from the argument, perhaps the argument is the wrong name of the test class");
        }
    }

    public void run() {
        List<Method> beforeMethods = getMethodsByAnnotation(Before.class);
        List<Method> afterMethods = getMethodsByAnnotation(After.class);
        List<Method> testMethods = getMethodsByAnnotation(Test.class);

        Map<String, Boolean> summaryInfo = new HashMap<>(testMethods.size());

        testMethods.forEach(testMethod -> {
            Object classInstance = ReflectionHelper.instantiate(testClass);
            boolean result = new TestHolder(classInstance, testMethod, beforeMethods, afterMethods).run();
            summaryInfo.put(testMethod.getName(), result);
        });

        printSummaryInfo(summaryInfo);
    }

    private List<Method> getMethodsByAnnotation(Class<? extends Annotation> annotation) {
        return Arrays.stream(testClass.getMethods())
                .filter(method -> method.isAnnotationPresent(annotation))
                .collect(Collectors.toList());
    }

    private void printSummaryInfo(Map<String, Boolean> summaryInfo) {
        // elem [0] - done
        // elem [1] - error
        int[] counts = {0, 0};

        summaryInfo.forEach((methodName, result) -> {
            print(methodName, Boolean.TRUE.equals(result) ? "PASSED" : "ERROR");
            counts[result ? 0 : 1]++;
        });

        System.out.printf("\nCount tests:\n\r done - %d,\n\r error - %d%n", counts[0], counts[1]);
    }

    private void print(String methodName, String result) {
        System.out.printf("method \"%s\" - %s%n", methodName, result);
    }
}
