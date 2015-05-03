package com.glomozda.machinerepair.domain.order;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.glomozda.machinerepair.domain.client.Client;
import com.glomozda.machinerepair.domain.machine.Machine;
import com.glomozda.machinerepair.domain.repairtype.RepairType;

@SuppressWarnings({"PMD.CommentRequired", "PMD.LawOfDemeter"})
@NamedQueries({
	@NamedQuery(name="Order.findAll", query="SELECT o FROM Order o"),
	@NamedQuery(name="Order.findAllWithFetching",
		query="SELECT o FROM Order o "
			+ "LEFT JOIN FETCH o.client LEFT JOIN FETCH o.repairType "
			+ "LEFT JOIN FETCH o.machine om "
			+ "LEFT JOIN FETCH om.machineServiceable "
			+ "ORDER BY o.start DESC"),
	@NamedQuery(name="Order.findOrderById",	query="SELECT o FROM Order o"
			+ " WHERE o.orderId = :id"),
	@NamedQuery(name="Order.findOrderByIdWithFetching",	query="SELECT o FROM Order o"
			+ " LEFT JOIN FETCH o.client LEFT JOIN FETCH o.repairType"
			+ " LEFT JOIN FETCH o.machine as om LEFT JOIN FETCH om.machineServiceable"
			+ " WHERE o.orderId = :id"),
	@NamedQuery(name="Order.findOrdersByStatus", query="SELECT o FROM Order o"
			+ " WHERE o.status = :status"),
	@NamedQuery(name="Order.countOrdersByStatus", query="SELECT COUNT(o) FROM Order o"
			+ " WHERE o.status = :status"),
	@NamedQuery(name="Order.findOrdersByStatusWithFetching", query="SELECT o FROM Order o"
			+ " LEFT JOIN FETCH o.client LEFT JOIN FETCH o.repairType"
			+ " LEFT JOIN FETCH o.machine as om LEFT JOIN FETCH om.machineServiceable"
			+ " WHERE o.status = :status"
			+ " ORDER BY o.client.clientName"),
	@NamedQuery(name="Order.findOrdersByClientId", query="SELECT o FROM Order o"
			+ " WHERE o.client.clientId = :id"),
	@NamedQuery(name="Order.countOrdersByClientId", query="SELECT Count(o) FROM Order o"
			+ " WHERE o.client.clientId = :id"),
	@NamedQuery(name="Order.findOrdersByClientIdAndStatusWithFetching",
		query="SELECT o FROM Order o"
			+ " LEFT JOIN FETCH o.client LEFT JOIN FETCH o.repairType"
			+ " LEFT JOIN FETCH o.machine as om LEFT JOIN FETCH om.machineServiceable"
			+ " WHERE o.client.clientId = :id AND o.status = :status"),
	@NamedQuery(name="Order.findOrderByClientIdAndMachineSNAndNotFinished",
		query="SELECT o FROM Order o"			
			+ " WHERE o.client.clientId = :id"
			+ " AND o.machine.machineSerialNumber = :sn"
			+ " AND o.status != 'finished'"),
	@NamedQuery(name="Order.countOrdersByClientIdAndStatus",
		query="SELECT COUNT(o) FROM Order o"			
			+ " WHERE o.client.clientId = :id AND o.status = :status"),
	@NamedQuery(name="Order.confirmOrderById", 
		query="UPDATE Order o SET status = 'started', manager = :manager"
			+ " WHERE o.orderId = :id AND o.status = 'pending'"),
	@NamedQuery(name="Order.setOrderStatusById", query="UPDATE Order o SET status = :status "
			+ " WHERE o.orderId = :id"),
	@NamedQuery(name="Order.cancelOrderById", query="DELETE FROM Order o"
			+ " WHERE o.orderId = :id AND o.status = 'pending'"),
	@NamedQuery(name="Order.countAll", query="SELECT COUNT(o) "
					+ "FROM Order o"),
	@NamedQuery(name="Order.findCurrentOrdersByClientIdWithFetching", 
		query="SELECT o FROM Order o"
			+ " LEFT JOIN FETCH o.client LEFT JOIN FETCH o.repairType"
			+ " LEFT JOIN FETCH o.machine as om LEFT JOIN FETCH om.machineServiceable"
			+ " WHERE o IN"
			+ " (SELECT o1 FROM Order o1"
			+ " WHERE o1.client.clientId = :id AND o1.status = 'pending')"
			+ " OR o IN"
			+ " (SELECT o2 FROM Order o2"			
			+ " WHERE o2.client.clientId = :id AND o2.status = 'ready')"
			+ " OR o IN"
			+ " (SELECT o3 FROM Order o3"			
			+ " WHERE o3.client.clientId = :id AND o3.status = 'started')"),
	@NamedQuery(name="Order.countCurrentOrdersByClientId", 
			query="SELECT COUNT(o) FROM Order o"				
				+ " WHERE o IN"
				+ " (SELECT o1 FROM Order o1"		
				+ " WHERE o1.client.clientId = :id AND o1.status = 'pending')"
				+ " OR o IN"
				+ " (SELECT o2 FROM Order o2"
				+ " WHERE o2.client.clientId = :id AND o2.status = 'ready')"
				+ " or o IN"
				+ " (SELECT o3 FROM Order o3"
				+ " WHERE o3.client.clientId = :id AND o3.status = 'started')"),
	@NamedQuery(name="Order.updateOrderById", query="UPDATE Order o "
			+ "SET o.repairType = :rt,"
			+ "o.start = :start, "
			+ "o.status = :status, "
			+ "o.manager = :manager "
			+ "WHERE o.orderId = :id")
			
})
@Entity
@Table(name = "orders")
public class Order {
	
