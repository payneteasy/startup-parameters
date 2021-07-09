package com.payneteasy.startup.parameters;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AStartupParameter {

    /**
     * Parameter name
     */
    String name();

    /**
     * Parameter default value
     */
    String value();

    /**
     * Variable will be masked in logs
     */
    boolean maskVariable() default false;
}
