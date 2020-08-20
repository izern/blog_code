package cn.izern.test.case3.loader;

/**
 * 查看classLoader及其关系
 *
 * @author: zern
 * @since 1.0.0
 */
public class EmptyClassLoader extends ClassLoader {

  @Override
  public Class<?> loadClass(String name) throws ClassNotFoundException {
    return super.loadClass(name, false);
  }


  @Override
  protected Class<?> findClass(String className) throws ClassNotFoundException {

    throw new ClassNotFoundException(className);
  }


}
