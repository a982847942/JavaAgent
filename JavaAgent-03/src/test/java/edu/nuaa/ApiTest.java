package edu.nuaa;

/**
 * @author brain
 * @version 1.0
 * @date 2023/12/26 17:34
 */
public class ApiTest {
    public static void main(String[] args) throws InterruptedException {
        ApiTest apiTest = new ApiTest();
        apiTest.echoHi();
    }

    private void echoHi() throws InterruptedException {
        System.out.println("hi agent");
        Thread.sleep((long) (Math.random() * 500));
    }
}
