package org.nbrc.mobile.dao;

import java.util.List;

/**
 * 
 * @author apple
 * @description 
 * @param <T>
 */
public interface IDAO<T> {
	T get(String id);
	T save(T obj);
	boolean delete(T obj);
	T update(T obj) ;
	List<T> query(String hql,Object... params);
	T queryObj(String hql,Object...params );
	int count();
	int count(String hql,Object...params);
	List<T> page(String hql,int page,int pagesize,Object... params);
}
