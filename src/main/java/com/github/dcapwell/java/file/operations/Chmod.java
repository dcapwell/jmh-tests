package com.github.dcapwell.java.file.operations;

public interface Chmod {
  // needs to be boolean, because different impls have different meanings in the return code
  public boolean chmod(String filename, int mode);
}
