package cn.izern.test.case2.ksql;

/**
 * @author: zern
 * @since 1.0.0
 */
public class KsqlConfig {


  public static final String HOST = "localhost";

  public static final int PORT = 8088;

  public static final String QUERY_URI = "/query";

  public static final String QUERY_URL = String.format("http://%s:%d%s", HOST, PORT, QUERY_URI);


}
