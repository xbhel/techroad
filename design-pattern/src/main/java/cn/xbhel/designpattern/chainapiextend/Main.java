package cn.xbhel.designpattern.chainapiextend;

import java.math.BigDecimal;

/**
 * 链式 API 继承引发的向下转型问题
 *
 * @author xbhel
 */
public class Main {

    public static void main(String[] args) {
        var member = new Member() //NOSONAR
                .setUserId("1")
                .setBalance(BigDecimal.valueOf(10_000))
                .setLevel(5)
                .setUsername("kevin")
                .setPassword("kevin")
                .setGender("male");
    }

}
