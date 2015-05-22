package com.glomozda.machinerepair.service.machineserviceable;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.glomozda.machinerepair.domain.machineserviceable.MachineServiceable;
import com.glomozda.machinerepair.repository.machineserviceable.MachineServiceableRepository;
import com.glomozda.machinerepair.service.EntityService;

@Service
public abstract class MachineServiceableService implements EntityService {

	@Autowired
	protected MachineServiceableRepository machineServiceableRepository;

	public abstract List<MachineServiceable> getAllAvailableOrderByTrademark();

	public abstract Integer setMachineServiceableAvailableById(Long machineServiceableId,
			Byte available);

	public abstract Integer updateMachineServiceableById(Long machineServiceableId,
			MachineServiceable machineServiceable);

	public abstract Boolean add(MachineServiceable ms);

	public abstract Long getMachineServiceableCount();

	public abstract MachineServiceable getMachineServiceableById(Long machineServiceableId);

	public abstract List<MachineServiceable> getAllOrderByTrademark();

	public abstract List<MachineServiceable> getAllOrderByName();

	public abstract List<MachineServiceable> getAll(Long start, Long length);

	public abstract List<MachineServiceable> getAll();

	public MachineServiceableService() {
		super();
	}

}