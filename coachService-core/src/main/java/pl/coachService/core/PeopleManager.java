package pl.coachService.core;

import pl.coachService.commons.CountDTO;
import pl.coachService.commons.DataIntegrityException;
import pl.coachService.commons.NotExistException;
import pl.coachService.commons.PersonDTO;
import pl.coachService.db.Person;
import pl.coachService.db.util.UniversalDAO;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

public final class PeopleManager {

    private static String encodePassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

    public static PersonDTO hidePassword(PersonDTO dto, boolean hide) {
        if (hide && dto != null) {
            dto.setPassword("<hidden>");
        }
        return dto;
    }

    protected PeopleManager() {

    }

    public static List<PersonDTO> listPeople() {
        return Person.getAllDTO();
    }

    public static List<PersonDTO> listPagedPeople(int pageNumber, String sortBy, int pageSize) {
        return UniversalDAO.getPage(Person.class, pageNumber, sortBy, pageSize);
    }

    public static CountDTO countPeople(){
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

    public static PersonDTO createPerson(String username, String password, String email) throws DataIntegrityException {
        if (username == null) {
            throw new DataIntegrityException("username has to be specified");
        } else if (password == null) {
            throw new DataIntegrityException("password has to be specified");
        } else if (email == null) {
            throw new DataIntegrityException("email must be specified");
        }

        Person person = new Person(username, encodePassword(password), email);
        Person result = Person.save(person);

        return hidePassword(Person.getDTO(result.getId()), true);
    }

    public static PersonDTO editPerson(Long personId, PersonDTO dto) throws NotExistException {
        Person person = Person.get(personId);
        if (person == null) {
            throw new NotExistException("person does not exist");
        }

        boolean modified = false;

        if (dto.getUsername() != null) {
            person.setUsername(dto.getUsername());
            modified = true;
        }
        if (dto.getEmail() != null) {
            person.setEmail(dto.getEmail());
            modified = true;
        }

        if (modified) {
            Person.update(person);
        }

        return Person.getDTO(personId);
    }


    public static PersonDTO deletePerson(Long personId) throws NotExistException {
        return Person.delete(personId).toDTO();
    }
}
