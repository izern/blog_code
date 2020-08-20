package cn.izern.test.case2;

import cn.izern.test.case2.ksql.KsqlConfig;
import cn.izern.test.case2.ksql.KsqlQueryParam;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * 使用httpClient 链接ksql
 *
 * @author: zern
 * @since 1.0.0
 */
public class HttpClientDemo {

  public static void main(String[] args) throws IOException {
    HttpPost httpPost = new HttpPost(KsqlConfig.QUERY_URL);
    httpPost.addHeader("Content-Type", "application/vnd.ksql.v1+json");
    httpPost.addHeader("Accept-Encoding", "*");
    KsqlQueryParam param = new KsqlQueryParam();
    // 这里使用更简单粗暴的ksql
    param.setKsql("SELECT * FROM riderLocations EMIT CHANGES;");
    StringEntity stringEntity = new StringEntity(new ObjectMapper().writeValueAsString(param)
        , Charset.forName("UTF-8"));
    httpPost.setEntity(stringEntity);
    exec(httpPost);
  }

  /**
   * 处理返回的结果
   *
   * @param response response
   */
  public static void printResult(CloseableHttpResponse response) throws IOException {
    InputStream inputStream;
    BufferedReader br;
    inputStream = response.getEntity().getContent();
    br = new BufferedReader(new InputStreamReader(inputStream));
    String line = "";
    try {
      while ((line = br.readLine()) != null && !Thread.currentThread().isInterrupted()) {
        System.out.println(line);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (br != null) {
        br.close();
      }
      if (inputStream != null) {
        inputStream.close();
      }
    }
  }

  /**
   * 生成http client
   *
   * @return CloseableHttpClient
   */
  public static CloseableHttpClient createHttpClient() {
    return HttpClients.createDefault();
  }

  public static void exec(HttpRequestBase http) throws IOException {
    CloseableHttpClient httpClient = createHttpClient();
    CloseableHttpResponse response = null;
    try {
      response = httpClient.execute(http);
      HttpEntity entity = response.getEntity();
      if (HttpStatus.SC_OK < response.getStatusLine().getStatusCode()
          || response.getStatusLine().getStatusCode() >= HttpStatus.SC_MULTIPLE_CHOICES) {

        String responseContent = EntityUtils.toString(entity, "utf-8");
        throw new HttpResponseException(response.getStatusLine().getStatusCode(),
            "execute http failed,response " + response.getStatusLine().getStatusCode() + " "
                + responseContent);
      } else {
        printResult(response);
      }
    } catch (IOException e) {
      throw new IOException(
          "execute http failed " + e);
    } finally {
      if (response != null) {
        response.close();
      }
    }
  }
}
