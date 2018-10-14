package guru.springframework.services;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import guru.springframework.domain.DomainObject;

public abstract class AbstractMapService {

	protected Map<Integer, DomainObject> database;

	public AbstractMapService() {
		database = new HashMap<>();
		loadDatabase();
	}

	abstract void loadDatabase();

	public List<DomainObject> listAll() {
		return new ArrayList<>(database.values());
	}

	public DomainObject getById(Integer id) {
		return database.get(id);
	}

	public DomainObject saveOrUpdate(DomainObject domainObject) {
		requireNonNull(domainObject, "The customer cannot be null");
		Integer id = domainObject.getId();

		if (isNull(id)) {
			id = getNextId();
			domainObject.setId(id);
		}

		database.put(id, domainObject);
		return database.get(id);
	}

	protected Integer getNextId() {
		return Collections.max(database.keySet()) + 1;
	}

	public void delete(Integer id) {
		database.remove(id);
	}

}
