package com.oyster.map.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.oyster.domain.Barrier;
import com.oyster.domain.Card;
import com.oyster.enums.Direction;
import com.oyster.enums.JourneyType;

/**
 * Created by nayan.kakati on 4/19/18.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest(JourneyMapRepository.class)
public class JourneyMapRepositoryTest {

	JourneyMapRepository journeyMapRepository;
	private Map<Integer,Barrier> lastBarrierCrossed;
	private Map<Integer, LinkedList<Barrier>> myOverallJourney;
	private Card card;
	private Barrier barrier;

	@Before
	public void init() {
		lastBarrierCrossed = new HashMap<>();
		myOverallJourney = new HashMap<>();
		card = new Card(12, 30);
		barrier = new Barrier(new HashSet<>(Arrays.asList(1)),"Holborn", Direction.INWARD, JourneyType.TUBE);
	}

	@Test
	public void save_journey_success() {
		journeyMapRepository = new JourneyMapRepository();
		journeyMapRepository.saveMyJourney(card.getNumber(), barrier);
		journeyMapRepository.saveMyJourney(card.getNumber(), barrier);
	}

	@Test
	public void get_saved_journey_success() {
		journeyMapRepository = new JourneyMapRepository();
		journeyMapRepository.saveMyJourney(card.getNumber(), barrier);
		Map<Integer, LinkedList<Barrier>> overallJourney = journeyMapRepository.getMyOverallJourney();
		Barrier actualBarrier = journeyMapRepository.getLastBarrierCrossed(card.getNumber());
		assertEquals(barrier.getName(), actualBarrier.getName());
		assertNotNull(overallJourney);
	}

}
