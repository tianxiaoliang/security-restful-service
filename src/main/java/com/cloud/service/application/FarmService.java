package com.cloud.service.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.service.domain.dao.FarmMapper;
import com.cloud.service.domain.model.Farm;
import com.cloud.service.domain.model.FarmExample;

@Service
public class FarmService {
	private Logger logger = Logger.getLogger(FarmService.class);

	@Autowired
	FarmMapper farmMapper;

	public boolean save(Farm farm) {
		// first check if exist
		FarmExample example = new FarmExample();
		com.cloud.service.domain.model.FarmExample.Criteria criteria = example
				.createCriteria();
		criteria.andScalrEndpointEqualTo(farm.getScalrEndpoint());
		criteria.andScalrEnvIdEqualTo(farm.getScalrEnvId());
		criteria.andScalrFarmIdEqualTo(farm.getScalrFarmId());
		List<Farm> farms = farmMapper.selectByExample(example);
		if (farms == null || farms.size() == 0) {
			farmMapper.insert(farm);
			logger.debug(String.format("Save farm  %s successfully!",
					farm.getScalrFarmName()));
			return true;
		} else {
			logger.info(String.format(
					"farm %s already exists, env %s on scalr %s",
					farm.getScalrFarmName(), farm.getScalrEnvId(),
					farm.getScalrEndpoint()));
			return false;
		}
	}

	public void deleteCollector(Integer id) {
		farmMapper.deleteByPrimaryKey(id);
	}

	public List<Farm> getfarms() {
		List<Farm> farms = farmMapper.selectByExample(null);
		return farms;
	}

	public List<Farm> getFarms(String endpoint) {
		FarmExample example = new FarmExample();
		com.cloud.service.domain.model.FarmExample.Criteria criteria = example
				.createCriteria();
		criteria.andScalrEndpointEqualTo(endpoint);
		List<Farm> farms = farmMapper.selectByExample(example);
		return farms;
	}

}
