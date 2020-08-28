package cn.izern.case4.encryption.codec;

import cn.izern.case4.encryption.exception.CodecException;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

/**
 * hex 编码器
 *
 * @author: zern
 * @since 1.0.0
 */
public class HexCoder extends Coder {

  public static final Coder INSTANCE = new HexCoder();

  protected HexCoder() {

  }

  /**
   * 将字节数组编码为hex字符串
   *
   * @param bytes 字节数组
   * @return hex字符串
   */
  @Override
  public String encodeString0(byte[] bytes) {
    return Hex.encodeHexString(bytes);
  }

  /**
   * 将字符串反编码为字节数组
   *
   * @param str hex字符串
   * @return 字节数组
   */
  @Override
  public byte[] decodeString0(String str) throws CodecException {
    try {
      return Hex.decodeHex(str);
    } catch (DecoderException e) {
      throw new CodecException(e);
    }
  }

}
