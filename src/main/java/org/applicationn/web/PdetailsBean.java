package org.applicationn.web;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;
import org.applicationn.domain.PdetailsEntity;
import org.applicationn.service.PdetailsService;
import org.applicationn.service.security.SecurityWrapper;
import org.applicationn.web.util.MessageFactory;
@Named("pdetailsBean")
@ViewScoped
public class PdetailsBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(PdetailsBean.class.getName());
///////////////////////////////////////////////////////////////////////////////////////////////////////s    
    private List<PdetailsEntity> pdetailsList;
    private List<PdetailsEntity> pdetailsList15days;
    private List<PdetailsEntity> pdetailsList30days;
    private List<Object[]> pdetailsList45days;
///////////////////////////////////////////////////////////////////////////////////////////////////////e
    private PdetailsEntity pdetails;
    
    @Inject
    private PdetailsService pdetailsService;
    
    public void prepareNewPdetails() {
        reset();
      this.pdetails = new PdetailsEntity();
        // set any default values now, if you need
        // Example: this.pdetails.setAnything("test");
    }

    public String persist() {

        if (pdetails.getId() == null && !isPermitted("pdetails:create")) {
            return "accessDenied";
        } else if (pdetails.getId() != null && !isPermitted(pdetails, "pdetails:update")) {
            return "accessDenied";
        }
        
        String message;
        
        try {
            
            if (pdetails.getId() != null) {
                pdetails = pdetailsService.update(pdetails);
                message = "message_successfully_updated";
            } else {
                pdetails = pdetailsService.save(pdetails);
                message = "message_successfully_created";
            }
        } catch (OptimisticLockException e) {
            logger.log(Level.SEVERE, "Error occured", e);
            message = "message_optimistic_locking_exception";
            // Set validationFailed to keep the dialog open
            FacesContext.getCurrentInstance().validationFailed();
        } catch (PersistenceException e) {
            logger.log(Level.SEVERE, "Error occured", e);
            message = "message_save_exception";
            // Set validationFailed to keep the dialog open
            FacesContext.getCurrentInstance().validationFailed();
        }
 ////////////////////////////////////////////////////////////////////////////////////////////////////s       
        pdetailsList = null;
        pdetailsList15days = null;
        pdetailsList30days = null;
        pdetailsList45days = null;
/////////////////////////////////////////////////////////////////////////////////////////////////////e
        FacesMessage facesMessage = MessageFactory.getMessage(message);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        
        return null;
    }
    
    public String delete() {
        
        if (!isPermitted(pdetails, "pdetails:delete")) {
            return "accessDenied";
        }
        
        String message;
        
        try {
            pdetailsService.delete(pdetails);
            message = "message_successfully_deleted";
            reset();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occured", e);
            message = "message_delete_exception";
            // Set validationFailed to keep the dialog open
            FacesContext.getCurrentInstance().validationFailed();
        }
        FacesContext.getCurrentInstance().addMessage(null, MessageFactory.getMessage(message));
        
        return null;
    }
    
    public void onDialogOpen(PdetailsEntity pdetails) {
        reset();
        this.pdetails = pdetails;
    }
////////////////////////////////////////////////////////////////////////////////////////////////s   
    public void reset() {
        pdetails = null;
        pdetailsList = null;
        pdetailsList15days = null;
        pdetailsList30days = null;
        pdetailsList45days = null;
        
    }
/////////////////////////////////////////////////////////////////////////////////////////////////e
    public PdetailsEntity getPdetails() {
        if (this.pdetails == null) {
            prepareNewPdetails();
        }
        return this.pdetails;
    }
    
    public void setPdetails(PdetailsEntity pdetails) {
        this.pdetails = pdetails;
    }
    
    public List<PdetailsEntity> getPdetailsList() {
        if (pdetailsList == null) {
            pdetailsList = pdetailsService.findAllPdetailsEntities();
        }
        return pdetailsList;
    }
 ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////s   
    public List<PdetailsEntity> getPdetailsList15days() {
        if (pdetailsList15days == null) {
            pdetailsList15days = pdetailsService.findAllPdetailsEntities1();
        }
        return pdetailsList15days;
    }
    public List<PdetailsEntity> getPdetailsList30days() {
        if (pdetailsList30days == null) {
            pdetailsList30days = pdetailsService.findAllPdetailsEntities2();
        }
        return pdetailsList30days;
    }
    public List<Object[]> getPdetailsList45days() {
        if (pdetailsList45days == null) {
            pdetailsList45days = pdetailsService.findAllPdetailsEntities3();
        }
        return pdetailsList45days;
    }
    
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////e    
    
    

    public void setPdetailsList(List<PdetailsEntity> pdetailsList) {
        this.pdetailsList = pdetailsList;
    }
    public void setPdetailsList15days(List<PdetailsEntity> pdetailsList15days) {
        this.pdetailsList15days = pdetailsList15days;
    }
    
    public void setPdetailsList30days(List<PdetailsEntity> pdetailsList30days) {
        this.pdetailsList30days = pdetailsList30days;
    }
    public void setPdetailsList45days(List<Object[]> pdetailsList45days) {
        this.pdetailsList45days = pdetailsList45days;
    }
 //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////s   
    public boolean isPermitted(String permission) {
        return SecurityWrapper.isPermitted(permission);
    }

    public boolean isPermitted(PdetailsEntity pdetails, String permission) {
        
        return SecurityWrapper.isPermitted(permission);
        
    }
    
}
