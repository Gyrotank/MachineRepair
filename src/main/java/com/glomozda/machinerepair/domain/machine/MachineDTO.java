package com.glomozda.machinerepair.domain.machine;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class MachineDTO {
	
	@Min(1)
	private Long machineServiceableId;
	
	@NotEmpty
	private String machineSerialNumber;
	
	@NotNull @Min(1950)
	private Integer machineYear;
	
	@NotNull @Min(0)
	private Integer machineTimesRepaired;
	
	public MachineDTO(){
	}
	
	public MachineDTO(final String machineSerialNumber,
			final Integer machineYear) {
		this.machineSerialNumber = machineSerialNumber;		
		this.machineYear = machineYear;
		this.machineTimesRepaired = 0;
	}
	
	public MachineDTO(final String machineSerialNumber,
			final Integer machineYear, final Integer machineTimesRepaired) {
		this.machineSerialNumber = machineSerialNumber;		
		this.machineYear = machineYear;
		this.machineTimesRepaired = machineTimesRepaired;
	}

	public Long getMachineServiceableId() {
		return machineServiceableId;
	}

	public void setMachineServiceableId(Long machineServiceableId) {
		this.machineServiceableId = machineServiceableId;
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

	public void setMachineYear(Integer machineYear) {
		this.machineYear = machineYear;
	}

	public Integer getMachineTimesRepaired() {
		return machineTimesRepaired;
	}

	public void setMachineTimesRepaired(Integer machineTimesRepaired) {
		this.machineTimesRepaired = machineTimesRepaired;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((machineSerialNumber == null) ? 0 : machineSerialNumber
						.hashCode());
		result = prime
				* result
				+ ((machineServiceableId == null) ? 0 : machineServiceableId
						.hashCode());
		result = prime
				* result
				+ ((machineTimesRepaired == null) ? 0 : machineTimesRepaired
						.hashCode());
		result = prime * result
				+ ((machineYear == null) ? 0 : machineYear.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		MachineDTO other = (MachineDTO) obj;
		if (machineSerialNumber == null) {
			if (other.machineSerialNumber != null) {
				return false;
			}
		} else if (!machineSerialNumber.equals(other.machineSerialNumber)) {
			return false;
		}
		if (machineServiceableId == null) {
			if (other.machineServiceableId != null) {
				return false;
			}
		} else if (!machineServiceableId.equals(other.machineServiceableId)) {
			return false;
		}
		if (machineTimesRepaired == null) {
			if (other.machineTimesRepaired != null) {
				return false;
			}
		} else if (!machineTimesRepaired.equals(other.machineTimesRepaired)) {
			return false;
		}
		if (machineYear == null) {
			if (other.machineYear != null) {
				return false;
			}
		} else if (!machineYear.equals(other.machineYear)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MachineDTO {machineServiceableId=");
		builder.append(machineServiceableId);
		builder.append(", machineSerialNumber=");
		builder.append(machineSerialNumber);
		builder.append(", machineYear=");
		builder.append(machineYear);
		builder.append(", machineTimesRepaired=");
		builder.append(machineTimesRepaired);
		builder.append("}");
		return builder.toString();
	}	
}
