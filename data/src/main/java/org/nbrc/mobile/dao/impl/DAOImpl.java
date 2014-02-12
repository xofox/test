package org.nbrc.mobile.dao.impl;


import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.nbrc.mobile.dao.IDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;


public abstract class DAOImpl<T> implements IDAO<T> {

	protected HibernateTemplate hibernateTemplate;
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		hibernateTemplate = new HibernateTemplate(sessionFactory);
		//hibernateTemplate.setFlushMode(HibernateAccessor.FLUSH_AUTO);
	}
	/**
	 * 保存对象
	 */
	public T save(T obj) {
		hibernateTemplate.save(obj);
		return obj;
	}
    /**
     * 删除对象
     */
	public boolean delete(T obj) {
		try{
			hibernateTemplate.delete(obj);
			return true;
		}catch (Exception e) {
			return false;
		}
	}
   /**
    * 根据id获得对象
    */
	@SuppressWarnings("unchecked")
	public T get(String id) {
		Class<T> clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		return hibernateTemplate.get(clazz, id);
	}
  /**
   * 更新对象
   */
	public T update(T obj) {
		hibernateTemplate.update(obj);
		return obj;
	}
    /**
     * 查询，返回列表
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> query(String hql, Object... params) {
		return (List<T>) hibernateTemplate.find(hql, params);
	}
	/**
	 * 查询所有记录数
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int count() {		
		Class<T> clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		String hql = "select count(*) from " + clazz.getName();
		return count(hql);
	}
	/**
	 * 根据HQL查询返回符合条件的记录数
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public int count(String hql,Object...params) {		
		List result = hibernateTemplate.find(hql,params);		
		return ((Long) result.get(0)).intValue();
	}
  /**
   * 根据HQL分页返回记录数
   */
	@Override
	public List<T> page(final String hql, final int page, final int pagesize, final Object... params) {
		@SuppressWarnings("unchecked")
		List<T> result = hibernateTemplate.executeFind(new HibernateCallback<List<T>>() {
			@Override
			public List<T> doInHibernate(Session session) throws HibernateException,	SQLException {
				Query q = session.createQuery(hql);
				q.setFirstResult(page*pagesize-pagesize);
				q.setMaxResults(pagesize);
				int i=0;
				for (Object o : params) {				
					q.setParameter(i, o);
					i++;
				}
				return q.list();
			}
		});
		return result;
	}
	/**
	 * 根据条件查询返回单个对象
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T queryObj(String hql, Object... params) {
		List<T> result =hibernateTemplate.find(hql, params);
		if(result.size()>0){
			return result.get(0);
		}
		return null;
	}
	
	
	

}
