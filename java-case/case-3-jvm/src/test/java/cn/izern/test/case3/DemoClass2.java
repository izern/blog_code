
package  cn.izern.test.case3;

import cn.izern.test.case3.loader.CustomClassLoader;
import cn.izern.test.case3.spi.service.HelloService;
import java.util.Arrays;
import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author: zern
 * @since 1.0.0
 */
public class DemoClass2 {

  private static String className = "cn.izern.test.case3.DemoClass2";

  public static void main(String[] args) throws ClassNotFoundException {
//    serviceLoader();
    Thread.currentThread().setContextClassLoader(new CustomClassLoader());
    serviceLoader();
  }

  public static void serviceLoader() throws ClassNotFoundException {
    System.out.println("加载HelloService实现类，并执行");
    Class<?> clazz = Thread.currentThread().getContextClassLoader()
        .loadClass(HelloService.class.getName());
//    Class<?> clazz =  Thread.currentThread().getContextClassLoader()
//        .loadClass("cn.izern.test.case3.spi.service.HelloService");
    Iterator<?> iterator = ServiceLoader.load(clazz).iterator();
    while (iterator.hasNext()) {
      Object obj = iterator.next();
      HelloService helloService = (HelloService) obj;
      System.out.println(String.format("实现类为  class: %s, classloader: %s", obj.getClass(),
          obj.getClass().getClassLoader()));
      System.out.println("  实现的接口有");
      Arrays.asList(obj.getClass().getInterfaces()).forEach(cl -> {
        System.out.println(String.format("  class:%s,classloader: %s", cl, cl.getClassLoader()));
      });

    }
  }

}
