package com.payneteasy.startup.parameters.impl;

import com.payneteasy.startup.parameters.AStartupParameter;

import java.io.File;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Logger;

public class StartupParametersInvocationHandler implements InvocationHandler {

    private static final Logger LOG = Logger.getLogger(StartupParametersInvocationHandler.class.getName());

    private final Map<String, StartupParameter> parameters;
    private final List<ParameterLoaderMetadata> loaders;

    public <T> StartupParametersInvocationHandler(Class<T> aClass, List<ParameterLoaderMetadata> aLoaders) {
        parameters = new HashMap<>();
        loaders = aLoaders;

        SortedMap<String, String> names = new TreeMap<>();
        for (Method method : aClass.getMethods()) {
            if(!method.isAnnotationPresent(AStartupParameter.class)) {
                continue;
            }

            AStartupParameter parameterAnnotation = method.getAnnotation(AStartupParameter.class);
            parameters.put(method.getName(), getValue(method, parameterAnnotation));
            names.put(parameterAnnotation.name(), method.getName());
        }

        int max = parameters.values().stream().map(startupParameter -> startupParameter.name.length()).max(Integer::compare).orElse(10);
        LOG.info("Startup parameters:");
        for (Map.Entry<String, String> entry : names.entrySet()) {
            StartupParameter param = parameters.get(entry.getValue());
            LOG.info(String.format("    %s %s = %s", param.from, pad(param.name, max), param.value));
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
        for (ParameterLoaderMetadata metadata : loaders) {
            String value = metadata.getLoader().getParameterValue(aName);
            if(value != null) {
                return new ValueFrom(value, metadata.getPrefix());
            }
        }

        return new ValueFrom(aDefaultValue, "d");
    }

    @Override
    public Object invoke(Object aProxy, Method aMethod, Object[] aArgs) throws Throwable {
        return parameters.get(aMethod.getName()).value;
    }

    private static class ValueFrom {
        private final String value;
        private final String from;

        private ValueFrom(String value, String from) {
            this.value = value;
            this.from = from;
        }
    }
}
