package cn.izern.case4.encryption;

import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

/**
 * rsa 非对称密钥使用api
 *
 * @author: zern
 * @since 1.0.0
 */
public class RSA {


  /**
   * 当前使用的加密算法
   */
  protected static final String ALGORITHM = "RSA";

  /**
   * 生成rsa公私钥
   *
   * @param bit 位数 1024 2048 3072 4096
   * @return rsa公私钥对
   */
  public static KeyPair keyGen(int bit) {
    KeyPairGenerator kpGen = null;
    try {
      kpGen = KeyPairGenerator.getInstance(ALGORITHM);
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    kpGen.initialize(bit);
    KeyPair kp = kpGen.generateKeyPair();
    return kp;
  }

  /**
   * 将字节数组解析为rsa公钥
   *
   * @param pkBytes 公钥字节数组，可以从keyPair.getPublic().getEncoded()获得
   * @return 公钥
   * @throws InvalidKeyException 转换异常，一般是格式错误
   */
  public static PublicKey parsePk(byte[] pkBytes) throws GeneralSecurityException {
    KeyFactory kf = KeyFactory.getInstance(ALGORITHM);
    X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(pkBytes);
    PublicKey pk = kf.generatePublic(x509KeySpec);
    return pk;

  }

  /**
   * 字节流转为私钥
   *
   * @param skBytes 私钥字节流，可以从keyPair.getPrivate().getEncoded()获得
   * @return 私钥
   * @throws GeneralSecurityException 转换异常，一般是格式错误
   */
  public static PrivateKey parseSk(byte[] skBytes) throws GeneralSecurityException {
    KeyFactory kf = KeyFactory.getInstance(ALGORITHM);
    PKCS8EncodedKeySpec skSpec = new PKCS8EncodedKeySpec(skBytes);
    PrivateKey sk = kf.generatePrivate(skSpec);
    return sk;
  }


  /**
   * 公钥加密
   *
   * @param src 原文
   * @param pk 公钥
   * @return 密文
   * @throws GeneralSecurityException 加密异常
   */
  public static byte[] encrypt(byte[] src, PublicKey pk) throws GeneralSecurityException {
    Cipher cipher = Cipher.getInstance("RSA");
    cipher.init(Cipher.ENCRYPT_MODE, pk);
    return cipher.doFinal(src);
  }

  /**
   * 私钥解密
   *
   * @param input 密文
   * @param sk 私钥
   * @return 原文
   * @throws GeneralSecurityException 解密异常
   */
  public static byte[] decrypt(byte[] input, PrivateKey sk) throws GeneralSecurityException {
    Cipher cipher = Cipher.getInstance(ALGORITHM);
    cipher.init(Cipher.DECRYPT_MODE, sk);
    return cipher.doFinal(input);
  }

  /**
   * 私钥签名
   *
   * @param src 原文
   * @param sk 私钥
   * @param signAlgorithms 签名算法
   * @return 签名
   * @throws GeneralSecurityException 签名异常
   */
  public static byte[] sign(byte[] src, PrivateKey sk, RSASignAlgorithms signAlgorithms)
      throws GeneralSecurityException {
    Signature signature = Signature.getInstance(signAlgorithms.toString());
    signature.initSign(sk);
    signature.update(src);
    return signature.sign();
  }

  /**
   * 私钥签名
   *
   * @param src 原文
   * @param sk 私钥
   * @return 签名
   * @throws GeneralSecurityException 签名异常
   */
  public static byte[] sign(byte[] src, PrivateKey sk) throws GeneralSecurityException {
    return sign(src, sk, RSASignAlgorithms.SHA256withRSA);
  }

  /**
   * 公钥验签
   *
   * @param src 原文
   * @param sign 签名
   * @param pk 公钥
   * @param signAlgorithms 签名算法
   * @return 验证是否是公钥对应的私钥签名
   * @throws GeneralSecurityException 签名异常
   */
  public static boolean verifySign(byte[] src, byte[] sign, PublicKey pk,
      RSASignAlgorithms signAlgorithms)
      throws GeneralSecurityException {
    Signature signature = Signature.getInstance(signAlgorithms.toString());
    signature.initVerify(pk);
    signature.update(src);
    return signature.verify(sign);
  }

  /**
   * 公钥验签
   *
   * @param src 原文
   * @param sign 签名
   * @param pk 公钥
   * @return 验证是否是公钥对应的私钥签名
   * @throws GeneralSecurityException 签名异常
   */
  public static boolean verifySign(byte[] src, byte[] sign, PublicKey pk)
      throws GeneralSecurityException {
    return verifySign(src, sign, pk, RSASignAlgorithms.SHA256withRSA);
  }


  /**
   * rsa签名算法
   *
   * @author: zern
   * @since 1.0.0
   */
  public enum RSASignAlgorithms {

    @Deprecated
    NONEwithRSA,
    @Deprecated
    MD2withRSA,
    MD5withRSA,
    SHA1withRSA,
    SHA224withRSA,
    SHA256withRSA,
    SHA384withRSA,
    SHA512withRSA,
  }

}
