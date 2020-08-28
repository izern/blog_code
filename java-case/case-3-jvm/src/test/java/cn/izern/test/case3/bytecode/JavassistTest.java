package cn.izern.test.case3.bytecode;

import cn.izern.test.case3.spi.service.HelloService;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtNewConstructor;
import javassist.CtNewMethod;
import javassist.NotFoundException;
import org.apache.dubbo.common.utils.ReflectUtils;
import org.junit.Test;

/**
 * @author: zern
 * @since 1.0.0
 */
public class JavassistTest {

  /**
   * 动态生成一个接口的实现类
   */
  @Test
  public void makeHelloServiceImpl()
      throws NotFoundException, CannotCompileException, IllegalAccessException, InstantiationException {
    Class<HelloService> cls = HelloService.class;

    ClassPool pool = ClassPool.getDefault();
    CtClass ctClass = pool.makeClass("cn.izern.test.case3.bytecode.proyx0");
    ctClass.addInterface(pool.get(cls.getName()));
    Method[] methods = cls.getMethods();
    // 实现方法
    for (Method method : methods) {
      String desc = getDesc(method);
      if (Modifier.isStatic(method.getModifiers())) {
        continue;
      }
      Class<?> rt = method.getReturnType();
      Class<?>[] pts = method.getParameterTypes();
      StringBuilder code = new StringBuilder();
      // public void sayhello
      code.append("public ").append(getName(rt)).append(" ").append(method.getName());
      code.append("(");
      // 拼接方法参数
      for (int i = 0; i < pts.length; i++) {
        if (i > 0) {
          code.append(',');
        }
        code.append(ReflectUtils.getName(pts[i]));
        code.append(" arg").append(i);
      }
      code.append(")");
      // 拼接方法异常
      Class<?>[] ets = method.getExceptionTypes();
      if (ets != null && ets.length > 0) {
        code.append(" throws ");
        for (int i = 0; i < ets.length; i++) {
          if (i > 0) {
            code.append(',');
          }
          code.append(ReflectUtils.getName(ets[i]));
        }
      }
      // 拼接方法体
      code.append("{")
          .append("System.out.println(\"invoke method ")
              .append(method.getName()).append("\"")
              .append(");");
      if (!Void.TYPE.equals(rt)) {
        code.append(" return null;");
      }
      code.append("}");
      ctClass.addMethod(CtNewMethod.make(code.toString(), ctClass));
    }
    // 构造函数
    ctClass.addConstructor(CtNewConstructor.defaultConstructor(ctClass));
    Class clazz = ctClass.toClass(cls.getClassLoader(), cls.getProtectionDomain());
    // new对象
    HelloService helloService = (HelloService) clazz.newInstance();
    helloService.sayHello();

  }


  /**
   * 获取方法描述符
   *
   * @param m method
   * @return 描述符
   */
  public static String getDesc(final Method m) {
    StringBuilder ret = new StringBuilder(m.getName()).append('(');
    Class<?>[] parameterTypes = m.getParameterTypes();
    for (int i = 0; i < parameterTypes.length; i++) {
      ret.append(getDesc(parameterTypes[i]));
    }
    ret.append(')').append(getDesc(m.getReturnType()));
    return ret.toString();
  }

  /**
   * 获取基本类型的描述符
   *
   * @param c cls
   * @return 描述符
   */
  public static String getDesc(Class<?> c) {
    StringBuilder ret = new StringBuilder();

    while (c.isArray()) {
      ret.append('[');
      c = c.getComponentType();
    }

    if (c.isPrimitive()) {
      String t = c.getName();
      if ("void".equals(t)) {
        ret.append("V");
      } else if ("boolean".equals(t)) {
        ret.append("Z");
      } else if ("byte".equals(t)) {
        ret.append("B");
      } else if ("char".equals(t)) {
        ret.append("C");
      } else if ("double".equals(t)) {
        ret.append("D");
      } else if ("float".equals(t)) {
        ret.append("F");
      } else if ("int".equals(t)) {
        ret.append("I");
      } else if ("long".equals(t)) {
        ret.append("J");
      } else if ("short".equals(t)) {
        ret.append("S");
      }
    } else {
      ret.append('L');
      ret.append(c.getName().replace('.', '/'));
      ret.append(';');
    }
    return ret.toString();
  }

  /**
   * 获取类名
   *
   * @param c cls
   * @return 描述符
   */
  public static String getName(Class<?> c) {
    if (c.isArray()) {
      StringBuilder sb = new StringBuilder();
      do {
        sb.append("[]");
        c = c.getComponentType();
      }
      while (c.isArray());

      return c.getName() + sb.toString();
    }
    return c.getName();
  }

}
