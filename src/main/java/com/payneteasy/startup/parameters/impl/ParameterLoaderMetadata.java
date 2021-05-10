package com.payneteasy.startup.parameters.impl;

import com.payneteasy.startup.parameters.IParameterLoader;

public class ParameterLoaderMetadata {

    private final String           prefix;
    private final IParameterLoader loader;

    public ParameterLoaderMetadata(String prefix, IParameterLoader loader) {
        this.prefix = prefix;
        this.loader = loader;
    }

    public String getPrefix() {
        return prefix;
    }

    public IParameterLoader getLoader() {
        return loader;
    }
}
