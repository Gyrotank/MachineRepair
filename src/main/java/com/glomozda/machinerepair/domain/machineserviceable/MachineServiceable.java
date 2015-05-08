package com.glomozda.machinerepair.domain.machineserviceable;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.glomozda.machinerepair.domain.machine.Machine;

@NamedQueries({
	@NamedQuery(name="MachineServiceable.findAll", query="SELECT ms FROM MachineServiceable ms"),
	@NamedQuery(name="MachineServiceable.findAllOrderByName",
	query="SELECT ms FROM MachineServiceable ms "
		+ "ORDER BY ms.machineServiceableName"),
	@NamedQuery(name="MachineServiceable.findAllOrderByTrademark",
		query="SELECT ms FROM MachineServiceable ms "
			+ "ORDER BY ms.machineServiceableTrademark"),
	@NamedQuery(name="MachineServiceable.findAllAvailableOrderByTrademark",
		query="SELECT ms FROM MachineServiceable ms "
			+ "WHERE ms.available = 1 "
			+ "ORDER BY ms.machineServiceableTrademark"),
	@NamedQuery(name="MachineServiceable.findMachineServiceableById",
		query="SELECT ms FROM MachineServiceable ms"
			+ " WHERE ms.machineServiceableId = :id"),
	@NamedQuery(name="MachineServiceable.countAll", query="SELECT COUNT(ms) "
		+ "FROM MachineServiceable ms"),
	@NamedQuery(name="MachineServiceable.updateMachineServiceableById", 
		query="UPDATE MachineServiceable ms "
			+ "SET ms.machineServiceableName = :name, "
			+ "ms.machineServiceableTrademark = :trademark, "
			+ "ms.machineServiceableCountry = :country,"
			+ "ms.machineServiceableCountryRu = :country_ru "
			+ "WHERE ms.machineServiceableId = :id"),
	@NamedQuery(name="MachineServiceable.setMachineServiceableAvailableById",
		query="UPDATE MachineServiceable ms SET available = :available "
				+ " WHERE ms.machineServiceableId = :id")
})
@Entity
@Table(name = "machinesServiceable")
public class MachineServiceable {
	@Id
	@GeneratedValue
	@Column(name = "machines_serviceable_id")
	private Long machineServiceableId;	
	
	@Column(name = "name")
	@NotEmpty
	private String  machineServiceableName;
	
	@Column(name = "trademark")
	@NotEmpty
	private String  machineServiceableTrademark;
	
	@Column(name = "country")
	@NotEmpty
	private String  machineServiceableCountry;
	
	@Column(name = "country_ru")
	@NotEmpty
	private String  machineServiceableCountryRu;
	
	@Column(name = "available")
	private Byte available;
		
	@OneToMany(mappedBy = "machineServiceable")
	private List<Machine> machines = new ArrayList<Machine>();	

	public MachineServiceable(){
	}

	public MachineServiceable(final String machineServiceableName,
			final String machineServiceableTrademark,
			final String machineServiceableCountry,
			final String machineServiceableCountryRu) {
		
		this.machineServiceableName = machineServiceableName;
		this.machineServiceableTrademark = machineServiceableTrademark;
		this.machineServiceableCountry = machineServiceableCountry;				
		this.machineServiceableCountryRu = machineServiceableCountryRu;
		this.available = 1;
	}
	
	public MachineServiceable(final String machineServiceableName,
			final String machineServiceableTrademark,
			final String machineServiceableCountry,
			final String machineServiceableCountryRu,
			final Byte available) {
		
		this.machineServiceableName = machineServiceableName;
		this.machineServiceableTrademark = machineServiceableTrademark;
		this.machineServiceableCountry = machineServiceableCountry;				
		this.machineServiceableCountryRu = machineServiceableCountryRu;
		this.available = available;
	}
	
	public Long getMachineServiceableId() {
		return machineServiceableId;
	}

	public void setMachineServiceableId(final Long machineServiceableId) {
		this.machineServiceableId = machineServiceableId;
	}
	
	public String getMachineServiceableName() {
		return machineServiceableName;
	}

	public void setMachineServiceableName(final String machineServiceableName) {
		this.machineServiceableName = machineServiceableName;
	}
	
	public String getMachineServiceableTrademark() {
		return machineServiceableTrademark;
	}

	public void setMachineServiceableTrademark(final String machineServiceableTrademark) {
		this.machineServiceableTrademark = machineServiceableTrademark;
	}

	public String getMachineServiceableCountry() {
		return machineServiceableCountry;
	}

	public void setMachineServiceableCountry(final String machineServiceableCountry) {
		this.machineServiceableCountry = machineServiceableCountry;
	}
	
	public String getMachineServiceableCountryRu() {
		return machineServiceableCountryRu;
	}

	public void setMachineServiceableCountryRu(String machineServiceableCountryRu) {
		this.machineServiceableCountryRu = machineServiceableCountryRu;
	}

	public Byte getAvailable() {
		return available;
	}

	public void setAvailable(Byte available) {
		this.available = available;
	}

	public void addMachine(final Machine machine) {
        this.machines.add(machine);
        if (machine.getMachineServiceable() != this) {
            machine.setMachineServiceable(this);
        }
    }
	
	public List<Machine> getMachines() {
		return machines;
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 13 * hash + (this.machineServiceableName != null 
				? this.machineServiceableName.hashCode() : 0);
		hash = 13 * hash + (this.machineServiceableTrademark != null 
				? this.machineServiceableTrademark.hashCode() : 0);
		hash = 13 * hash + (this.machineServiceableCountry != null 
				? this.machineServiceableCountry.hashCode() : 0);
		hash = 13 * hash + (this.machineServiceableCountryRu != null 
				? this.machineServiceableCountryRu.hashCode() : 0);
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
		final MachineServiceable other = (MachineServiceable) obj;
		if ((this.machineServiceableName == null) 
				? other.machineServiceableName != null 
				: !this.machineServiceableName.equals(other.machineServiceableName)) {
			return false;
		}
		if (this.machineServiceableTrademark != other.machineServiceableTrademark 
				&& (this.machineServiceableTrademark == null 
				|| !this.machineServiceableTrademark.equals(other.machineServiceableTrademark))) {
			return false;
		}
		if (this.machineServiceableCountry != other.machineServiceableCountry 
				&& (this.machineServiceableCountry == null 
				|| !this.machineServiceableCountry.equals(other.machineServiceableCountry))) {
			return false;
		}
		if (this.machineServiceableCountryRu != other.machineServiceableCountryRu 
				&& (this.machineServiceableCountryRu == null 
				|| !this.machineServiceableCountryRu.equals(other.machineServiceableCountryRu))) {
			return false;
		}
		return true;
	}    

	@Override
	public String toString() {
		return "machineServiceable{" + 
				"machineServiceableId=" + machineServiceableId + 
				", machineServiceableName=" + machineServiceableName + 
				", machineServiceableTrademark=" + machineServiceableTrademark +
				", machineServiceableCountry=" + machineServiceableCountry +
				", machineServiceableCountryRu=" + machineServiceableCountryRu + '}'+"\n";
	}
	    
}
