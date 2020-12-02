package com.example.TeamWork.entity;
/*
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "teams")
public class Team {
    @Id
    private int teamId;
    @Column
    private int maxCountOfPeople;
    @Column
    private String specialization;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable (name="tasks", joinColumns = @JoinColumn(name = "teamId"),
            inverseJoinColumns = @JoinColumn(name="taskId"))
    private List<Task> taskList;
    public String getSpecialization() {
        return specialization;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getTeamId() {
        return teamId;
    }

    public int getMaxCountOfPeople() {
        return maxCountOfPeople;
    }

    public void setMaxCountOfPeople(int maxCountOfPeople) {
        this.maxCountOfPeople = maxCountOfPeople;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public Team(int id){
        this.teamId=id;
    }
    public Team(int id, int maxCountOfPeople, String specialization){
        this.teamId=id;
        this.maxCountOfPeople=maxCountOfPeople;
        this.specialization=specialization;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }
}

*/
