package com.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

public class MySqlDaoFactory {
	
	/**
	 * 
	 * @param name
	 * @param query
	 * @param pageing
	 * @return
	 */
	public List<?> findToListLimit(Class<?> name, Map<String, Object> query, Map<String, Integer> pageing) {
		SessionFactory sf = DataRegistery.getSessionFactory();
		Session session = null;
		Criteria criteria = null;
		List<?> list = null;
		try {
			session = sf.openSession();
			criteria = session.createCriteria(name);

			if (!(query == null || query.isEmpty())) {
				criteria.add(Restrictions.allEq(query));
			}
			if (!(pageing == null || pageing.isEmpty())) {
				criteria.setFirstResult(pageing.get("pageNumber"));
				criteria.setMaxResults(pageing.get("pageReturn"));
			}

			list = criteria.list();
		} catch (HibernateException e) {
			System.out.println("(ERROR) [" + name.getName() + "] findToListLimit error");
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return list;
	}

	public List<?> findToList(Class<?> name, Map<String, Object> query) {
		SessionFactory sf = DataRegistery.getSessionFactory();
		Session session = null;
		Criteria criteria = null;
		List<?> list = null;
		try {
			session = sf.openSession();
			criteria = session.createCriteria(name);

			if (query == null || query.isEmpty()) {
				list = criteria.list();
				return list;
			} else {
				criteria.add(Restrictions.allEq(query));
			}
			list = criteria.list();
		} catch (HibernateException e) {
			System.out.println("(ERROR) [" + name.getName() + "] findToList error");
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return list;
	}

	public Object findById(Class<?> name, Long id) {
		SessionFactory sf = DataRegistery.getSessionFactory();
		Session session = null;
		Criteria criteria = null;
		Object obj = null;
		try {
			session = sf.openSession();
			criteria = session.createCriteria(name);

			criteria.add(Restrictions.idEq(id));
			obj = criteria.uniqueResult();
		} catch (HibernateException e) {
			System.out.println("(ERROR) [" + name.getName() + "] findById error");
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return obj;
	}

	public Object findById(Class<?> name, String id) {
		SessionFactory sf = DataRegistery.getSessionFactory();
		Session session = null;
		Criteria criteria = null;
		Object obj = null;
		try {
			session = sf.openSession();
			criteria = session.createCriteria(name);

			criteria.add(Restrictions.idEq(id));
			obj = criteria.uniqueResult();
		} catch (HibernateException e) {
			System.out.println("(ERROR) [" + name.getName() + "] findById error");
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return obj;
	}

	public Object findById(Class<?> name, int id) {
		SessionFactory sf = DataRegistery.getSessionFactory();
		Session session = null;
		Criteria criteria = null;
		Object obj = null;
		try {
			session = sf.openSession();
			criteria = session.createCriteria(name);

			criteria.add(Restrictions.idEq(id));
			obj = criteria.uniqueResult();
		} catch (HibernateException e) {
			System.out.println("(ERROR) [" + name.getName() + "] findById error");
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return obj;
	}

	public Object find(Class<?> name, Map<String, Object> query) {
		SessionFactory sf = DataRegistery.getSessionFactory();
		Session session = null;
		Criteria criteria = null;
		Object obj = null;
		try {
			session = sf.openSession();
			criteria = session.createCriteria(name);

			if (query == null || query.isEmpty()) {
				obj = criteria.uniqueResult();
				return obj;
			} else {
				criteria.add(Restrictions.allEq(query));
			}
			obj = criteria.uniqueResult();
		} catch (HibernateException e) {
			System.out.println("(ERROR) [" + name.getName() + "] find error");
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return obj;
	}

	public boolean save(Class<?> name, Object obj) {
		SessionFactory sf = DataRegistery.getSessionFactory();
		Session session = null;
		Transaction tr = null;
		int isSave = 0;
		try {
			session = sf.openSession();
			tr = session.beginTransaction();
			Object object = session.save(name.getName(), obj);
			if (object instanceof String) {
				System.out.println("~~~~~~~~~~~~~~~~" + object);
				isSave = (object == null ? 0 : 1);
			} else {
				isSave = (Integer) object;
			}
			tr.commit();
		} catch (HibernateException e) {
			System.out.println("(ERROR) [" + name.getName() + "] save error");
			e.printStackTrace();
			tr.rollback();
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return isSave > 0 ? true : false;
	}

	public boolean deleteById(Class<?> name, int id) {
		Object obj = findById(name, id);
		SessionFactory sf = DataRegistery.getSessionFactory();
		Session session = null;
		Transaction tr = null;
		boolean isDelete = true;
		try {
			session = sf.openSession();
			tr = session.beginTransaction();
			session.delete(name.getName(), obj);
			tr.commit();
		} catch (HibernateException e) {
			System.out.println("(ERROR) [" + name.getName() + "] delete error");
			e.printStackTrace();
			tr.rollback();
			isDelete = false;
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return isDelete;
	}

	public boolean deleteById(Class<?> name, String id) {
		Object obj = findById(name, id);
		SessionFactory sf = DataRegistery.getSessionFactory();
		Session session = null;
		Transaction tr = null;
		boolean isDelete = true;
		try {
			session = sf.openSession();
			tr = session.beginTransaction();
			session.delete(name.getName(), obj);
			tr.commit();
		} catch (HibernateException e) {
			System.out.println("(ERROR) [" + name.getName() + "] delete error");
			e.printStackTrace();
			tr.rollback();
			isDelete = false;
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return isDelete;
	}

	public boolean deleteById(Class<?> name, Long id) {
		Object obj = findById(name, id);
		SessionFactory sf = DataRegistery.getSessionFactory();
		Session session = null;
		Transaction tr = null;
		boolean isDelete = true;
		try {
			session = sf.openSession();
			tr = session.beginTransaction();
			session.delete(name.getName(), obj);
			tr.commit();
		} catch (HibernateException e) {
			System.out.println("(ERROR) [" + name.getName() + "] delete error");
			e.printStackTrace();
			tr.rollback();
			isDelete = false;
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return isDelete;
	}

	public boolean delete(Class<?> name, Object obj) {
		SessionFactory sf = DataRegistery.getSessionFactory();
		Session session = null;
		Transaction tr = null;
		boolean isDelete = true;
		try {
			session = sf.openSession();
			tr = session.beginTransaction();
			session.delete(name.getName(), obj);
			tr.commit();
		} catch (HibernateException e) {
			System.out.println("(ERROR) [" + name.getName() + "] delete error");
			e.printStackTrace();
			tr.rollback();
			isDelete = false;
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return isDelete;
	}

	public boolean update(Class<?> name, Object obj) {
		SessionFactory sf = DataRegistery.getSessionFactory();
		Session session = null;
		Transaction tr = null;
		boolean isUpdate = true;
		try {
			session = sf.openSession();
			tr = session.beginTransaction();
			session.update(name.getName(), obj);
			tr.commit();
		} catch (HibernateException e) {
			System.out.println("(ERROR) [" + name.getName() + "] update error");
			e.printStackTrace();
			tr.rollback();
			isUpdate = false;
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return isUpdate;
	}
}
