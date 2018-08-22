package com.oyster.service;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.oyster.domain.Barrier;
import com.oyster.domain.Card;
import com.oyster.enums.Direction;
import com.oyster.enums.JourneyType;
import com.oyster.exception.InsufficientBalanceException;
import com.oyster.exception.JourneyNotValidException;
import com.oyster.map.repository.BarrierRepository;
import com.oyster.map.repository.JourneyMapRepository;
import com.oyster.service.impl.BarrierServiceImpl;

/**
 * Created by nayan.kakati on 4/19/18.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest(BarrierServiceImpl.class)
public class BarrierServiceImplTest {

	@Mock
	BarrierRepository barrierRepository;
	@Mock
	JourneyMapRepository journeyMapRepository;

	BarrierService barrierService;

	private Card card;
	private List<Barrier> barriers;
	private Barrier lastBarrierTravelled;

	@Before
	public void init() throws Exception {
		card = new Card(Math.abs(new Random().nextInt()),30.0D);
		barriers = new ArrayList<>();
		barriers.add(new Barrier(new HashSet<>(Arrays.asList(1)),"Holborn", Direction.INWARD, JourneyType.TUBE ));
		barriers.add(new Barrier(new HashSet<>(Arrays.asList(1)),"Holborn", Direction.EXIT, JourneyType.TUBE ));

		barriers.add(new Barrier(new HashSet<>(Arrays.asList(1,2)),"Earl’s Court", Direction.INWARD, JourneyType.TUBE ));
		barriers.add(new Barrier(new HashSet<>(Arrays.asList(1,2)),"Earl’s Court", Direction.EXIT, JourneyType.TUBE ));

		barrierRepository = mock(BarrierRepository.class);
		journeyMapRepository = mock(JourneyMapRepository.class);
		whenNew(BarrierRepository.class).withAnyArguments().thenReturn(barrierRepository);
		whenNew(JourneyMapRepository.class).withAnyArguments().thenReturn(journeyMapRepository);
	}

	@Test
	public void get_barriers_with_success() throws Exception {
		when(barrierRepository.getBarriers()).thenReturn(barriers);

		barrierService = new BarrierServiceImpl();
		List<Barrier> actualBarriers = barrierService.getBarriers();

		assertNotNull(actualBarriers);
		assertEquals(barriers.get(0).getName(), actualBarriers.get(0).getName());
		assertEquals(barriers.get(0).getDirection(), actualBarriers.get(0).getDirection());
		assertEquals(barriers.get(0).getJourneyType(), actualBarriers.get(0).getJourneyType());
		assertEquals(barriers.get(0).getZones(), actualBarriers.get(0).getZones());
	}

	@Test(expected = InsufficientBalanceException.class)
	public void card_insufficient_tube_travel_failure() throws JourneyNotValidException, InsufficientBalanceException {
		lastBarrierTravelled = new Barrier(new HashSet<>(Arrays.asList(1)),"Earl’s Court", Direction.EXIT, JourneyType.TUBE);
		when(journeyMapRepository.getLastBarrierCrossed(anyInt())).thenReturn(lastBarrierTravelled);
		doNothing().when(journeyMapRepository).saveMyJourney(anyInt(),any());

		Barrier currentBarrierToBeTravelled = new Barrier(new HashSet<>(Arrays.asList(1)),"Earl’s Court", Direction.INWARD, JourneyType.TUBE);
		barrierService = new BarrierServiceImpl();
		card.deductFromCard(30);
		Boolean actualBarrierResult = barrierService.isBarrierSuccessfullyPassed(currentBarrierToBeTravelled, card);

		assertEquals(actualBarrierResult, true);
	}

	@Test(expected = InsufficientBalanceException.class)
	public void card_insufficient_bus_travel_failure() throws JourneyNotValidException, InsufficientBalanceException {
		lastBarrierTravelled = new Barrier(new HashSet<>(Arrays.asList(1)),"Earl’s Court", Direction.EXIT, JourneyType.BUS);
		when(journeyMapRepository.getLastBarrierCrossed(anyInt())).thenReturn(lastBarrierTravelled);
		doNothing().when(journeyMapRepository).saveMyJourney(anyInt(),any());

		Barrier currentBarrierToBeTravelled = new Barrier(new HashSet<>(Arrays.asList(1)),"Earl’s Court", Direction.INWARD, JourneyType.BUS);
		barrierService = new BarrierServiceImpl();
		card.deductFromCard(30);
		Boolean actualBarrierResult = barrierService.isBarrierSuccessfullyPassed(currentBarrierToBeTravelled, card);

		assertEquals(actualBarrierResult, true);
	}

	@Test
	public void is_barrier_successfully_passed_with_journey_bus_success() throws JourneyNotValidException, InsufficientBalanceException {
		lastBarrierTravelled = new Barrier(new HashSet<>(Arrays.asList(1)),"Earl’s Court", Direction.EXIT, JourneyType.BUS);
		when(journeyMapRepository.getLastBarrierCrossed(anyInt())).thenReturn(lastBarrierTravelled);
		doNothing().when(journeyMapRepository).saveMyJourney(anyInt(),any());

		Barrier currentBarrierToBeTravelled = new Barrier(new HashSet<>(Arrays.asList(1)),"Earl’s Court", Direction.INWARD, JourneyType.BUS);
		barrierService = new BarrierServiceImpl();
		Boolean actualBarrierResult = barrierService.isBarrierSuccessfullyPassed(currentBarrierToBeTravelled, card);

		assertEquals(actualBarrierResult, true);
	}

	@Test(expected = JourneyNotValidException.class)
	public void is_barrier_successfully_passed_with_journey_bus_failure() throws JourneyNotValidException, InsufficientBalanceException {
		lastBarrierTravelled = new Barrier(new HashSet<>(Arrays.asList(1)),"Earl’s Court", Direction.INWARD, JourneyType.BUS);
		when(journeyMapRepository.getLastBarrierCrossed(anyInt())).thenReturn(lastBarrierTravelled);
		doNothing().when(journeyMapRepository).saveMyJourney(anyInt(),any());

		Barrier currentBarrierToBeTravelled = new Barrier(new HashSet<>(Arrays.asList(1)),"Earl’s Court", Direction.INWARD, JourneyType.BUS);
		barrierService = new BarrierServiceImpl();
		Boolean actualBarrierResult = barrierService.isBarrierSuccessfullyPassed(currentBarrierToBeTravelled, card);
	}

	@Test
	public void is_barrier_successfully_passed_with_inward_journey_tube_success() throws JourneyNotValidException, InsufficientBalanceException {
		when(journeyMapRepository.getLastBarrierCrossed(anyInt())).thenReturn(lastBarrierTravelled);
		doNothing().when(journeyMapRepository).saveMyJourney(anyInt(),any());

		Barrier currentBarrierToBeTravelled = new Barrier(new HashSet<>(Arrays.asList(1)),"Earl’s Court", Direction.INWARD, JourneyType.TUBE);
		barrierService = new BarrierServiceImpl();
		Boolean actualBarrierResult = barrierService.isBarrierSuccessfullyPassed(currentBarrierToBeTravelled, card);
		assertEquals(actualBarrierResult, true);
	}

	@Test(expected = JourneyNotValidException.class)
	public void is_barrier_successfully_passed_with_inward_journey_tube_failure() throws JourneyNotValidException, InsufficientBalanceException {
		lastBarrierTravelled = new Barrier(new HashSet<>(Arrays.asList(1)),"Earl’s Court", Direction.INWARD, JourneyType.TUBE);
		when(journeyMapRepository.getLastBarrierCrossed(anyInt())).thenReturn(lastBarrierTravelled);
		doNothing().when(journeyMapRepository).saveMyJourney(anyInt(),any());

		Barrier currentBarrierToBeTravelled = new Barrier(new HashSet<>(Arrays.asList(1)),"Earl’s Court", Direction.INWARD, JourneyType.TUBE);
		barrierService = new BarrierServiceImpl();
		Boolean actualBarrierResult = barrierService.isBarrierSuccessfullyPassed(currentBarrierToBeTravelled, card);
	}

	@Test
	public void is_barrier_successfully_passed_with_exit_journey_tube_only_Zone_1_success() throws JourneyNotValidException, InsufficientBalanceException {
		lastBarrierTravelled = new Barrier(new HashSet<>(Arrays.asList(1)),"Earl’s Court", Direction.INWARD, JourneyType.TUBE);
		when(journeyMapRepository.getLastBarrierCrossed(anyInt())).thenReturn(lastBarrierTravelled);
		doNothing().when(journeyMapRepository).saveMyJourney(anyInt(),any());

		Barrier currentBarrierToBeTravelled = new Barrier(new HashSet<>(Arrays.asList(1)),"Earl’s Court", Direction.EXIT, JourneyType.TUBE);
		barrierService = new BarrierServiceImpl();
		Boolean actualBarrierResult = barrierService.isBarrierSuccessfullyPassed(currentBarrierToBeTravelled, card);
		assertEquals(actualBarrierResult, true);
	}

	@Test
	public void is_barrier_successfully_passed_with_exit_journey_tube_any_1_outside_Zone_1_success() throws JourneyNotValidException, InsufficientBalanceException {
		lastBarrierTravelled = new Barrier(new HashSet<>(Arrays.asList(1)),"Earl’s Court", Direction.INWARD, JourneyType.TUBE);
		when(journeyMapRepository.getLastBarrierCrossed(anyInt())).thenReturn(lastBarrierTravelled);
		doNothing().when(journeyMapRepository).saveMyJourney(anyInt(),any());

		Barrier currentBarrierToBeTravelled = new Barrier(new HashSet<>(Arrays.asList(2)),"Earl’s Court", Direction.EXIT, JourneyType.TUBE);
		barrierService = new BarrierServiceImpl();
		Boolean actualBarrierResult = barrierService.isBarrierSuccessfullyPassed(currentBarrierToBeTravelled, card);
		assertEquals(actualBarrierResult, true);
	}

	@Test
	public void is_barrier_successfully_passed_with_exit_journey_tube_any_2_excluding_Zone_1_success() throws JourneyNotValidException, InsufficientBalanceException {
		lastBarrierTravelled = new Barrier(new HashSet<>(Arrays.asList(3)),"Earl’s Court", Direction.INWARD, JourneyType.TUBE);
		when(journeyMapRepository.getLastBarrierCrossed(anyInt())).thenReturn(lastBarrierTravelled);
		doNothing().when(journeyMapRepository).saveMyJourney(anyInt(),any());

		Barrier currentBarrierToBeTravelled = new Barrier(new HashSet<>(Arrays.asList(2)),"Earl’s Court", Direction.EXIT, JourneyType.TUBE);
		barrierService = new BarrierServiceImpl();
		Boolean actualBarrierResult = barrierService.isBarrierSuccessfullyPassed(currentBarrierToBeTravelled, card);
		assertEquals(actualBarrierResult, true);
	}

	@Test
	public void is_barrier_successfully_passed_with_exit_journey_tube_any_2_including_Zone_1_success() throws JourneyNotValidException, InsufficientBalanceException {
		lastBarrierTravelled = new Barrier(new HashSet<>(Arrays.asList(1,2)),"Earl’s Court", Direction.INWARD, JourneyType.TUBE);
		when(journeyMapRepository.getLastBarrierCrossed(anyInt())).thenReturn(lastBarrierTravelled);
		doNothing().when(journeyMapRepository).saveMyJourney(anyInt(),any());

		Barrier currentBarrierToBeTravelled = new Barrier(new HashSet<>(Arrays.asList(3)),"Earl’s Court", Direction.EXIT, JourneyType.TUBE);
		barrierService = new BarrierServiceImpl();
		Boolean actualBarrierResult = barrierService.isBarrierSuccessfullyPassed(currentBarrierToBeTravelled, card);
		assertEquals(actualBarrierResult, true);
	}

	@Test
	public void is_barrier_successfully_passed_with_exit_journey_tube_any_3_excluding_Zone_1_success() throws JourneyNotValidException, InsufficientBalanceException {
		lastBarrierTravelled = new Barrier(new HashSet<>(Arrays.asList(2,3)),"Earl’s Court", Direction.INWARD, JourneyType.TUBE);
		when(journeyMapRepository.getLastBarrierCrossed(anyInt())).thenReturn(lastBarrierTravelled);
		doNothing().when(journeyMapRepository).saveMyJourney(anyInt(),any());

		Barrier currentBarrierToBeTravelled = new Barrier(new HashSet<>(Arrays.asList(4)),"Earl’s Court", Direction.EXIT, JourneyType.TUBE);
		barrierService = new BarrierServiceImpl();
		Boolean actualBarrierResult = barrierService.isBarrierSuccessfullyPassed(currentBarrierToBeTravelled, card);
		assertEquals(actualBarrierResult, true);
	}

	@Test
	public void is_barrier_successfully_passed_with_exit_journey_tube_any_4_excluding_Zone_1_failure() throws JourneyNotValidException, InsufficientBalanceException{
		lastBarrierTravelled = new Barrier(new HashSet<>(Arrays.asList(2,3)),"Earl’s Court", Direction.INWARD, JourneyType.TUBE);
		when(journeyMapRepository.getLastBarrierCrossed(anyInt())).thenReturn(lastBarrierTravelled);
		doNothing().when(journeyMapRepository).saveMyJourney(anyInt(),any());

		Barrier currentBarrierToBeTravelled = new Barrier(new HashSet<>(Arrays.asList(4,5)),"Earl’s Court", Direction.EXIT, JourneyType.TUBE);
		barrierService = new BarrierServiceImpl();
		Boolean actualBarrierResult = barrierService.isBarrierSuccessfullyPassed(currentBarrierToBeTravelled, card);
		assertEquals(actualBarrierResult, true);
	}
}
