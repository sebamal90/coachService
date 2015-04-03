package pl.coachService.api.resource;

import pl.coachService.commons.DataIntegrityException;
import pl.coachService.commons.NotExistException;
import pl.coachService.commons.PersonDTO;
import org.springframework.stereotype.Component;
import pl.coachService.core.PeopleManager;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Component
@Path("/person")
public class PersonApiResource {
  /*  @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public PersonListDTO getPersonList(
            @QueryParam("page") int pageNumber,
            @DefaultValue("id") @QueryParam("orderBy") String orderBy,
            @DefaultValue("20") @QueryParam("perPage") int pageSize
    ) {

        if(pageNumber == 0) {
            return new PersonListDTO(PeopleManager.listPeople(), countPeople().getCount());
        } else {
            return new PersonListDTO(PeopleManager.listPagedPeople(pageNumber, orderBy, pageSize), countPeople().getCount());
        }



    }
      */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public PersonDTO addPerson(PersonDTO person) throws DataIntegrityException {
        return PeopleManager.createPerson(person.getUsername(), person.getEmail());
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