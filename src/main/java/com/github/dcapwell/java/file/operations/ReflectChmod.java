package com.github.dcapwell.java.file.operations;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class ReflectChmod implements Chmod {
  private final Method chmodMethod;

  public ReflectChmod() throws ClassNotFoundException, NoSuchMethodException {
    Class<?> fspClass = Class.forName("java.util.prefs.FileSystemPreferences");
    chmodMethod = fspClass.getDeclaredMethod("chmod", String.class, Integer.TYPE);
    chmodMethod.setAccessible(true);
  }

  @Override
  public boolean chmod(String filename, int mode) {
    try {
      return ((Integer) chmodMethod.invoke(null, filename, mode)).intValue() == Chmods.OK;
    } catch (IllegalAccessException e) {
      return false;
    } catch (InvocationTargetException e) {
      return false;
    }
  }
}
