package com.oyster.service.impl;

import java.util.Random;

import com.oyster.domain.Card;
import com.oyster.map.repository.CardMapRepository;
import com.oyster.service.CardService;

/**
 * Created by nayan.kakati on 4/19/18.
 */
public class CardServiceImpl implements CardService {

	private CardMapRepository cardMapRepository;

	public CardServiceImpl() {
		cardMapRepository = new CardMapRepository();
	}

	@Override
	public Card saveCard() {
		Card card = new Card(Math.abs(new Random().nextInt()),0.0D);
		cardMapRepository.saveCard(card);
		return card;
	}

	@Override
	public Card getCard(Integer number) {
		return cardMapRepository.getCard(number);
	}
}
