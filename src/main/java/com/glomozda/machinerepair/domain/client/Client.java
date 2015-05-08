package com.glomozda.machinerepair.domain.client;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.glomozda.machinerepair.domain.order.Order;
import com.glomozda.machinerepair.domain.user.User;

@NamedQueries({
	@NamedQuery(name="Client.findAll", query="SELECT c FROM Client c"),
	@NamedQuery(name="Client.findAllWithFetching", query="SELECT c FROM Client c "
			+ "LEFT JOIN FETCH c.clientUser "
			+ "ORDER BY c.clientName"),
	@NamedQuery(name="Client.findAllClientIds", query="SELECT c.clientId FROM Client c"),
	@NamedQuery(name="Client.findClientByUserId", query="SELECT c FROM Client c "
			+ "WHERE c.clientUser.userId = :id"),
	@NamedQuery(name="Client.findClientByUserIdWithFetching", query="SELECT c FROM Client c "
			+ " LEFT JOIN FETCH c.clientUser "
			+ "WHERE c.clientUser.userId = :id"),
	@NamedQuery(name="Client.findClientByLogin", query="SELECT c FROM Client c "
			+ "WHERE c.clientUser.login = :login"),
	@NamedQuery(name="Client.findClientByLoginWithFetching", query="SELECT c FROM Client c "
			+ " LEFT JOIN FETCH c.clientUser "
			+ "WHERE c.clientUser.login = :login"),
	@NamedQuery(name="Client.setClientNameById", query="UPDATE Client c SET clientName = :name "
			+ " WHERE c.clientId = :id"),
	@NamedQuery(name="Client.countAll", query="SELECT COUNT(c) FROM Client c")
})
@Entity
@Table(name = "clients")
public class Client {
	
	@Id
	@GeneratedValue
	@Column(name = "clients_id")
	private Long clientId;
	
	@Column(name = "name")
	@NotEmpty
	private String clientName;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "users_id")
	private User clientUser;
	
	@OneToMany(mappedBy="client")
	private List<Order> orders = new ArrayList<Order>();	
	
	public Client(){
	}
	
	public void addOrder(final Order order) {
        this.orders.add(order);
        if (order.getClient() != this) {
            order.setClient(this);
        }
    }
	
	public List<Order> getOrders() {
		return orders;
	}
	
	public Long getClientId() {
		return clientId;
	}
	
	public void setClientId(final Long clientId) {
		this.clientId = clientId;
	}
	
	public String getClientName() {
		return clientName;
	}
	
	public void setClientName(final String clientName) {
		this.clientName = clientName;
	}
	
	public User getClientUser() {
		return clientUser;
	}
	
	public void setClientUser(final User clientUser) {
		this.clientUser = clientUser;
	}	
	
	@Override
	public int hashCode() {
		int hash = 3;
		hash = 13 * hash + (this.clientName != null ? this.clientName.hashCode() : 0);		
		return hash;
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Client other = (Client) obj;
		if ((this.clientName == null) ? other.clientName != null : !this.clientName.equals(other.clientName)) {
			return false;
		}
		return true;
	}    

	@Override
	public String toString() {
		if (clientUser == null) {
			return "client{" + "clientId=" + clientId + ", clientName=" + clientName +
				", clientUser=NO USER}\n";
		} else {
			return "client{" + "clientId=" + clientId + ", clientName=" + clientName +
					", clientUser=" + clientUser.getLogin() + '}'+"\n";
		}
	}
}
