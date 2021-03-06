/*
	 ?? ๊ด?? จ?ฉ?ด ??
	 - ??ธ(Cryptography) : ?ด? ๋ถ๊??ฅ? ??๋ก? ๋ณ???๊ฑฐ๋ ?? ??ธ?? ๋ฉ์์ง?๋ฅ? ?ด? ๊ฐ??ฅ? ??๋ก? ๋ณ???? ๊ธฐ์ 
	 - ?๋ฌ?(Plaintext)    : ?ด? ๊ฐ??ฅ? ??? ๋ฉ์์ง?
	 - ??ธ๋ฌ?(Ciphertext) : ?ด? ๋ถ๊??ฅ? ??? ๋ฉ์์ง?
	 - ??ธ?(Encryption) : ?๋ฌธ์ ??ธ๋ฌธ์ผ๋ก? ๋ณ???? ๊ณผ์ 
	 - ๋ณตํธ?(Decryption) : ??ธ๋ฌธ์ ?๋ฌธ์ผ๋ก? ๋ณ???? ๊ณผ์ 
	 - ??์น??ค ??ธ(?? ๋น๋??ค ??ธ) : ??ธ??ค?? ๋ณตํธ??ค๊ฐ? ๊ฐ์? ??ธ
	 - ๋น๋?์น??ค ??ธ(?? ๊ณต๊ฐ?ค ??ธ) : ??ธ??ค?? ๋ณตํธ??ค๊ฐ? ?ค๋ฅ? ??ธ
	
	
	?? ?๋ฐฉํฅ ??ธ? ?๊ณ ๋ฆฌ์ฆ?(AES-256 Advanced Encryption Standard) ??
	
	  ?๋ฐฉํฅ ??ธ? ?๊ณ ๋ฆฌ์ฆ์? ?๋ฌธ์? ??ธ๋ฌธ์ผ๋ก?, ??ธ๋ฌธ์? ?๋ฌธ์ผ๋ก? ๋ณ???? ??ธ? ๋ฐ? ๋ณตํธ?๊ฐ? ?ด๋ฃจ์ด์ง?? ?๊ณ ๋ฆฌ์ฆ? ?ด?ค. 
	  ๋ง์ด ?ฌ?ฉ?? ?๊ณ ๋ฆฌ์ฆ์? AES-256 ???ค. 
	  ์ฃผ๋ก ?ด๋ฆ?, ์ฃผ์, ?ฐ?ฝ์ฒ? ?ฑ ๋ณตํธ? ???ฐ ??? ? ๋ณด๋?? ?ด ?๊ณ ๋ฆฌ์ฆ์ ?ด?ฉ?ด? ??ธ๋ฌธ์ผ๋ก? ๊ด?๋ฆฌํ?ค.
	 
	 ?ปโ?ปโ??  Java ๋ฅ? ?ด?ฉ? ๊ตฌํ  ?ปโ?ปโ??
	 == ๊ฐ๋ฐ?  ์ค?๋น๋จ๊ณ? ==
	  ๊ธฐ๋ณธ?ผ๋ก? ? ๊ณตํ? JDK๋ฅ? ?ค์นํ๋ฉ? ??ธ ?๊ณ ๋ฆฌ์ฆ์ ๋ง๋ค ? ?? API๊ฐ? ? ๊ณต๋์ง?๋ง?, 
	 AES-128 ๋ณด๋ค ??จ๊ณ? ? ??? ?จ๊ณ์ธ AES-256? ๊ตฌํ?๊ธ? ??ด?? ๋ณ๋? ?ผ?ด๋ธ๋ฌ๋ฆ? ??ฅ ??ผ?ด ????ค.
	 ?ค?ผ?ด?ฌ ???ด์ง?? JDK ?ค?ด๋ก๋ ??ด์ง?? ๊ฐ?๋ฉ? ??์ฒ๋ผ JCE ๋ฅ? ?ค?ด๋ฐ๋?ค.
	  ?? ? JRE ๋ฒ์ ? ๋ง๋ ?ด?น ??ผ? ?ค?ด๋ก๋ ๋ฐ์? ?์ถ์ ?ผ ? local_policy.jar ??ผ๊ณ? US_export_policy.jar ??ผ?
	 JDK?ค์น๊ฒฝ๋ก?\jre\lib\security ??  JRE?ค์น๊ฒฝ๋ก?\lib\security ? 
	 local_policy.jar ??ผ๊ณ? US_export_policy.jar ?๊ฐ? ??ผ? ๋ชจ๋ ๋ถ์ฌ?ฃ๊ธฐ๋?? ??ฌ ?ฎ?ด?ด?ค.
	 (Linux ๊ณ์ด?? JDK?ค์น๊ฒฝ๋ก์๋ง? ?ฃ?ด์ฃผ๋ฉด ?ด๊ฒฐ๋จ)
	 (JDK ?ค์น? ๊ฒฝ๋ก๋ฅ? ๋ชจ๋ฅด๋ฉ? ?ด์ปดํจ?ฐ ?ฐ?ด๋ฆ? > ??ฑ > ๊ณ ๊ธ ??ค? ?ค?  > ?๊ฒฝ๋?? > JAVA_HOME? ์ฐพ๋?ค)
	  ๊ทธ๋ฐ ?ค?? WAS(?ฐ์บ?)๋ฅ? ?ฌ๊ตฌ๋??ค. 
	  
	 https://mvnrepository.com/artifact/commons-codec/commons-codec ? ๊ฐ??
	 ?ฌ?ฌ๊ฐ?์ง? ๋ฒ์ ?ด ???ฐ 1.11 ? ?ค?ด๊ฐ?? jar(327 KB)๋ฅ? ?ด๋ฆ???ฌ ?ค?ด? ๋ฐ๋?ค. 
	 ?ค?ด๋ฐ์? ??ผ๋ช์? commons-codec-1.11.jar ?ธ?ฐ ?ด ??ผ? C:\Program Files\Java\jdk1.8.0_112\jre\lib\ext ๊ฒฝ๋ก? ๋ถ์ฌ??ค.
	 ?ด?น ?๋ก์ ?ธ? Build Path ? ๊ฐ?? Libraies ?ญ?? Add External JARs.. ๋ฅ? ?ด๋ฆ???ฌ commons-codec-1.11.jar ??ผ? ์ง์  ?ฌ? ค??ค. 
	   
	
	 >>> JDK ๋ฒ์ ๋ณ? Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy Files ?ค?ด๋ก๋ ๊ฒฝ๋ก <<<
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
 * ?๋ฐฉํฅ ??ธ? ?๊ณ ๋ฆฌ์ฆ์ธ AES256 ??ธ?๋ฅ? ์ง???? ?ด??ค
 */
public class AES256 {
    private String iv;
    private Key keySpec;

    /** 
     * ??ฑ?
     * 16?๋ฆฌ์ ?ค๊ฐ์ ?? ฅ??ฌ ๊ฐ์ฒด๋ฅ? ??ฑ??ค.
     * @param key ??ธ?/๋ณตํธ?๋ฅ? ?? ?ค๊ฐ?
     * @throws UnsupportedEncodingException ?ค๊ฐ์ ๊ธธ์ด๊ฐ? 16?ด??ผ ๊ฒฝ์ฐ ๋ฐ์
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
     * AES256 ?ผ๋ก? ??ธ? ??ค.
     * @param str ??ธ??  ๋ฌธ์?ด
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
     * AES256?ผ๋ก? ??ธ?? txt ๋ฅ? ๋ณตํธ???ค.
     * @param str ๋ณตํธ??  ๋ฌธ์?ด
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
