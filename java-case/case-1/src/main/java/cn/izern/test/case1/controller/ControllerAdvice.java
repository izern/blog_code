package cn.izern.test.case1.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: zern
 * @since 1.0.0
 */
@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

  @ExceptionHandler
  @ResponseBody
  public String exception(Exception e) {
    e.printStackTrace();
    return e.getMessage();
  }
}
