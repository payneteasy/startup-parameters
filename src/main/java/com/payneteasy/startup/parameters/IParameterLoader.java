package com.payneteasy.startup.parameters;

public interface IParameterLoader {

    /**
     * Load value for the parameter
     *
     * @param aParameterName parameter name
     *
     * @return if null then we skip this value
     */
    String getParameterValue(String aParameterName);

}
