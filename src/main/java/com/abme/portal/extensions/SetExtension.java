package com.abme.portal.extensions;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SetExtension {
    public  static <I, O> Set<O> map(Set<I> input, Function<I, O> mapper) {
        return input
                .stream()
                .map(mapper)
                .collect(Collectors.toSet());
    }
}
