package com.oyster.domain;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.when;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.oyster.enums.Direction;
import com.oyster.enums.JourneyType;


/**
 * Created by nayan.kakati on 4/19/18.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest(Barrier.class)
public class BarrierTest {

	private Set<Integer> zones;
	private String name;
	private Direction direction;
	private JourneyType journeyType;

	Barrier barrier;
	Barrier checkBarrier;

	@Before
	public void init() {
		zones = new HashSet<>(Arrays.asList(1));
		name = "Holborn";
		direction = Direction.INWARD;
		journeyType = JourneyType.BUS;
		checkBarrier = new Barrier(zones, name, direction, journeyType);
	}

	@Test
	public void create_Barrier_with_success() {
		barrier = new Barrier();
		barrier.setDirection(direction);
		barrier.setJourneyType(journeyType);
		barrier.setName(name);
		barrier.setZones(zones);
	}

	@Test
	public void get_All_the_properties_For_Barrier() {
		barrier = Mockito.mock(Barrier.class);
		when(barrier.getDirection()).thenReturn(checkBarrier.getDirection());
		when(barrier.getJourneyType()).thenReturn(checkBarrier.getJourneyType());
		when(barrier.getZones()).thenReturn(checkBarrier.getZones());
		when(barrier.getName()).thenReturn(checkBarrier.getName());

		Direction actualDirection = barrier.getDirection();
		JourneyType actualJourneyType = barrier.getJourneyType();
		Set<Integer> actualZones = barrier.getZones();
		String actualName = barrier.getName();

		assertEquals(checkBarrier.getDirection(), actualDirection);
		assertEquals(checkBarrier.getJourneyType(), actualJourneyType);
		assertEquals(checkBarrier.getName(), actualName);
		assertEquals(checkBarrier.getZones(),actualZones);

	}
}
