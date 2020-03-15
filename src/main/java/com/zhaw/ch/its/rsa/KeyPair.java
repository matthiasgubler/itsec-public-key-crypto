package com.zhaw.ch.its.rsa;

import java.io.Serializable;

public class KeyPair implements Serializable {
    private PublicKey publicKey;
    private PrivateKey privateKey;

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public KeyPair cloneOfPublicKey() {
        KeyPair newKeyPair = new KeyPair();
        PublicKey newPublicKey = new PublicKey(this.publicKey.getN(), this.publicKey.getE());
        newKeyPair.setPublicKey(newPublicKey);
        return newKeyPair;
    }
}
