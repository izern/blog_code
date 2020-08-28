package cn.izern.test.case3.bytecode;

/**
 * @author: zern
 * @since 1.0.0
 */
public class ByteCodeExample {

  private static final int num = 10;

  private int version = 1;

  public int add(int a, int b) {
    int c = a + b;
    System.out.println(c);
    return c;
  }

}
