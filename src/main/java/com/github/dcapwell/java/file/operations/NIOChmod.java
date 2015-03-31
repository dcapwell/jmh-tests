package com.github.dcapwell.java.file.operations;

import static java.nio.file.attribute.PosixFilePermission.GROUP_EXECUTE;
import static java.nio.file.attribute.PosixFilePermission.GROUP_READ;
import static java.nio.file.attribute.PosixFilePermission.GROUP_WRITE;
import static java.nio.file.attribute.PosixFilePermission.OTHERS_EXECUTE;
import static java.nio.file.attribute.PosixFilePermission.OTHERS_READ;
import static java.nio.file.attribute.PosixFilePermission.OTHERS_WRITE;
import static java.nio.file.attribute.PosixFilePermission.OWNER_EXECUTE;
import static java.nio.file.attribute.PosixFilePermission.OWNER_READ;
import static java.nio.file.attribute.PosixFilePermission.OWNER_WRITE;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.util.EnumSet;
import java.util.Set;

public final class NIOChmod implements Chmod {
  @Override
  public boolean chmod(String filename, int mode) {
    try {
      Files.setPosixFilePermissions(Paths.get(filename), convertToPermissionsSet(mode));
      return true;
    } catch (IOException e) {
      return false;
    }
  }

  private static Set<PosixFilePermission> convertToPermissionsSet(int mode) {
    Set<PosixFilePermission> result = EnumSet.noneOf(PosixFilePermission.class);

    if (isSet(mode, 0400)) {
      result.add(OWNER_READ);
    }
    if (isSet(mode, 0200)) {
      result.add(OWNER_WRITE);
    }
    if (isSet(mode, 0100)) {
      result.add(OWNER_EXECUTE);
    }

    if (isSet(mode, 040)) {
      result.add(GROUP_READ);
    }
    if (isSet(mode, 020)) {
      result.add(GROUP_WRITE);
    }
    if (isSet(mode, 010)) {
      result.add(GROUP_EXECUTE);
    }
    if (isSet(mode, 04)) {
      result.add(OTHERS_READ);
    }
    if (isSet(mode, 02)) {
      result.add(OTHERS_WRITE);
    }
    if (isSet(mode, 01)) {
      result.add(OTHERS_EXECUTE);
    }
    return result;
  }

  private static boolean isSet(int mode, int testbit) {
    return (mode & testbit) == testbit;
  }
}
