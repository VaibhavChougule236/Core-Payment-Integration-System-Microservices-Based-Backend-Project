//package com.hulkhiretech.payments.trustly.security;
//
//import java.io.FileReader;
//import java.nio.charset.StandardCharsets;
//import java.security.KeyFactory;
//import java.security.KeyPair;
//import java.security.PrivateKey;
//import java.security.PublicKey;
//import java.security.Security;
//import java.security.Signature;
//import java.security.spec.X509EncodedKeySpec;
//import java.util.Base64;
//import java.util.Map;
//import java.util.TreeMap;
//
//import org.bouncycastle.jce.provider.BouncyCastleProvider;
//import org.bouncycastle.openssl.PEMEncryptedKeyPair;
//import org.bouncycastle.openssl.PEMKeyPair;
//import org.bouncycastle.openssl.PEMParser;
//import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
//import org.bouncycastle.openssl.jcajce.JcePEMDecryptorProviderBuilder;
//import org.springframework.stereotype.Component;
//
//import com.google.gson.Gson;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//
//import com.google.gson.JsonParser;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//public class RS256SignerVerifier {
//
//    private static final Gson gson = new Gson();
//    private static final Map<String, String> algorithmToPrefix = Map.of(
//            "SHA256withRSA", "alg=RS256;",
//            "SHA384withRSA", "alg=RS384;",
//            "SHA512withRSA", "alg=RS512;",
//            "SHA1withRSA", ""
//    );
//    private static final Map<String, String> prefixToAlgorithm = Map.of(
//            "alg=RS256;", "SHA256withRSA",
//            "alg=RS384;", "SHA384withRSA",
//            "alg=RS512;", "SHA512withRSA"
//    );
//
//    static {
//        Security.addProvider(new BouncyCastleProvider());
//    }
//
//    private static String sign(String method, String uuid, JsonElement data, PrivateKey privateKey, String algorithm) throws Exception {
//        String plaintext = method + uuid + serializeData(data);
//        
//        log.info("Signing plaintext: {}", plaintext);
//        
//        Signature signature = Signature.getInstance(algorithm, "BC");
//        signature.initSign(privateKey);
//        signature.update(plaintext.getBytes(StandardCharsets.UTF_8));
//        byte[] signed = signature.sign();
//        
//        String prefix = algorithmToPrefix.getOrDefault(algorithm, "");
//        prefix = "";// TODO, this is inline with the Trustly Mock service. When pointing to trustly system, remove this code.
//        return prefix + Base64.getEncoder().encodeToString(signed);
//    }
//
//    public static boolean verify(String method, String uuid, JsonElement data, String signatureStr, PublicKey publicKey) throws Exception {
//        String algorithm = "SHA1withRSA"; // default
//        if (signatureStr.startsWith("alg=RS")) {
//            String prefix = signatureStr.substring(0, 10);
//            algorithm = prefixToAlgorithm.getOrDefault(prefix, "SHA1withRSA");
//            signatureStr = signatureStr.substring(10);
//        }
//        byte[] decodedSig = Base64.getDecoder().decode(signatureStr);
//        String plaintext = method + uuid + serializeData(data);
//        Signature signature = Signature.getInstance(algorithm, "BC");
//        signature.initVerify(publicKey);
//        signature.update(plaintext.getBytes(StandardCharsets.UTF_8));
//        return signature.verify(decodedSig);
//    }
//
//    private static String serializeData(JsonElement element) {
//        if (element.isJsonPrimitive()) {
//            return element.getAsString();
//        } else if (element.isJsonArray()) {
//            StringBuilder sb = new StringBuilder();
//            for (JsonElement e : element.getAsJsonArray()) {
//                sb.append(serializeData(e));
//            }
//            return sb.toString();
//        } else if (element.isJsonObject()) {
//            JsonObject obj = element.getAsJsonObject();
//            TreeMap<String, JsonElement> sortedMap = new TreeMap<>();
//            for (Map.Entry<String, JsonElement> entry : obj.entrySet()) {
//                sortedMap.put(entry.getKey(), entry.getValue());
//            }
//            StringBuilder sb = new StringBuilder();
//            for (Map.Entry<String, JsonElement> entry : sortedMap.entrySet()) {
//                sb.append(entry.getKey()).append(serializeData(entry.getValue()));
//            }
//            return sb.toString();
//        }
//        return "";
//    }
//
//    public static PrivateKey loadPrivateKey(String pemPath) throws Exception {
//        try (PEMParser pemParser = new PEMParser(new FileReader(pemPath))) {
//            Object object = pemParser.readObject();
//
//            JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");
//
//            if (object instanceof PEMKeyPair keyPair) {
//                KeyPair kp = converter.getKeyPair(keyPair);
//                return kp.getPrivate();
//            }
//
//            if (object instanceof PEMEncryptedKeyPair encryptedKeyPair) {
//                // For encrypted keys, use password here if required
//                char[] password = "password".toCharArray();
//                PEMKeyPair decrypted = encryptedKeyPair.decryptKeyPair(
//                    new JcePEMDecryptorProviderBuilder().build(password));
//                KeyPair kp = converter.getKeyPair(decrypted);
//                return kp.getPrivate();
//            }
//
//            throw new IllegalArgumentException("Unsupported private key format.");
//        }
//    }
//
//    public static PublicKey loadPublicKey(String pemPath) throws Exception {
//        String key = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(pemPath)))
//                .replace("-----BEGIN PUBLIC KEY-----", "")
//                .replace("-----END PUBLIC KEY-----", "")
//                .replaceAll("\\s+", "");
//        byte[] decoded = Base64.getDecoder().decode(key);
//        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decoded);
//        KeyFactory keyFactory = KeyFactory.getInstance("RSA", "BC");
//        return keyFactory.generatePublic(keySpec);
//    }
//
//    // For test/demo
//    public static void main(String[] args) throws Exception {
//        String method = "Deposit";
//        String uuid = "abc-123";
//
//        String json = "{ \"Amount\": \"100\", \"Currency\": \"EUR\", \"Details\": { \"Account\": \"XYZ\", \"Name\": \"John\" } }";
//        JsonElement data = JsonParser.parseString(json);
//
//        PrivateKey privateKey = loadPrivateKey("./src/main/resources/merchant-private.pem");
//        PublicKey publicKey = loadPublicKey("./src/main/resources/merchant-public.pem");
//
//        String signature = sign(method, uuid, data, privateKey, "SHA256withRSA");
//        log.info("Signature: " + signature);
//
//        boolean isValid = verify(method, uuid, data, signature, publicKey);
//        log.info("Verified: " + isValid);
//        System.out.println("Is signature valid? " + isValid);
//    }
//
//	public static String sign(String method, String uuid, String json) {
//		try {
//			JsonElement data = JsonParser.parseString(json);
//			
//			PrivateKey privateKey = loadPrivateKey("./src/main/resources/merchant-private.pem");
//			
//			String signature = sign(method, uuid, data, privateKey, "SHA256withRSA");
//			log.info("Signature: " + signature);
//			
//			return signature;
//		} catch (Exception e) {
//			log.error("Error signing data: {}", e.getMessage(), e);
//			return null;
//		}
//	}
//    
//}


