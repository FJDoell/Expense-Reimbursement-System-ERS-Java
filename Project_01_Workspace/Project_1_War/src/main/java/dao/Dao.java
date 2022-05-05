package dao;

import java.util.TreeMap;

/**
 * This is the basis for all DAOs. Note that save is both insert and update.
 * You will have to check using getAll().contains.
 * Also be sure to make your own "allTs" private variable.
 * This is simply a TEMPLATE to make sure I do not forget a method.
 * @author darkm
 *
 * @param <T>
 */
public interface Dao<T> {
	T getById(int id);
    
    TreeMap<Integer, T> getAll();
    
    // save and update are the same with TreeMaps
    void save(T t);
    
    void delete(int id);
}
