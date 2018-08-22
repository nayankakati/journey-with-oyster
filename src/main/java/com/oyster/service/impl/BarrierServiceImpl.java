package com.oyster.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import com.oyster.domain.Barrier;
import com.oyster.domain.Card;
import com.oyster.enums.Direction;
import com.oyster.exception.InsufficientBalanceException;
import com.oyster.exception.JourneyNotValidException;
import com.oyster.map.repository.BarrierRepository;
import com.oyster.map.repository.CostRepository;
import com.oyster.map.repository.JourneyMapRepository;
import com.oyster.service.BarrierService;


/**
 * Created by nayan.kakati on 4/19/18.
 */
public class BarrierServiceImpl implements BarrierService {

	private BarrierRepository barrierRepository;
	private JourneyMapRepository journeyMapRepository;
	public BarrierServiceImpl() {
		barrierRepository = new BarrierRepository();
		journeyMapRepository = new JourneyMapRepository();
	}

	@Override
	public List<Barrier> getBarriers() {
		return barrierRepository.getBarriers();
	}

	@Override
	public boolean isBarrierSuccessfullyPassed(Barrier barrier, Card card) throws JourneyNotValidException, InsufficientBalanceException {
		Double totalCostForTheJourney = 0.0D;
		Barrier lastBarrierTravelled = journeyMapRepository.getLastBarrierCrossed(card.getNumber());
		checkIfItsAValidDirectionAndJourneyType(lastBarrierTravelled, barrier);

		if(barrier.getJourneyType().name().equals("BUS")) {
			totalCostForTheJourney = CostRepository.BUS_COST;
			if(card.getBalance() < totalCostForTheJourney) throw new InsufficientBalanceException("Card balance is low for this travel, Please recharge your card");
			card.deductFromCard(totalCostForTheJourney);
			barrier.setDirection(Direction.EXIT);
			journeyMapRepository.saveMyJourney(card.getNumber(), barrier);
			return true;
		}

		if(barrier.getDirection().name().equals("INWARD")) {
			chargeMaximumAmountTubeStartJourney(barrier, card);
		}
		else {
			totalCostForTheJourney = calculateTotalExpenseForTheTravel(lastBarrierTravelled, barrier);
			if (totalCostForTheJourney != CostRepository.MAX_COST) {
				totalCostForTheJourney = Math.abs(-CostRepository.MAX_COST + totalCostForTheJourney);
				card.addToCard(totalCostForTheJourney);
			}
		}
		journeyMapRepository.saveMyJourney(card.getNumber(), barrier);
		return true;
	}

	private void checkIfItsAValidDirectionAndJourneyType(Barrier lastBarrierTravelled , Barrier barrier) throws JourneyNotValidException {
		if(Objects.nonNull(lastBarrierTravelled) && lastBarrierTravelled.getDirection().equals(barrier.getDirection()) ||
			(lastBarrierTravelled == null && barrier.getDirection().name().equals("EXIT"))) {
			throw new JourneyNotValidException("You cannot make this journey with "+barrier.getDirection().name()+" direction.");
		}
	}

	private void chargeMaximumAmountTubeStartJourney(Barrier barrier, Card card) throws InsufficientBalanceException {
		if(Objects.nonNull(barrier) && barrier.getJourneyType().name().equals("TUBE")) {
			Double chargeMaxAmountForTube = CostRepository.MAX_COST;
			if(card.getBalance() < chargeMaxAmountForTube) throw new InsufficientBalanceException("Card balance is low for this travel, Please recharge your card");
			card.deductFromCard(chargeMaxAmountForTube);
		}
	}

	private Double calculateTotalExpenseForTheTravel(Barrier lastBarrierTravelled, Barrier barrier) {
		if(lastBarrierTravelled.getZones().contains(1) && barrier.getZones().contains(1)) return CostRepository.ONLY_ZONE_1_COST;
		boolean isZone1Travelled = false;
		AtomicInteger totalZonesTravelled = new AtomicInteger() ;

		if(lastBarrierTravelled.getZones().contains(1) || barrier.getZones().contains(1)) {
			isZone1Travelled = true;
			totalZonesTravelled.getAndIncrement();
		}

		List<Integer> visitedZones = new ArrayList<>();
		lastBarrierTravelled.getZones().stream().forEach(zoneId -> {
			if(zoneId != 1) {
				totalZonesTravelled.getAndIncrement();
				visitedZones.add(zoneId);
			}
		});

		barrier.getZones().stream().forEach(zoneId -> {
			if(zoneId != 1 && !visitedZones.contains(zoneId)) totalZonesTravelled.getAndIncrement();
		});
		return calculateTotalExpenseForTheTravel(totalZonesTravelled.get(), isZone1Travelled);
	}

	private Double calculateTotalExpenseForTheTravel(Integer totalZonesTravelled, Boolean isZone1Travelled) {
		if(totalZonesTravelled == 1 && isZone1Travelled) return CostRepository.ONLY_ZONE_1_COST;
		if(totalZonesTravelled == 1 && !isZone1Travelled) return CostRepository.ANY_1_OUTSIDE_ZONE_1;
		if(totalZonesTravelled == 2 && isZone1Travelled) return CostRepository.ANY_1_OUTSIDE_ZONE_1;
		if(totalZonesTravelled == 2 && !isZone1Travelled) return CostRepository.ANY_2_ZONES_EXCLUDING_ZONE_1;
		if(totalZonesTravelled == 3 && isZone1Travelled) return CostRepository.ANY_2_ZONES_INCLUDING_ZONE_1;
		if(totalZonesTravelled == 3) return CostRepository.ANY_3_ZONES;
		return 0D;
	}
}
