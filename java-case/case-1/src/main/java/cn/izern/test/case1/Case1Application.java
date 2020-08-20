package cn.izern.test.case1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author: zern
 * @since 1.0.0
 */
@SpringBootApplication
@ServletComponentScan
public class Case1Application {

  // 如下配置，开启一个即可。

  /**
   * 1. 问题复现，出现错误
   * @return filterRegistrationBean
   */
//  @Bean
//  public FilterRegistrationBean filterRegist() {
//    FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new
//        CustomHttpServletRequestFilter());
//    filterRegistrationBean.setOrder(-99);
//    filterRegistrationBean.setName("paramDecryptFilter");
//    filterRegistrationBean.setUrlPatterns(Arrays.asList("/*"));
//    return filterRegistrationBean;
//  }

  /**
   * 2. 解决问题1，修复request bug
   * @return filterRegistrationBean
   */
//  @Bean
//  public FilterRegistrationBean filterRegist() {
//    FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new
//        CustomHttpServletRequestFilter2());
//    filterRegistrationBean.setOrder(-99);
//    filterRegistrationBean.setName("paramDecryptFilter");
//    filterRegistrationBean.setUrlPatterns(Arrays.asList("/*"));
//    return filterRegistrationBean;
//  }

  /**
   * 3. DecryptRequestBodyAdvice.java 实现需求 给这个类添加ControllerAdvice
   */


  public static void main(String[] args) {
    SpringApplication.run(Case1Application.class, args);
  }
}
