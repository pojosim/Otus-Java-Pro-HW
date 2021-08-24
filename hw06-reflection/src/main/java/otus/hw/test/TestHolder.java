package otus.hw.test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TestHolder {
    private final Logger logger = Logger.getLogger(TestHolder.class.getName());

    private static final String MSG_ERROR_BEFORE = "Error invoke before methods";
    private static final String MSG_ERROR_TEST = "Error invoke test methods";
    private static final String MSG_ERROR_AFTER = "Error invoke after methods";

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
            befores.forEach(before -> execute(before, instance));
        } catch (TestException e) {
            logError(MSG_ERROR_BEFORE);
            return false;
        }

        try {
            execute(test, instance);
            return true;
        } catch (TestException e) {
            logError(MSG_ERROR_TEST);
            return false;
        } finally {
            try {
                afters.forEach(method -> execute(method, instance));
            } catch (TestException e) {
                logError(MSG_ERROR_AFTER);
            }
        }
    }

    private void execute(Method method, Object instance) throws TestException {
        try {
            method.invoke(instance);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new TestException(e);
        }
    }

    private void logError(String msg) {
        logger.log(Level.WARNING, msg);
    }
}
