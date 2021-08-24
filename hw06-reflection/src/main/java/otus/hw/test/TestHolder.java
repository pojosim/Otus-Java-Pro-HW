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
        } catch (RuntimeException e) {
            logError(MSG_ERROR_BEFORE, e);
            return false;
        }

        try {
            execute(test, instance);
            return true;
        } catch (RuntimeException e) {
            logError(MSG_ERROR_TEST, e);
            return false;
        } finally {
            afters.forEach(method -> execute(method, instance));
        }
    }

    private void execute(Method method, Object instance) {
        try {
            method.invoke(instance);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void logError(String msg, Throwable thrown) {
        logger.log(Level.WARNING, msg, thrown);
    }
}
