package com.ysmal.ssc.model;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name = "history")
public class ChangedAtribute implements Serializable {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "idrecord")
    private long idRecord;
    @Column(name = "aributename")
    private String atributeName;
    @Column(name = "oldvalue")
    private String oldValue;
    @Column(name = "newvalue")
    private String newValue;
    @Column(name = "createdate")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Calendar createdate;

    public long getId() {
        return id;
    }

    public long getIdRecord() {
        return idRecord;
    }

    public String getAtributeName() {
        return atributeName;
    }

    public String getOldValue() {
        return oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setIdRecord(long idRecord) {
        this.idRecord = idRecord;
    }

    public void setAtributeName(String atributeName) {
        this.atributeName = atributeName;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public Calendar getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Calendar createdate) {
        this.createdate = createdate;
    }
    
    
}
