package cn.izern.test.case1.servlet;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Base64;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import org.apache.commons.io.IOUtils;

/**
 * 自定义HttpServletRequest，用于读取原request参数，进行解密
 *
 * @author: 热心网友
 * @since 1.0.0
 */
public class CustomHttpServletRequest2 extends HttpServletRequestWrapper {

  private final ByteArrayInputStream body; // 报文

  public CustomHttpServletRequest2(HttpServletRequest request) {
    super(request);
    byte[] bytes = new byte[1];
    try {
      ServletInputStream inputStream = request.getInputStream();
      bytes = IOUtils.toByteArray(inputStream);
      // 对参数解密，这里使用base64代替
      bytes = Base64.getDecoder().decode(bytes);
    } catch (IOException e) {
      e.printStackTrace();
    }
    body = new ByteArrayInputStream(bytes);
  }

  @Override
  public ServletInputStream getInputStream() {

    return new CustomServletInputStream();
  }

  @Override
  public BufferedReader getReader() {
    return new BufferedReader(new InputStreamReader(body));
  }

  class CustomServletInputStream extends ServletInputStream {

    ReadListener listener;

    @Override
    public int read() {
      return body.read();
    }

    @Override
    public boolean isFinished() {
      return body.available() <= 0;
    }

    @Override
    public boolean isReady() {
      return true;
    }

    @Override
    public void setReadListener(ReadListener readListener) {
      this.listener = readListener;
    }

  }
}
