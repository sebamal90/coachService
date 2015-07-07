package pl.coachService.core;

import pl.coachService.commons.DataIntegrityException;
import pl.coachService.commons.NotExistException;
import pl.coachService.commons.ActivityDTO;
import pl.coachService.db.Activity;
import pl.coachService.db.Person;
import pl.coachService.db.util.UniversalDAO;

import java.sql.Date;
import java.util.List;

public final class ActivityManager {

    protected ActivityManager() {

    }

    public static List<ActivityDTO> listAllActivitiesForPerson(Long personId) throws NotExistException {
        return Activity.getActivityListForPersonDTO(Person.get(personId));
    }

    public static List<ActivityDTO> listPagedActivitiesForPerson(Long personId, int pageNumber, String sortBy, int pageSize) throws NotExistException {
        return Activity.getPagedActivityListForPersonDTO(Person.get(personId), pageNumber, sortBy, pageSize);
    }

    public static int countActivities() {
        return UniversalDAO.count(Activity.class);
    }

    public static ActivityDTO getActivity(Long activityId) throws NotExistException {
        ActivityDTO foundPerson = Activity.getDTO(activityId);

        if (foundPerson == null) {
            throw new NotExistException("Activity not found");
        } else {
            return foundPerson;
        }
    }

    public static ActivityDTO createActivity(ActivityDTO activityDTO) throws DataIntegrityException, NotExistException {
        if (activityDTO.getName() == null) {
            throw new DataIntegrityException("name has to be specified");
        } else if (activityDTO.getPersonId() == null) {
            throw new DataIntegrityException("person has to be specified");
        } else if (activityDTO.getStartDate() == null) {
            throw new DataIntegrityException("startdate has to be specified");
        } else if (activityDTO.getTime() == null) {
            throw new DataIntegrityException("time has to be specified");
        }  else if (activityDTO.getType() == null) {
            throw new DataIntegrityException("type has to be specified");
        }

        Activity activity = new Activity(activityDTO.getName(), Person.get(activityDTO.getPersonId()), new Date(activityDTO.getStartDate()),
                activityDTO.getTime(), activityDTO.getType());
        Activity result = Activity.save(activity);

        return Activity.getDTO(result.getId());
    }

    public static ActivityDTO editActivity(Long activityId, ActivityDTO activityDTO) throws NotExistException {
        Activity activity = Activity.get(activityId);
        if (activity == null) {
            throw new NotExistException("person does not exist");
        }

        boolean modified = false;

        if (activityDTO.getName() != null) {
            activity.setName(activityDTO.getName());
            modified = true;
        }
        if (activityDTO.getDescription() != null) {
            activity.setDescription(activityDTO.getDescription());
            modified = true;
        }

        if (modified) {
            Activity.update(activity);
        }

        return Activity.getDTO(activityId);
    }

    public static ActivityDTO deleteActivity(Long activityId) throws NotExistException {
        return Activity.delete(activityId).toDTO();
    }
}
