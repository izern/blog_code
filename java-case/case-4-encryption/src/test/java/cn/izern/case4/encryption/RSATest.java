package cn.izern.case4.encryption;

import cn.izern.case4.encryption.RSA.RSASignAlgorithms;
import cn.izern.case4.encryption.codec.PlainStringCoder;
import cn.izern.case4.encryption.exception.CodecException;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.util.Arrays;
import java.util.List;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author: zern
 * @since 1.0.0
 */
public class RSATest {

  @Before
  public void before() {
    Security.addProvider(new BouncyCastleProvider());
  }

  private List<Integer> bits = Arrays.asList(new Integer[]{1024, 2048, 3072, 4096});

  private String src = "blog.izern.cn";

  private byte[] srcBytes = PlainStringCoder.INSTANCE.decodeString(src);

  public RSATest() throws CodecException {
  }

  @Test
  public void keyGen() {
    bits.forEach(bit -> {
      KeyPair keyPair = RSA.keyGen(bit);
      try {
        PublicKey publicKey = RSA.parsePk(keyPair.getPublic().getEncoded());
        Assert.assertEquals("解析出的公钥与原公钥不一致", keyPair.getPublic(), publicKey);
        PrivateKey privateKey = RSA.parseSk(keyPair.getPrivate().getEncoded());
        Assert.assertEquals("解析出的私钥与原公钥不一致", keyPair.getPrivate(), privateKey);
      } catch (GeneralSecurityException e) {
        e.printStackTrace();
      }
    });

  }

  @Test
  public void rsaEncrypt() {
    bits.forEach(bit -> {
      KeyPair keyPair = RSA.keyGen(bit);
      try {
        byte[] encrypt = RSA.encrypt(srcBytes, keyPair.getPublic());
        byte[] decrypt = RSA.decrypt(encrypt, keyPair.getPrivate());
        Assert.assertEquals("解密数据与原文不符", PlainStringCoder.INSTANCE.encodeString(decrypt), src);
        System.out.println(String.format("%d bit %s verify encrypt done.", bit));
      } catch (GeneralSecurityException e) {
        e.printStackTrace();
      }
    });
  }

  @Test
  public void rsaSign() {
    bits.forEach(bit -> {
      KeyPair keyPair = RSA.keyGen(bit);
      RSASignAlgorithms[] values = RSASignAlgorithms.values();
      for (RSASignAlgorithms signAlgorithms : values) {
        try {
          byte[] sign = RSA.sign(srcBytes, keyPair.getPrivate(), signAlgorithms);
          boolean verifySign = RSA.verifySign(srcBytes, sign, keyPair.getPublic(), signAlgorithms);
          Assert.assertTrue("签名无法验证，当前签名算法为" + signAlgorithms, verifySign);
          System.out.println(String.format("%d bit %s verify sign done.", bit, signAlgorithms));
        } catch (GeneralSecurityException e) {
          e.printStackTrace();
        }
      }
    });

  }


}
