package cn.izern.case4.encryption;

import cn.izern.case4.encryption.AES.AESAlgorithm;
import cn.izern.case4.encryption.codec.HexCoder;
import cn.izern.case4.encryption.codec.PlainStringCoder;
import cn.izern.case4.encryption.exception.CodecException;
import java.security.Security;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author: zern
 * @since 1.0.0
 */
public class AESTest {

  private int[] bits = new int[]{128, 192, 256};

  private String src = "go blog.izern.cn";

  @Before
  public void before() {
    // AES 加密算法JDK提供不全， 可以找第三方包提供
    Security.addProvider(new BouncyCastleProvider());
  }


  @Test
  public void keyGen() throws CodecException {
    for (int bit : bits) {
      byte[] bytes = AES.genKey(bit);
      Assert.assertFalse(bytes.length == bit);
      System.out.println(HexCoder.INSTANCE.encodeString(bytes));
    }
  }

  @Test
  public void aes() throws Exception {
    byte[] srcBytes = PlainStringCoder.INSTANCE.decodeString(src);
    for (int bit : bits) {
      byte[] key = AES.genKey(bit);
      AESAlgorithm[] aesAlgorithms = AESAlgorithm.values();
      for (AESAlgorithm algorithm : aesAlgorithms) {
        byte[] encrypt = AES.encrypt(key, srcBytes, algorithm);
        byte[] decrypt = AES.decrypt(key, encrypt, algorithm);
        String decryptSrc = PlainStringCoder.INSTANCE.encodeString(decrypt);
        Assert.assertEquals(String.format("解密数据不相符，当前加密算法为：%s, 加密位数为：%d", algorithm, bit),
            src, decryptSrc);
        System.out.println(algorithm + " done. ");
      }
      System.out.println(bit + " bit password test done.");
    }
  }

  /**
   * 使用其它开发语言或工具验证
   * <li>1. 加密结果是否一致</li>
   * <li>2. 没有IV的，可以尝试解密查看是否可以得到原文</li>
   * 如openssl加密: echo -n 'blog.izern.cn ' | openssl enc -aes-128-ecb -K ${key} -nosalt -nopad |
   * xxd  -C
   */
  @Test
  public void aes2() throws Exception {
    byte[] srcBytes = PlainStringCoder.INSTANCE.decodeString(src);
    byte[] key = AES.genKey(128);
    AESAlgorithm[] aesAlgorithms = AESAlgorithm.values();
    System.out.println("key: " + HexCoder.INSTANCE.encodeString(key));
    for (AESAlgorithm algorithm : aesAlgorithms) {
      byte[] encrypt = AES.encrypt(key, srcBytes, algorithm);
      System.out.println("加密算法：" + algorithm + "\t值为：" + HexCoder.INSTANCE.encodeString(encrypt));
    }
  }

}
