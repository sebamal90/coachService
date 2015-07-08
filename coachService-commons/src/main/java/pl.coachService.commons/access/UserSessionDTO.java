package pl.coachService.commons.access;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserSessionDTO {
    private String username;
    private String id;
    private long expires;

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
