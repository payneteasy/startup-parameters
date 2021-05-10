package com.payneteasy.startup.parameters;

import com.payneteasy.startup.parameters.impl.ParameterLoaderMetadata;
import com.payneteasy.startup.parameters.impl.StartupParametersInvocationHandler;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

public class StartupParametersBuilder {

    private final List<ParameterLoaderMetadata> loaders = new ArrayList<>();

    public StartupParametersBuilder addLoader(String aPrefix, IParameterLoader aLoader) {
        loaders.add(new ParameterLoaderMetadata(aPrefix, aLoader));
        return this;
    }

    public <T> T getStartupParameters(Class<T> aClass) {
        //noinspection unchecked
        return (T) Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader()
                , new Class[]{aClass}
                , new StartupParametersInvocationHandler(aClass, loaders)
        );

    }



}
