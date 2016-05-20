package com.hq.poc.activity.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import com.hq.poc.activity.dto.AbstractDto;

public abstract class MockService<T> implements AbstractService<T> {

	// In memory mock activity management
	private Map<Integer, T> entities = new HashMap<>();
	private Map<String, Integer> fkeyToId = new HashMap<>();
	private final AtomicInteger idGen = new AtomicInteger();

	abstract AbstractDto<T> createDto(T entity);

	@Override
	public AbstractDto<T> findById(int id) {
		System.out.println(String.format("MockService.findById(%d)", id));

		AbstractDto<T> dto = null; 
		if (entities.containsKey(id)) {
			T entity = entities.get(id);		
			dto = createDto(entity);
			dto.setId(fkeyToId.get(dto.getFKey(entity)));			
		}
		return dto;
	}

	@Override
	public Collection<AbstractDto<T>> findAll() {
		System.out.println("MockService.findAll()");

		final Collection<AbstractDto<T>> dtos = new ArrayList<AbstractDto<T>>(entities.values().size());
		entities.values().forEach(new Consumer<T>() {
			@Override
			public void accept(T entity) {
				AbstractDto<T> dto = createDto(entity);
				dto.setId(fkeyToId.get(dto.getFKey(entity)));
				dtos.add(dto);				
			}
		});
		return dtos;
	}

	@Override
	public String create(AbstractDto<T> dto) {
		String response = null;

		T entity = dto.getEntity();
		String fkey = dto.getFKey(entity);
		System.out.println(String.format("MockService.create(attempt to create %s)", fkey));
		if (!fkeyToId.containsKey(fkey)) {
			int id = idGen.incrementAndGet();			
			fkeyToId.put(fkey, id);

			dto.setId(id);
			entities.put(id, entity);
			response = String.valueOf(id);

			System.out.println(String.format("MockService.create(%d)", id));
		}
		
		return response;
	}

	@Override
	public void update(AbstractDto<T> dto) {
		int id = dto.getId();
		System.out.println(String.format("MockService.update(%d)", id));

		if (entities.containsKey(id)) {
			entities.remove(id);
			entities.put(id, dto.getEntity());
		}
	}

	@Override
	public void delete(int id) {
		System.out.println(String.format("MockService.delete(%d)", id));
		
		if (entities.containsKey(id)) {
			entities.remove(id);
		}
	}

}
