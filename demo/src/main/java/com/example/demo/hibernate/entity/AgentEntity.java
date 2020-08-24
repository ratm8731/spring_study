package com.example.demo.hibernate.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
@Table(name = "agent")
@NamedQueries({
	@NamedQuery(
				name = "Agent.findAllWithSoftware",
				query = "select distinct agent from AgentEntity agent "+ 
						"left join fetch agent.software s" +
						"left join fetch agent.adminSendMessage message"),
	@NamedQuery(
			name = "Agent.findId",
			query = "select distinct agent from AgentEntity agent "+ 
					"left join fetch agent.software s" +
					"left join fetch agent.adminSendMessage message "+ 
					"where agent.id = :id")
})
public class AgentEntity implements Serializable{
	private Long id;
	private String name;
	private String department;
	private int level;
	private Date createDate;
	private int version;
	private Set<SoftwareEntity> software = new HashSet<>();
	private Set<AdminSendMessageEntity> adminSendMessage = new HashSet<>();
	
	
	public AgentEntity() {}

	public AgentEntity(String name) {
		this.name = name;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "NAME")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "DEPARTMENT")
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	
	@Column(name = "LEVEL")
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "CREATE_DATE")
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	@Version
	@Column(name = "VERSION")
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	
	@OneToMany(mappedBy = "agent", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval=true)
	public Set<SoftwareEntity> getSoftware() {
		return software;
	}
	
	public boolean addSoftware(SoftwareEntity software) {
		software.setAgent(this);
		return getSoftware().add(software);
	}
	
	public void removeSoftware(SoftwareEntity software) {
		getSoftware().remove(software);
	}
	
	public void setSoftware(Set<SoftwareEntity> software) {
		this.software = software;
	}
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name = "ADMIN_MESSAGE",
				joinColumns = @JoinColumn(name = "AGENT_ID"),
				inverseJoinColumns = @JoinColumn(name = "MESSAGE_ID")
	)
	public Set<AdminSendMessageEntity> getAdminSendMessage() {
		return adminSendMessage;
	}

	public void setAdminSendMessage(Set<AdminSendMessageEntity> adminSendMessage) {
		this.adminSendMessage = adminSendMessage;
	}

	@Override
	public String toString() {
		return "AgentEntity [id=" + id + ", name=" + name + ", department=" + department + ", level=" + level
				+ ", createDate=" + createDate + ", version=" + version + "]";
	}
	
}
