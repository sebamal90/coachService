package pl.coachService.core;

import org.apache.log4j.Logger;
import pl.coachService.commons.DataIntegrityException;
import pl.coachService.commons.ServerErrorException;
import pl.coachService.core.access.UserManager;

import java.io.IOException;

public class StartupManager {

    public static final String ADMIN_USERNAME = "admin";
    public static final String ADMIN_PASSWORD = "admin";

    public static final String SYSTEM_USERNAME = "coachService";
    public static final String SYSTEM_PASSWORD = "admin";

    static final Logger LOGGER = Logger.getLogger(StartupManager.class);

    public void startup() throws ServerErrorException, IOException, NoSuchFieldException, IllegalAccessException {
        // load configuration files
        ConfigLoader.load();

        // try to create admin user (if already exists -> it's handled as exception)
        try {
            UserManager.addUser(ADMIN_USERNAME, ADMIN_PASSWORD);
        } catch (DataIntegrityException e) {
            LOGGER.info("skipping admin account creation");
        }

        try {
            UserManager.addUser(SYSTEM_USERNAME, SYSTEM_PASSWORD);
        } catch (DataIntegrityException e) {
            LOGGER.info("skipping system account creation");
        }
    }
}
