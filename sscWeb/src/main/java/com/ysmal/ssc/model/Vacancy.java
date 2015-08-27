package com.ysmal.ssc.model;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Version;

@Entity
@Table(name = "vacancy")
public class Vacancy implements Serializable {

    //for null record in table
    public Vacancy() {
        this.name = "";
        this.salary = "";
        this.companyName = "";
        this.updatedate = null;
    }

    @Id
    private long id;
    @Version
    @Column(name = "version")
    private long version;
    @Column(name = "name", nullable = true)
    private String name;
    @Column(name = "stringdate")
    private String stringDate;
    @Column(name = "location")
    private String location;
    @Column(name = "vacref")
    private String vacRef;
    @Column(name = "salary")
    private String salary;
    //private Company company;
    @Column(name = "companyname")
    private String companyName;
    @Column(name = "address")
    private String address;
    @Column(name = "searchword")
    private String searchWord;
    @Column(name = "createdate")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Calendar createdate;
    @Column(name = "updatedate")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Calendar updatedate;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStringDate() {
        return stringDate;
    }

    public String getLocation() {
        return location;
    }

    public String getRef() {
        return vacRef;
    }

    public String getSalary() {
        return salary;
    }

    public String getCompanyName() {
        return companyName;
    }

    /*
     public Company getCompany() {
     return company;
     }
     */
    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStringDate(String stringDate) {
        this.stringDate = stringDate;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setRef(String ref) {
        this.vacRef = ref;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /*
     public void setCompany(Company company) {
     this.company = company;
     }
     */
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSearchWord() {
        return searchWord;
    }

    public void setSearchWord(String searchWord) {
        this.searchWord = searchWord;
    }

    //@Version
    public long getVersion() {
        return version;
    }

    public String getVacRef() {
        return vacRef;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public void setVacRef(String vacRef) {
        this.vacRef = vacRef;
    }

    public Calendar getCreatedate() {
        return createdate;
    }

    public Calendar getUpdatedate() {
        return updatedate;
    }

    public void setCreatedate(Calendar createdate) {
        this.createdate = createdate;
    }

    public void setUpdatedate(Calendar updatedate) {
        this.updatedate = updatedate;
    }
    
}
