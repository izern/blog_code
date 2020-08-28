package cn.izern.test.case3.layout;

import java.math.BigInteger;
import org.junit.Test;
import org.openjdk.jol.info.ClassLayout;

/**
 * @author: zern
 * @since 1.0.0
 */
public class LayoutTest {


  @Test
  public void emptyClassLayout() {
    System.out.println(ClassLayout.parseInstance(new Empty()).toPrintable());
  }

  @Test
  public void arrayClassLayout() {
    System.out.println(ClassLayout.parseInstance(1).toPrintable());
    System.out.println(ClassLayout.parseInstance(new int[]{1}).toPrintable());
    System.out.println(ClassLayout.parseInstance(1L).toPrintable());
    System.out.println(ClassLayout.parseInstance(new int[]{1, 2, 3}).toPrintable());
    System.out.println(ClassLayout.parseInstance(new long[]{1L, 2L, 3L}).toPrintable());
  }

  @Test
  public void refFieldClassLayout() {
    System.out.println(ClassLayout.parseInstance(new BigInteger("1")).toPrintable());
    System.out.println(ClassLayout.parseInstance(1L).toPrintable());
    System.out.println(ClassLayout.parseInstance(1F).toPrintable());
    System.out.println(ClassLayout.parseInstance(1D).toPrintable());
    System.out.println(ClassLayout.parseInstance(new RefField(true, 1L)).toPrintable());

  }
}
