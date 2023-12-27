package edu.nuaa.javaassist;

/**
 * @author brain
 * @version 1.0
 * @date 2023/12/27 10:31
 */
public @interface RpcGatewayClazz {
    String clazzDesc() default "";
    String alias() default "";
    long timeOut() default 350;
}
