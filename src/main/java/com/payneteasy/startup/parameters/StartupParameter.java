package com.payneteasy.startup.parameters;


public class StartupParameter {

    public final String name;
    public final Object value;
    public final String from;

    public StartupParameter(String name, Object value, String from) {
        this.name = name;
        this.value = value;
        this.from = from;
    }
}
