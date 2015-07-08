package pl.coachService.db.access;

import org.hibernate.Session;
import pl.coachService.commons.access.UserSessionDTO;
import pl.coachService.db.HibernateUtil;
import pl.coachService.db.util.DbObj;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "usersSessions")
public class UserSession implements DbObj<UserSessionDTO> {

    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "id")
    private String id;

    @Column(name = "expires")
    private long expires;

    public UserSession() {

    }

    public UserSession(String username, String id, long expires) {
        this.username = username;
        this.id = id;
        this.expires = expires;
    }

    public UserSessionDTO toDTO() {
        UserSessionDTO dtoObj = new UserSessionDTO();
        dtoObj.setUsername(this.username);
        dtoObj.setId(this.id);
        dtoObj.setExpires(this.expires);
        return dtoObj;
    }

    // DAO

    public static UserSessionDTO getDTO(String username) {
        UserSessionDTO userSesDto = null;
        Session session = HibernateUtil.openSession();
        try {
            UserSession userSes = (UserSession) session.get(UserSession.class, username);
            if (userSes != null) {
                userSesDto = userSes.toDTO();
            }
        } finally {
            session.close();
        }
        return userSesDto;
    }

    public static UserSession get(String username) {
        UserSession userSes = null;
        Session session = HibernateUtil.openSession();
        try {
            userSes = (UserSession) session.get(UserSession.class, username);
        } finally {
            session.close();
        }
        return userSes;
    }

    public static boolean save(UserSession userSes) {
        boolean result = true;
        Session session = HibernateUtil.openSession();
        try {
            session.beginTransaction();
            session.save(userSes);
            session.getTransaction().commit();
            if (session.get(UserSession.class, userSes.username) == null) {
                result = false;
            }
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
        return result;
    }

    public static boolean delete(UserSession userSes) {
        boolean result = true;
        Session session = HibernateUtil.openSession();
        try {
            session.beginTransaction();
            session.delete(userSes);
            session.getTransaction().commit();
            if (session.get(UserSession.class, userSes.username) != null) {
                result = false;
            }
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
        return result;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setExpires(long expires) {
        this.expires = expires;
    }

    public long getExpires() {
        return expires;
    }
}
