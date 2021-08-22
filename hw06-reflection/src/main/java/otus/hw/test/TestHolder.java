package otus.hw.test;

import java.lang.reflect.Method;
import java.util.List;


public class TestHolder {
    private Object instance;
    private Method test;
    private List<Method> befores;
    private List<Method> afters;

    public TestHolder(Object instance, Method test, List<Method> befores, List<Method> afters) {
        this.instance = instance;
        this.test = test;
        this.befores = befores;
        this.afters = afters;
    }

    public boolean run() {
        boolean result;

        befores.forEach(before -> execute(before, instance));
        result = execute(test, instance);
        afters.forEach(method -> execute(method, instance));

        return result;
    }

    private boolean execute(Method method, Object instance) {
        try {
            method.invoke(instance);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
