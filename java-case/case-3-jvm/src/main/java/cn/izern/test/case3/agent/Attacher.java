package cn.izern.test.case3.agent;

import com.sun.tools.attach.VirtualMachine;

/**
 * @author: zern
 * @since 1.0.0
 */
public class Attacher {

  public static void main(String[] args) {
    try {
      VirtualMachine vm = VirtualMachine.attach("120045");
      vm.loadAgent("/root/workspace/demo/blog_code/java-case/out/artifacts/agent_jar/case-3-jvm.jar");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
