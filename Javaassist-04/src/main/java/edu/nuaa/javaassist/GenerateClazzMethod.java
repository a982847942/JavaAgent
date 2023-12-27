package edu.nuaa.javaassist;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.bytecode.AccessFlag;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author brain
 * @version 1.0
 * @date 2023/12/27 9:16
 */
public class GenerateClazzMethod extends ClassLoader{
    public static void main(String[] args) throws Exception {

        ClassPool pool = ClassPool.getDefault();

        // 获取类
        CtClass ctClass = pool.get(ApiTest.class.getName());
//        ctClass.replaceClassName("ApiTest", "ApiTest02"); //这行代码有什么作用？貌似没有修改Class名称？
        String clazzName = ctClass.getName();
//        System.out.println("clazzName:" + clazzName);

        // 获取方法
        CtMethod ctMethod = ctClass.getDeclaredMethod("strToInt");
        String methodName = ctMethod.getName();

        // 方法信息：methodInfo.getDescriptor();
        MethodInfo methodInfo = ctMethod.getMethodInfo();

        // 方法：入参信息
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
        CtClass[] parameterTypes = ctMethod.getParameterTypes();

        boolean isStatic = (methodInfo.getAccessFlags() & AccessFlag.STATIC) != 0;  // 判断是否为静态方法
        int parameterSize = isStatic ? attr.tableLength() : attr.tableLength() - 1; // 静态类型取值
        List<String> parameterNameList = new ArrayList<>(parameterSize);            // 入参名称
        List<String> parameterTypeList = new ArrayList<>(parameterSize);            // 入参类型
        StringBuilder parameters = new StringBuilder();                             // 参数组装；$1、$2...，$$可以获取全部，但是不能放到数组初始化

        for (int i = 0; i < parameterSize; i++) {
            parameterNameList.add(attr.variableName(i + (isStatic ? 0 : 1))); // 静态类型去掉第一个this参数
            parameterTypeList.add(parameterTypes[i].getName());
            if (i + 1 == parameterSize) {
                parameters.append("$").append(i + 1);
            } else {
                parameters.append("$").append(i + 1).append(",");
            }
        }

        // 方法：出参信息
        CtClass returnType = ctMethod.getReturnType();
        String returnTypeName = returnType.getName();

        // 方法：生成方法唯一标识ID
        int idx = Monitor.generateMethodId(clazzName, methodName, parameterNameList, parameterTypeList, returnTypeName);

        // 定义属性
        ctMethod.addLocalVariable("startNanos", CtClass.longType);
        ctMethod.addLocalVariable("parameterValues", pool.get(Object[].class.getName()));

        // 方法前加强
        ctMethod.insertBefore("{ startNanos = System.nanoTime(); parameterValues = new Object[]{" + parameters.toString() + "}; }");

        // 方法后加强
        ctMethod.insertAfter("{ edu.nuaa.javaassist.Monitor.point(" + idx + ", startNanos, parameterValues, $_);}", false); // 如果返回类型非对象类型，$_ 需要进行类型转换

        // 方法；添加TryCatch
        ctMethod.addCatch("{ edu.nuaa.javaassist.Monitor.point(" + idx + ", $e); throw $e; }", ClassPool.getDefault().get("java.lang.Exception"));   // 添加异常捕获

        // 输出类的内容
        ctClass.writeFile();

        // 测试调用  这种热加载思想还是使用类加载器重新加载修改后的class文件
        byte[] bytes = ctClass.toBytecode();
        Class<?> clazzNew = new GenerateClazzMethod().defineClass("edu.nuaa.javaassist.ApiTest", bytes, 0, bytes.length);

        // 反射获取 main 方法
        Method method = clazzNew.getMethod("strToInt", String.class, String.class);
        Object obj_01 = method.invoke(clazzNew.newInstance(), "1", "2");
        System.out.println("正确入参：" + obj_01);

        Object obj_02 = method.invoke(clazzNew.newInstance(), "a", "b");
        System.out.println("异常入参：" + obj_02);
    }
}
