package otus.hw.test;

import otus.hw.test.exception.AfterTestException;
import otus.hw.test.exception.BeforeTestException;
import otus.hw.test.exception.TestException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TestHolder {
    private final Logger logger = Logger.getLogger(TestHolder.class.getName());

    private final Object instance;
    private final Method test;
    private final List<Method> befores;
    private final List<Method> afters;

    public TestHolder(Object instance, Method test, List<Method> befores, List<Method> afters) {
        this.instance = instance;
        this.test = test;
        this.befores = befores;
        this.afters = afters;
    }

    public boolean run() {
        try {
            invokeAllBefore();
            invokeTest(test, instance);
            return true;
        } catch (TestException e) {
            logError(e.getMsgError());
            return false;
        } finally {
            try {
                invokeAllAfter();
            } catch (TestException e) {
                logError(e.getMsgError());
            }
        }
    }

    private void invokeAllBefore() throws TestException{
        try {
            befores.forEach(before -> invokeTest(before, instance));
        } catch (TestException e) {
            throw new BeforeTestException(e);
        }
    }

    private void invokeTest(Method method, Object instance) throws TestException {
        try {
            method.invoke(instance);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new TestException(e);
        }
    }

    private void invokeAllAfter() {
        try {
            afters.forEach(method -> invokeTest(method, instance));
        } catch (TestException e) {
            throw new AfterTestException(e);
        }
    }

    private void logError(String msg) {
        logger.log(Level.WARNING, msg);
    }
}
