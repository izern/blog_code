package cn.izern.test.case3.layout;

/**
 * @author: zern
 * @since 1.0.0
 */
public class RefField {

  private long num;
  /**
   * 任意引用属性
   */
  private boolean bool;

  public RefField(boolean bool, long num) {
    this.bool = bool;
    this.num = num;
  }
}
