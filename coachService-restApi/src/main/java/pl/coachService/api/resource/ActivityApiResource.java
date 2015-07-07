package pl.coachService.api.resource;

import org.springframework.stereotype.Component;
import pl.coachService.commons.DataIntegrityException;
import pl.coachService.commons.NotExistException;
import pl.coachService.commons.ActivityDTO;
import pl.coachService.core.ActivityManager;
import pl.coachService.core.ActivityManager;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Component
@Path("/activity")
public class ActivityApiResource {

    private static final String ACTIVITY_ID = "activityId";

    @GET
    @Path("/{personId}/list")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ActivityDTO> getActivityList(
            @PathParam("personId") Long personId,
            @QueryParam("page") int pageNumber,
            @DefaultValue("id") @QueryParam("orderBy") String orderBy,
            @DefaultValue("20") @QueryParam("perPage") int pageSize) throws NotExistException {

        if (pageNumber == 0) {
            return ActivityManager.listAllActivitiesForPerson(personId);
        } else {
            return ActivityManager.listPagedActivitiesForPerson(personId, pageNumber, orderBy, pageSize);
        }
    }

    @GET
    @Path("/list/count")
    @Produces(MediaType.APPLICATION_JSON)
    public int countActivities() {
        return ActivityManager.countActivities();

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ActivityDTO addActivity(ActivityDTO activity) throws DataIntegrityException, NotExistException {
        return ActivityManager.createActivity(activity);
    }

    @PUT
    @Path("/{activityId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ActivityDTO editActivity(@PathParam(ACTIVITY_ID) Long activityId, ActivityDTO activity) throws DataIntegrityException, NotExistException {
        return ActivityManager.editActivity(activityId, activity);
    }

    @DELETE
    @Path("/{activityId}")
    @Produces(MediaType.APPLICATION_JSON)
    public ActivityDTO deleteActivity(@PathParam(ACTIVITY_ID) Long activityId) throws NotExistException {
        return ActivityManager.deleteActivity(activityId);
    }

    @GET
    @Path("/{activityId}")
    @Produces(MediaType.APPLICATION_JSON)
    public ActivityDTO showActivity(@PathParam(ACTIVITY_ID) Long activityId) throws NotExistException {
        return ActivityManager.getActivity(activityId);
    }
}
