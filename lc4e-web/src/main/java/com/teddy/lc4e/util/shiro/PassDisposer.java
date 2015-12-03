package com.teddy.lc4e.util.shiro;

import com.teddy.lc4e.database.model.User;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class PassDisposer {

    private static RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    private final static String algorithmName = "md5";
    private final static int hashIterations = 2;

    public static void encryptPassword(User user) {

        user.set(User.S_PASSSALT, randomNumberGenerator.nextBytes().toHex());

        String newPassword = new SimpleHash(algorithmName, user.getStr(User.S_PASSWORD), ByteSource.Util.bytes(user.getStr(User.S_NAME) + user.getStr(User.S_PASSSALT)), hashIterations).toHex();

        user.set(User.S_PASSWORD, newPassword);
    }
}
