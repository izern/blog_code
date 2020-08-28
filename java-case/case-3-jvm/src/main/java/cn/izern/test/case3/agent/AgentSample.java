package cn.izern.test.case3.agent;

import cn.izern.test.case3.bytecode.AppRunDemo;
import cn.izern.test.case3.bytecode.TransformerSample;
import java.lang.instrument.Instrumentation;

/**
 * @author: zern
 * @since 1.0.0
 */
public class AgentSample {
  public static void agentmain(String args, Instrumentation inst) {
    //指定我们自己定义的Transformer，在其中利用Javassist做字节码替换
    inst.addTransformer(new TransformerSample(), true);
    try {
      //重定义类并载入新的字节码
      inst.retransformClasses(AppRunDemo.class);
      System.out.println("Agent Load Done.");
    } catch (Exception e) {
      System.out.println("agent load failed!");
    }
  }

}
