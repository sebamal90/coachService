package pl.coachService.commons;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TeamDTO {

    private Long id;
    private String name;
    private String description;

    public TeamDTO() {

    }

    public TeamDTO(Long id, String name) {
        this.id = id;
        this.name = name;
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

    public void setName(String username) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
