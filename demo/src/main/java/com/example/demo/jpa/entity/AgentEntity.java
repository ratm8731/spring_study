package com.example.demo.jpa.entity;

import org.dom4j.tree.AbstractEntity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name = "agent")
@NamedQueries({
	@NamedQuery(
			name = "Agent.findAll",
			query = "select agent from AgentEntity agent "
	),
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
@SqlResultSetMapping(
		name = "agentResult",
		entities = @EntityResult(entityClass = AgentEntity.class)
)
public class AgentEntity extends CommonAbstractEntity {
	public static final String FIND_ALL = "Agent.findAll";
	public static final String FIND_AGENT_BY_ID = "Agent.findId";
	public static final String FIND_ALL_WITH_SOFTWARE = "Agent.findAllWithSoftware";

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
	@ManyToMany(fetch=FetchType.EAGER,cascade = CascadeType.MERGE)
	@JoinTable(name = "ADMIN_MESSAGE",
				joinColumns = @JoinColumn(name = "AGENT_ID"),
				inverseJoinColumns = @JoinColumn(name = "MESSAGE_ID")
	)
	private Set<AdminSendMessageEntity> adminSendMessage = new HashSet<>();
	
	
	public AgentEntity() {}

	public AgentEntity(String name) {
		this.name = name;
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
	
	
	public Set<AdminSendMessageEntity> getAdminSendMessage() {
		return adminSendMessage;
	}

	public void setAdminSendMessage(Set<AdminSendMessageEntity> adminSendMessage) {
		this.adminSendMessage = adminSendMessage;
	}

	@Override
	public String toString() {
		return "AgentEntity [id=" + id + ", name=" + name + ", department=" + department + ", level=" + level
				+ ", createDate=" + createDate + "]";
	}
	
}
