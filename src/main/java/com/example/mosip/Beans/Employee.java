package com.example.mosip.Beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "employee")
public class Employee {
    @Id
    public int empid;
    @Column(nullable = false)
    public String empname;
    @Column(nullable = false)
    public int age;
    @Column(nullable = false)
    public String address;
public Employee()
{

}
    public Employee(int empid, String empname, int age, String address) {
        this.empid = empid;
        this.empname = empname;
        this.age = age;
        this.address = address;
    }

    public int getEmpid() {
        return empid;
    }

    public void setEmpid(int empid) {
        this.empid = empid;
    }

    public String getEmpname() {
        return empname;
    }

    public void setEmpname(String empname) {
        this.empname = empname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
