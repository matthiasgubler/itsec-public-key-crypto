package com.zhaw.ch.its.rsa;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.SecureRandom;

public class RSAImpl implements RSA {

    public static final int BIT_LENGTH_PRIME_2 = 1033;
    public static final int BIT_LENGTH_PRIME_1 = 1015;
    private KeyPair keyPair;

    // Generates random RSA key pair
    public RSAImpl() {
        keyPair = new KeyPair();
        BigInteger p = new BigInteger(BIT_LENGTH_PRIME_1, 100, new SecureRandom());
        BigInteger q = new BigInteger(BIT_LENGTH_PRIME_2, 100, new SecureRandom());

        BigInteger n = p.multiply(q);
        BigInteger phi_n = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        BigInteger e = BigInteger.valueOf(3);
        while (!e.gcd(phi_n).equals(BigInteger.ONE)) {
            e = e.nextProbablePrime();
        }

        BigInteger d = e.modInverse(phi_n);

        keyPair.setPublicKey(new PublicKey(n, e));
        keyPair.setPrivateKey(new PrivateKey(d));
    }

    // Reads public key or key pair from input stream
    public RSAImpl(ObjectInputStream is) throws IOException {
        try {
            Object object = is.readObject();
            if (object instanceof KeyPair) {
                keyPair = (KeyPair) object;
            } else {
                throw new IOException("Invalid Type" + object);
            }
        } catch (ClassNotFoundException e) {
            throw new IOException(e);
        }
    }

    private void checkInputLength(BigInteger input) throws BadMessageException {
        BigInteger maxValue = keyPair.getPublicKey().getN().subtract(BigInteger.ONE);
        if (input.compareTo(BigInteger.ONE) < 1 && input.compareTo(maxValue) < 0) {
            throw new BadMessageException("Value is not beween 1 and n-1");
        }
    }

    private void privateKeyPresenceCheck() throws OperationNotSupportedException {
        if (keyPair.getPrivateKey() == null) {
            throw new OperationNotSupportedException("Private Key not present");
        }
    }

    @Override
    public BigInteger encrypt(BigInteger plain) throws BadMessageException {
        checkInputLength(plain);

        PublicKey publicKey = keyPair.getPublicKey();
        return plain.modPow(publicKey.getE(), publicKey.getN());
    }

    @Override
    public BigInteger decrypt(BigInteger cipher) throws BadMessageException, OperationNotSupportedException {
        checkInputLength(cipher);
        privateKeyPresenceCheck();
        PublicKey publicKey = keyPair.getPublicKey();
        PrivateKey privateKey = keyPair.getPrivateKey();

        return cipher.modPow(privateKey.getD(), publicKey.getN());
    }

    @Override
    public BigInteger sign(BigInteger message) throws BadMessageException, OperationNotSupportedException {
        return decrypt(message);
    }

    @Override
    public boolean verify(BigInteger message, BigInteger signature) throws BadMessageException {
        BigInteger messageToVerify = encrypt(signature);
        return messageToVerify.equals(message);
    }

    @Override
    public void save(ObjectOutputStream os) throws IOException, OperationNotSupportedException {
        privateKeyPresenceCheck();
        os.writeObject(keyPair);
    }

    @Override
    public void savePublic(ObjectOutputStream os) throws IOException {
        os.writeObject(keyPair.cloneOfPublicKey());
    }
}
