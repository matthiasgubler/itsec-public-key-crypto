package com.zhaw.ch.its.rsa;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

public class RSAExercise11Sign {

    public static void main(String[] args) {
        String plainText = String.join(" ", args);

        try {
            new RSAExercise11Sign().runRSATestEx11(plainText);
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

    public void runRSATestEx11(String plain) throws IOException, OperationNotSupportedException, BadMessageException, ClassNotFoundException {
        byte[] plainInAscii = plain.getBytes(StandardCharsets.US_ASCII);
        BigInteger plaintextBigInteger = new BigInteger(plainInAscii);
        File keypairFile = new File("persistent_keypair.gubi");
        ObjectInputStream is_keypair = new ObjectInputStream(new FileInputStream(keypairFile));
        RSA rsa = new RSAImpl(is_keypair);
        BigInteger signedMessage = rsa.sign(plaintextBigInteger);

        ObjectOutputStream signedOS = new ObjectOutputStream(new FileOutputStream(new File("signed.gubi")));
        signedOS.writeObject(signedMessage);
        signedOS.close();

        ObjectInputStream signedIS = new ObjectInputStream(new FileInputStream(new File("signed.gubi")));
        BigInteger signed = (BigInteger) signedIS.readObject();
        signedIS.close();

        File publicKeyFile = new File("persistent_public_key.gubi");
        ObjectInputStream is_publickey = new ObjectInputStream(new FileInputStream(publicKeyFile));
        RSA rsaDec = new RSAImpl(is_publickey);
        boolean isVerified = rsaDec.verify(plaintextBigInteger, signed);


        System.out.println("Message: " + plain + " has following verification result: " + isVerified);
        is_publickey.close();
    }

}
