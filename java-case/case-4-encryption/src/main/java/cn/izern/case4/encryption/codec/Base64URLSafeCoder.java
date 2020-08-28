package cn.izern.case4.encryption.codec;

import cn.izern.case4.encryption.Constant;
import cn.izern.case4.encryption.exception.CodecException;
import java.util.Base64;

/**
 * RFC4648_URLSAFE base64编码器
 *
 * @author: zern
 * @since 1.0.0
 */
public class Base64URLSafeCoder extends Coder {

  public static final Coder INSTANCE = new Base64URLSafeCoder();

  protected Base64URLSafeCoder() {
  }

  /**
   * 将字节数组编码为hex字符串
   *
   * @param bytes 字节数组
   * @return hex字符串
   */
  @Override
  public String encodeString0(byte[] bytes) {
    return Base64.getUrlEncoder().encodeToString(bytes);
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
      return Base64.getUrlDecoder().decode(str.getBytes(Constant.DEFAULT_CHARSET));
    } catch (Exception e) {
      throw new CodecException(e);
    }
  }

}
