package com.probase.potzr.SmartBanking;

import org.apache.commons.codec.digest.MessageDigestAlgorithms;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.DigestInfo;
import org.bouncycastle.util.encoders.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;

public class Test {

    public static void main(String[] args) throws KeyStoreException, IOException, UnrecoverableKeyException, NoSuchAlgorithmException, CertificateException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, SignatureException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        LocalDate ld = LocalDate.now();
        System.out.println(ld.toString());

        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        keyStore.load(new FileInputStream("C:\\jcodes\\dev\\keys\\mcv1\\SmartBanking-sandbox-signing.p12"), "chelsea7".toCharArray());
        PrivateKey privateKey =
                (PrivateKey) keyStore.getKey("smartbanking", "chelsea7".toCharArray());
        System.out.println(privateKey.toString());
        System.out.println(privateKey);



        PublicKey publicKey = keyStore.getCertificate("smartbanking").getPublicKey();
        String payload = "{\"clientCustomData\":[{\"removeTag\":false,\"tagContainer\":\"ADD_INFO_01\",\"tagName\":\"Charles Nchimunya\",\"tagValue\":\"1\"}],\"clientNumber\":\"1\",\"clientType\":\"PNR\",\"orderDepartment\":\"Smart Banking\",\"serviceGroupCode\":\"Smart Banking\",\"additionalDate01\":\"2026-01-01T02:49\",\"additionalDate02\":\"2026-01-01T23:01:25\",\"clientBaseAddressData\":{\"addressLine1\":\"4 Lukanga Road\",\"addressLine2\":\"Roma\",\"addressLine3\":\"Lusaka\",\"city\":\"Lusaka\",\"country\":\"ZMB\",\"postalCode\":\"1111\",\"state\":\"Lusaka\"},\"clientCompanyData\":{\"companyDepartment\":\"SmartBanking\",\"companyName\":\"Probase\",\"companyTradeName\":\"Probase\",\"position\":\"Customer\"},\"clientContactData\":{\"email\":\"smicer66@gmail.com\",\"phoneNumberHome\":\"08094073705\",\"phoneNumberMobile\":\"08094073705\"},\"clientIdentificationData\":{\"identificationDocumentDetails\":\"1\",\"identificationDocumentNumber\":\"ZM-891821910AB\",\"identificationDocumentType\":\"Passport\"},\"clientPersonalData\":{\"birthDate\":\"1953-01-01\",\"birthName\":\"Charles Nchimunya\",\"citizenship\":\"ZM\",\"firstName\":\"Charles\",\"gender\":\"M\",\"lastName\":\"Nchimunya\",\"maritalStatus\":\"DS\"},\"embossedData\":{\"firstName\":\"Charles\",\"lastName\":\"Nchimunya\"}}";
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] messageBytes = payload.getBytes(StandardCharsets.UTF_8);
        byte[] messageHash = md.digest(messageBytes);

        AlgorithmIdentifier algorithmIdentifier = new AlgorithmIdentifier(PKCSObjectIdentifiers.sha256WithRSAEncryption);
        DigestInfo digestInfo = new DigestInfo(algorithmIdentifier, messageHash);
        byte[] hashToEncrypt = digestInfo.getEncoded();

        System.out.println(new String(Base64.encode(hashToEncrypt), StandardCharsets.UTF_8));


        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] encryptedMessageHash = cipher.doFinal(hashToEncrypt);
        System.out.println(new String(Base64.encode(encryptedMessageHash), StandardCharsets.UTF_8));
//
//        Signature signature = Signature.getInstance("SHA256withRSA");
//        signature.initSign(privateKey);
//
//
//        signature.update(payload.getBytes(StandardCharsets.UTF_8));
//        byte[] digitalSignature = signature.sign();
//        System.out.println(">>>>>" + new String(Base64.encode(digitalSignature)));
    }
}
