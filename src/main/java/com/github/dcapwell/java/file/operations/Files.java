package com.github.dcapwell.java.file.operations;

import java.io.File;
import java.io.IOException;

public final class Files {
  private Files() {}

  public static File tmpFile() {
    // use the caller's name in the file
    StackTraceElement caller = new Throwable().getStackTrace()[1];
    Class<?> clazz = clazz(caller.getClassName());
    File file = null;
    try {
      file = File.createTempFile(clazz.getSimpleName(), basename(clazz.getPackage().getName()));
    } catch (IOException e) {
      throw new AssertionError(e);
    }
    return file;
  }

  private static Class<?> clazz(String name) {
    try {
      return Class.forName(name);
    } catch (ClassNotFoundException e) {
      throw new AssertionError(e);
    }
  }

  private static String basename(String value) {
    return basename(value, "\\.");
  }

  private static String basename(String value, String split) {
    String[] result = value.split(split);
    return result[result.length - 1];
  }
}
