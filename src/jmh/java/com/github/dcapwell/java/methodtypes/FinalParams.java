package com.github.dcapwell.java.methodtypes;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.CompilerControl;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Shows that final params have no effect on runtime performance
 */
public class FinalParams {
    private static final int left = ThreadLocalRandom.current().nextInt();
    private static final int right = ThreadLocalRandom.current().nextInt();

    @Benchmark
    public int doNothing() {
        // if below tests equal this one, then test bug
        return 4;
    }

    @Benchmark
    public int nonFinal() {
        return add(left, right);
    }

    @Benchmark
    public int withFinal() {
        return addFinal(left, right);
    }

    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    private static int add(int left, int right) {
        return left + right;
    }

    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    private static int addFinal(final int left, final int right) {
        return left + right;
    }
}
