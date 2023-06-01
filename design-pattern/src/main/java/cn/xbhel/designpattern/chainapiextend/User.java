package cn.xbhel.designpattern.chainapiextend;

import lombok.Data;

/**
 * 父类支持链式访问
 * @author xbhel
 */
@Data
public class User<T extends User<T>> {
    private String userId;
    private String username;
    private String password;
    private String gender;

    @SuppressWarnings("unchecked")
    public T self(){
        return (T)this;
    }

    public T setUserId(String userId) {
        this.userId = userId;
        return self();
    }

    public T setUsername(String username) {
        this.username = username;
        return self();
    }

    public T setPassword(String password) {
        this.password = password;
        return self();
    }

    public T setGender(String gender) {
        this.gender = gender;
        return self();
    }
}
