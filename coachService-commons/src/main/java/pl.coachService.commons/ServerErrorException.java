package pl.coachService.commons;


public class ServerErrorException extends Exception {

    public ServerErrorException(String message) {
        super(message);
    }

    public ServerErrorException(String message, Throwable parentException) {
        super(message, parentException);
    }
}
