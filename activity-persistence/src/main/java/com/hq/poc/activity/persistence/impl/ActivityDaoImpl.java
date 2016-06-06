package com.hq.poc.activity.persistence.impl;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import com.hq.poc.activity.dao.ActivityDaoService;
import com.hq.poc.activity.dao.ActivityDto;
import com.hq.poc.activity.entity.Activity;

@Transactional
public class ActivityDaoImpl implements ActivityDaoService {

	/**
	 * Example d'alternative avec l'utilisation du composant Camel JPA
	 * 
	 * @Consume(context="activity",
	 *          uri="jpa://com.hq.poc.activity.dao.ActivityDao?consumeDelete=false&consumer.namedQuery=findById&consumer.parameters=#params")
	 * int getActivity(Activity activity) {
	 *    return activity.getEmployeeId();
	 * }
	 * cf. http://stackoverflow.com/questions/36609830/camel-jpa-query-parameters-not-being-seen
	 * 
	 * public interface ActivityCreator {
	 *    Activity createActivity(Activity activity);
	 * }
	 * 
	 * @Produce(context="activity",
	 *          uri="jpa://com.hq.poc.activity.dao.ActivityDao")
	 * protected ActivityCreator producer;
	 * 
	 * public String createActivity(ActivityDto dto) {
	 *    Activity activity = producer.createActivity(dto.getEntity());
	 *    return Integer.toString(activity.getId());
	 * }
	 * 
	 */

	// cf. http://aries.apache.org/modules/jpaproject.html
	@PersistenceContext(unitName="activity")
	EntityManager em;

	@Transactional(TxType.SUPPORTS)
	@Override
	public ActivityDto findById(int id) {
		ActivityDto dto = null;
		Activity activity = em.find(Activity.class, id);

		if (activity == null) {
			throw new EntityNotFoundException();
		} else {
			dto = new ActivityDto();
			dto.fromEntity(activity);		
		}
		return dto;
	}

	@Transactional(TxType.SUPPORTS)
	@Override
	public Collection<ActivityDto> findAll() {
        CriteriaQuery<Activity> query = em.getCriteriaBuilder().createQuery(Activity.class);
        List<Activity> activities =
        		em.createQuery(query.select(query.from(Activity.class))).getResultList();
		final List<ActivityDto> dtos = new ArrayList<ActivityDto>(activities.size());

		for (Activity activity : activities) {
			ActivityDto dto = new ActivityDto();
			dto.fromEntity(activity);
			dtos.add(dto);
		}
        return dtos;
	}

	@Override
	public ActivityDto create(ActivityDto dto) {
		Activity activity = dto.toEntity();
		em.persist(activity);
		em.flush();

		dto.setFKey(activity.getId());
		return dto;
	}

	@Override
	public void update(ActivityDto dto) {		
		Activity activity = dto.toEntity();

		if (em.find(Activity.class, activity.getId()) == null) {
			throw new EntityNotFoundException();
		} else {
			em.persist(activity);
		}
	}

	@Override
	public void delete(int id) {
		Activity activity = em.find(Activity.class, id);
		
		if (activity == null) {
			throw new EntityNotFoundException();
		} else {
			em.remove(activity);
		}
	}

	public void setupMockData() {
		ActivityDto dto = new ActivityDto();
		dto.setName("hq-audit");
		dto.setEmployeeId(100007);
		dto.setDuration(10.);
		create(dto);

		dto = new ActivityDto();
		dto.setName("hq-poc");
		dto.setEmployeeId(100007);
		dto.setDuration(15.25);
		create(dto);
	}

}
