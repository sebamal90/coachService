package pl.coachService.db.access;

import pl.coachService.commons.NotExistException;
import pl.coachService.commons.access.UserAccountDTO;
import pl.coachService.db.util.DbObj;
import pl.coachService.db.util.UniversalDAO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "users")
public class UserAccount implements DbObj<UserAccountDTO> {

    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    public String getUsername() {

        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //constructors

    public UserAccount() {

    }

    public UserAccount(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserAccountDTO toDTO() {
        UserAccountDTO dtoObj = new UserAccountDTO();
        dtoObj.setUsername(this.username);
        dtoObj.setPassword(this.password);

        return dtoObj;
    }

    public static boolean exists(String username) {
        return UniversalDAO.exists(UserAccount.class, username);
    }

    public static UserAccountDTO getDTO(String username) {
        return (UserAccountDTO) UniversalDAO.get(UserAccount.class, username);
    }

    public static UserAccount get(String username) throws NotExistException {
        return (UserAccount) UniversalDAO.getDb(UserAccount.class, username);
    }

    public static List<UserAccountDTO> getAllDTO() {
        return UniversalDAO.getAll(UserAccount.class);
    }

    public static void save(UserAccount userAcc) {
        UniversalDAO.create(userAcc);
    }

    public static void update(UserAccount userAcc) {
        UniversalDAO.update(userAcc);
    }
}
