package com.timmax.realestate.util;

import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

public class Util {

    private Util() {
    }

    public static <T extends Comparable<T>> boolean isBetweenHalfOpen(
            T value,
            @Nullable T start,
            @Nullable T end) {
        return (start == null || value.compareTo(start) >= 0) && (end == null || value.compareTo(end) < 0);
    }

    public static @Nullable
    Float parseFloatOrNull(@Nullable String str) {
        return StringUtils.hasLength(str) ? Float.parseFloat(str) : null;
    }

    public static Float getValueIfIsNotNullOrGetFloatMinValue(@Nullable Float value) {
        return value != null ? value : Float.MIN_VALUE;
    }

    public static Float getValueIfIsNotNullOrGetFloatMaxValue(@Nullable Float value) {
        return value != null ? value : Float.MAX_VALUE;
    }
}
