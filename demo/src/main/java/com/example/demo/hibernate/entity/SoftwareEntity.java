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
public class SoftwareEntity implements Serializable{
	private Long id;
	private String name;
	private Date insertDate;
	private int version;
	private AgentEntity agent;
	
	public SoftwareEntity() {}
	public SoftwareEntity(String name) {
		this.name = name;
		this.insertDate = new Date();
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
	
	@Temporal(TemporalType.DATE)
	@Column(name = "INSERT_DATE")
	public Date getInsertDate() {
		return insertDate;
	}
	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}
	
	@Version
	@Column(name = "VERSION")
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	@ManyToOne
	@JoinColumn(name = "AGENT_ID")
	public AgentEntity getAgent() {
		return agent;
	}
	public void setAgent(AgentEntity agent) {
		this.agent = agent;
	}
	@Override
	public String toString() {
		return "Software [id=" + id + ", name=" + name + ", insertDate=" + insertDate + ", version=" + version
				+ ", agent=" + agent + "]";
	}
	
}
