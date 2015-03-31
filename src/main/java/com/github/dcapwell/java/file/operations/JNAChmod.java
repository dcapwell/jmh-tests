package com.github.dcapwell.java.file.operations;

import com.sun.jna.Library;
import com.sun.jna.Native;

public final class JNAChmod implements Chmod {
  private interface POSIX extends Library {
    public int chmod(String filename, int mode);
  }

  private static final POSIX POSIX = (POSIX) Native.loadLibrary("c", POSIX.class);

  @Override
  public boolean chmod(String filename, int mode) {
    return POSIX.chmod(filename, mode) == Chmods.OK;
  }
}
