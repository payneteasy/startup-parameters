package com.payneteasy.startup.parameters.impl;

import com.payneteasy.startup.parameters.IParameterLoader;

public class ParameterSystemLoader implements IParameterLoader {

    @Override
    public String getParameterValue(String aParameterName) {
        return System.getenv(aParameterName);
    }
}
