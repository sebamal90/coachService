package pl.coachService.db.util;

import pl.coachService.commons.NotExistException;
import pl.coachService.db.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.StatelessSession;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public final class UniversalDAO {
    private UniversalDAO() {

    }

    public static boolean exists(Class clazz, Serializable id) {
        boolean result = true;
        Session session = HibernateUtil.openSession();
        try {
            DbObj object = (DbObj) session.get(clazz, id);
            if (object == null) {
                result = false;
            }
        } finally {
            session.close();
        }

        return result;
    }

    // returns DTO
    public static Object get(Class clazz, Serializable id) {
        Object dto = null;
        Session session = HibernateUtil.openSession();
        try {
            DbObj object = (DbObj) session.get(clazz, id);
            if (object != null) {
                dto = object.toDTO();
            }
        } finally {
            session.close();
        }

        return dto;
    }

    // returns DB object
    public static Object getDb(Class clazz, Serializable id) throws NotExistException {
        Object object = null;
        Session session = HibernateUtil.openSession();
        try {
            object = session.get(clazz, id);
            if (object == null) {
                throw new NotExistException(clazz.getName() + " with id " + id + " does not exist in database");
            }
        } finally {
            session.close();
        }

        return object;
    }

    // returns DTO
    public static List getAll(Class clazz) {
        List dtoList = new LinkedList<>();
        Session session = HibernateUtil.openSession();
        try {
            List<DbObj> dbObjList;

            dbObjList = session.createCriteria(clazz).list();

            if (dbObjList != null) {
                for (DbObj object : dbObjList) {
                    dtoList.add(object.toDTO());
                }
            }
        } finally {
            session.close();
        }

        return dtoList;
    }

    public static List getPage(Class clazz, int pageNumber, String sortBy, int pageSize) {
        List dtoList = new LinkedList<>();
        Session session = HibernateUtil.openSession();
        try {
            Criteria criteria = session.createCriteria(clazz);
            criteria.setFirstResult((pageNumber - 1) * pageSize);
            criteria.setMaxResults(pageSize);
            criteria.addOrder(Order.asc(sortBy));
            List<DbObj> dbObjList = criteria.list();

            if (dbObjList != null) {
                for (DbObj object : dbObjList) {
                    dtoList.add(object.toDTO());
                }
                //throw new NotExistException(clazz.getName() + " list does not exist in database");
            }
        } finally {
            session.close();
        }

        return dtoList;
    }


    public static List getFlaggedPage(Class clazz, int pageNumber, String sortBy, int pageSize, Map<String, Object> flags) {
        List dtoList = new LinkedList<>();
        Session session = HibernateUtil.openSession();
        try {
            List<DbObj> dbObjList;

            Criteria criteria = constructCriteria(clazz, session, flags);

            criteria.setFirstResult((pageNumber - 1) * pageSize);
            criteria.setMaxResults(pageSize);
            criteria.addOrder(Order.asc(sortBy));
            dbObjList = criteria.list();

            if (dbObjList != null) {
                for (DbObj object : dbObjList) {
                    dtoList.add(object.toDTO());
                }
            }
        } finally {
            session.close();
        }

        return dtoList;
    }

    public static List getFlagged(Class clazz, Map<String, Object> flags) {
        List dtoList = new LinkedList<>();
        Session session = HibernateUtil.openSession();
        try {
            List<DbObj> dbObjList;

            Criteria criteria = constructCriteria(clazz, session, flags);
            dbObjList = criteria.list();

            if (dbObjList != null) {
                for (DbObj object : dbObjList) {
                    dtoList.add(object.toDTO());
                }
            }
        } finally {
            session.close();
        }

        return dtoList;
    }


    private static Criteria constructCriteria(Class clazz, Session session, Map<String, Object> flags) {

        Criteria criteria = session.createCriteria(clazz);

        for (Map.Entry<String, Object> entry : flags.entrySet()) {

            if (entry.getValue().equals("isNull")) {
                criteria.add(Restrictions.isNull(entry.getKey()));
            } else if (entry.getValue().equals("notNull")) {
                criteria.add(Restrictions.isNotNull(entry.getKey()));
            } else {
                criteria.add(Restrictions.eq(entry.getKey(), entry.getValue()));
            }

        }

        return criteria;
    }

    public static int count(Class clazz) {
        int count = 0;
        StatelessSession session = HibernateUtil.getSessionFactory().openStatelessSession();
        try {
            count = (int) (long) session.createCriteria(clazz)
                    .setProjection(Projections.rowCount())
                    .uniqueResult();
        } finally {
            session.close();
        }
        return count;

    }

    public static int count(Class clazz, Map<String, Object> flags) {

        int rowCount;
        Session session = HibernateUtil.openSession();
        try {

            Criteria criteria = constructCriteria(clazz, session, flags);

            criteria.setProjection(Projections.rowCount());

            rowCount = (int) (long) criteria.uniqueResult();

        } finally {
            session.close();
        }

        return rowCount;
    }




    // accepts DB object
    // returns ID
    public static Serializable create(Object object) {
        Serializable key = null;
        Session session = HibernateUtil.openSession();
        try {
            session.beginTransaction();
            key = session.save(object);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
        return key;
    }

    // accepts DB object
    public static void update(Object object) {
        Session session = HibernateUtil.openSession();
        try {
            session.beginTransaction();
            session.update(object);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    // accepts DB object
    public static void delete(Object object) {
        Session session = HibernateUtil.openSession();
        try {
            session.beginTransaction();
            session.delete(object);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}
