package com.github.dcapwell.java.methodtypes;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import java.util.Collections;
import java.util.List;

@State(Scope.Thread)
public class FunctionTypes {
  private final RealWork work = new RealWork();
  private final Work1 work1 = work;
  private final Work2 work2 = work;

  @Benchmark
  public int staticFunctions() {
    return RealWork.doWorkStatic(Collections.emptyList());
  }

  @Benchmark
  public int staticFunctionsWithState() {
    return RealWork.doWorkStatic();
  }

  @Benchmark
  public int interfaceMethods() {
    return work1.doWorkInterface(Collections.emptyList());
  }

  @Benchmark
  public int interfaceMethodsWithState() {
    return work1.doWorkInterface();
  }

  @Benchmark
  public int abstractMethods() {
    return work2.doWorkAbstract(Collections.emptyList());
  }

  @Benchmark
  public int abstractMethodsWithState() {
    return work2.doWorkAbstract();
  }

  private interface Work1 {
    int doWorkInterface(List<String> empty);
    int doWorkInterface();
  }

  private static abstract class Work2 {
    abstract int doWorkAbstract(List<String> empty);
    abstract int doWorkAbstract();
  }

  private static final class RealWork extends Work2 implements Work1 {
    private static final List<String> EMPTY = Collections.emptyList();
    private final List<String> empty = Collections.emptyList();

    public static int doWorkStatic(List<String> empty) {
      return empty.size();
    }

    public static int doWorkStatic() {
      return EMPTY.size();
    }

    @Override
    public int doWorkInterface(final List<String> empty) {
      return empty.size();
    }

    @Override
    public int doWorkInterface() {
      return empty.size();
    }

    @Override
    int doWorkAbstract(final List<String> empty) {
      return empty.size();
    }

    @Override
    int doWorkAbstract() {
      return empty.size();
    }
  }
}
