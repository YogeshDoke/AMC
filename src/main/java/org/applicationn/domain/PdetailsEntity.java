package org.applicationn.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity(name="Pdetails")
@Table(name="\"PDETAILS\"")
public class PdetailsEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Size(max = 50)
    @Column(length = 50, name="\"name\"")
    @NotNull
    private String name;

    @Column(name="\"edate\"")
    @Temporal(TemporalType.DATE)
    private Date edate;

   

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getEdate() {
        return this.edate;
    }

    public void setEdate(Date edate) {
        this.edate = edate;
    }

}
