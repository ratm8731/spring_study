package com.example.demo.transaction.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "software")
public class SoftwareEntity implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false, name = "ID")
	private Long id;

	@Version
	@Column(name = "VERSION")
	private int version;

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
		return "Software [id=" + id + ", name=" + name + ", insertDate=" + insertDate + "]";
	}

}
