package cn.izern.test.case3.bytecode;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

/**
 * @author: zern
 * @since 1.0.0
 */
public class TransformerSample implements ClassFileTransformer {

  @Override
  public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
      ProtectionDomain protectionDomain, byte[] classfileBuffer)
      throws IllegalClassFormatException {
    try {
      ClassPool cp = ClassPool.getDefault();
      CtClass cc = cp.get("cn.izern.test.case3.bytecode.AppRunDemo");
      CtMethod m = cc.getDeclaredMethod("business");
      m.insertBefore("{ System.out.println(\"before\"); }");
      m.insertAfter("{ System.out.println(\"after\"); }");
      return cc.toBytecode();
    } catch (Exception e) {
      e.printStackTrace();
    }

    return new byte[0];
  }
}
