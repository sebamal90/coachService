package pl.coachService.api.resource;

import org.springframework.stereotype.Component;
import pl.coachService.commons.DataIntegrityException;
import pl.coachService.commons.NotExistException;
import pl.coachService.commons.TeamDTO;
import pl.coachService.core.TeamsManager;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;


@Component
@Path("/team")
public class TeamApiResource {

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public List<TeamDTO> getTeamsList(){
        return TeamsManager.listTeams();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public TeamDTO addTeam(TeamDTO team) throws DataIntegrityException {
        return TeamsManager.createTeam(team);
    }

    @PUT
    @Path("/{personId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public TeamDTO editTeam(@PathParam("teamId") Long teamId, TeamDTO team) throws DataIntegrityException, NotExistException {
        return TeamsManager.editTeam(teamId, team);

    }

    @DELETE
    @Path("/{teamId}")
    @Produces(MediaType.APPLICATION_JSON)
    public TeamDTO deleteTeam(@PathParam("teamId") Long teamId) throws NotExistException {
        return TeamsManager.deleteTeam(teamId);
    }

    @GET
    @Path("/{teamId}")
    @Produces(MediaType.APPLICATION_JSON)
    public TeamDTO showTeam(@PathParam("teamId") Long teamId) throws NotExistException {
        return TeamsManager.getTeam(teamId);
    }
}
