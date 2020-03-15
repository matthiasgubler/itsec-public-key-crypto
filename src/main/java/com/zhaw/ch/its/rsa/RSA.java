package com.zhaw.ch.its.rsa;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigInteger;

public interface RSA {

    BigInteger encrypt(BigInteger plain) throws BadMessageException;

    BigInteger decrypt(BigInteger cipher) throws BadMessageException, OperationNotSupportedException;

    void save(ObjectOutputStream os) throws IOException, OperationNotSupportedException;

    void savePublic(ObjectOutputStream os) throws IOException;

}
