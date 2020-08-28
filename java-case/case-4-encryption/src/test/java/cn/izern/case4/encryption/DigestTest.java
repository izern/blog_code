package cn.izern.case4.encryption;

import cn.izern.case4.encryption.Digest.DigestAlgorithm;
import cn.izern.case4.encryption.codec.HexCoder;
import org.junit.Test;

/**
 * 各种摘要算法
 *
 * @author: zern
 * @since 1.0.0
 */
public class DigestTest {


  @Test
  public void digest() {
    DigestAlgorithm[] digestAlgorithmValues = DigestAlgorithm.values();

    for (DigestAlgorithm digestAlgorithm : digestAlgorithmValues) {
      System.out.println(Digest.digest("hello zern", digestAlgorithm, HexCoder.INSTANCE));
    }
  }

}
