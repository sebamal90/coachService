package pl.coachService.commons;

import java.sql.Date;

public class ActivityDTO {
    private Long id;
    private String name;
    private long personId;
    private Date startDate;
    private String type;
    private float distance;
    private int time;
    private int heartRateMax;
    private int heartRateAvg;
    private int powerMax;
    private int powerAvg;
    private int speedMax;
    private int speedAvg;
    private int cadenceMax;
    private int cadenceAvg;
    private float calories;
    private float temperatureAvg;
    private String description;

    public ActivityDTO() {
    }

    public ActivityDTO(Long id, String name, long personId, Date startDate, int time, String type) {
        this.id = id;
        this.name = name;
        this.personId = personId;
        this.startDate = startDate;
        this.time = time;
        this.type = type;
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

    public long getPerson() {
        return personId;
    }

    public void setPerson(long personId) {
        this.personId = personId;
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

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getHeartRateMax() {
        return heartRateMax;
    }

    public void setHeartRateMax(int heartRateMax) {
        this.heartRateMax = heartRateMax;
    }

    public int getHeartRateAvg() {
        return heartRateAvg;
    }

    public void setHeartRateAvg(int heartRateAvg) {
        this.heartRateAvg = heartRateAvg;
    }

    public int getPowerMax() {
        return powerMax;
    }

    public void setPowerMax(int powerMax) {
        this.powerMax = powerMax;
    }

    public int getPowerAvg() {
        return powerAvg;
    }

    public void setPowerAvg(int powerAvg) {
        this.powerAvg = powerAvg;
    }

    public int getSpeedMax() {
        return speedMax;
    }

    public void setSpeedMax(int speedMax) {
        this.speedMax = speedMax;
    }

    public int getSpeedAvg() {
        return speedAvg;
    }

    public void setSpeedAvg(int speedAvg) {
        this.speedAvg = speedAvg;
    }

    public int getCadenceMax() {
        return cadenceMax;
    }

    public void setCadenceMax(int cadenceMax) {
        this.cadenceMax = cadenceMax;
    }

    public int getCadenceAvg() {
        return cadenceAvg;
    }

    public void setCadenceAvg(int cadenceAvg) {
        this.cadenceAvg = cadenceAvg;
    }

    public float getCalories() {
        return calories;
    }

    public void setCalories(float calories) {
        this.calories = calories;
    }

    public float getTemperatureAvg() {
        return temperatureAvg;
    }

    public void setTemperatureAvg(float temperatureAvg) {
        this.temperatureAvg = temperatureAvg;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
