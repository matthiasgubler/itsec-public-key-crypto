package com.zhaw.ch.its.ex1;

public class GeneralNumberFieldSieve {

    public static double sieve(double b) {
        double fac1 = Math.pow(b, (1d / 3d));
        double ln_b = Math.log(b);
        double fac2 = Math.pow(ln_b, (2d / 3d));
        return Math.exp(1.92 * fac1 * fac2);
    }

}
