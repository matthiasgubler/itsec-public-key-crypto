package com.zhaw.ch.its.rsa;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

public class RSARunner {

    public static void main(String[] args) {
        String plainText = String.join(" ", args);

        try {
            new RSARunner().runRSATestEx05(plainText);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OperationNotSupportedException e) {
            e.printStackTrace();
        } catch (BadMessageException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void runRSATestEx05(String plain) throws IOException, OperationNotSupportedException, BadMessageException, ClassNotFoundException {
        byte[] plainInAscii = plain.getBytes(StandardCharsets.US_ASCII);
        BigInteger plaintextBigInteger = new BigInteger(plainInAscii);
        File publicKeyFile = new File("persistent_public_key.gubi");
        ObjectInputStream is_publickey = new ObjectInputStream(new FileInputStream(publicKeyFile));
        RSA rsa = new RSAImpl(is_publickey);
        BigInteger encryptedMessage = rsa.encrypt(plaintextBigInteger);

        ObjectOutputStream cipherOS = new ObjectOutputStream(new FileOutputStream(new File("cipher.gubi")));
        cipherOS.writeObject(encryptedMessage);
        cipherOS.close();


        ObjectInputStream cipherIS = new ObjectInputStream(new FileInputStream(new File("cipher.gubi")));
        BigInteger cipher = (BigInteger) cipherIS.readObject();
        cipherIS.close();

        File keypairFile = new File("persistent_keypair.gubi");
        ObjectInputStream is_keypair = new ObjectInputStream(new FileInputStream(keypairFile));
        RSA rsaDec = new RSAImpl(is_keypair);
        BigInteger message = rsaDec.decrypt(cipher);


        System.out.println(new String(message.toByteArray()));
        is_keypair.close();
    }

    public void fullTest(String plain) throws IOException, OperationNotSupportedException, BadMessageException {
        System.out.println(plain);
        File keyPairFile = new File("keypair.gubi");
        File publicKeyFile = new File("public_key.gubi");
        ObjectOutputStream os_keypair = new ObjectOutputStream(new FileOutputStream(keyPairFile));
        ObjectOutputStream os_publickey = new ObjectOutputStream(new FileOutputStream(publicKeyFile));
        RSA rsa = new RSAImpl();
        rsa.save(os_keypair);
        rsa.savePublic(os_publickey);

        os_keypair.close();
        os_publickey.close();

        byte[] plainInAscii = plain.getBytes(StandardCharsets.US_ASCII);
        BigInteger plaintextBigInteger = new BigInteger(plainInAscii);
        BigInteger cipher = rsa.encrypt(plaintextBigInteger);

        System.out.println(cipher);

        ObjectInputStream is = new ObjectInputStream(new FileInputStream(keyPairFile));
        rsa = new RSAImpl(is);

        BigInteger decryptedBigInteger = rsa.decrypt(cipher);

        System.out.println(new String(decryptedBigInteger.toByteArray()));
        is.close();
    }

}
