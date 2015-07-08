package pl.coachService.core;

import pl.coachService.commons.CountDTO;
import pl.coachService.commons.DataIntegrityException;
import pl.coachService.commons.NotExistException;
import pl.coachService.commons.PersonDTO;
import pl.coachService.db.Person;
import pl.coachService.db.Team;
import pl.coachService.db.util.UniversalDAO;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class PeopleManager {

    protected PeopleManager() {

    }

    public static List<PersonDTO> listPeople() {
        return Person.getAllDTO();
    }

    public static List<PersonDTO> listPagedPeople(int pageNumber, String sortBy, int pageSize) {
        return UniversalDAO.getPage(Person.class, pageNumber, sortBy, pageSize);
    }

    public static CountDTO countPeople() {
        return new CountDTO("people", UniversalDAO.count(Person.class));
    }

    public static PersonDTO getPerson(Long personId) throws NotExistException {
        PersonDTO foundPerson = Person.getDTO(personId);

        if (foundPerson == null) {
            throw new NotExistException("Person not found");
        } else {
            return foundPerson;
        }
    }

    public static PersonDTO createPerson(String firstname, String lastname, String email) throws DataIntegrityException {
        if (firstname == null) {
            throw new DataIntegrityException("firstname has to be specified");
        } else if (lastname == null) {
            throw new DataIntegrityException("lastname has to be specified");
        } else if (email == null) {
            throw new DataIntegrityException("email must be specified");
        }

        //Person person = new Person(username, encodePassword(password), email);
        Person person = new Person(firstname, lastname, email);
        Person result = Person.save(person);

        //return hidePassword(Person.getDTO(result.getId()), true);
        return Person.getDTO(result.getId());
    }

    //CHECKSTYLE:OFF
    public static PersonDTO editPerson(Long personId, PersonDTO dtoObj) throws NotExistException {
        Person person = Person.get(personId);
        if (person == null) {
            throw new NotExistException("person does not exist");
        }

        if (dtoObj.getEmail() != null) {
            person.setEmail(dtoObj.getEmail());
        }
        if (dtoObj.getFirstname() != null) {
            person.setFirstname(dtoObj.getFirstname());
        }
        if (dtoObj.getLastname() != null) {
            person.setLastname(dtoObj.getLastname());
        }
        if (dtoObj.getPhone() != null) {
            person.setPhone(dtoObj.getPhone());
        }
        if (dtoObj.getBirthdate() != null) {
            person.setBirthdate(dtoObj.getBirthdate());
        }
        if (dtoObj.getHeight() != null) {
            person.setHeight(dtoObj.getHeight());
        }
        if (dtoObj.getTimezone() != null) {
            person.setTimezone(dtoObj.getTimezone());
        }
        if (dtoObj.getAddress() != null) {
            person.setAddress(dtoObj.getAddress());
        }
        if (dtoObj.getPostalCode() != null) {
            person.setPostalCode(dtoObj.getPostalCode());
        }
        if (dtoObj.getCity() != null) {
            person.setCity(dtoObj.getCity());
        }
        if (dtoObj.getCountry() != null) {
            person.setCountry(dtoObj.getCountry());
        }

        if (dtoObj.getTeams() != null) {
            Set<Team> teamList = new HashSet();
            for (Long teamId : dtoObj.getTeams()) {
                teamList.add(Team.get(teamId));
            }
            person.setTeams(teamList);
        }

        Person.update(person);

        return Person.getDTO(personId);
    }
    //CHECKSTYLE:ON


    public static PersonDTO deletePerson(Long personId) throws NotExistException {
        return Person.delete(personId).toDTO();
    }
}
