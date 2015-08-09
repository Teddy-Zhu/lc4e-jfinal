package com.teddy.lc4e.core.util.shiro;

import com.teddy.lc4e.core.database.mapping.T_User;
import com.teddy.lc4e.core.database.model.User;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class PassDisposer {

    private static RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    private final static String algorithmName = "md5";
    private final static int hashIterations = 2;

    public static void encryptPassword(User user) {

        user.set(T_User.passsalt, randomNumberGenerator.nextBytes().toHex());

        String newPassword = new SimpleHash(algorithmName, user.getStr(T_User.password), ByteSource.Util.bytes(user.getStr(T_User.name) + user.getStr(T_User.passsalt)), hashIterations).toHex();

        user.set(T_User.password, newPassword);
    }
}
