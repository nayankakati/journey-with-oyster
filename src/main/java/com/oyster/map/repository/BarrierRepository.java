package com.oyster.map.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.oyster.domain.Barrier;
import com.oyster.enums.Direction;
import com.oyster.enums.JourneyType;

/**
 * Created by nayan.kakati on 4/19/18.
 */
public class BarrierRepository {

	private List<Barrier> barriers = createBarriers();

	public List<Barrier> getBarriers() {
		return barriers;
	}

	private List<Barrier> createBarriers() {
		barriers = new ArrayList<>();

		barriers.add(new Barrier(getZones(1),"Holborn", Direction.INWARD, JourneyType.TUBE ));
		barriers.add(new Barrier(getZones(1),"Holborn", Direction.EXIT, JourneyType.TUBE ));

		barriers.add(new Barrier(getZones(1,2),"Earl’s Court", Direction.INWARD, JourneyType.TUBE ));
		barriers.add(new Barrier(getZones(1,2),"Earl’s Court", Direction.EXIT, JourneyType.TUBE ));

		//barriers.add(new Barrier(getZones(1),"Earl’s Court", Direction.INWARD, JourneyType.BUS ));

		barriers.add(new Barrier(getZones(3),"Wimbledon", Direction.INWARD, JourneyType.TUBE ));
		barriers.add(new Barrier(getZones(3),"Wimbledon", Direction.EXIT, JourneyType.TUBE ));

		barriers.add(new Barrier(getZones(2),"Hammersmith", Direction.INWARD, JourneyType.TUBE ));
		barriers.add(new Barrier(getZones(2),"Hammersmith", Direction.EXIT, JourneyType.TUBE ));

		return barriers;
	}

	private Set<Integer> getZones(Integer...zones) {
		return new HashSet<>(Arrays.asList(zones));
	}
}
