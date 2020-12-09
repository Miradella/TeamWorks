package com.example.TeamWork.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "projects")
public class Project  implements Serializable  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    @Column
    private String projectname;
    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private  Date deadline;
    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private Date projectstart;
    @Column
    private  int priority;


  @ManyToOne( cascade = CascadeType.REFRESH)
  @JoinColumn(name = "customer_Id", nullable = false)
  private Customer customer;


    public int getPriority() {
        return priority;
    }


    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Date getProjectStart() {
        return projectstart;
    }

    public int getProjectId() {
        return Id;
    }

    public String getProjectName() {
        return projectname;
    }

    public void setProjectId(int projectId) {
        this.Id = projectId;
    }

    public void setProjectName(String projectName) {
        this.projectname = projectName;
    }

    public void setProjectStart(Date projectStart) {
        this.projectstart = projectStart;
    }

    public Date getDeadline() {
        return deadline;
    }


    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }


    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}



