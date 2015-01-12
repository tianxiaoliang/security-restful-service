package com.cloud.service.requests;
 
 
import com.cloud.service.domain.model.Farm;

public class RegisterFarmResultRequest {

	private Farm farm;

	public Farm getFarm() {
		return farm;
	}

	public void setFarm(Farm farm) {
		this.farm = farm;
	}

}
