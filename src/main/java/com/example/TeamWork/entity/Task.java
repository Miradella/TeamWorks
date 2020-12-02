package com.example.TeamWork.entity;
/*
import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    private int taskId;
    @Column
    private int projectId;
    @Column
    private String taskName;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "employeeId")
    private People people;
    @Column
    private Date deadline;
    @Column
    private int priority;

    @ManyToMany (mappedBy = "tasks")
    private List<Team> teamList;

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getProjectId() {
        return projectId;
    }

    public Date getDeadline() {
        return deadline;
    }

    public int getTaskId() {
        return taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public List<Team> getTeamList() {
        return teamList;
    }

    public void setTeamList(List<Team> teamList) {
        this.teamList = teamList;
    }

    public People getPeople() {
        return people;
    }

    public void setPeople(People people) {
        this.people = people;
    }
}

*/
