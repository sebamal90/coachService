package pl.coachService.db;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import pl.coachService.commons.ActivityDTO;
import pl.coachService.commons.NotExistException;
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
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "activity")
public class Activity implements DbObj<ActivityDTO> {
    @Id
    @GeneratedValue
    @Column(name = "activity_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @Column(name = "startDate")
    private Date startDate;

    @Column(name = "type")
    private String type;

    @Column(name = "distance")
    private Float distance;

    @Column(name = "time")
    private int time;

    @Column(name = "heartRateMax")
    private Integer heartRateMax;

    @Column(name = "heartRateAvg")
    private Integer heartRateAvg;

    @Column(name = "powerMax")
    private Integer powerMax;

    @Column(name = "powerAvg")
    private Integer powerAvg;

    @Column(name = "speedMax")
    private Integer speedMax;

    @Column(name = "speedAvg")
    private Integer speedAvg;

    @Column(name = "cadenceMax")
    private Integer cadenceMax;

    @Column(name = "cadenceAvg")
    private Integer cadenceAvg;

    @Column(name = "calories")
    private Float calories;

    @Column(name = "temperatureAvg")
    private Float temperatureAvg;

    @Column(name = "description")
    private String description;

    public Activity() {

    }

    public Activity(String name, Person person, Date startDate, int time, String type) {
        this.name = name;
        this.person = person;
        this.startDate = startDate;
        this.time = time;
        this.type = type;
    }

    public Activity(Long id, String name, Person person, Date startDate, int time, String type) {
        this.id = id;
        this.name = name;
        this.person = person;
        this.startDate = startDate;
        this.time = time;
        this.type = type;
    }

    //CHECKSTYLE:OFF
    public ActivityDTO toDTO() {
        ActivityDTO dtoObj = new ActivityDTO(this.id, this.name, this.person.getId(), this.startDate.getTime(), this.time, this.type);

        if (this.distance != null) {
            dtoObj.setDistance(this.distance);
        }
        if (this.heartRateMax != null) {
            dtoObj.setHeartRateMax(this.heartRateMax);
        }
        if (this.heartRateAvg != null) {
            dtoObj.setHeartRateAvg(this.heartRateAvg);
        }
        if (this.powerMax != null) {
            dtoObj.setPowerMax(this.powerMax);
        }
        if (this.powerAvg != null) {
            dtoObj.setPowerAvg(this.powerAvg);
        }
        if (this.speedMax != null) {
            dtoObj.setSpeedMax(this.speedMax);
        }
        if (this.speedAvg != null) {
            dtoObj.setSpeedAvg(this.speedAvg);
        }
        if (this.cadenceMax != null) {
            dtoObj.setCadenceMax(this.cadenceMax);
        }
        if (this.cadenceAvg != null) {
            dtoObj.setCadenceAvg(this.cadenceAvg);
        }
        if (this.calories != null) {
            dtoObj.setCalories(this.calories);
        }
        if (this.temperatureAvg != null) {
            dtoObj.setTemperatureAvg(this.temperatureAvg);
        }
        if (this.description != null) {
            dtoObj.setDescription(this.description);
        }
        return dtoObj;
    }
    //CHECKSTYLE:ON

    // DAO

    public static ActivityDTO getDTO(Long id) {
        return (ActivityDTO) UniversalDAO.get(Activity.class, id);
    }

    public static List<ActivityDTO> getAllDTO() {
        return UniversalDAO.getAll(Activity.class);
    }

    public static List<ActivityDTO> getActivityListForPersonDTO(Person person) {
        List<ActivityDTO> resultList = new LinkedList<>();
        Session session = HibernateUtil.openSession();
        try {
            List<Activity> dbObjList;
            dbObjList = session.createCriteria(Activity.class).add(Restrictions.eq("person", person)).list();
            for (Activity act : dbObjList) {
                resultList.add(act.toDTO());
            }
        } finally {
            session.close();
        }

        return resultList;
    }

    public static List<ActivityDTO> getPagedActivityListForPersonDTO(Person person, int pageNumber, String sortBy, int pageSize) {
        List<ActivityDTO> resultList = new LinkedList<>();
        Session session = HibernateUtil.openSession();
        try {
            Criteria criteria = session.createCriteria(Activity.class);
            criteria.add(Restrictions.eq("person", person));
            criteria.setFirstResult((pageNumber - 1) * pageSize);
            criteria.setMaxResults(pageSize);
            criteria.addOrder(Order.asc(sortBy));
            List<Activity> dbObjList = criteria.list();

            if (dbObjList != null) {
                for (Activity act : dbObjList) {
                    resultList.add(act.toDTO());
                }
            }
        } finally {
            session.close();
        }

        return resultList;
    }

    public static Activity get(Long id) throws NotExistException {
        return (Activity) UniversalDAO.getDb(Activity.class, id);
    }

    public static Activity save(Activity activity) {
        UniversalDAO.create(activity);
        return activity;
    }

    public static void update(Activity activity) {
        UniversalDAO.update(activity);
    }

    public static Activity delete(Long activityId) throws NotExistException {
        Activity activityToBeDeleted = Activity.get(activityId);
        if (activityToBeDeleted == null) {
            throw new NotExistException("activity cannot be deleted, because it does not exist");
        }
        UniversalDAO.delete(activityToBeDeleted);

        return activityToBeDeleted;
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

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Float getDistance() {
        return distance;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public Integer getHeartRateMax() {
        return heartRateMax;
    }

    public void setHeartRateMax(Integer heartRateMax) {
        this.heartRateMax = heartRateMax;
    }

    public Integer getHeartRateAvg() {
        return heartRateAvg;
    }

    public void setHeartRateAvg(Integer heartRateAvg) {
        this.heartRateAvg = heartRateAvg;
    }

    public Integer getPowerMax() {
        return powerMax;
    }

    public void setPowerMax(Integer powerMax) {
        this.powerMax = powerMax;
    }

    public Integer getPowerAvg() {
        return powerAvg;
    }

    public void setPowerAvg(Integer powerAvg) {
        this.powerAvg = powerAvg;
    }

    public Integer getSpeedMax() {
        return speedMax;
    }

    public void setSpeedMax(Integer speedMax) {
        this.speedMax = speedMax;
    }

    public Integer getSpeedAvg() {
        return speedAvg;
    }

    public void setSpeedAvg(Integer speedAvg) {
        this.speedAvg = speedAvg;
    }

    public Integer getCadenceMax() {
        return cadenceMax;
    }

    public void setCadenceMax(Integer cadenceMax) {
        this.cadenceMax = cadenceMax;
    }

    public Integer getCadenceAvg() {
        return cadenceAvg;
    }

    public void setCadenceAvg(Integer cadenceAvg) {
        this.cadenceAvg = cadenceAvg;
    }

    public Float getCalories() {
        return calories;
    }

    public void setCalories(Float calories) {
        this.calories = calories;
    }

    public Float getTemperatureAvg() {
        return temperatureAvg;
    }

    public void setTemperatureAvg(Float temperatureAvg) {
        this.temperatureAvg = temperatureAvg;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
