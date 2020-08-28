package cn.izern.case4.encryption;

import cn.izern.case4.encryption.codec.Coder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 各种摘要算法
 *
 * @author: zern
 * @since 1.0.0
 */
public class Digest {

  /**
   * <p>摘要算法枚举</p>
   *
   * @see <a href="https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#MessageDigest">java8
   * guides</a>
   */
  public enum DigestAlgorithm {

    /**
     * md5
     */
    MD5("MD5"),
    /**
     * sha-1
     */
    SHA1("SHA-1"),
    /**
     * sha-224
     */
    SHA224("SHA-224"),
    /**
     * sha-256
     */
    SHA256("SHA-256"),
    /**
     * sha-384
     */
    SHA384("SHA-384"),
    /**
     * sha-512
     */
    SHA512("SHA-512");

    private String name;

    DigestAlgorithm(String name) {
      this.name = name;
    }

    @Override
    public String toString() {
      return name;
    }
  }


  /**
   * 计算摘要
   *
   * @param src 源字符串
   * @param digestAlgorithm 摘要算法
   * @return 摘要
   */
  public static byte[] digest(String src, DigestAlgorithm digestAlgorithm) {
    if (src == null) {
      return null;
    }
    MessageDigest md = null;
    try {
      md = MessageDigest.getInstance(digestAlgorithm.toString());
      md.update(src.getBytes(Constant.DEFAULT_CHARSET));
      return md.digest();
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }

  }

  /**
   * 计算摘要
   *
   * @param src 源字符串
   * @param digestAlgorithm 摘要算法
   * @param resultCodec 摘要结果转字符串编码方式
   * @return 摘要
   */
  public static String digest(String src, DigestAlgorithm digestAlgorithm, Coder resultCodec) {
    byte[] digest = digest(src, digestAlgorithm);
    return resultCodec.encodeString(digest);
  }

  /**
   * md5 摘要
   *
   * @param src 源字符串
   * @param resultCodec 摘要结果转字符串编码方式
   * @return 摘要
   */
  public String md5(String src, Coder resultCodec) {
    return digest(src, DigestAlgorithm.MD5, resultCodec);
  }

  /**
   * sha256 摘要
   *
   * @param src 源字符串
   * @param coder 摘要结果转字符串编码方式
   * @return 摘要
   */
  public String sha256(String src, Coder coder) {
    return digest(src, DigestAlgorithm.SHA256, coder);
  }

}
