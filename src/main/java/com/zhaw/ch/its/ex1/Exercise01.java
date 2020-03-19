package com.zhaw.ch.its.ex1;

public class Exercise01 {

    public static final double[] SEARCHED_BIT_VALUES = {128d, 256d, 384d, 512d};

    public static void main(String[] args) {
        new Exercise01().run();
    }

    public void run() {

        double j = 1000;
        for (int i = 0; i < SEARCHED_BIT_VALUES.length; i++) {
            double bitValueToSearch = SEARCHED_BIT_VALUES[i];

            double w = 0;
            while (w < bitValueToSearch) {
                double res = GeneralNumberFieldSieve.sieve(j);
                w = (Math.log(res) / Math.log(2));
                j++;
            }

            System.out.println("For " + bitValueToSearch + " bit security, you need a " + j + " Bit number");
        }

    }
}
