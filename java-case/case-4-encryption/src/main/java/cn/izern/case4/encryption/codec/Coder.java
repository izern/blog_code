
package cn.izern.case4.encryption.codec;

import cn.izern.case4.encryption.exception.CodecException;

/**
 * 字符串-字节数组编解码器
 *
 * @author: zern
 * @since 1.0.0
 */
public abstract class Coder {

  protected Coder() {
  }

  /**
   * 空字符串
   */
  protected static final String EMPTY_STRING = "";

  /**
   * 空字节数组
   */
  protected static final byte[] EMPTY_BYTES = new byte[0];

  /**
   * 将字节数组编码为字符串
   *
   * @param bytes 字节数组
   * @return 字符串
   */
  public String encodeString(byte[] bytes) {
    if (bytes == null) {
      return null;
    }
    if (bytes.length == 0) {
      return EMPTY_STRING;
    }
    return encodeString0(bytes);
  }

  /**
   * 将字符串反编码为字节数组
   *
   * @param str 字符串
   * @return 字节数组
   */
  public byte[] decodeString(String str) throws CodecException {
    if (str == null) {
      return null;
    }
    if (str.length() == 0) {
      return EMPTY_BYTES;
    }
    try {
      return decodeString0(str);
    } catch (Exception e) {
      throw new CodecException("decode failed", e);
    }
  }

  /**
   * 将字符串反编码为字节数组
   *
   * @param str 字符串
   * @return 字节数组
   */
  protected byte[] decodeString0(String str) throws CodecException {
    throw new UnsupportedOperationException();
  }


  /**
   * 将字节数组编码为字符串
   *
   * @param bytes 字节数组
   * @return 字符串
   */
  protected String encodeString0(byte[] bytes) {
    throw new UnsupportedOperationException();
  }
}
