package cn.izern.case4.encryption;

import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author: zern
 * @since 1.0.0
 */
public class AES {

  /**
   * 当前使用的加密算法
   */
  protected static final String ALGORITHM = "AES";


  /**
   * 生成aes密钥
   *
   * @param bit 位数 ，128,192,256
   * @return 密钥
   * @throws NoSuchAlgorithmException 加密算法找不到
   */
  public static byte[] genKey(int bit) {
    KeyGenerator kg;
    try {
      kg = KeyGenerator.getInstance(ALGORITHM);
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
    kg.init(bit);
    SecretKey sk = kg.generateKey();
    byte[] bytes = sk.getEncoded();
    return bytes;
  }


  /**
   * aes加密
   *
   * @param key 密钥
   * @param input 原文
   * @param algorithm 加密算法
   * @return 密文
   * @throws GeneralSecurityException 加密异常
   */
  public static byte[] encrypt(byte[] key, byte[] input, AESAlgorithm algorithm)
      throws GeneralSecurityException {
    Cipher cipher = Cipher.getInstance(algorithm.toString());
    SecretKeySpec keySpec = new SecretKeySpec(key, ALGORITHM);
    SecureRandom sr;
    byte[] iv = null;
    switch (algorithm.mode) {
      case ECB:
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        break;
      case GCM:
        sr = SecureRandom.getInstanceStrong();
        iv = sr.generateSeed(16);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, new GCMParameterSpec(128, iv));
        break;
      default:
        sr = SecureRandom.getInstanceStrong();
        iv = sr.generateSeed(16);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv));
    }

    byte[] data = cipher.doFinal(input);
    // IV不需要保密，把IV和密文一起返回:
    return join(iv, data);
  }

  /**
   * AES 解密
   *
   * @param key 密钥
   * @param input 密文
   * @param algorithm 加密算法
   * @return 原文
   * @throws GeneralSecurityException 加密异常
   */
  public static byte[] decrypt(byte[] key, byte[] input, AESAlgorithm algorithm)
      throws GeneralSecurityException {
    Cipher cipher = Cipher.getInstance(algorithm.toString());
    SecretKeySpec keySpec = new SecretKeySpec(key, ALGORITHM);

    byte[] iv = null;
    byte[] data;
    if (algorithm.hasIv) {
      // 把input分割成IV和密文， 和join逻辑相反
      iv = new byte[16];
      data = new byte[input.length - 16];
      System.arraycopy(input, 0, iv, 0, 16);
      System.arraycopy(input, 16, data, 0, data.length);
    } else {
      data = new byte[input.length];
      System.arraycopy(input, 0, data, 0, data.length);
    }
    switch (algorithm.mode) {
      case ECB:
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        break;
      case GCM:
        cipher.init(Cipher.DECRYPT_MODE, keySpec, new GCMParameterSpec(128, iv));
        break;
      default:
        cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv));
    }
    return cipher.doFinal(data);
  }


  /**
   * 此处可以自定义拼接的方式，可以放头部、尾部、也可以加自定义固定字符串标识加了IV
   *
   * @param bs1 字节1
   * @param bs2 字节2
   * @return 合并后的字节
   */
  public static byte[] join(byte[] bs1, byte[] bs2) {
    if (bs1 == null) {
      return bs2;
    }
    if (bs2 == null) {
      return bs1;
    }
    if (bs1 == null && bs2 == null) {
      return null;
    }
    byte[] r = new byte[bs1.length + bs2.length];
    System.arraycopy(bs1, 0, r, 0, bs1.length);
    System.arraycopy(bs2, 0, r, bs1.length, bs2.length);
    return r;
  }

  /**
   * <p>aes 加密算法</p>
   *
   * @author: zern
   * @see <a href="https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#Cipher">java8
   * guides</a>
   * @since 1.0.0
   */
  public enum AESAlgorithm {

    AES_ECB_NoPadding(AESMode.ECB, Padding.NoPadding, false),
    AES_ECB_PKCS5Padding(AESMode.ECB, Padding.PKCS5Padding, false),
    AES_ECB_PKCS7Padding(AESMode.ECB, Padding.PKCS7Padding, false),

    //    AES_CBC_NoPadding(AESMode.CBC, Padding.NoPadding, true),
    AES_CBC_PKCS5Padding(AESMode.CBC, Padding.PKCS5Padding, true),
    AES_CBC_PKCS7Padding(AESMode.CBC, Padding.PKCS7Padding, true),


    AES_CTR_NoPadding(AESMode.CTR, Padding.NoPadding, true),
    AES_CTR_PKCS5Padding(AESMode.CTR, Padding.PKCS5Padding, true),
    AES_CTR_PKCS7Padding(AESMode.CTR, Padding.PKCS7Padding, true),

    AES_GCM_NoPadding(AESMode.GCM, Padding.NoPadding, true),
    AES_GCM_PKCS5Padding(AESMode.GCM, Padding.PKCS5Padding, true),
//    AES_GCM_PKCS7Padding(AESMode.GCM, Padding.PKCS7Padding, true),

    AES_PCBC_NoPadding(AESMode.PCBC, Padding.NoPadding, true),
    AES_PCBC_PKCS5Padding(AESMode.PCBC, Padding.PKCS5Padding, true);
//    AES_PCBC_PKCS7Padding(AESMode.PCBC, Padding.PKCS7Padding, true);

    private AESMode mode;
    private Padding padding;
    private boolean hasIv;

    AESAlgorithm(AESMode mode, Padding padding, boolean hasIv) {
      this.mode = mode;
      this.padding = padding;
      this.hasIv = hasIv;
    }

    @Override
    public String toString() {
      return String.format("%s/%s/%s", "AES", mode.toString(), padding.toString());
    }
  }

  /**
   * <p>aes 加密模式</p>
   *
   * @author: zern
   * @see <a href="https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#Cipher">java8
   * guides</a>
   * @since 1.0.0
   */
  public enum AESMode {
    ECB, CBC, CTR, PCBC, GCM
  }

  /**
   * <p>aes 填充对齐</p>
   *
   * @author: zern
   * @see <a href="https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#Cipher">java8
   * guides</a>
   * @since 1.0.0
   */
  public enum Padding {
    NoPadding, PKCS5Padding, PKCS7Padding
  }


}
