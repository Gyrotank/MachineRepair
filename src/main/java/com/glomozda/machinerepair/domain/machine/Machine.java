package com.glomozda.machinerepair.domain.machine;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.glomozda.machinerepair.domain.machineserviceable.MachineServiceable;
import com.glomozda.machinerepair.domain.order.Order;

@SuppressWarnings({"PMD.CommentRequired", "PMD.LawOfDemeter"})
@Entity
@Table(name = "machines")
public class Machine {

	@Id
	@GeneratedValue
	@Column(name = "machines_id")
	private Integer machineId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "machine_serviceable_id")	
	private MachineServiceable machineServiceable;
	
	@Column(name = "serial_number")
	@NotEmpty
	private String  machineSerialNumber;
		
	@Column(name = "year")
	@NotNull @Min(1950)
	private Integer machineYear;
	
	@Column(name = "times_repaired")
	@NotNull @Min(0)
	private Integer machineTimesRepaired;
	
	@OneToMany(mappedBy = "machine")
	private List<Order> orders = new ArrayList<Order>();	

	public Machine(){
	}
	
	public Machine(final String machineSerialNumber,
			final Integer machineYear) {
		this.machineSerialNumber = machineSerialNumber;		
		this.machineYear = machineYear;
		this.machineTimesRepaired = 0;
	}
	
	public Machine(final String machineSerialNumber,
			final Integer machineYear, final Integer machineTimesRepaired) {
		this.machineSerialNumber = machineSerialNumber;		
		this.machineYear = machineYear;
		this.machineTimesRepaired = machineTimesRepaired;
	}
	
	public void addOrder(final Order order) {
        this.orders.add(order);
        if (order.getMachine() != this) {
            order.setMachine(this);
        }
    }
	
	public List<Order> getOrders() {
		return orders;
	}
	
	public MachineServiceable getMachineServiceable() {
		return machineServiceable;
	}

	public void setMachineServiceable(final MachineServiceable machineServiceable) {
		this.machineServiceable = machineServiceable;
		
		if (machineServiceable != null) {
			if (!machineServiceable.getMachines().contains(this)) {
				machineServiceable.getMachines().add(this);
			}
		}
	}

	public String getMachineSerialNumber() {
		return machineSerialNumber;
	}

	public void setMachineSerialNumber(String machineSerialNumber) {
		this.machineSerialNumber = machineSerialNumber;
	}

	public Integer getMachineYear() {
		return machineYear;
	}

	public void setMachineYear(final Integer machineYear) {
		this.machineYear = machineYear;
	}

	public Integer getMachineId() {
		return machineId;
	}

	public void setMachineId(final Integer machineId) {
		this.machineId = machineId;
	}

	public Integer getMachineTimesRepaired() {
		return machineTimesRepaired;
	}

	public void setMachineTimesRepaired(final Integer machineTimesRepaired) {
		this.machineTimesRepaired = machineTimesRepaired;
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 13 * hash + (this.machineSerialNumber != null ? this.machineSerialNumber.hashCode() : 0);
		hash = 13 * hash + (this.machineYear != null ? this.machineYear.hashCode() : 0);
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
		final Machine other = (Machine) obj;
		if ((this.machineSerialNumber == null) ? other.machineSerialNumber != null : !this.machineSerialNumber.equals(other.machineSerialNumber)) {
			return false;
		}		
		if (this.machineYear != other.machineYear && (this.machineYear == null || !this.machineYear.equals(other.machineYear))) {
			return false;
		}
		return true;
	}    

	@Override
	public String toString() {
		return "machine{" + "machineId=" + machineId + ", machineSerialNumber=" + machineSerialNumber + ", machineYear=" + machineYear + 
				", machineTimesRepaired=" + machineTimesRepaired + '}'+"\n";
	}
	    
}
