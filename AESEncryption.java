import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AESEncryption {

    public static String encryptAES256(String key, String message) throws Exception {
        // Make sure the key is 32 bytes long
        key = key.substring(0, 32);

        // Pad the message to a multiple of 16 bytes
        int paddingLength = 16 - message.length() % 16;
        StringBuilder padding = new StringBuilder();
        for (int i = 0; i < paddingLength; i++) {
            padding.append((char) paddingLength);
        }
        String paddedMessage = message + padding.toString();

        // Create the AES cipher object
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);

        // Encrypt the message
        byte[] encryptedMessage = cipher.doFinal(paddedMessage.getBytes());

        // Encode the encrypted message in base64
        String encodedMessage = Base64.getEncoder().encodeToString(encryptedMessage);

        // Return the encoded message as a string
        return encodedMessage;
    }

    public static String decryptAES256(String key, String encodedMessage) throws Exception {
        // Make sure the key is 32 bytes long
        key = key.substring(0, 32);

        // Decode the base64-encoded message
        byte[] encryptedMessage = Base64.getDecoder().decode(encodedMessage);

        // Create the AES cipher object
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec);

        // Decrypt the message
        byte[] decryptedMessage = cipher.doFinal(encryptedMessage);

        // Remove the padding from the decrypted message
        int paddingLength = decryptedMessage[decryptedMessage.length - 1];
        String unpaddedMessage = new String(decryptedMessage, 0, decryptedMessage.length - paddingLength);

        // Return the decrypted message as a string
        return unpaddedMessage;
    }

    public static void main(String[] args) {
        // Test the encryption and decryption functions
        String key = "mysecretkey";
        String message = "This is a secret message!";

        try {
            String encryptedMessage = encryptAES256(key, message);
            System.out.println("Encrypted message: " + encryptedMessage);

            String decryptedMessage = decryptAES256(key, encryptedMessage);
            System.out.println("Decrypted message: " + decryptedMessage);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
