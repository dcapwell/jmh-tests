package com.github.dcapwell.java.file.operations;

import jnr.ffi.LibraryLoader;

public final class JNRFFIChmod implements Chmod {
  @Override
  public boolean chmod(String filename, int mode) {
    return POSIX.chmod(filename, mode) == Chmods.OK;
  }

  // in JNA this can be private, in JNR-FFI this must be public
  public interface POSIX {
    public int chmod(String filename, int mode);
  }

  private static final POSIX POSIX = LibraryLoader.create(POSIX.class).load("c");
}
