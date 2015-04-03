package pl.coachService.core;

import pl.coachService.commons.ServerErrorException;

import java.io.IOException;

public class StartupManager {

    public static final String ADMIN_USERNAME = "admin";
    public static final String ADMIN_PASSWORD = "admin";

    public void startup() throws ServerErrorException, IOException, NoSuchFieldException, IllegalAccessException {
        // load configuration files
        ConfigLoader.load();
    }
}
