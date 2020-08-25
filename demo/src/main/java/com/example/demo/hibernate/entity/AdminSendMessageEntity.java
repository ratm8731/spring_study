package com.example.demo.hibernate.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "admin_send_message")
public class AdminSendMessageEntity extends CommonAbstractEntity{
	@Column(name = "MESSAGE")
	private String message;
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name = "ADMIN_MESSAGE",
				joinColumns = @JoinColumn(name = "MESSAGE_ID"),
				inverseJoinColumns = @JoinColumn(name = "AGENT_ID")
	)
	private Set<AgentEntity> agents = new HashSet<>();
	
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	public Set<AgentEntity> getAgents() {
		return agents;
	}
	public void setAgents(Set<AgentEntity> agents) {
		this.agents = agents;
	}
	
	@Override
	public String toString() {
		return "AdminSendMessageEntity [id=" + id + ", message=" + message + ", agents=" + agents + "]";
	}
	
}
