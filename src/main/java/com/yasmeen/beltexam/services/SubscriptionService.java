package com.yasmeen.beltexam.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yasmeen.beltexam.models.Subscription;
import com.yasmeen.beltexam.repositories.SubscriptionRepository;

@Service
public class SubscriptionService {
	
	private SubscriptionRepository subRepo;
	
	public SubscriptionService(SubscriptionRepository subRepo) {
		this.subRepo = subRepo;
	}

	public List<Subscription> getAll(){
		return (List<Subscription>) subRepo.findAll();
	}
	public Subscription getOneById(Long id) {
		return subRepo.findOne(id);
	}
	public void createSub(Subscription sub) {
		subRepo.save(sub);
	}
	public void updateSub(Subscription sub) {
		subRepo.save(sub);
	}
	public void deleteSub(Long id) {
		subRepo.delete(id);
	}
}
