package com.example.demo.jpa.entity;

import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "software")
public class SoftwareEntity extends CommonAbstractEntity {
	@Column(name = "NAME")
	private String name;
	@Temporal(TemporalType.DATE)
	@Column(name = "INSERT_DATE")
	private Date insertDate;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "AGENT_ID")
	private AgentEntity agent;

	public SoftwareEntity() {}
	public SoftwareEntity(String name, Date insertDate) {
		this.name = name;
		this.insertDate = insertDate;
	}


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}


	public Date getInsertDate() {
		return insertDate;
	}
	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}


	public AgentEntity getAgent() {
		return agent;
	}
	public void setAgent(AgentEntity agent) {
		this.agent = agent;
	}
	@Override
	public String toString() {
		return "Software [id=" + id + ", name=" + name + ", insertDate=" + insertDate + ", agent=" + agent + "]";
	}
	
}
