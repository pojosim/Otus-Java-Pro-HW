package otus.hw.test;

import java.lang.reflect.InvocationTargetException;
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

    public void run() throws InvocationTargetException, IllegalAccessException {
        for (Method before : befores) {
            execute(before, instance);
        }

        execute(test, instance);

        for (Method method : afters) {
            execute(method, instance);
        }
    }

    private void execute(Method method, Object instance) throws InvocationTargetException, IllegalAccessException {
        method.invoke(instance);
    }
}
