package br.com.jhdev.models.dao;
import java.util.List;
/**
 * Dao
 */
public interface Dao {

	public int create(Object obj);
	public Object read(int id);
	public List<Object> readAll();
	public int update(Object obj);
	public int delete(Object obj);
}