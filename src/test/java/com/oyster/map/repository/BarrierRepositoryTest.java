package com.oyster.map.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import com.oyster.domain.Barrier;

/**
 * Created by nayan.kakati on 4/19/18.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(BarrierRepository.class)
public class BarrierRepositoryTest {

	private List<Barrier> barriers ;
	private BarrierRepository barrierRepository;

	@Before
	public void init() {
		barrierRepository = new BarrierRepository();
	}

	@Test
	public void create_barriers_success() throws Exception {
		 barriers = Whitebox.invokeMethod(barrierRepository,"createBarriers");
		 assertNotNull(barriers);
	}

	@Test
	public void get_barrier_success() throws Exception {
		barriers = Whitebox.invokeMethod(barrierRepository,"createBarriers");
		List<Barrier> actualBarriers = barrierRepository.getBarriers();
		assertEquals(barriers, actualBarriers);
	}
}
