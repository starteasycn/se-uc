package cn.starteasy.uaa.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * 员工，不是我们内部的一个用户，但他可以一键转换为一个用户.                                               
 * 
 */
@ApiModel(description = ""
    + "员工，不是我们内部的一个用户，但他可以一键转换为一个用户.                                          "
    + "")
@Entity
@Table(name = "employee")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "hire_date")
    private ZonedDateTime hireDate;

    @Column(name = "salary")
    private Long salary;

    @Column(name = "commission_pct")
    private Long commissionPct;

    @ManyToMany(mappedBy = "employees")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Company> companies = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Employee firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Employee lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public Employee email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Employee phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ZonedDateTime getHireDate() {
        return hireDate;
    }

    public Employee hireDate(ZonedDateTime hireDate) {
        this.hireDate = hireDate;
        return this;
    }

    public void setHireDate(ZonedDateTime hireDate) {
        this.hireDate = hireDate;
    }

    public Long getSalary() {
        return salary;
    }

    public Employee salary(Long salary) {
        this.salary = salary;
        return this;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }

    public Long getCommissionPct() {
        return commissionPct;
    }

    public Employee commissionPct(Long commissionPct) {
        this.commissionPct = commissionPct;
        return this;
    }

    public void setCommissionPct(Long commissionPct) {
        this.commissionPct = commissionPct;
    }

    public Set<Company> getCompanies() {
        return companies;
    }

    public Employee companies(Set<Company> companies) {
        this.companies = companies;
        return this;
    }

    public Employee addCompany(Company company) {
        companies.add(company);
        company.getEmployees().add(this);
        return this;
    }

    public Employee removeCompany(Company company) {
        companies.remove(company);
        company.getEmployees().remove(this);
        return this;
    }

    public void setCompanies(Set<Company> companies) {
        this.companies = companies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Employee employee = (Employee) o;
        if(employee.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, employee.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Employee{" +
            "id=" + id +
            ", firstName='" + firstName + "'" +
            ", lastName='" + lastName + "'" +
            ", email='" + email + "'" +
            ", phoneNumber='" + phoneNumber + "'" +
            ", hireDate='" + hireDate + "'" +
            ", salary='" + salary + "'" +
            ", commissionPct='" + commissionPct + "'" +
            '}';
    }
}
