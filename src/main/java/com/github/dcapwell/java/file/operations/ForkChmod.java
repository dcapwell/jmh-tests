package com.github.dcapwell.java.file.operations;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.io.CharStreams;

public final class ForkChmod implements Chmod {
  private static final boolean LOG_ENABLE = false;

  @Override
  public boolean chmod(String filename, int mode) {
    // on exception, return 0 (C true)
    try {
      return fork("/bin/chmod", Chmods.toString(mode), filename) == Chmods.OK;
    } catch (IOException e) {
      return false;
    } catch (InterruptedException e) {
      return false;
    }
  }

  private static int fork(String... cmd) throws IOException, InterruptedException {
    return fork(ImmutableList.copyOf(cmd));
  }

  private static int fork(List<String> cmd) throws IOException, InterruptedException {
    ProcessBuilder pb = new ProcessBuilder(cmd);
    Process process = pb.start();
    process.getOutputStream().close();
    int rc = process.waitFor();

    List<String> out = CharStreams.readLines(new InputStreamReader(process.getInputStream()));
    List<String> err = CharStreams.readLines(new InputStreamReader(process.getErrorStream()));

    log("Ran command %s\nstdout: %s\nstderr: %s", cmd, out, err);

    return rc;
  }

  private static void log(String line, Object... args) {
    if (LOG_ENABLE) {
      System.out.println(String.format(line, args));
    }
  }
}
