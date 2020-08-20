package cn.izern.test.case1.entity;

import java.io.Serializable;

/**
 * @author: zern
 * @since 1.0.0
 */
public class User implements Serializable {

  private String id;

  private String name;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
