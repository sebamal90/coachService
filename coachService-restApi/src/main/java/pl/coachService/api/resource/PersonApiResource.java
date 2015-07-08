package pl.coachService.api.resource;

import pl.coachService.commons.DataIntegrityException;
import pl.coachService.commons.NotExistException;
import pl.coachService.commons.PersonDTO;
import org.springframework.stereotype.Component;
import pl.coachService.commons.access.UserAccountDTO;
import pl.coachService.core.PeopleManager;

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
@Path("/person")
public class PersonApiResource {

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public List<PersonDTO> getPersonList() {
        return PeopleManager.listPeople();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public PersonDTO addPerson(PersonDTO person, UserAccountDTO user) throws DataIntegrityException {
        return PeopleManager.createPerson(person.getFirstname(), person.getLastname(), person.getEmail());
    }

    @PUT
    @Path("/{personId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public PersonDTO editPerson(@PathParam("personId") Long personId, PersonDTO person) throws DataIntegrityException, NotExistException {
        return PeopleManager.editPerson(personId, person);
    }

    @DELETE
    @Path("/{personId}")
    @Produces(MediaType.APPLICATION_JSON)
    public PersonDTO deletePerson(@PathParam("personId") Long personId) throws NotExistException {
        return PeopleManager.deletePerson(personId);
    }

    @GET
    @Path("/{personId}")
    @Produces(MediaType.APPLICATION_JSON)
    public PersonDTO showPerson(@PathParam("personId") Long personId) throws NotExistException {
        return PeopleManager.getPerson(personId);
    }
}
