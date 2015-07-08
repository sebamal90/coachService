package pl.coachService.core.access;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import pl.coachService.commons.ServerErrorException;
import pl.coachService.core.StartupManager;
import pl.coachService.db.access.UserSession;

import java.math.BigInteger;
import java.security.SecureRandom;

public final class UserAuth {
    private UserAuth() { }

    public static String addSession(String username, long expires) throws ServerErrorException {
        String id = new SessionIdentifierGenerator().nextSessionId();

        //remove last user session if exist
        if (UserSession.get(username) != null) {
            if (!UserAuth.deleteSession(username)) {
                throw new ServerErrorException("session was not saved in database. ");
            }
        }

        //add user session
        UserSession.save(new UserSession(username, id, expires));
        if (UserSession.get(username) == null) {
            throw new ServerErrorException("session was not saved in database. ");
        }

        return id;
    }

    private static final class SessionIdentifierGenerator {
        private SecureRandom random = new SecureRandom();

        public String nextSessionId() {
            return new BigInteger(130, random).toString(32);
        }
    }

    public static boolean deleteSession(String username) {
        UserSession us = UserSession.get(username);
        UserSession.delete(us);
        if (UserSession.get(username) == null) {
            return true;
        } else {
            return false;
        }
    }

    // returns coachservice if run by system
    public static String getActiveUsername() {
        String author = StartupManager.SYSTEM_USERNAME;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            UserDetails principal = (UserDetails) authentication.getPrincipal();
            if (principal != null) {
                author = principal.getUsername();
            }
        }
        return author;
    }
}


