package cn.izern.test.case3.spi;

import cn.izern.test.case3.loader.CustomClassLoader;
import cn.izern.test.case3.spi.service.HelloService;
import java.util.Iterator;
import java.util.ServiceLoader;
import org.apache.dubbo.common.bytecode.Proxy;
import org.junit.Test;

/**
 * @author: zern
 * @since 1.0.0
 */
public class SpiTest {


  @Test
  public void loaderTest() throws ClassNotFoundException {
    serviceLoader();
    Thread.currentThread().setContextClassLoader(new CustomClassLoader());
    serviceLoader();
  }

  @Test
  public void serviceLoader() throws ClassNotFoundException {
    System.out.println("加载HelloService实现类，并执行");
    Class<HelloService> clazz = (Class<HelloService>) Thread.currentThread().getContextClassLoader()
        .loadClass(HelloService.class.getName());
    Iterator<HelloService> iterator = ServiceLoader.load(clazz).iterator();
    while (iterator.hasNext()) {

      Object obj = iterator.next();

      Proxy proxy = Proxy.getProxy(clazz);
      HelloService helloService = (HelloService) proxy
          .newInstance((p, method, args) -> method.invoke(obj, args));
      helloService.sayHello();
//      try {
//        Method sayHello = clazz.getMethod("sayHello");
//        sayHello.invoke(obj);
//      } catch (Exception e) {
//        e.printStackTrace();
//      }
      System.out.println(String.format("当前实现类为%s,加载器为:%S", obj.toString(),
          obj.getClass().getClassLoader()));
    }
  }
}
