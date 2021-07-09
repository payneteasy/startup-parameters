package com.payneteasy.startup.parameters.impl;


class StartupParameter {

    final String  name;
    final Object  value;
    final String  from;
    final boolean maskVariable;

    StartupParameter(String name, Object value, String from, boolean aMaskVariable) {
        this.name = name;
        this.value = value;
        this.from = from;
        maskVariable = aMaskVariable;
    }
}
