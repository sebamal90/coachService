package pl.coachService.core;

import pl.coachService.commons.CountDTO;
import pl.coachService.commons.DataIntegrityException;
import pl.coachService.commons.NotExistException;
import pl.coachService.commons.TeamDTO;
import pl.coachService.db.Team;
import pl.coachService.db.util.UniversalDAO;

import java.util.List;

public final class TeamsManager {

    protected TeamsManager() {

    }

    public static List<TeamDTO> listTeams() {
        return Team.getAllDTO();
    }

    public static List<TeamDTO> listPagedTeams(int pageNumber, String sortBy, int pageSize) {
        return UniversalDAO.getPage(Team.class, pageNumber, sortBy, pageSize);
    }

    public static CountDTO countTeams() {
        return new CountDTO("teams", UniversalDAO.count(Team.class));
    }

    public static TeamDTO getTeam(Long teamId) throws NotExistException {
        TeamDTO foundPerson = Team.getDTO(teamId);

        if (foundPerson == null) {
            throw new NotExistException("Team not found");
        } else {
            return foundPerson;
        }
    }

    public static TeamDTO createTeam(TeamDTO teamDTO) throws DataIntegrityException {
        if (teamDTO.getName() == null) {
            throw new DataIntegrityException("name has to be specified");
        }

        Team team = new Team(teamDTO.getName(), teamDTO.getDescription());
        Team result = Team.save(team);

        return Team.getDTO(result.getId());
    }

    public static TeamDTO editTeam(Long teamId, TeamDTO teamDTO) throws NotExistException {
        Team team = Team.get(teamId);
        if (team == null) {
            throw new NotExistException("team does not exist");
        }

        boolean modified = false;

        if (teamDTO.getName() != null) {
            team.setName(teamDTO.getName());
            modified = true;
        }
        if (teamDTO.getDescription() != null) {
            team.setDescription(teamDTO.getDescription());
            modified = true;
        }

        if (modified) {
            Team.update(team);
        }

        return Team.getDTO(teamId);
    }

    public static TeamDTO deleteTeam(Long teamId) throws NotExistException {
        return Team.delete(teamId).toDTO();
    }
}
