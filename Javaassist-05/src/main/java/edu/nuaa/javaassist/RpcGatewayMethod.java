package edu.nuaa.javaassist;

/**
 * @author brain
 * @version 1.0
 * @date 2023/12/27 10:32
 */
public @interface RpcGatewayMethod {
    String methodName() default "";
    String methodDesc() default "";
}
