package com.zhaw.ch.its.ex1;

public class GeneralNumberFieldSieve {

    public static double sieve(double b) {
        return Math.exp(1.92 * Math.pow(b, 1 / 3) * Math.pow(Math.log(b), 2 / 3));
    }

    public static void main(String[] args) {
        double res = GeneralNumberFieldSieve.sieve(512);
        System.out.println(res);
        System.out.println(Math.log(res) / Math.log(2));
    }
}
