package cn.xbhel.designpattern.chainapiextend;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * 子类继承了父类的属性并拓展了自己的属性
 * @author xbhel
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class Member extends User<Member>{
    /** 会员等级 */
    private int level;
    /** 余额 */
    private BigDecimal balance;
}
