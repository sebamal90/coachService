package pl.coachService.db.util;

public interface DbObj<Dto> {
    Dto toDTO();
    //void copyValues(Dto dto);
}
