package pl.coachService.db;

import org.hibernate.Session;
import pl.coachService.commons.NotExistException;
import pl.coachService.commons.TeamDTO;
import pl.coachService.db.util.DbObj;
import pl.coachService.db.util.UniversalDAO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "team")
public class Team implements DbObj<TeamDTO> {

    @Id
    @GeneratedValue
    @Column(name = "team_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "teams")
    private Set<Person> persons;

    public Team() {

    }

    public Team(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Team(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public TeamDTO toDTO() {
        TeamDTO dtoObj = new TeamDTO(this.getId(), this.name);
        if (this.description != null) {
            dtoObj.setDescription(this.description);
        }
        return dtoObj;
    }

    // DAO

    public static TeamDTO getDTO(Long id) {
        return (TeamDTO) UniversalDAO.get(Team.class, id);
    }

    public static List<TeamDTO> getAllDTO() {
        return UniversalDAO.getAll(Team.class);
    }

    public static Team get(Long id) throws NotExistException {
        return (Team) UniversalDAO.getDb(Team.class, id);
    }

    public static Team save(Team team) {
        UniversalDAO.create(team);
        return team;
    }

    public static void update(Team team) {
        UniversalDAO.update(team);
    }

    public static Team delete(Long teamId) throws NotExistException {
        Session session = HibernateUtil.openSession();
        Team teamToBeDeleted = null;

        try {
            session.beginTransaction();
            teamToBeDeleted = (Team) session.get(Team.class, teamId);
            if (teamToBeDeleted == null) {
                throw new NotExistException("activity cannot be deleted, because it does not exist");
            }

            session.delete(teamToBeDeleted);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }

        return teamToBeDeleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Person> getPersons() {
        return persons;
    }

    public void setPersons(Set<Person> persons) {
        this.persons = persons;
    }
}
