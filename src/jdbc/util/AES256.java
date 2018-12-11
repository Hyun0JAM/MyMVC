/*
	 ?? Í¥??†®?ö©?ñ¥ ??
	 - ?ïî?ò∏(Cryptography) : ?ï¥?èÖ Î∂àÍ??ä•?ïú ?òï?ÉúÎ°? Î≥??ôò?ïòÍ±∞ÎÇò ?òê?äî ?ïî?ò∏?ôî?êú Î©îÏãúÏß?Î•? ?ï¥?èÖ Í∞??ä•?ïú ?òï?ÉúÎ°? Î≥??ôò?ïò?äî Í∏∞Ïà†
	 - ?èâÎ¨?(Plaintext)    : ?ï¥?èÖ Í∞??ä•?ïú ?òï?Éú?ùò Î©îÏãúÏß?
	 - ?ïî?ò∏Î¨?(Ciphertext) : ?ï¥?èÖ Î∂àÍ??ä•?ïú ?òï?Éú?ùò Î©îÏãúÏß?
	 - ?ïî?ò∏?ôî(Encryption) : ?èâÎ¨∏ÏùÑ ?ïî?ò∏Î¨∏ÏúºÎ°? Î≥??ôò?ïò?äî Í≥ºÏ†ï
	 - Î≥µÌò∏?ôî(Decryption) : ?ïî?ò∏Î¨∏ÏùÑ ?èâÎ¨∏ÏúºÎ°? Î≥??ôò?ïò?äî Í≥ºÏ†ï
	 - ??Ïπ??Ç§ ?ïî?ò∏(?òê?äî ÎπÑÎ??Ç§ ?ïî?ò∏) : ?ïî?ò∏?ôî?Ç§?? Î≥µÌò∏?ôî?Ç§Í∞? Í∞ôÏ? ?ïî?ò∏
	 - ÎπÑÎ?Ïπ??Ç§ ?ïî?ò∏(?òê?äî Í≥µÍ∞ú?Ç§ ?ïî?ò∏) : ?ïî?ò∏?ôî?Ç§?? Î≥µÌò∏?ôî?Ç§Í∞? ?ã§Î•? ?ïî?ò∏
	
	
	?? ?ñëÎ∞©Ìñ• ?ïî?ò∏?ôî ?ïåÍ≥†Î¶¨Ï¶?(AES-256 Advanced Encryption Standard) ??
	
	  ?ñëÎ∞©Ìñ• ?ïî?ò∏?ôî ?ïåÍ≥†Î¶¨Ï¶òÏ? ?èâÎ¨∏Ïóê?Ñú ?ïî?ò∏Î¨∏ÏúºÎ°?, ?ïî?ò∏Î¨∏Ïóê?Ñú ?èâÎ¨∏ÏúºÎ°? Î≥??ôò?ïò?äî ?ïî?ò∏?ôî Î∞? Î≥µÌò∏?ôîÍ∞? ?ù¥Î£®Ïñ¥Ïß??äî ?ïåÍ≥†Î¶¨Ï¶? ?ù¥?ã§. 
	  ÎßéÏù¥ ?Ç¨?ö©?ïò?äî ?ïåÍ≥†Î¶¨Ï¶òÏ? AES-256 ?ûÖ?ãà?ã§. 
	  Ï£ºÎ°ú ?ù¥Î¶?, Ï£ºÏÜå, ?ó∞?ùΩÏ≤? ?ì± Î≥µÌò∏?ôî ?ïò?äî?ç∞ ?ïÑ?öî?ïú ?†ïÎ≥¥Î?? ?ù¥ ?ïåÍ≥†Î¶¨Ï¶òÏùÑ ?ù¥?ö©?ï¥?Ñú ?ïî?ò∏Î¨∏ÏúºÎ°? Í¥?Î¶¨Ìïú?ã§.
	 
	 ?ª‚?ª‚??  Java Î•? ?ù¥?ö©?ïú Íµ¨ÌòÑ  ?ª‚?ª‚??
	 == Í∞úÎ∞ú?†Ñ Ï§?ÎπÑÎã®Í≥? ==
	  Í∏∞Î≥∏?úºÎ°? ?†úÍ≥µÌïò?äî JDKÎ•? ?Ñ§ÏπòÌïòÎ©? ?ïî?ò∏ ?ïåÍ≥†Î¶¨Ï¶òÏùÑ ÎßåÎì§ ?àò ?ûà?äî APIÍ∞? ?†úÍ≥µÎêòÏß?Îß?, 
	 AES-128 Î≥¥Îã§ ?ïú?ã®Í≥? ?çî ?Üí?? ?ã®Í≥ÑÏù∏ AES-256?ùÑ Íµ¨ÌòÑ?ïòÍ∏? ?úÑ?ï¥?Ñú?äî Î≥ÑÎèÑ?ùò ?ùº?ù¥Î∏åÎü¨Î¶? ?ôï?û• ?åå?ùº?ù¥ ?ïÑ?öî?ïò?ã§.
	 ?ò§?ùº?Å¥?Ç¨ ?ôà?éò?ù¥Ïß??ùò JDK ?ã§?ö¥Î°úÎìú ?éò?ù¥Ïß??óê Í∞?Î©? ?ïÑ?ûòÏ≤òÎüº JCE Î•? ?ã§?ö¥Î∞õÎäî?ã§.
	  ?ûê?ã†?ùò JRE Î≤ÑÏ†Ñ?óê ÎßûÎäî ?ï¥?ãπ ?åå?ùº?ùÑ ?ã§?ö¥Î°úÎìú Î∞õÏïÑ?Ñú ?ïïÏ∂ïÏùÑ ?ëº ?õÑ local_policy.jar ?åå?ùºÍ≥? US_export_policy.jar ?åå?ùº?ùÑ
	 JDK?Ñ§ÏπòÍ≤ΩÎ°?\jre\lib\security ??  JRE?Ñ§ÏπòÍ≤ΩÎ°?\lib\security ?óê 
	 local_policy.jar ?åå?ùºÍ≥? US_export_policy.jar ?ëêÍ∞? ?åå?ùº?ùÑ Î™®Îëê Î∂ôÏó¨?Ñ£Í∏∞Î?? ?ïò?ó¨ ?çÆ?ñ¥?ì¥?ã§.
	 (Linux Í≥ÑÏó¥?óê?äî JDK?Ñ§ÏπòÍ≤ΩÎ°úÏóêÎß? ?Ñ£?ñ¥Ï£ºÎ©¥ ?ï¥Í≤∞Îê®)
	 (JDK ?Ñ§Ïπ? Í≤ΩÎ°úÎ•? Î™®Î•¥Î©? ?Ç¥Ïª¥Ìì®?Ñ∞ ?ö∞?Å¥Î¶? > ?Üç?Ñ± > Í≥†Í∏â ?ãú?ä§?Öú ?Ñ§?†ï > ?ôòÍ≤ΩÎ??àò > JAVA_HOME?ùÑ Ï∞æÎäî?ã§)
	  Í∑∏Îü∞ ?ã§?ùå?óê WAS(?Ü∞Ï∫?)Î•? ?û¨Íµ¨Îèô?ïú?ã§. 
	  
	 https://mvnrepository.com/artifact/commons-codec/commons-codec ?óê Í∞??Ñú
	 ?ó¨?ü¨Í∞?Ïß? Î≤ÑÏ†Ñ?ù¥ ?ûà?äî?ç∞ 1.11 ?óê ?ì§?ñ¥Í∞??Ñú jar(327 KB)Î•? ?Å¥Î¶??ïò?ó¨ ?ã§?ö¥?ùÑ Î∞õÎäî?ã§. 
	 ?ã§?ö¥Î∞õÏ? ?åå?ùºÎ™ÖÏ? commons-codec-1.11.jar ?ù∏?ç∞ ?ù¥ ?åå?ùº?ùÑ C:\Program Files\Java\jdk1.8.0_112\jre\lib\ext Í≤ΩÎ°ú?óê Î∂ôÏó¨?ëî?ã§.
	 ?ï¥?ãπ ?îÑÎ°úÏ†ù?ä∏?ùò Build Path ?óê Í∞??Ñú Libraies ?É≠?óê?Ñú Add External JARs.. Î•? ?Å¥Î¶??ïò?ó¨ commons-codec-1.11.jar ?åå?ùº?ùÑ ÏßÅÏ†ë ?ò¨?†§?ëî?ã§. 
	   
	
	 >>> JDK Î≤ÑÏ†ÑÎ≥? Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy Files ?ã§?ö¥Î°úÎìú Í≤ΩÎ°ú <<<
	 jdk8 (Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy Files 8)
	 https://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html
	
	 jdk7 (Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy Files 7)
	 http://www.oracle.com/technetwork/java/javase/downloads/jce-7-download-432124.html
	 
	
	 jdk6 (Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy Files 6)
	 http://www.oracle.com/technetwork/java/javase/downloads/jce-6-download-429243.html  
*/

