package com.bits.group13.fitnesstracker.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class ValueParser<T extends Enum<T>, V> {
  private final Map<V, T> valueMap;

  private ValueParser(Class<T> valueClass, Function<T, V> keyProvider) {
    valueMap = new HashMap<>();
    for (T constant : valueClass.getEnumConstants()) {
      valueMap.put(keyProvider.apply(constant), constant);
    }
  }

  public static <T extends Enum<T>, V> ValueParser<T, V> of(
      Class<T> valueClass, Function<T, V> keyProvider) {
    return new ValueParser<>(valueClass, keyProvider);
  }

  public T parse(V value) {
    return valueMap.get(value);
  }

  public Collection<V> allValues() {
    return valueMap.keySet();
  }
}
