package cn.gdrfgdrf.ConnectComputerServer.Utils;

import cn.gdrfgdrf.ConnectComputerServer.Bean.POJO.TokenEntity;
import cn.gdrfgdrf.ConnectComputerServer.Bean.POJO.UserEntity;
import cn.gdrfgdrf.ConnectComputerServer.Enum.RSAKeyEnum;
import io.jsonwebtoken.Jwts;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author gdrfgdrf
 */
public class TokenUtils {
    private TokenUtils() {}

    public static long EXPIRED_TIME;
    public static long NETTY_EXPIRED_TIME;

    public static TokenEntity create(UserEntity user, PrivateKey privateKey) {
        Map<String, Object> claimMap = generateMapFromUser(user);
        long timestamp = System.currentTimeMillis();

        TokenEntity token = new TokenEntity();
        token.setId(null);
        token.setUserId(user.getId());
        token.setContent(Jwts.builder()
                .setId(String.valueOf(user.getId()))
                .setIssuedAt(new Date(timestamp))
                .setExpiration(new Date(timestamp + EXPIRED_TIME))
                .addClaims(claimMap)
                .signWith(privateKey)
                .compact());

        return token;
    }

    public static TokenEntity create(UserEntity user) {
        return create(user, RSAKeyEnum.TOKEN_KEY.getPrivateKey());
    }

    public static Map<String, Object> generateMapFromUser(UserEntity user) {
        Map<String, Object> result = new HashMap<>();

        result.put("id", user.getId());
        result.put("username", user.getUsername());
        result.put("userGroup", user.getUserGroup());

        return result;
    }

    public static void verifyToken(String token, PublicKey publicKey) {
        Jwts.parserBuilder().
                setSigningKey(publicKey).
                build().
                parseClaimsJws(token);
    }

    public static void verifyToken(String token) {
        verifyToken(token, RSAKeyEnum.TOKEN_KEY.getPublicKey());
    }


    public static Map<String, Object> parseToken(String token, PublicKey publicKey) {
        return Jwts.parserBuilder()
                .setSigningKey(publicKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public static Map<String, Object> parseToken(String token) {
        return parseToken(token, RSAKeyEnum.TOKEN_KEY.getPublicKey());
    }

}
