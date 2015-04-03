package pl.coachService.commons;

import java.sql.Date;

public class ActivityPlanDTO {

    private Long id;
    private Date date;
    private String description;

    public ActivityPlanDTO() {

    }

    public ActivityPlanDTO(Long id) {
        this.id = id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
