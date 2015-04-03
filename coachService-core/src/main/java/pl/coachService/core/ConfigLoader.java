package pl.coachService.core;

import pl.coachService.db.HibernateUtil;

import java.io.File;
import java.io.IOException;

public final class ConfigLoader {
    private static final String HOME_LOCATION = System.getProperty("user.home");
    private static final String COACHSERVICE_DIR = System.getProperty("coachService.home", HOME_LOCATION + "/.coachService");

    public static final String HIBERNATE_CONFIG_RESOURCE = System.getProperty("coachService.db.cfg.res", "hibernate.cfg.xml");
    public static final String HIBERNATE_CONFIG_FILE = System.getProperty("coachService.db.cfg.file", "hibernate.cfg.xml");
    public static final String HIBERNATE_CONFIG_FILE_PATH = getHome() + "/" + HIBERNATE_CONFIG_FILE;

    private ConfigLoader() {

    }

    public static void load() throws IOException, NoSuchFieldException, IllegalAccessException {
        // initialization of hibernate with custom config file or with built-in-jar config file
        File hibernateConfigFile = new File(HIBERNATE_CONFIG_FILE_PATH);
        if (hibernateConfigFile.isFile()) {
            HibernateUtil.init(HibernateUtil.getConfig(hibernateConfigFile));

        } else {
            HibernateUtil.init(HibernateUtil.getConfig(HIBERNATE_CONFIG_RESOURCE));
        }

        File homeDir = new File(getHome());
        homeDir.mkdirs();
    }

    public static String getHome() {
        return COACHSERVICE_DIR;
    }

}