package jdbc.util;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * ?ñëÎ∞©Ìñ• ?ïî?ò∏?ôî ?ïåÍ≥†Î¶¨Ï¶òÏù∏ AES256 ?ïî?ò∏?ôîÎ•? Ïß??õê?ïò?äî ?Å¥?ûò?ä§
 */
public class AES256 {
    private String iv;
    private Key keySpec;

    /** 
     * ?Éù?Ñ±?ûê
     * 16?ûêÎ¶¨Ïùò ?Ç§Í∞íÏùÑ ?ûÖ?†•?ïò?ó¨ Í∞ùÏ≤¥Î•? ?Éù?Ñ±?ïú?ã§.
     * @param key ?ïî?ò∏?ôî/Î≥µÌò∏?ôîÎ•? ?úÑ?ïú ?Ç§Í∞?
     * @throws UnsupportedEncodingException ?Ç§Í∞íÏùò Í∏∏Ïù¥Í∞? 16?ù¥?ïò?ùº Í≤ΩÏö∞ Î∞úÏÉù
     */
    public AES256(String key) throws UnsupportedEncodingException {
        this.iv = key.substring(0, 16);
        byte[] keyBytes = new byte[16];
        byte[] b = key.getBytes("UTF-8");
        int len = b.length;
        if(len > keyBytes.length){
            len = keyBytes.length;
        }
        System.arraycopy(b, 0, keyBytes, 0, len);
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

        this.keySpec = keySpec;
    }

