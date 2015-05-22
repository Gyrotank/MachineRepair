package com.glomozda.machinerepair.service.machine;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.glomozda.machinerepair.domain.machine.Machine;
import com.glomozda.machinerepair.repository.machine.MachineRepository;
import com.glomozda.machinerepair.service.EntityService;

@Service
public abstract class MachineService implements EntityService {

	@Autowired
	protected MachineRepository machineRepository;

	public abstract Integer updateMachineById(Long machineId, Machine machine);

	public abstract Machine getMachineById(Long machineId);

	public abstract Long getMachineCount();

	public abstract Integer incrementTimesRepairedById(Long machineId);

	public abstract Boolean add(Machine m, Long machineServiceableId);

	public abstract Machine getMachineByIdWithFetching(Long machineId);

	public abstract Machine getMachineForSerialNumberWithFetching(String machineSerialNumber);

	public abstract Machine getMachineForSerialNumber(String machineSerialNumber);

	public abstract List<Machine> getAllWithFetching(Long start, Long length);

	public abstract List<Machine> getAllWithFetching();

	public abstract List<Machine> getAll(Long start, Long length);

	public abstract List<Machine> getAll();

}