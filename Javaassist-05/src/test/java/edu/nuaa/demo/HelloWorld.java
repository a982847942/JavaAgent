package edu.nuaa.demo;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.Bytecode;
import javassist.bytecode.ConstPool;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.LongMemberValue;
import javassist.bytecode.annotation.StringMemberValue;

/**
 * @author brain
 * @version 1.0
 * @date 2023/12/27 10:35
 */
public class HelloWorld {
    public static void main(String[] args) throws Exception {

        ClassPool pool = ClassPool.getDefault();

        // 创建类信息
        CtClass ctClass = pool.makeClass("edu.nuaa.javaassist.HelloWorld");

        // 添加方法
        CtMethod mainMethod = new CtMethod(CtClass.doubleType, "queryInterestFee", new CtClass[]{pool.get(String.class.getName())}, ctClass);
        mainMethod.setModifiers(Modifier.PUBLIC);

        MethodInfo methodInfo = mainMethod.getMethodInfo();

        ConstPool cp = methodInfo.getConstPool();

        // 类添加注解
        AnnotationsAttribute clazzAnnotationsAttribute = new AnnotationsAttribute(cp, AnnotationsAttribute.visibleTag);
        Annotation clazzAnnotation = new Annotation("edu/nuaa/javaassist/RpcGatewayClazz", cp);
        clazzAnnotation.addMemberValue("clazzDesc", new StringMemberValue("用户信息查询服务", cp));
        clazzAnnotation.addMemberValue("alias", new StringMemberValue("api", cp));
        clazzAnnotation.addMemberValue("timeOut", new LongMemberValue(500L, cp));
        clazzAnnotationsAttribute.setAnnotation(clazzAnnotation);
        ctClass.getClassFile().addAttribute(clazzAnnotationsAttribute);

        // 方法添加注解
        AnnotationsAttribute methodAnnotationsAttribute = new AnnotationsAttribute(cp, AnnotationsAttribute.visibleTag);
        Annotation methodAnnotation = new Annotation("edu/nuaa/javaassist/RpcGatewayMethod", cp);
        methodAnnotation.addMemberValue("methodName", new StringMemberValue("查询息费", cp));
        methodAnnotation.addMemberValue("methodDesc", new StringMemberValue("interestFee", cp));
        methodAnnotationsAttribute.setAnnotation(methodAnnotation);
        methodInfo.addAttribute(methodAnnotationsAttribute);

        // 指令控制
        Bytecode bytecode = new Bytecode(cp);

        bytecode.addGetstatic("java/math/BigDecimal", "TEN", "Ljava/math/BigDecimal;");
        bytecode.addInvokevirtual("java/math/BigDecimal", "doubleValue", "()D");
        bytecode.addReturn(CtClass.doubleType);

        methodInfo.setCodeAttribute(bytecode.toCodeAttribute());

        ctClass.addMethod(mainMethod);

        ctClass.writeFile();

    }
}
