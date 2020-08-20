package cn.izern.test.case3.loader;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author: zern
 * @since 1.0.0
 */
public class CustomClassLoader extends ClassLoader {

  @Override
  public Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
    synchronized (getClassLoadingLock(name)) {
      Class<?> c = findLoadedClass(name);
      if (c == null) {
        long t0 = System.nanoTime();
        if (name != null && name.startsWith("cn.izern")) {
          c = findClass(name);
        } else {
          c = getParent().loadClass(name);
        }
        if (c == null) {
          long t1 = System.nanoTime();
          c = findClass(name);
          sun.misc.PerfCounter.getParentDelegationTime().addTime(t1 - t0);
          sun.misc.PerfCounter.getFindClassTime().addElapsedTimeFrom(t1);
          sun.misc.PerfCounter.getFindClasses().increment();
        }
      }
      if (resolve) {
        resolveClass(c);
      }
      return c;
    }
  }

  @Override
  protected Class<?> findClass(String className) throws ClassNotFoundException {
    String dirPath = CustomClassLoader.class.getResource("/").getFile();
    String filePath = dirPath.concat(className).replaceAll("\\.", "/").concat(".class");
    byte[] data = new byte[0];
    try {
      data = getData(filePath);
    } catch (IOException e) {
      e.printStackTrace();
      throw new ClassNotFoundException(className);
    }
    return defineClass(className, data, 0, data.length);
  }

  private byte[] getData(String filePath) throws IOException {
    InputStream inputStream = null;
    ByteArrayOutputStream outputStream = null;

    File file = new File(filePath);
    if (!file.exists()) {
      throw new FileNotFoundException(filePath);
    }

    try {
      inputStream = new FileInputStream(file);
      outputStream = new ByteArrayOutputStream();

      int size = 0;
      byte[] buffer = new byte[1024];

      while ((size = inputStream.read(buffer)) != -1) {
        outputStream.write(buffer, 0, size);
      }

      return outputStream.toByteArray();
    } catch (IOException e) {
      throw e;
    } finally {
      closeForce(inputStream);
      closeForce(outputStream);
    }
  }

  public void closeForce(Closeable closeable) {
    try {
      closeable.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
