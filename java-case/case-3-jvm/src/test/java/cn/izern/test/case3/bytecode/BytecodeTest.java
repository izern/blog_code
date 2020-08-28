package cn.izern.test.case3.bytecode;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import org.junit.Test;

/**
 * @author: zern
 * @since 1.0.0
 */
public class BytecodeTest {

  @Test
  public void readClassContent() throws IOException {
    String classes = Thread.currentThread().getContextClassLoader().getResource("").getFile();
    File file = new File((classes + "cn/izern/test/case3/bytecode/ByteCodeExample.class")
        .replace("test-classes", "classes"));
    FileInputStream is = new FileInputStream(file);
    int b = 0;
    int count = 0;
    while ((b = is.read()) >= 0) {
      count++;
      if (b < 0xf) {
        System.out.print(0);
      }
      System.out.print(Integer.toHexString(b).toUpperCase() + " ");
      if ((count & 15) == 0) {
        System.out.println();
      }
    }
  }

  public void gcTest() {
    ByteBuffer byteBuffer = ByteBuffer.allocateDirect(20000);
    BytecodeTest test = new BytecodeTest();
    test = null;
    System.gc();

  }

}
