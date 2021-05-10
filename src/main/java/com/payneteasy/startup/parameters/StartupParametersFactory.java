package com.payneteasy.startup.parameters;

import com.payneteasy.startup.parameters.impl.ParameterPropertyLoader;
import com.payneteasy.startup.parameters.impl.ParameterSystemLoader;

public class StartupParametersFactory {

    public static <T> T getStartupParameters(Class<T> aClass) {
        return new StartupParametersBuilder()
                .addLoader("p", new ParameterPropertyLoader())
                .addLoader("e", new ParameterSystemLoader())
                .getStartupParameters(aClass);
    }

}
