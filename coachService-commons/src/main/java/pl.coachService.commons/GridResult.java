package pl.coachService.commons;

import java.util.Collection;

public interface GridResult<T> {

    Collection<T> getRows();

    long getTotal();

}
