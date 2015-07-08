package pl.coachService.commons;

public class ResponseData {
    private int statusCode;
    private String message;
    private String description;

    private String clazzName;

    public ResponseData(int statusCode) {
        this.statusCode = statusCode;
        message = "unknown";
        description = "";
    }

    public ResponseData(int statusCode, String name) {
        this.statusCode = statusCode;
        this.message = name;
        description = "";
    }

    public ResponseData(int statusCode, String name, String description) {
        this.statusCode = statusCode;
        this.message = name;
        this.description = description;
    }

    public ResponseData(int statusCode, String name, String description, String clazzName) {
        this.statusCode = statusCode;
        this.message = name;
        this.description = description;
        this.clazzName = clazzName;
    }

    // Getter and Setter methods

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getResponseCode() {
        return Math.abs(message.hashCode() % Integer.MAX_VALUE);
    }

    public void setResponseCode(int code) {

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getClazzName() {
        return clazzName;
    }

    public void setClazzName(String clazzName) {
        this.clazzName = clazzName;
    }


}
