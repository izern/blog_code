package cn.izern.test.case1.servlet;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * 自定义HttpServletRequest，用于读取原request参数，进行解密
 *
 * @author: 热心网友
 * @since 1.0.0
 */
public class CustomHttpServletRequest extends HttpServletRequestWrapper {

  private final byte[] body; // 报文

  public CustomHttpServletRequest(HttpServletRequest request) {
    super(request);
    //        String sign = request.getHeader("sign");
//        String timestamp = request.getHeader("timestamp");
//        String token = request.getHeader("token");
//
//        if (!(StringUtils.isNotBlank(sign) && StringUtils.isNotBlank(timestamp) && StringUtils.isNotBlank(token))) {
//            throw new AdminException(AdminExceptionEnum.HEADER_NULL);
//        }
//
//        ServletInputStream inputStream = request.getInputStream();
//
//        byte[] oldBody = IOUtils.toByteArray(new InputStreamReader(inputStream), StandardCharsets.UTF_8);
//
//        String bodyStr = new String(oldBody, StandardCharsets.UTF_8);
//
//        //sign=MD5(请求参数 + token + timestamp )
//
//        String newSign = DigestUtils.md5DigestAsHex((bodyStr + token + timestamp).getBytes(StandardCharsets.UTF_8));
//        if (!sign.equals(newSign)) {
//            throw new AdminException(AdminExceptionEnum.PARAM_MODIFY);
//        }
//        body = bodyStr.getBytes(StandardCharsets.UTF_8);
    body = "{\"name\":1}".getBytes(StandardCharsets.UTF_8);
  }

  @Override
  public ServletInputStream getInputStream() {
    return new CustomServletInputStream();
  }

  @Override
  public BufferedReader getReader() {
    return new BufferedReader(new InputStreamReader(getInputStream()));
  }

  class CustomServletInputStream extends ServletInputStream {

    @Override
    public int read() {
      return new ByteArrayInputStream(body).read();
    }

    @Override
    public boolean isFinished() {
      return false;
    }

    @Override
    public boolean isReady() {
      return false;
    }

    @Override
    public void setReadListener(ReadListener readListener) {
    }
  }
}
