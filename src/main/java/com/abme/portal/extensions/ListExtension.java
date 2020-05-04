package com.abme.portal.extensions;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ListExtension {
    public static <T> List<T> randomListElements(List<T> of, int lowerBound, int upperBoundExclusive, Random random) {
        Collections.shuffle(of, random);
        var numberOfUsers = random.nextInt(upperBoundExclusive) + lowerBound;
        return of
                .stream()
                .limit(numberOfUsers)
                .collect(Collectors.toList());
    }
}
