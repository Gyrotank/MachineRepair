package com.glomozda.machinerepair.domain.order;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.glomozda.machinerepair.domain.client.Client;
import com.glomozda.machinerepair.domain.machine.Machine;
import com.glomozda.machinerepair.domain.repairtype.RepairType;

@SuppressWarnings({"PMD.CommentRequired", "PMD.LawOfDemeter"})
@Entity
@Table(name = "orders")
public class Order {
	
	@Id
	@GeneratedValue
	@Column(name = "orders_id")
	private Integer orderId;
	
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
	
	public Order(){
	}

	public Order(final Date start) {		
		this.start = start;
		this.status = "pending";
	}
	
	public Order(final Date start, final String status) {		
		this.start = start;
		this.status = status;
	}
	
	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(final Integer orderId) {
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

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 13 * hash + (this.client != null ? this.client.getClientId().hashCode() : 0);
		hash = 13 * hash + (this.repairType != null ? this.repairType.getRepairTypeId().hashCode() : 0);
		hash = 13 * hash + (this.machine != null ? this.machine.getMachineId().hashCode() : 0);
		hash = 13 * hash + (this.start != null ? this.start.hashCode() : 0);
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
		if ((this.repairType == null) ? other.repairType != null : !this.repairType.equals(other.repairType)) {
			return false;
		}
		if ((this.machine == null) ? other.machine != null : !this.machine.equals(other.machine)) {
			return false;
		}
		if ((this.start == null) ? other.start != null : !this.start.equals(other.start)) {
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
		res += ", start=" + start + ", status=" + status + '}'+"\n";				
		
		return res;
	}
}
