package com.example.dao;

import java.util.List;
import javax.persistence.EntityManager;

import org.springframework.transaction.annotation.Transactional;

/*
 * Clase abstracta con metodos de uso recurrente por las clases
 */
public abstract class AbstractDao<T> {
    private Class<T> entityClass;

    public AbstractDao(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();
    

    /******* ACTUALIZAR Y CREAR ********/
    @Transactional
    public void update(T entity) {
    	getEntityManager().merge(entity);
    	getEntityManager().close();
    }


    /**************** ENCONTRAR TODOS ***************/
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }
    
}
