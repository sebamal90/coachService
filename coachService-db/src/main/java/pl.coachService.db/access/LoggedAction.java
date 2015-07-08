package pl.coachService.db.access;

import pl.coachService.commons.access.LoggedActionDTO;
import pl.coachService.db.util.DbObj;
import pl.coachService.db.util.UniversalDAO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "actions")
public class LoggedAction implements DbObj<LoggedActionDTO> {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "date")
    private Date date;

    @Column(name = "type")
    private String type;

    @ManyToOne
    @JoinColumn(name = "username")
    private UserAccount user;

    public LoggedAction() {
    }

    public LoggedAction(Long id, Date date, String type) {
        this.id = id;
        this.date = date;
        this.type = type;
    }

    public LoggedActionDTO toDTO() {
        LoggedActionDTO dtoObj = new LoggedActionDTO();
        dtoObj.setId(this.id);
        dtoObj.setDate(this.date);
        dtoObj.setType(this.type);
        dtoObj.setUser(this.user.toDTO());
        return dtoObj;
    }


    public static void create(LoggedAction action) {
        UniversalDAO.create(action);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public UserAccount getUser() {
        return user;
    }

    public void setUser(UserAccount user) {
        this.user = user;
    }
}
