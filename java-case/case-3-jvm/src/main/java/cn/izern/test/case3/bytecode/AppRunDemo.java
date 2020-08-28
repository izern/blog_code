package cn.izern.test.case3.bytecode;

import java.util.concurrent.TimeUnit;

/**
 * @author: zern
 * @since 1.0.0
 */
public class AppRunDemo implements Runnable {

  public void business() {
    System.out.println("process ...");
    try {
      TimeUnit.SECONDS.sleep(5);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void run() {

    while (true) {
      business();
    }
  }

  public static void main(String[] args) throws InterruptedException {
    AppRunDemo demo = new AppRunDemo();
    new Thread(demo).start();
    TimeUnit.SECONDS.sleep(2000);
  }
}
