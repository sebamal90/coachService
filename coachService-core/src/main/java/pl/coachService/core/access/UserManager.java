package pl.coachService.core.access;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.coachService.commons.CountDTO;
import pl.coachService.commons.DataIntegrityException;
import pl.coachService.commons.NotExistException;
import pl.coachService.commons.ServerErrorException;
import pl.coachService.commons.access.UserAccountDTO;
import pl.coachService.db.access.UserAccount;
import pl.coachService.db.util.UniversalDAO;

import java.util.List;

public final class UserManager {

    private UserManager() { }

    public static UserAccountDTO hidePassword(UserAccountDTO dto, boolean hide) {
        if (hide && dto != null) {
            dto.setPassword("<hidden>");
        }
        return dto;
    }

    private static String encodePassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

    // User Creation

    public static final String[] FORBIDDEN_USERNAMES = new String[]{
            "list",
            "login",
            "logout"
    };

    public static final int MINIMUM_USERNAME_LENGTH = 3;
    public static final int MAXIMUM_USERNAME_LENGTH = 20;

    public static final String USERNAME_REGEX_PATTERN = "^[a-z0-9_-]*$";

    public static UserAccountDTO addUser(String username, String password) throws DataIntegrityException, ServerErrorException {
        if (username == null || password == null) {
            throw new DataIntegrityException(
                    "missing account creation data: not all required account creation fields specified");
        }
        for (String forbidden : FORBIDDEN_USERNAMES) {
            if (forbidden.equals(username)) {
                throw new DataIntegrityException("username forbidden: user with username "
                        + username + " can't be created, because username is forbidden");
            }
        }
        if (username.length() < MINIMUM_USERNAME_LENGTH) {
            throw new DataIntegrityException("username too short: username should not be shorter than "
                    + MINIMUM_USERNAME_LENGTH);
        }
        if (username.length() > MAXIMUM_USERNAME_LENGTH) {
            throw new DataIntegrityException("username too long: username should not be longer than "
                    + MAXIMUM_USERNAME_LENGTH);
        }
        if (!username.matches(USERNAME_REGEX_PATTERN)) {
            throw new DataIntegrityException("username not matching pattern: username has to match regex pattern "
                    + USERNAME_REGEX_PATTERN);
        }

        if (UserAccount.exists(username)) {
            throw new DataIntegrityException("user already exists: can't create user with username "
                    + username);
        }
        UserAccount.save(new UserAccount(username, encodePassword(password)));

        UserAccountDTO result;
        try {
            result = getUserDTO(username, true);
        } catch (NotExistException ex) {
            throw new ServerErrorException("added user but result is null", ex);
        }


        return result;
    }

    // User Modification

    public static UserAccountDTO modifyUser(String username, UserAccountDTO userAccModData) throws DataIntegrityException, NotExistException {
        if (username == null) {
            throw new DataIntegrityException("no username specified: can't modify unspecified user account");
        }
        UserAccount userAcc = UserAccount.get(username);
        if (userAcc == null) {
            throw new NotExistException("User does not exist");
        }

        boolean modified = false;

        // change password
        if (userAccModData.getPassword() != null) {
            modified = true;
            userAcc.setPassword(encodePassword(userAccModData.getPassword()));
        }

        if (modified) {
            UserAccount.update(userAcc);
        }

        return getUserDTO(username, true);
    }

    // User Get


    public static UserAccountDTO getUserDTO(String username, boolean hidePass) throws NotExistException {

        UserAccountDTO user = UserAccount.getDTO(username);

        if (user == null) {
            throw new NotExistException("User does not exist");
        }

        return hidePassword(UserAccount.getDTO(username), hidePass);
    }

    public static List<UserAccountDTO> getAllDTO() {
        List<UserAccountDTO> userDtoList = UserAccount.getAllDTO();

        for (UserAccountDTO userAcc : userDtoList) {
            hidePassword(userAcc, true);
        }
        return userDtoList;

    }


    public static CountDTO countUsers() {
        return new CountDTO("users", UniversalDAO.count(UserAccount.class));
    }

    public static List<UserAccountDTO> getAllPagedDTO(int pageNumber, String sortBy, int pageSize) {

        String sortByLocal = sortBy;


        if (sortByLocal.equals("password")) {
            sortByLocal = "username";
        }

        List<UserAccountDTO> userDtoList = UniversalDAO.getPage(UserAccount.class, pageNumber, sortByLocal, pageSize);

        for (UserAccountDTO userAcc : userDtoList) {
            hidePassword(userAcc, true);
        }
        return userDtoList;

    }
}
