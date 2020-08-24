package com.example.demo.hibernate.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
@Table(name = "software")
public class SoftwareEntity extends AbstractEntity{
	@Column(name = "NAME")
	private String name;
	@Temporal(TemporalType.DATE)
	@Column(name = "INSERT_DATE")
	private Date insertDate;
	@ManyToOne
	@JoinColumn(name = "AGENT_ID")
	private AgentEntity agent;
	
	public SoftwareEntity() {}
	public SoftwareEntity(String name) {
		this.name = name;
		this.insertDate = new Date();
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
