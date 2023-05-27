package com.stefan;

import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;
import static org.junit.jupiter.api.Assertions.assertTrue;

@NoArgsConstructor(access = PRIVATE)
public class AssertUtil {

    public static <T> void assertEquals(T expected, T actual, String... excludeFields) {
        ReflectionEquals reflectionEquals = new ReflectionEquals(expected, excludeFields);
        assertTrue(reflectionEquals.matches(actual));
    }

    public static <T> void assertCollectionsEquals(List<T> expected, List<T> actual, String... excludeFields) {
        Assertions.assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i), actual.get(i), excludeFields);
        }
    }
}
