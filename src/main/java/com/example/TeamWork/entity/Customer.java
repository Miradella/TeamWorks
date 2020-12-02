package com.example.TeamWork.entity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@Entity
@Table (name = "customers")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Customer {
    private int Id;
    private String name;
    private String phone_number;
    private String email;

  private static final long serialVersionUID = 4048798961366546485L;
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
    public String getPhone_number() {
        return phone_number;
    }
    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    @Column
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
