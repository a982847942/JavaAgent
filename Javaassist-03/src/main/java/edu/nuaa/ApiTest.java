package edu.nuaa;

import java.util.Random;

/**
 * @author brain
 * @version 1.0
 * @date 2023/12/26 22:09
 */
public class ApiTest {
    public String queryGirlfriendCount(String boyfriendName) {
        return boyfriendName + "的前女友数量：" + (new Random().nextInt(10) + 1L) + " 个";
    }
}
