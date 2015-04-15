/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pustaka.dao.daoImpl;

import com.pustaka.common.GenericDaoImpl;
import com.pustaka.dao.UserInternalDao;
import com.pustaka.model.Customer;
import com.pustaka.model.UserInternal;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Repository;

/**
 *
 * @author dwi
 */
@Repository("userInternalDao")
public class UserInternalDaoImpl extends GenericDaoImpl<UserInternal, Integer> implements UserInternalDao{
    
    @Override
    public UserInternal findById(int id) {
        Criteria crit = getCurrentSession().createCriteria(UserInternal.class);
        crit.add(Restrictions.eq("idUser", id));
        return (UserInternal)crit.uniqueResult();
    }

    @Override
    public UserInternal findByUsernameAndPassword(String username, String password) {
        Criteria crit = getCurrentSession().createCriteria(UserInternal.class);
        crit.add(Restrictions.eq("username", username));
        crit.add(Restrictions.eq("password", password));
        return (UserInternal)crit.uniqueResult();
//        Query query = getCurrentSession().createQuery("from UserInternal where username = :username and password = :password");
//        query.setParameter("username", username);
//        query.setParameter("password", password);
//        return (UserInternal) query.uniqueResult();
    }

    @Override
    public List<UserInternal> getUserList() {
        Criteria crit = getCurrentSession().createCriteria(UserInternal.class);
        return crit.list();
    }

    @Override
    public List<UserInternal> findBySearch(String anything) {
        StringBuilder sb= new StringBuilder("SELECT id_user, nama, username, password, email, level "
                + " FROM USER_INTERNAL WHERE id_user like '%"+anything+"%'"
                + " OR nama like '%"+anything+"%' "
                + " OR username like '%"+anything+"%' "
                + " OR email like '%"+anything+"%' "
                + " OR level like '%"+anything+"%' ");
        System.out.println("query data == " + sb.toString());
        SQLQuery result = getCurrentSession().createSQLQuery(sb.toString());
        result.setResultTransformer(new ResultTransformer() {

            @Override
            public Object transformTuple(Object[] result, String[] aliases) {
                UserInternal user= null;
                
                try {
                    user= new UserInternal();
                    user.setIdUser((Integer) result[0]);
                    user.setNama((String) result[1]);
                    user.setUsername((String) result[2]);
                    user.setPassword((String) result[3]);
                    user.setEmail((String) result[4]);
                    user.setLevel((String) result[5]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return user;
            }

            @Override
            public List transformList(List list) {
                return list;
            }
        }); 
        
        return result.list();
    }

}
