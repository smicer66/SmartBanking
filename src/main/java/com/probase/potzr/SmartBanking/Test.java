package com.probase.potzr.SmartBanking;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mastercard.developer.encryption.EncryptionException;
import com.mastercard.developer.encryption.JweConfig;
import com.mastercard.developer.encryption.JweConfigBuilder;
import com.mastercard.developer.encryption.JweEncryption;
import com.mastercard.developer.oauth.Util;
import com.probase.potzr.SmartBanking.models.requests.CreateMCClientRequest;
import com.probase.potzr.SmartBanking.util.MCUtil;
import com.probase.potzr.SmartBanking.util.OAuth2;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;
import org.apache.commons.lang3.SerializationUtils;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.DigestInfo;
import org.bouncycastle.util.encoders.Base64;
import org.json.JSONObject;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;

public class Test {


    public static void main(String[] a)
    {
//        String c = "{\"clientCustomData\":[{\"removeTag\":false,\"tagContainer\":\"ADD_INFO_01\",\"tagName\":\"Charles Nchimunya\",\"tagValue\":\"1\"}],\"clientNumber\":\"1\",\"clientType\":\"PNR\",\"orderDepartment\":\"Smart Banking\",\"serviceGroupCode\":\"Smart Banking\",\"additionalDate01\":\"2026-01-01T02:49\",\"additionalDate02\":\"2026-01-04T03:02:21\",\"clientBaseAddressData\":{\"addressLine1\":\"4 Lukanga Road\",\"addressLine2\":\"Roma\",\"addressLine3\":\"Lusaka\",\"city\":\"Lusaka\",\"country\":\"ZMB\",\"postalCode\":\"1111\",\"state\":\"Lusaka\"},\"clientCompanyData\":{\"companyDepartment\":\"SmartBanking\",\"companyName\":\"Probase\",\"companyTradeName\":\"Probase\",\"position\":\"Customer\"},\"clientContactData\":{\"email\":\"smicer66@gmail.com\",\"phoneNumberHome\":\"08094073705\",\"phoneNumberMobile\":\"08094073705\"},\"clientIdentificationData\":{\"identificationDocumentDetails\":\"1\",\"identificationDocumentNumber\":\"ZM-891821910AB\",\"identificationDocumentType\":\"Passport\"},\"clientPersonalData\":{\"birthDate\":\"1953-01-01\",\"birthName\":\"Charles Nchimunya\",\"citizenship\":\"ZM\",\"firstName\":\"Charles\",\"gender\":\"M\",\"lastName\":\"Nchimunya\",\"maritalStatus\":\"DS\"},\"embossedData\":{\"firstName\":\"Charles\",\"lastName\":\"Nchimunya\"}}";
//        MessageDigest digest;
//        try {
//            digest = MessageDigest.getInstance("SHA-256");
//        } catch (NoSuchAlgorithmException var6) {
//            throw new IllegalStateException("Unable to obtain RSA-SHA256 message digest", var6);
//        }
//
//        digest.reset();
//        //byte[] byteArray = null == payload ? "".getBytes() : payload.getBytes(charset);
//        byte[] hash = digest.digest(c.getBytes());
//        String s = Util.b64Encode(hash);
//        System.out.println(s);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String payload = "{\"clientCustomData\":[{\"removeTag\":false,\"tagContainer\":\"ADD_INFO_01\",\"tagName\":\"Charles Nchimunya\",\"tagValue\":\"1\"}],\"clientNumber\":\"1\",\"clientType\":\"PNR\",\"orderDepartment\":\"Smart Banking\",\"serviceGroupCode\":\"Smart Banking\",\"additionalDate01\":\"2026-01-01T02:49\",\"additionalDate02\":\"2026-01-01T23:01:25\",\"clientBaseAddressData\":{\"addressLine1\":\"4 Lukanga Road\",\"addressLine2\":\"Roma\",\"addressLine3\":\"Lusaka\",\"city\":\"Lusaka\",\"country\":\"ZMB\",\"postalCode\":\"1111\",\"state\":\"Lusaka\"},\"clientCompanyData\":{\"companyDepartment\":\"SmartBanking\",\"companyName\":\"Probase\",\"companyTradeName\":\"Probase\",\"position\":\"Customer\"},\"clientContactData\":{\"email\":\"smicer66@gmail.com\",\"phoneNumberHome\":\"08094073705\",\"phoneNumberMobile\":\"08094073705\"},\"clientIdentificationData\":{\"identificationDocumentDetails\":\"1\",\"identificationDocumentNumber\":\"ZM-891821910AB\",\"identificationDocumentType\":\"Passport\"},\"clientPersonalData\":{\"birthDate\":\"1953-01-01\",\"birthName\":\"Charles Nchimunya\",\"citizenship\":\"ZM\",\"firstName\":\"Charles\",\"gender\":\"M\",\"lastName\":\"Nchimunya\",\"maritalStatus\":\"DS\"},\"embossedData\":{\"firstName\":\"Charles\",\"lastName\":\"Nchimunya\"}}";


        try {
            CreateMCClientRequest createMCClientRequest = objectMapper.readValue(payload, CreateMCClientRequest.class);
            String jsonBody = objectMapper.writeValueAsString(payload);
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(jsonBody.getBytes(StandardCharsets.UTF_8));
            String consumerKey = "nVw7JfSOIHC8zrY7PrWT4759Kpow_i3jMN4HHPZH5ec9c2d6!6405d5d712a94ab192c6512a9ae1b8f60000000000000000";
            MCUtil mcUtil = new MCUtil();

            URI uri = URI.create("https://sandbox.api.mastercard.com/global-processing/core/clients");
            String method = "POST";
            Charset charset = StandardCharsets.UTF_8;

//            StringBuilder hexString = new StringBuilder();
//            for (byte b : hash) {
//                hexString.append(String.format("%02x", b));
//            }
//
//            String authorizationHash1 = hexString.toString();
            System.out.println(Util.b64Encode(hash));


            CertificateFactory fact = CertificateFactory.getInstance("X.509");
            FileInputStream is = new FileInputStream ("C:\\jcodes\\dev\\keys\\mcv1\\mastercard-processing-coreClientEnc1767500467717.pem");
            X509Certificate encryptionCertificate = (X509Certificate) fact.generateCertificate(is);

            JweConfig config = JweConfigBuilder.aJweEncryptionConfig()

                    .withEncryptionCertificate(encryptionCertificate)

                    .build();

            String encryptedData = JweEncryption.encryptPayload(payload, config);
            JSONObject js = new JSONObject(encryptedData);
            encryptedData = js.getString("encryptedData");
            System.out.println(encryptedData);

            String authorizationHash = OAuth2.getAuthorizationHeader(uri, method, jsonBody, charset, consumerKey, mcUtil.getPrivateKey());
//            authorizationHash = "OAuth oauth_consumer_key=\"nVw7JfSOIHC8zrY7PrWT4759Kpow_i3jMN4HHPZH5ec9c2d6%216405d5d712a94ab192c6512a9ae1b8f60000000000000000\",oauth_signature_method=\"HMAC-SHA1\",oauth_timestamp=\"1767326161\",oauth_nonce=\"RdX0COkxkmu\",oauth_version=\"1.0\",oauth_signature=\""+authorizationHash+"\"";

            System.out.println(authorizationHash);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .header("Content-Type", "application/json")
                    .header("Authorization", authorizationHash)
                    .POST(HttpRequest.BodyPublishers.ofString(encryptedData))
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Status Code: " + response.statusCode());
            System.out.println("Response Body: " + response.body());





        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (UnrecoverableKeyException e) {
            throw new RuntimeException(e);
        } catch (CertificateException e) {
            throw new RuntimeException(e);
        } catch (KeyStoreException e) {
            throw new RuntimeException(e);
        } catch (EncryptionException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main1(String[] args) throws KeyStoreException, IOException, UnrecoverableKeyException, NoSuchAlgorithmException, CertificateException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, SignatureException {
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
        System.out.println(publicKey);
    }
}
