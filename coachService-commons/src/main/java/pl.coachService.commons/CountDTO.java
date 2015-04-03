package pl.coachService.commons;

public class CountDTO {

    private String resource;
    private int count;

    public CountDTO(String resource, int count) {
        this.resource = resource;
        this.count = count;
    }

    public String getResource() {
        return resource;
    }

    public int getCount() {
        return count;
    }
}
