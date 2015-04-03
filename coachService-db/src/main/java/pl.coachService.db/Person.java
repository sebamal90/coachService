package pl.coachService.db;

import pl.coachService.commons.NotExistException;
import pl.coachService.commons.PersonDTO;
import pl.coachService.commons.TeamDTO;
import pl.coachService.db.util.DbObj;
import pl.coachService.db.util.UniversalDAO;
import org.hibernate.Session;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "person")
public class Person implements DbObj<PersonDTO> {
    private static final String PERSON_FIELD_NAME = "person";

    @Id
    @GeneratedValue
    @Column(name = "person_id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "birthdate")
    private Date birthdate;

    @Column(name = "height")
    private Integer height;

    @Column(name = "timezone")
    private String timezone;

    @Column(name = "address")
    private String address;

    @Column(name = "postalcode")
    private String postalcode;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @OneToMany(mappedBy = PERSON_FIELD_NAME)
    private Set<Activity> activities;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "person_team", joinColumns = {
            @JoinColumn(name = "person_id") }, inverseJoinColumns = { @JoinColumn(name = "team_id") })
    private Set<Team> teams;

    public Person() {

    }

    public Person(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public Person(Long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    //CHECKSTYLE:OFF
    public PersonDTO toDTO() {
        Set<TeamDTO> teamsDTO = new HashSet<>();
        PersonDTO dtoObj = new PersonDTO(this.id, this.username, this.email);

        if (this.firstname != null) {
            dtoObj.setFirstname(this.firstname);
        }
        if (this.lastname != null) {
            dtoObj.setLastname(this.lastname);
        }
        if (this.phone != null) {
            dtoObj.setPhone(this.phone);
        }
        if (this.birthdate != null) {
            dtoObj.setBirthdate(this.birthdate);
        }
        if (this.height != null) {
            dtoObj.setHeight(this.height);
        }
        if (this.timezone != null) {
            dtoObj.setTimezone(this.timezone);
        }
        if (this.address != null) {
            dtoObj.setAddress(this.address);
        }
        if (this.postalcode != null) {
            dtoObj.setPostalCode(this.postalcode);
        }
        if (this.city != null) {
            dtoObj.setCity(this.city);
        }
        if (this.country != null) {
            dtoObj.setCountry(this.country);
        }
        if (teams != null) {
            for (Team team : teams) {
                teamsDTO.add(team.toDTO());
            }
        }
        dtoObj.setTeams(teamsDTO);

        return dtoObj;
    }
    //CHECKSTYLE:ON

    // DAO
    public static PersonDTO getDTO(Long id) {
        return (PersonDTO) UniversalDAO.get(Person.class, id);
    }

    public static List<PersonDTO> getAllDTO() {
        return UniversalDAO.getAll(Person.class);
    }

    public static Person get(Long id) throws NotExistException {
        return (Person) UniversalDAO.getDb(Person.class, id);
    }

    public static Person save(Person person) {
        UniversalDAO.create(person);
        return person;
    }

    public static void update(Person person) {
        UniversalDAO.update(person);
    }

    public static Person delete(Long personId) throws NotExistException {
        Session session = HibernateUtil.openSession();
        Person personToBeDeleted = null;

        try {
            session.beginTransaction();
            personToBeDeleted = (Person) session.get(Person.class, personId);
            if (personToBeDeleted == null) {
                throw new NotExistException("person cannot be deleted, because it does not exist");
            }
   /*
            for (DetectedFace face : personToBeDeleted.getFaces()) {
                face.setLinkedBy(null);
                face.setPerson(null);
                session.update(face);
            }
   */
            session.delete(personToBeDeleted);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }

        return personToBeDeleted;
    }

    public static String getPersonFieldName() {
        return PERSON_FIELD_NAME;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Set<Team> getTeams() {
        return teams;
    }

    public void setTeams(Set<Team> teams) {
        this.teams = teams;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Set<Activity> getActivities() {
        return activities;
    }

    public void setActivities(Set<Activity> activities) {
        this.activities = activities;
    }
}
