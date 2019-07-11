package sample;
import java.security.MessageDigest;

/**
 * Class generates hash of string value using SHA-256 encryption
 */
public class Hash {
    /**
     * generates SHA-256 of input string
     * @param value of which hash is to be return
     * @return hash of input string
     */
    public static String getSha256(String value) {
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(value.getBytes());
            return bytesToHex(md.digest());
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }
    private static String bytesToHex(byte[] bytes) {
        StringBuffer result = new StringBuffer();
        for (byte b : bytes) result.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        return result.toString();
    }
}