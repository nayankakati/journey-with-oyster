package com.oyster.service;

import java.util.List;

import com.oyster.domain.Barrier;
import com.oyster.domain.Card;
import com.oyster.exception.InsufficientBalanceException;
import com.oyster.exception.JourneyNotValidException;

/**
 * Created by nayan.kakati on 4/19/18.
 */
public interface BarrierService {

	 List<Barrier> getBarriers();

	 boolean isBarrierSuccessfullyPassed(Barrier barrier, Card card) throws JourneyNotValidException, InsufficientBalanceException;
}
