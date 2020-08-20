package cn.izern.test.case1.advice;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Base64;
import org.apache.commons.io.IOUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

/**
 * @author: zern
 * @since 1.0.0
 */
@ControllerAdvice
public class DecryptRequestBodyAdvice implements RequestBodyAdvice {

  @Override
  public boolean supports(MethodParameter methodParameter, Type targetType,
      Class<? extends HttpMessageConverter<?>> converterType) {
    return true;
  }

  @Override
  public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter,
      Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {

    System.out.println("----DecryptRequestBodyAdvice beforeBodyRead");
    byte[] bytes = IOUtils.toByteArray(inputMessage.getBody());

    // 解密， 这里使用base64代替
    ByteArrayInputStream is = new ByteArrayInputStream(Base64.getDecoder().decode(bytes));
    return new HttpInputMessage() {
      @Override
      public InputStream getBody() throws IOException {
        return is;
      }

      @Override
      public HttpHeaders getHeaders() {
        return inputMessage.getHeaders();
      }
    };
  }

  @Override
  public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter,
      Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
    return body;
  }

  @Override
  public Object handleEmptyBody(Object body, HttpInputMessage inputMessage,
      MethodParameter parameter, Type targetType,
      Class<? extends HttpMessageConverter<?>> converterType) {
    return null;
  }
}
