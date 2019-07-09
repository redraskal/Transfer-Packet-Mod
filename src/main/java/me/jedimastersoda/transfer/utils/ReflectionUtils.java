package me.jedimastersoda.transfer.utils;

import java.lang.reflect.Field;

public class ReflectionUtils {

  public static Field getField(Class<?> clazz, Class<?> type, int index) {
    int currentIndex = 0;

    for(int i = 0; i < clazz.getDeclaredFields().length; i++) {
      Field field = clazz.getDeclaredFields()[i];

      if(field.getType() != type) continue;

      if(currentIndex == index) {
        return field;
      }

      currentIndex++;
    }

    return null;
  }

  public static void modifyPrivateField(Field field, Object object, Object value)
      throws IllegalArgumentException, IllegalAccessException {
    field.setAccessible(true);

    field.set(object, value);
    
    field.setAccessible(false);
  }
}