package com.payneteasy.startup.parameters.impl;

import com.payneteasy.startup.parameters.AStartupParameter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Logger;

public class StartupParametersInvocationHandler implements InvocationHandler {

    private static final Logger LOG = Logger.getLogger(StartupParametersInvocationHandler.class.getName());

    private final Map<String, StartupParameter> parameters;
    private final List<ParameterLoaderMetadata> loaders;
    private final ValueConverter                converter = new ValueConverter();

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
            Object           value = maskVariable(param.value, param.maskVariable);
            LOG.info(String.format("    %s %s = %s", param.from, pad(param.name, max), value));
        }
    }

    private Object maskVariable(Object aValue, boolean aMaskVariable) {
        if(!aMaskVariable) {
            return aValue;
        }

        if(aValue == null) {
            return null;
        }

        int           length = aValue.toString().length();
        StringBuilder sb     = new StringBuilder(length);
        for(int i=0; i<length; i++) {
            sb.append('*');
        }

        return sb.toString();
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
        String    name         = aAnnotation.name();
        String    defaultValue = aAnnotation.value();
        ValueFrom valueFrom    = getValue(name, defaultValue);
        String    textValue    = valueFrom.value;
        Class<?>  type         = aMethod.getReturnType();

        try {
            Object value = converter.convertValue(type, textValue, aMethod);
            return new StartupParameter(name, value, valueFrom.from, aAnnotation.maskVariable());
        } catch (Exception e) {
            throw new IllegalStateException("Cannot parse '" + textValue + "' for type " + type + " and method " + aMethod.getName());
        }

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
