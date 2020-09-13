package com.example.certificategenerator;

public class Stud {
    int id;
    String name;
    String email;
    String domain;
    String duration;
    String date;
    String mobile;

    public Stud(int id, String name, String email, String domain, String duration, String date, String mobile) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.domain = domain;
        this.duration = duration;
        this.date = date;
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return
                "" + id +
                "." + name ;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Stud() {
    }

    public Stud(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
