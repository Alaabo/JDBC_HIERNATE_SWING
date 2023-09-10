/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1.dao;

import com.mycompany.mavenproject1.model.etudiant;
import com.mycompany.mavenproject1.util.HibernateUtil;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;



import java.util.List;
import org.hibernate.query.Query;


public class etudiantDao {
    float moy = 10;
        
    public List<etudiant> getEtudiant(){
        Transaction transaction = null;
        List<etudiant> et = null;
        
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            //start the transaction
            transaction = session.beginTransaction();

            //we use hql syntax here
            //et = session.createQuery("from etudiant").list();
            //

            CriteriaBuilder cr = session.getCriteriaBuilder();
            CriteriaQuery<etudiant> cq = cr.createQuery(etudiant.class);
            Root<etudiant> etudroot = cq.from(etudiant.class);
            cq.select(etudroot);
            et = session.createQuery(cq).list();
            //using criteria
            
            //commit the change
            transaction.commit();
        }catch (Exception e){
            System.out.println(e);

            //undo changes
            if(transaction != null){transaction.rollback();}
        }
        return et;
    }
    public List<etudiant> getMoyB10(){
        
        Transaction transaction = null;
        List<etudiant> et = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            //start the transaction
            transaction = session.beginTransaction();

            //we use hql syntax here
            
            //
            CriteriaBuilder cr = session.getCriteriaBuilder();
            CriteriaQuery<etudiant> cq = cr.createQuery(etudiant.class);
            Root<etudiant> root = cq.from(etudiant.class);
            Predicate predicatecontrolpositive = cr.greaterThan(root.get("control"), moy);
            Predicate predicateineropositive = cr.greaterThan(root.get("intero"), moy);
            Predicate general = cr.or(predicatecontrolpositive ,predicateineropositive );
            cq.where(general);
            et = session.createQuery(cq).list();
            
            //using criteria
            
            //commit the change
            transaction.commit();
        }catch (Exception e){
            System.out.println(e);

            //undo changes
            if(transaction != null){transaction.rollback();}
        }
        return et;
    }
    public List<etudiant> getMoyenU10(){
        Transaction transaction = null;
        List<etudiant> et = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            //start the transaction
            transaction = session.beginTransaction();

            //we use hql syntax here
           CriteriaBuilder cr = session.getCriteriaBuilder();
            CriteriaQuery<etudiant> cq = cr.createQuery(etudiant.class);
            Root<etudiant> root = cq.from(etudiant.class);
            Predicate predicatecontrolpositive = cr.lessThan(root.get("control"), moy);
            Predicate predicateineropositive = cr.lessThan(root.get("intero"), moy);
            Predicate general = cr.or(predicatecontrolpositive ,predicateineropositive );
            cq.where(general);
            et = session.createQuery(cq).list();
            
            
            //commit the change
            transaction.commit();
        }catch (Exception e){
            System.out.println(e);

            //undo changes
            if(transaction != null){transaction.rollback();}
        }
        return et;
    }
    public etudiant getEtudiantbyid(int id){
        Transaction transaction = null;
        etudiant et = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            //start the transaction
            transaction = session.beginTransaction();

            //save the data
             CriteriaBuilder cr = session.getCriteriaBuilder();
            CriteriaQuery<etudiant> cq = cr.createQuery(etudiant.class);
            Root<etudiant> root = cq.from(etudiant.class);
            Predicate predicateid = cr.equal(root.get("id"), id);
           
            cq.where(predicateid);
            et = session.createQuery(cq).getSingleResult();
            
            //commit the change
            transaction.commit();
        }catch (Exception e){
            System.out.println(e);

            //undo changes
            if(transaction != null){transaction.rollback();}
        }
        return et;
    }
    //save etudiant
    public void saveEtudiant(etudiant E){
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            //start the transaction
            transaction = session.beginTransaction();

            //save the data
            session.save(E);

            //commit the change
            transaction.commit();
        }catch (Exception e){
            System.out.println(e);

            //undo changes
            if(transaction != null){transaction.rollback();}
        }
    }
    
     
   
    //Update etudiant
    public void updateEtudiant(etudiant E){
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            //start the transaction
            transaction = session.beginTransaction();

            //update it if already existing
//            session.update(E);
            //usingHQL
            Query q = session.createQuery("update etudiant set name= :n , intero= :i , control= :c where id= :x");
            q.setParameter("n" , E.getName());
            q.setParameter("i" , E.getIntero());
            q.setParameter("c" , E.getControl());
            q.setParameter("x" , E.getId());
            q.executeUpdate();
            
            //commit the change
            transaction.commit();
        }catch (Exception e){
            System.out.println(e);

            //undo changes
            if(transaction != null){transaction.rollback();}
        }
    }
    //getById etudiant
   
    //getAll etudiant
    
    
    
    //Delete etudiant
    public void deleteEtudiant(int id){
        Transaction transaction = null;
        etudiant et = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            //start the transaction
            transaction = session.beginTransaction();

            Query q = session.createQuery("delete from etudiant where id="+id);
            
            q.executeUpdate();
            //save the data
//            et = session.get(etudiant.class, id);
//            session.delete(et);
            //commit the change
            transaction.commit();
        }catch (Exception e){
            System.out.println(e);

            //undo changes
            if(transaction != null){transaction.rollback();}
        }

    }

      
     
}
