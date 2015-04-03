package pl.coachService.commons;

import java.util.List;

public class PlanDTO {

    private Long id;
    private String title;
    private String description;
    private int daysCount;
    private List<ActivityPlanDTO> activityPlans;

    public PlanDTO() {

    }

    public PlanDTO(Long id, String title, int daysCount) {
        this.id = id;
        this.title = title;
        this.daysCount = daysCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDaysCount() {
        return daysCount;
    }

    public void setDaysCount(int daysCount) {
        this.daysCount = daysCount;
    }

    public List<ActivityPlanDTO> getActivityPlans() {
        return activityPlans;
    }

    public void setActivityPlans(List<ActivityPlanDTO> activityPlans) {
        this.activityPlans = activityPlans;
    }
}
