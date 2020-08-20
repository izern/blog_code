package cn.izern.test.case2.ksql;

import java.io.Serializable;
import java.util.Map;

/**
 * @author: zern
 * @since 1.0.0
 */
public class KsqlQueryParam implements Serializable {
  private String ksql;
  private Map<String, String> streamsProperties;

  public String getKsql() {
    return ksql;
  }

  public void setKsql(String ksql) {
    this.ksql = ksql;
  }

  public Map<String, String> getStreamsProperties() {
    return streamsProperties;
  }

  public void setStreamsProperties(Map<String, String> streamsProperties) {
    this.streamsProperties = streamsProperties;
  }
}
