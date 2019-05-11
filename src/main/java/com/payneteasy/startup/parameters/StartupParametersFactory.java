package com.payneteasy.startup.parameters;

import java.lang.reflect.Proxy;

public class StartupParametersFactory {

    public static <T> T getStartupParameters(Class<T> aClass) {
        //noinspection unchecked
        return (T) Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader()
                , new Class[]{aClass}
                , new StartupParametersInvocationHandler(aClass)
        );
    }

}
