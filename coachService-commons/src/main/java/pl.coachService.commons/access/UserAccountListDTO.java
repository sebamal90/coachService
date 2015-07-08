package pl.coachService.commons.access;

import pl.coachService.commons.GridResult;

import java.util.ArrayList;
import java.util.List;

public class UserAccountListDTO implements GridResult<UserAccountDTO> {
    private List<UserAccountDTO> rows;
    private long total;

    public UserAccountListDTO() {
        this(new ArrayList<UserAccountDTO>(), 0);
    }

    public UserAccountListDTO(List<UserAccountDTO> rows, long total) {
        this.rows = rows;
        this.total = total;
    }

    @Override
    public List<UserAccountDTO> getRows() {
        return rows;
    }

    @Override
    public long getTotal() {
        return total;
    }
}