    /**
     * AES256 ?úºÎ°? ?ïî?ò∏?ôî ?ïú?ã§.
     * @param str ?ïî?ò∏?ôî?ï† Î¨∏Ïûê?ó¥
     * @return String
     * @throws NoSuchAlgorithmException
     * @throws GeneralSecurityException
     * @throws UnsupportedEncodingException
     */
    public String encrypt(String str) throws NoSuchAlgorithmException, GeneralSecurityException, UnsupportedEncodingException{
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        c.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));
        byte[] encrypted = c.doFinal(str.getBytes("UTF-8"));
        String enStr = new String(Base64.encodeBase64(encrypted));
        return enStr;
    }

    /**
     * AES256?úºÎ°? ?ïî?ò∏?ôî?êú txt Î•? Î≥µÌò∏?ôî?ïú?ã§.
     * @param str Î≥µÌò∏?ôî?ï† Î¨∏Ïûê?ó¥
     * @return
     * @throws NoSuchAlgorithmException
     * @throws GeneralSecurityException
     * @throws UnsupportedEncodingException
     */
    public String decrypt(String str) throws NoSuchAlgorithmException, GeneralSecurityException, UnsupportedEncodingException {
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        c.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));
        byte[] byteStr = Base64.decodeBase64(str.getBytes());
        return new String(c.doFinal(byteStr), "UTF-8");
    }

}// end of class AES256///////////////////////////////////////
