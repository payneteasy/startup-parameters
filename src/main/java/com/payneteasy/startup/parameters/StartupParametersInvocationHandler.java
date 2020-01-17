package com.payneteasy.startup.parameters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.*;

public class StartupParametersInvocationHandler implements InvocationHandler {

    private static final Logger LOG = LoggerFactory.getLogger(StartupParametersInvocationHandler.class);

    private final Map<String, StartupParameter> parameters;

    public <T> StartupParametersInvocationHandler(Class<T> aClass) {
        parameters = new HashMap<>();

        SortedMap<String, String> names = new TreeMap<>();
        for (Method method : aClass.getMethods()) {
            if(!method.isAnnotationPresent(AStartupParameter.class)) {
                continue;
            }

            AStartupParameter parameterAnnotation = method.getAnnotation(AStartupParameter.class);
            parameters.put(method.getName(), getValue(method, parameterAnnotation));
            names.put(parameterAnnotation.name(), method.getName());
        }

        int max = parameters.values().stream().map(startupParameter -> startupParameter.name.length()).max(Integer::compare).get();
        LOG.info("Startup parameters:");
        for (Map.Entry<String, String> entry : names.entrySet()) {
            StartupParameter param = parameters.get(entry.getValue());
            LOG.info("    {} {} = {}", param.from, pad(param.name, max), param.value);
        }
    }

    private CharSequence pad(String aText, int aMax) {
        StringBuilder sb = new StringBuilder(aMax);
        sb.append(aText);
        while ( sb.length() < aMax) {
            sb.append(' ');
        }
        return sb;
    }

    private StartupParameter getValue(Method aMethod, AStartupParameter aAnnotation) {
        String name = aAnnotation.name();
        String defaultValue = aAnnotation.value();
        ValueFrom valueFrom = getValue(name, defaultValue);
        String textValue = valueFrom.value;

        Class<?> type = aMethod.getReturnType();
        Object value;
        if(type == int.class) {
            value = Integer.parseInt(textValue);
        } else if (type == boolean.class) {
            value = Boolean.parseBoolean(textValue);
        } else if (type == long.class) {
            value = Long.parseLong(textValue);
        } else if(type == String.class) {
            value =  textValue;
        } else if(type == File.class) {
            value =  new File(textValue);
        } else {
            throw new IllegalStateException("Type " + type + " is unsupported for method " + aMethod);
        }

        return new StartupParameter(name, value, valueFrom.from);
    }

    private ValueFrom getValue(String aName, String aDefaultValue) {
        String value = System.getProperty(aName);
        if(hasText(value)) {
            return new ValueFrom(value, "p");
        }

        value = System.getenv(aName);
        if(hasText(value)) {
            return new ValueFrom(value, "e");
        }

        return new ValueFrom(aDefaultValue, "d");
    }

    private boolean hasText(String aText) {
        return aText != null && aText.trim().length() > 0;
    }

    @Override
    public Object invoke(Object aProxy, Method aMethod, Object[] aArgs) throws Throwable {
        return parameters.get(aMethod.getName()).value;
    }

    private static class ValueFrom {
        private final String value;
        private final String from;

        public ValueFrom(String value, String from) {
            this.value = value;
            this.from = from;
        }
    }
}
