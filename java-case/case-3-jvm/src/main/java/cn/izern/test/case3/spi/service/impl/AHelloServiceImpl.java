package cn.izern.test.case3.spi.service.impl;

import cn.izern.test.case3.spi.service.HelloService;

/**
 * @author: zern
 * @since 1.0.0
 */
public class AHelloServiceImpl implements HelloService {

  @Override
  public void sayHello() {
    System.out.println(String.format("来自%s的hello", this.toString()));
  }
}
