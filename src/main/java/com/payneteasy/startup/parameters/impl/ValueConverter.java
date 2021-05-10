package com.payneteasy.startup.parameters.impl;

import java.io.File;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Period;
import java.util.*;

public class ValueConverter {

    private interface IConverter {
        Object convert(String aText);
    }

    private interface ITypedConverter {
        Optional<?> convert(Class<?> type, String aText);
    }

    private final Map<Class, IConverter> map;
    private final List<ITypedConverter>  list;

    public ValueConverter() {
        map = new HashMap<>();
        map.put(char.class       , aText -> aText.charAt(0));
        map.put(Character.class  , aText -> aText.charAt(0));
        map.put(Boolean.class    , Boolean::parseBoolean);
        map.put(boolean.class    , Boolean::valueOf);
        map.put(Double.class     , Double::parseDouble);
        map.put(double.class     , Double::valueOf);
        map.put(Float.class      , Float::parseFloat);
        map.put(float.class      , Float::valueOf);
        map.put(int.class        , Integer::parseInt);
        map.put(Integer.class    , Integer::valueOf);
        map.put(long.class       , Long::parseLong);
        map.put(Long.class       , Long::valueOf);
        map.put(short.class      , Short::parseShort);
        map.put(Short.class      , Short::valueOf);
        map.put(byte.class       , Byte::parseByte);
        map.put(Byte.class       , Byte::valueOf);
        map.put(String.class     , aText -> aText);
        map.put(Period.class     , Period::parse);
        map.put(Duration.class   , Duration::parse);
        map.put(File.class       , File::new);
        map.put(BigDecimal.class , BigDecimal::new);

        list = new ArrayList<>();
        // enum
        list.add((type, aText) -> {
            if(type.isEnum()) {
                //noinspection unchecked
                Class<? extends Enum> classEnum = (Class<? extends Enum>) type;
                //noinspection unchecked
                return Optional.of(Enum.valueOf(classEnum, aText));
            } else {
                return Optional.empty();
            }
        });
    }

    Object convertValue(Class<?> type, String textValue, Method aMethod) {
        IConverter converter = map.get(type);
        if(converter != null) {
            return converter.convert(textValue);
        }

        for (ITypedConverter typedConverter : list) {
            Optional optional = typedConverter.convert(type, textValue);
            if(optional.isPresent()) {
                return optional.get();
            }
        }
        throw new IllegalStateException("Type " + type + " is unsupported for method " + aMethod);
    }


}
