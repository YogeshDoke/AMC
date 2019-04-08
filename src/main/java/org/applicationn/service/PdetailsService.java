package org.applicationn.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Named;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;


import org.applicationn.domain.PdetailsEntity;

@Named
public class PdetailsService extends BaseService<PdetailsEntity> implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final long MS_IN_SECOND = 1000;
    private static final long MS_IN_MINUTE = MS_IN_SECOND * 60;
    private static final long MS_IN_HOUR = MS_IN_MINUTE * 60;
    private static final long MS_IN_DAY = MS_IN_HOUR * 24;

    public PdetailsService() {
        super(PdetailsEntity.class);
    }

    @Transactional
    public List<PdetailsEntity> findAllPdetailsEntities() {
        return entityManager.createQuery("SELECT o FROM Pdetails o ", PdetailsEntity.class).getResultList();
    }
////////////////////////////////////////////////////////////////////////////////////////////////////s
    @Transactional
    public List<PdetailsEntity> findAllPdetailsEntities1() {

        Date now = new Date();
        Date next15days = new Date(now.getTime() + (15 * MS_IN_DAY));
        Query q = entityManager.createQuery("Select o from Pdetails o "
                + "where o.edate > :now and o.edate <= :next15days");
        q.setParameter("now", now);
        q.setParameter("next15days", next15days);
        List<PdetailsEntity> results = (List<PdetailsEntity>) q.getResultList();
        return results;

    }
    
    @Transactional
    public List<PdetailsEntity> findAllPdetailsEntities2() {

        Date now = new Date();
        Date next30days = new Date(now.getTime() + (30 * MS_IN_DAY));
        Date next15days = new Date(now.getTime() + (15 * MS_IN_DAY));
        Query q = entityManager.createQuery("Select o from Pdetails o "
                + "where o.edate > :next15days and o.edate <= :next30days");
        q.setParameter("next15days", next15days);
        q.setParameter("next30days", next30days);
        List<PdetailsEntity> results = (List<PdetailsEntity>) q.getResultList();
        return results;

    }
    @Transactional
    public List<Object[]> findAllPdetailsEntities3() {

        Date now = new Date();
        Date next45days = new Date(now.getTime() + (45 * MS_IN_DAY));
        Date next30days = new Date(now.getTime() + (30 * MS_IN_DAY));
        TypedQuery<Object[]> q = entityManager.createQuery("Select o.name,o.edate from Pdetails o "
                + "where o.edate > :next30days and o.edate <= :next45days",Object[].class);
        q.setParameter("next30days", next30days);
        q.setParameter("next45days", next45days);
        List<Object[]> results = (List<Object[]>) q.getResultList();
        return results;

    }
////////////////////////////////////////////////////////////////////////////////////////////////////////e
    @Override
    @Transactional
    public long countAllEntries() {
        return entityManager.createQuery("SELECT COUNT(o) FROM Pdetails o", Long.class).getSingleResult();
    }

    @Override
    protected void handleDependenciesBeforeDelete(PdetailsEntity pdetails) {

        /* This is called before a Pdetails is deleted. Place here all the
         steps to cut dependencies to other entities */
    }

}
