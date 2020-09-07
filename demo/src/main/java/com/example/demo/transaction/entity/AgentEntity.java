package com.example.demo.transaction.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "agent")
@NamedQueries({
        @NamedQuery(
                name = "Agent.findAll",
                query = "select agent from AgentEntity agent "
        ),
        @NamedQuery(
                name = "Agent.countAll",
                query = "select count(agent) from AgentEntity agent")
})
public class AgentEntity implements Serializable {
    public static final String FIND_ALL = "Agent.findAll";
    public static final String COUNT_ALL = "Agent.countAll";

    public AgentEntity(String name) {
        this.name = name;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, name = "ID")
    private Long id;

    @Version
    @Column(name = "VERSION")
    private int version;

    @Column(name = "NAME")
    private String name;
    @Column(name = "DEPARTMENT")
    private String department;
    @Column(name = "LEVEL")
    private int level;
    @Temporal(TemporalType.DATE)
    @Column(name = "CREATE_DATE")
    private Date createDate;

    @OneToMany(mappedBy = "agent", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval=true)
    private Set<SoftwareEntity> software = new HashSet<>();

    public AgentEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Set<SoftwareEntity> getSoftware() {
        return software;
    }

    public void setSoftware(Set<SoftwareEntity> software) {
        this.software = software;
    }
    public boolean addSoftware(SoftwareEntity software) {
        software.setAgent(this);
        return getSoftware().add(software);
    }

    public void removeSoftware(SoftwareEntity software) {
        getSoftware().remove(software);
    }

    @Override
    public String toString() {
        return "AgentEntity{" +
                "name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", level=" + level +
                ", createDate=" + createDate +
                '}';
    }
}