	@Id
	@GeneratedValue
	@Column(name = "orders_id")
	private Long orderId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "client_id")	
	private Client client;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "repair_type_id")
	private RepairType repairType;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "machine_id")	
	private Machine machine;
	
	@Column(name = "start")	
	private Date start;
	
	@Column(name = "status")
	@NotEmpty
	private String status;
	
	@Column(name = "manager")
	@NotEmpty
	private String manager;
	
	public Order(){
	}

	public Order(final Date start) {		
		this.start = start;
		this.status = "pending";
		this.manager = "-";
	}
	
	public Order(final Date start, final String status) {		
		this.start = start;
		this.status = status;
		this.manager = "-";
	}
	
	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(final Long orderId) {
		this.orderId = orderId;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(final Client client) {
        this.client = client;
        if (!client.getOrders().contains(this)) {
            client.getOrders().add(this);
        }
    }

	public RepairType getRepairType() {
		return repairType;
	}

	public void setRepairType(final RepairType repairType) {
		this.repairType = repairType;
		
		if (!repairType.getOrders().contains(this)) {
            repairType.getOrders().add(this);
        }
	}

	public Machine getMachine() {
		return machine;
	}

	public void setMachine(final Machine machine) {
		this.machine = machine;
		
        if (!machine.getOrders().contains(this)) {
            machine.getOrders().add(this);
        }
	}

	public Date getStart() {
		return start;
	}

	public void setStart(final Date start) {
		this.start = start;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(final String status) {		
		this.status = status;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 13 * hash + (this.client != null ? this.client.getClientId().hashCode() : 0);
		hash = 13 * hash + (this.repairType != null ? this.repairType.getRepairTypeId().hashCode() : 0);
		hash = 13 * hash + (this.machine != null ? this.machine.getMachineId().hashCode() : 0);
		hash = 13 * hash + (this.start != null ? this.start.hashCode() : 0);
		hash = 13 * hash + (this.manager != null ? this.manager.hashCode() : 0);
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
		final Order other = (Order) obj;
		if ((this.client == null) ? other.client != null : !this.client.equals(other.client)) {
			return false;
		}
		if ((this.repairType == null) ? other.repairType != null 
				: !this.repairType.equals(other.repairType)) {
			return false;
		}
		if ((this.machine == null) ? other.machine != null 
				: !this.machine.equals(other.machine)) {
			return false;
		}
		if ((this.start == null) ? other.start != null : !this.start.equals(other.start)) {
			return false;
		}
		if ((this.manager == null) ? other.manager != null 
				: !this.manager.equals(other.manager)) {
			return false;
		}
		return true;
	}    

	@Override
	public String toString() {
		String res = "order{" + "orderId=" + orderId;
		
		if (client == null) {
			res += ", clientId=NO CLIENT";
		} else {
			res += ", clientId=" + client.getClientId();
		}
		if (repairType == null) {
			res += ", repairTypeId=NO REPAIR TYPE";
		} else {
			res += ", repairTypeId=" + repairType.getRepairTypeId();
		}
		if (machine == null) {
			res += ", machineId=NO MACHINE";
		} else {
			res += ", machineId=" + machine.getMachineId();
		}
		res += ", start=" + start + ", status=" + status + ", manager=" + manager + '}'+"\n";				
		
		return res;
	}
}