package com.hulkhiretech.payments.trustly.security;

import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Map;
import java.util.TreeMap;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMEncryptedKeyPair;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.openssl.jcajce.JcePEMDecryptorProviderBuilder;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RS256SignerVerifier {

    private static final Gson gson = new Gson();

    private static final Map<String, String> algorithmToPrefix = Map.of(
        "SHA256withRSA", "alg=RS256;",
        "SHA384withRSA", "alg=RS384;",
        "SHA512withRSA", "alg=RS512;",
        "SHA1withRSA", ""
    );

    private static final Map<String, String> prefixToAlgorithm = Map.of(
        "alg=RS256;", "SHA256withRSA",
        "alg=RS384;", "SHA384withRSA",
        "alg=RS512;", "SHA512withRSA"
    );

    // Toggle this based on environment
    private static final boolean IS_MOCK_ENV = false;

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * Signs the given data using the specified RSA algorithm and private key.
     */
    private static String sign(String method, String uuid, JsonElement data, PrivateKey privateKey, String algorithm) throws Exception {
        String plaintext = method + uuid + serializeData(data);
        log.info("Signing plaintext: {}", plaintext);

        Signature signature = Signature.getInstance(algorithm, "BC");
        signature.initSign(privateKey);
        signature.update(plaintext.getBytes(StandardCharsets.UTF_8));
        byte[] signedBytes = signature.sign();

        String prefix = IS_MOCK_ENV ? "" : algorithmToPrefix.getOrDefault(algorithm, "");
        prefix="";
        return prefix + Base64.getEncoder().encodeToString(signedBytes);
    }

    /**
     * Public method to sign JSON string input using default SHA256withRSA algorithm.
     */
    public static String sign(String method, String uuid, String json) {
        try {
            JsonElement data = JsonParser.parseString(json);
            PrivateKey privateKey = loadPrivateKey("./src/main/resources/merchant-private.pem");
            return sign(method, uuid, data, privateKey, "SHA256withRSA");
        } catch (Exception e) {
            log.error("Error signing data: {}", e.getMessage(), e);
            return null;
        }
    }

    /**
     * Verifies a signature using the provided public key.
     */
    public static boolean verify(String method, String uuid, JsonElement data, String signatureStr, PublicKey publicKey) throws Exception {
        String algorithm = "SHA1withRSA"; // Default (fallback)

        // Extract algorithm prefix if available
        if (signatureStr.startsWith("alg=RS")) {
            String prefix = signatureStr.substring(0, 10);
            algorithm = prefixToAlgorithm.getOrDefault(prefix, "SHA1withRSA");
            signatureStr = signatureStr.substring(10);
        } else if (!IS_MOCK_ENV) {
            log.warn("Missing algorithm prefix, using default: {}", algorithm);
        } else {
            algorithm = "SHA256withRSA"; // Trustly Mock behavior
        }

        byte[] decodedSignature = Base64.getDecoder().decode(signatureStr);
        String plaintext = method + uuid + serializeData(data);
        log.info("Verifying plaintext: {}", plaintext);
        log.info("Using algorithm: {}", algorithm);

        Signature signature = Signature.getInstance(algorithm, "BC");
        signature.initVerify(publicKey);
        signature.update(plaintext.getBytes(StandardCharsets.UTF_8));
        return signature.verify(decodedSignature);
    }

    /**
     * Deterministically serializes a JSON object to a flattened string with sorted keys.
     */
    private static String serializeData(JsonElement element) {
        if (element.isJsonPrimitive()) {
            return element.getAsString();
        } else if (element.isJsonArray()) {
            StringBuilder sb = new StringBuilder();
            for (JsonElement e : element.getAsJsonArray()) {
                sb.append(serializeData(e));
            }
            return sb.toString();
        } else if (element.isJsonObject()) {
            JsonObject obj = element.getAsJsonObject();
            TreeMap<String, JsonElement> sortedMap = new TreeMap<>();
            obj.entrySet().forEach(entry -> sortedMap.put(entry.getKey(), entry.getValue()));

            StringBuilder sb = new StringBuilder();
            sortedMap.forEach((key, value) -> sb.append(key).append(serializeData(value)));
            return sb.toString();
        }
        return "";
    }

    /**
     * Loads an RSA private key from a PEM file.
     */
    public static PrivateKey loadPrivateKey(String pemPath) throws Exception {
        try (PEMParser pemParser = new PEMParser(new FileReader(pemPath))) {
            Object object = pemParser.readObject();
            JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");

            if (object instanceof PEMKeyPair keyPair) {
                return converter.getKeyPair(keyPair).getPrivate();
            }

            if (object instanceof PEMEncryptedKeyPair encryptedKeyPair) {
                char[] password = "password".toCharArray(); // Load securely in real scenarios
                PEMKeyPair decrypted = encryptedKeyPair.decryptKeyPair(
                    new JcePEMDecryptorProviderBuilder().build(password));
                return converter.getKeyPair(decrypted).getPrivate();
            }

            throw new IllegalArgumentException("Unsupported private key format.");
        }
    }

    /**
     * Loads an RSA public key from a PEM file.
     */
    public static PublicKey loadPublicKey(String pemPath) throws Exception {
        String keyContent = new String(Files.readAllBytes(Paths.get(pemPath)))
            .replace("-----BEGIN PUBLIC KEY-----", "")
            .replace("-----END PUBLIC KEY-----", "")
            .replaceAll("\\s+", "");

        byte[] decoded = Base64.getDecoder().decode(keyContent);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
        return KeyFactory.getInstance("RSA", "BC").generatePublic(spec);
    }

    /**
     * Test runner / demo
     */
    public static void main(String[] args) throws Exception {
        String method = "Deposit";
        String uuid = "abc-123";
        String json = """
            {
                "Amount": "100",
                "Currency": "EUR",
                "Details": {
                    "Account": "XYZ",
                    "Name": "John"
                }
            }
        """;

        JsonElement data = JsonParser.parseString(json);

        PrivateKey privateKey = loadPrivateKey("./src/main/resources/merchant-private.pem");
        PublicKey publicKey = loadPublicKey("./src/main/resources/merchant-public.pem");

        String signature = sign(method, uuid, data, privateKey, "SHA256withRSA");
        log.info("Signature: {}", signature);

        boolean isValid = verify(method, uuid, data, signature, publicKey);
        log.info("Signature valid: {}", isValid);
        System.out.println("Signature valid? " + isValid);
    }
}

