package shit.htmlforms.services;

import java.util.Collection;

public interface AbstractService<T> {
    boolean create(T entity);
    boolean update(String id, T entity);
    boolean delete(String id);
    Collection<T> get();
}
