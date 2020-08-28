package cn.izern.case4.encryption.codec;

import cn.izern.case4.encryption.Constant;

/**
 * @author: zern
 * @since 1.0.0
 */
public class PlainStringCoder extends Coder {

  public static final Coder INSTANCE = new PlainStringCoder();

  protected PlainStringCoder() {

  }


  /**
   * 将字节数组编码为字符串
   *
   * @param bytes 字节数组
   * @return 字符串
   */
  @Override
  public String encodeString0(byte[] bytes) {
    return new String(bytes, Constant.DEFAULT_CHARSET);
  }

  /**
   * 将字符串反编码为字节数组
   *
   * @param str 字符串
   * @return 字节数组
   */
  @Override
  public byte[] decodeString0(String str) {
    return str.getBytes(Constant.DEFAULT_CHARSET);
  }

}
