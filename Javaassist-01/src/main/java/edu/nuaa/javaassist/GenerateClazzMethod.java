package edu.nuaa.javaassist;

import javassist.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author brain
 * @version 1.0
 * @date 2023/12/26 21:38
 */
public class GenerateClazzMethod {
    public static void main(String[] args) throws IOException, CannotCompileException, NotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        /*
        1.创建 ClassPool，它是一个基于HashMap实现的 CtClass 对象容器。
        2.使用 CtClass，创建我们的类信息，也就是类的路径和名称。
        3.接下来就是给类添加方法。包括；方法的属性、类型、名称、入参、出参和方法体的内容。
        4.在方法创建好后还需要创建一个空的构造函数，每一个类都会在编译后生成这样一个构造函数。
        5.当方法创建完成后，我们使用 ctClass.writeFile() 进行输出方法的内容信息。也就可以看到通过我们使用 Javassist 生成类的样子。
        6.最后就是我们的反射调用 main 方法，测试输出结果。
        * */

        ClassPool pool = ClassPool.getDefault();

        // 创建类 classname：创建类路径和名称
        CtClass ctClass = pool.makeClass("edu.nuaa.javassist.HelloWorld");

        // 添加方法
        CtMethod mainMethod = new CtMethod(CtClass.voidType, "main", new CtClass[]{pool.get(String[].class.getName())}, ctClass);
        mainMethod.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
        mainMethod.setBody("{System.out.println(\"javassist hi helloworld by brain\");}");
        ctClass.addMethod(mainMethod);

        // 创建无参数构造方法  注释掉这段代码默认也会加上无参构造器
        CtConstructor ctConstructor = new CtConstructor(new CtClass[]{}, ctClass);
        ctConstructor.setBody("{}");
        ctClass.addConstructor(ctConstructor);

        // 输出类内容
        ctClass.writeFile();

        // 测试调用
        Class clazz = ctClass.toClass();
        Object obj = clazz.newInstance();

        Method main = clazz.getDeclaredMethod("main", String[].class);
        main.invoke(obj, (Object)new String[1]);

    }
}
