package com.example.TeamWork.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;

@Entity
@Table (name = "customers")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Customer implements Serializable {
    private int Id;
    private String name;
    private String phoneNumber;
    private String email;
    /*@OneToMany(mappedBy = "customer")
    private List<Project> projects;
  private static final long serialVersionUID = 4048798961366546485L;
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return Id;
    }
    public void setId(int customerId) {
        this.Id = customerId;
    }

    @Column
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Column
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Column
    @Email
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
/*
  public List<Project> getProjects() {
    return projects;
  }

  public void setProjects(List<Project> projects) {
    this.projects = projects;
  }

 */
}
