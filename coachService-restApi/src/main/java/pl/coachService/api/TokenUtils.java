package pl.coachService.api;

import org.springframework.security.crypto.codec.Hex;

import pl.coachService.commons.access.UserSessionDTO;
import pl.coachService.db.access.UserSession;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class TokenUtils {

    //static final Logger LOGGER = Logger.getLogger(TokenUtils.class);
    public static final String MAGIC_KEY = "CoachServiceApp";

    private TokenUtils() { };

    public static String getUserNameFromToken(String authToken) {
        if (null == authToken) {
            return null;
        }

        String[] parts = authToken.split(":");
        return parts[0];
    }

    public static boolean validateToken(String authToken, String username) {
        String[] parts = authToken.split(":");
        String signature = parts[1];

        UserSessionDTO userSesDto = UserSession.getDTO(username);
        if (userSesDto == null) {
            //LOGGER.info("session for user " + username + " not found");
            return false;
        } else if (userSesDto.getExpires() < System.currentTimeMillis()) {
            UserSession us = UserSession.get(username);
            if (us != null) {
                UserSession.delete(us);
            }
            //LOGGER.info("session for user " + username + " expired");
            return false;
        }

        return signature.equals(TokenUtils.computeSignature(username, userSesDto.getId()));
    }

    public static String createToken(String username, String sessionId) {
        StringBuilder tokenBuilder = new StringBuilder();
        tokenBuilder.append(username);
        tokenBuilder.append(":");
        tokenBuilder.append(TokenUtils.computeSignature(username, sessionId));

        return tokenBuilder.toString();
    }

    public static String computeSignature(String username, String sessionId) {
        StringBuilder signatureBuilder = new StringBuilder();
        signatureBuilder.append(username);
        signatureBuilder.append(":");
        signatureBuilder.append(sessionId);
        signatureBuilder.append(":");
        signatureBuilder.append(sessionId);
        signatureBuilder.append(":");
        signatureBuilder.append(TokenUtils.MAGIC_KEY);

        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("No MD5 algorithm available!");
        }

        return new String(Hex.encode(digest.digest(signatureBuilder.toString().getBytes())));
    }

    public static String extractAuthTokenFromRequest(HttpServletRequest httpRequest) {
        /* Get token from header */
        String authToken = httpRequest.getHeader("X-Auth-Token");

        /* If token not found get it from request parameter */
        if (authToken == null) {
            authToken = httpRequest.getParameter("token");
        }

        return authToken;
    }
}
