package cn.xbhel.techroad.commons.secure;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Provider;
import java.security.Security;

/**
 * 全局 Provider
 * @author xbhel
 */
public enum GlobalProvider {

    INSTANCE;

    private final Provider provider;

    GlobalProvider() {
        this.provider = new BouncyCastleProvider();
        Security.addProvider(this.provider);
    }

    public Provider getProvider() {
        return provider;
    }
}
