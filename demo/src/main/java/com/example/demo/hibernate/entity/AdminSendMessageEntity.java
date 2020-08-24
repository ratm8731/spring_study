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
public class AdminSendMessageEntity implements Serializable{
	private Long id;
	private String message;
	private Set<AgentEntity> agents = new HashSet<>();
	
	@Id
	@Column(name = "ID")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "MESSAGE")
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name = "ADMIN_MESSAGE",
				joinColumns = @JoinColumn(name = "MESSAGE_ID"),
				inverseJoinColumns = @JoinColumn(name = "AGENT_ID")
	)
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
