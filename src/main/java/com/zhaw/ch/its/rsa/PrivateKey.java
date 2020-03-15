package com.zhaw.ch.its.rsa;

import java.io.Serializable;
import java.math.BigInteger;

public class PrivateKey implements Serializable {
    private BigInteger d;

    public PrivateKey(BigInteger d) {
        this.d = d;
    }

    public BigInteger getD() {
        return d;
    }

}
