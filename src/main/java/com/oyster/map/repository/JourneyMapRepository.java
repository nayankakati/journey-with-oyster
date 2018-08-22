package com.oyster.map.repository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;

import com.oyster.domain.Barrier;

/**
 * Created by nayan.kakati on 4/19/18.
 */
public class JourneyMapRepository {

	private Map<Integer,Barrier> lastBarrierCrossed = new HashMap<>();
	private Map<Integer, LinkedList<Barrier>> myOverallJourney = new HashMap<>();

	public Barrier getLastBarrierCrossed(Integer cardNumber) {
		return lastBarrierCrossed.get(cardNumber);
	}

	public Map<Integer, LinkedList<Barrier>> getMyOverallJourney() {
		return myOverallJourney;
	}

	public void saveMyJourney(Integer cardNumber, Barrier barrier) {
		lastBarrierCrossed.put(cardNumber, barrier);

		LinkedList<Barrier> myTempJourney = myOverallJourney.get(cardNumber);
		if(Objects.nonNull(myTempJourney)) {
			myTempJourney.add(barrier);
			myOverallJourney.put(cardNumber, myTempJourney);
		} else {
			myTempJourney = new LinkedList<>(Arrays.asList(barrier));
			myOverallJourney.put(cardNumber, myTempJourney);
		}
	}
}
