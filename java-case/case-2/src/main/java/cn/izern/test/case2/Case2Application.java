package cn.izern.test.case2;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author: zern
 * @since 1.0.0
 */
@SpringBootApplication
@ServletComponentScan
public class Case2Application {


  public static void main(String[] args) {
    SpringApplication.run(Case2Application.class, args);
  }
}
