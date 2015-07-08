package pl.coachService.commons.access;

public class TokenDTO {

    private final String token;

    public TokenDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }
}
