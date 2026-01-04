package com.probase.potzr.SmartBanking.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mastercard.developer.interceptors.OkHttpOAuth1Interceptor;
import com.mastercard.developer.mdes_digital_enablement_client.*;
import com.mastercard.developer.oauth.OAuth;
import com.mastercard.developer.signers.HttpsUrlConnectionSigner;
import com.mastercard.developer.utils.AuthenticationUtils;
import okhttp3.*;
import okio.Buffer;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MCUtil {
    public String consumerKey = "nVw7JfSOIHC8zrY7PrWT4759Kpow_i3jMN4HHPZH5ec9c2d6!6405d5d712a94ab192c6512a9ae1b8f60000000000000000";
    String signingKeyAlias = "smartbanking";
    String signingKeyFilePath = "C:\\jcodes\\dev\\keys\\mcv1\\SmartBanking-sandbox-signing.p12";
    String signingKeyPassword = "chelsea7";

    private PrivateKey getPrivateKey() throws UnrecoverableKeyException, CertificateException, IOException, KeyStoreException, NoSuchAlgorithmException {

        PrivateKey signingKey = AuthenticationUtils.loadSigningKey(
                signingKeyFilePath,
                signingKeyAlias,
                signingKeyPassword
        );

        return signingKey;
    }

    public String signOld(Object object) throws UnrecoverableKeyException, CertificateException, IOException, KeyStoreException, NoSuchAlgorithmException {

//        Charset charset = StandardCharsets.UTF_8;
//        URL url = new URL("https://sandbox.api.mastercard.com/global-processing/core/clients");
        ObjectMapper objectMapper = new ObjectMapper();
        String payload = objectMapper.writeValueAsString(object);

        payload = "{\"clientCustomData\":[{\"removeTag\":false,\"tagContainer\":\"ADD_INFO_01\",\"tagName\":\"Charles Nchimunya\",\"tagValue\":\"1\"}],\"clientNumber\":\"1\",\"clientType\":\"PNR\",\"orderDepartment\":\"Smart Banking\",\"serviceGroupCode\":\"Smart Banking\",\"additionalDate01\":\"2026-01-01T02:49\",\"additionalDate02\":\"2026-01-01T23:01:25\",\"clientBaseAddressData\":{\"addressLine1\":\"4 Lukanga Road\",\"addressLine2\":\"Roma\",\"addressLine3\":\"Lusaka\",\"city\":\"Lusaka\",\"country\":\"ZMB\",\"postalCode\":\"1111\",\"state\":\"Lusaka\"},\"clientCompanyData\":{\"companyDepartment\":\"SmartBanking\",\"companyName\":\"Probase\",\"companyTradeName\":\"Probase\",\"position\":\"Customer\"},\"clientContactData\":{\"email\":\"smicer66@gmail.com\",\"phoneNumberHome\":\"08094073705\",\"phoneNumberMobile\":\"08094073705\"},\"clientIdentificationData\":{\"identificationDocumentDetails\":\"1\",\"identificationDocumentNumber\":\"ZM-891821910AB\",\"identificationDocumentType\":\"Passport\"},\"clientPersonalData\":{\"birthDate\":\"1953-01-01\",\"birthName\":\"Charles Nchimunya\",\"citizenship\":\"ZM\",\"firstName\":\"Charles\",\"gender\":\"M\",\"lastName\":\"Nchimunya\",\"maritalStatus\":\"DS\"},\"embossedData\":{\"firstName\":\"Charles\",\"lastName\":\"Nchimunya\"}}";
        //payload = "{\"clientCustomData\": [{\"removeTag\": false,\"tagContainer\": \"ADD_INFO_01\",\"tagName\": \"TAG_01\",\"tagValue\": \"TAG_01_VALUE\"}],\"clientNumber\": \"ABC_5698521931\",\"clientType\": \"PR\",\"orderDepartment\": \"Department\",\"serviceGroupCode\": \"021\",\"additionalDate01\": \"2021-01-27T09:59:44Z\",\"additionalDate02\": \"2021-02-15T20:58:39Z\",\"clientBaseAddressData\": {\"addressLine1\": \"Mrs. Alice Smith Apartment\",\"addressLine2\": \"1c 213\",\"addressLine3\": \"Derrick Street\",\"addressLine4\": \"2nd floor\",\"city\": \"Boston\",\"country\": \"USA\",\"postalCode\": \"02130\",\"state\": \"MA\"},\"clientCompanyData\": {\"companyDepartment\": \"Department\",\"companyName\": \"Company\",\"companyTradeName\": \"Company Trade\",\"position\": \"Employee\"},\"clientContactData\": {\"email\": \"johndoe@example.com\",\"fax\": \"0048123456777\",\"faxHome\": \"0048123456888\",\"phoneNumberHome\": \"0048123456999\",\"phoneNumberMobile\": \"0048123456778\",\"phoneNumberWork\": \"0048123456789\"},\"clientIdentificationData\": {\"identificationDocumentDetails\": \"161235698529429\",\"identificationDocumentNumber\": \"161235698529328\",\"identificationDocumentType\": \"Passport\",\"socialNumber\": \"161235698529227\",\"taxPosition\": \"Tax position\",\"taxpayerIdentifier\": \"161235698529531\"},\"clientPersonalData\": {\"birthDate\": \"2021-06-25\",\"birthName\": \"Doe\",\"birthPlace\": \"Warsaw\",\"citizenship\": \"USA\",\"firstName\": \"John\",\"gender\": \"M\",\"language\": \"en\",\"lastName\": \"Doe\",\"maritalStatus\": \"DS\",\"middleName\": \"Carl\",\"secretPhrase\": \"secret\",\"shortName\": \"Madley\",\"suffix\": \"PhD\",\"title\": \"MR\"},\"clientExpiryDate\": \"2029-06-25\",\"embossedData\": {\"companyName\": \"COMPANY\",\"firstName\": \"JOHN\",\"lastName\": \"DOE\",\"title\": \"MR\"}}";
//        HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
//        con.setRequestMethod("POST");
//        con.setRequestProperty("Content-Type", "application/json; charset=" + charset.name());
//        con.setDoOutput(true);
//
//        HttpsUrlConnectionSigner signer = new HttpsUrlConnectionSigner(charset, consumerKey, getPrivateKey());
//        signer.sign(con, payload);
//
//        try(OutputStream os = con.getOutputStream()) {
//            byte[] input = payload.getBytes("utf-8");
//            os.write(input, 0, input.length);
//        }
//
//        try(BufferedReader br = new BufferedReader(
//                new InputStreamReader(con.getInputStream(), "utf-8"))) {
//            StringBuilder response = new StringBuilder();
//            String responseLine = null;
//            while ((responseLine = br.readLine()) != null) {
//                response.append(responseLine.trim());
//            }
//            System.out.println(response.toString());
//        }



        System.out.println("payload....");
        System.out.println(payload);
        //payload = "{\"clientCustomData\":[{\"removeTag\":false,\"tagContainer\":\"ADD_INFO_01\",\"tagName\":\"Charles Nchimunya\",\"tagValue\":\"1\"}],\"clientNumber\":\"1\",\"clientType\":\"PNR\",\"orderDepartment\":\"Smart Banking\",\"serviceGroupCode\":\"Smart Banking\",\"additionalDate01\":\"2026-01-01T02:49\",\"additionalDate02\":\"2026-01-01T23:01:25\",\"clientBaseAddressData\":{\"addressLine1\":\"4 Lukanga Road\",\"addressLine2\":\"Roma\",\"addressLine3\":\"Lusaka\",\"city\":\"Lusaka\",\"country\":\"ZMB\",\"postalCode\":\"1111\",\"state\":\"Lusaka\"},\"clientCompanyData\":{\"companyDepartment\":\"SmartBanking\",\"companyName\":\"Probase\",\"companyTradeName\":\"Probase\",\"position\":\"Customer\"},\"clientContactData\":{\"email\":\"smicer66@gmail.com\",\"phoneNumberHome\":\"08094073705\",\"phoneNumberMobile\":\"08094073705\"},\"clientIdentificationData\":{\"identificationDocumentDetails\":\"1\",\"identificationDocumentNumber\":\"ZM-891821910AB\",\"identificationDocumentType\":\"Passport\"},\"clientPersonalData\":{\"birthDate\":\"1953-01-01\",\"birthName\":\"Charles Nchimunya\",\"citizenship\":\"ZM\",\"firstName\":\"Charles\",\"gender\":\"M\",\"lastName\":\"Nchimunya\",\"maritalStatus\":\"DS\"},\"embossedData\":{\"firstName\":\"Charles\",\"lastName\":\"Nchimunya\"}}";
        URI uri = URI.create(
                "https://sandbox.api.mastercard.com/global-processing/core/clients"
                 );
        String method = "POST";
        Charset charset = StandardCharsets.UTF_8;
        String authHeader = OAuth.getAuthorizationHeader(uri, method, payload, charset, consumerKey, getPrivateKey());
        return authHeader;
    }


    public String sign(Object object) throws UnrecoverableKeyException, CertificateException, IOException, KeyStoreException, NoSuchAlgorithmException, ApiException {
        ApiClient apiClient = Configuration.getDefaultApiClient();
        apiClient.setBasePath("https://sandbox.api.mastercard.com/global-processing/core/clients");
        //RequestBody.create((byte[]) obj, MediaType.parse(contentType));
        okhttp3.OkHttpClient http3Client = apiClient.getHttpClient();
        PrivateKey signingKey = AuthenticationUtils.loadSigningKey(signingKeyFilePath, signingKeyAlias, signingKeyPassword);
        apiClient.setHttpClient(
                apiClient.getHttpClient()
                        .newBuilder()
                        .addInterceptor(new OkHttpOAuth1Interceptor(consumerKey, signingKey))
                        .build()
        );
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        MediaType mediaType = MediaType.parse("application/json");
        System.out.println(objectMapper.writeValueAsString(object));
        RequestBody body = RequestBody.create(mediaType, objectMapper.writeValueAsString(object));
        //"{\n  \"clientNumber\": \"1\",\n  \"clientType\": \"PNR\",\n  \"clientCustomData\": [\n    {\n      \"tagContainer\": \"ADD_INFO_03\",\n      \"tagName\": \"-3F\",\n      \"removeTag\": \"false\",\n      \"tagValue\": \"1\"\n    },\n    {\n      \"tagContainer\": \"ADD_INFO_02\",\n      \"tagName\": \"Charles Nchimunya\",\n      \"removeTag\": \"false\",\n      \"tagValue\": \"1\"\n    }\n  ],\n  \"orderDepartment\": \"Smart Banking\",\n  \"serviceGroupCode\": \"Smart Banking\",\n  \"additionalDate01\": \"2026-01-01T02:49:25\",\n  \"additionalDate02\": \"2026-01-01T23:01:25\",\n  \"clientBaseAddressData\": {\n    \"addressLine1\": \"4 Lukanga Road\",\n    \"addressLine2\": \"Roma\",\n    \"addressLine3\": \"Lusaka\",\n    \"addressLine4\": \"Lusaka\",\n    \"city\": \"Lusaka\",\n    \"country\": \"ZMB\",\n    \"postalCode\": \"1111\",\n    \"state\": \"Lusaka\"\n  },\n  \"clientCompanyData\": {\n    \"companyDepartment\": \"SmartBanking\",\n    \"companyName\": \"Probase\",\n    \"companyTradeName\": \"Probase\",\n    \"position\": \"Customer\"\n  },\n  \"clientContactData\": {\n    \"email\": \"smicer66@gmail.com\",\n    \"fax\": \"08094073705\",\n    \"faxHome\": \"08094073705\",\n    \"phoneNumberHome\": \"08094073705\",\n    \"phoneNumberMobile\": \"08094073705\",\n    \"phoneNumberWork\": \"08094073705\"\n  },\n  \"clientIdentificationData\": {\n    \"identificationDocumentDetails\": \"1\",\n    \"identificationDocumentNumber\": \"ZM-891821910AB\",\n    \"identificationDocumentType\": \"Passport\",\n    \"socialNumber\": \"<string>\",\n    \"taxPosition\": \"<string>\",\n    \"taxpayerIdentifier\": \"<string>\"\n  },\n  \"clientPersonalData\": {\n    \"birthDate\": \"<date>\",\n    \"birthName\": \"<string>\",\n    \"birthPlace\": \"<string>\",\n    \"citizenship\": \"<string>\",\n    \"firstName\": \"<string>\",\n    \"gender\": \"N\",\n    \"language\": \"sn\",\n    \"lastName\": \"<string>\",\n    \"maritalStatus\": \"<string>\",\n    \"middleName\": \"<string>\",\n    \"secretPhrase\": \"<string>\",\n    \"shortName\": \"<string>\",\n    \"suffix\": \"<string>\",\n    \"title\": \"<string>\"\n  },\n  \"clientExpiryDate\": \"<date>\",\n  \"embossedData\": {\n    \"companyName\": \"<string>\",\n    \"firstName\": \"<string>\",\n    \"lastName\": \"<string>\",\n    \"title\": \"<string>\"\n  }\n}"


        Request request = new Request.Builder()
                .url("https://sandbox.api.mastercard.com/global-processing/core/clients")
                //.method("POST", body)
                .post(body)
                //.addHeader("Idempotency-Key", "<uuid>")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", "OAuth oauth_consumer_key=\"nVw7JfSOIHC8zrY7PrWT4759Kpow_i3jMN4HHPZH5ec9c2d6%216405d5d712a94ab192c6512a9ae1b8f60000000000000000\",oauth_signature_method=\"HMAC-SHA1\",oauth_timestamp=\"1767326161\",oauth_nonce=\"RdX0COkxkmu\",oauth_version=\"1.0\",oauth_signature=\"4DTuXTngwPcYa%2BfW0F0Y%2F9lI%2Fdk%3D\"")
                .build();

        //(String baseUrl, String path, String method, List<Pair> queryParams, List<Pair> collectionQueryParams, Object body, Map<String, String> headerParams, Map<String, String> cookieParams, Map<String, Object> formParams, String[] authNames, ApiCallback callback)

        Response response = apiClient.getHttpClient().newCall(request).execute();


        Buffer buffer = new Buffer();
        request.body().writeTo(buffer);
        String t =  buffer.readUtf8();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>");
        System.out.println(t);

        String responseData = new String(response.body().bytes(), StandardCharsets.UTF_8);
        System.out.println(response);
        System.out.println(responseData);
//        System.out.println(response.getData());

        return responseData;
    }

}
