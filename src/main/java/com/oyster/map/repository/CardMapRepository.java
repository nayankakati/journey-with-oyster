package com.oyster.map.repository;

import java.util.HashMap;
import java.util.Map;

import com.oyster.domain.Card;

/**
 * Created by nayan.kakati on 4/19/18.
 */

public class CardMapRepository {
	private Map<Integer, Card> cards = new HashMap<>();

	public Card getCard(Integer number) {
		return cards.get(number);
	}

	public Card saveCard(Card card) {
		cards.put(card.getNumber(), card);
		return card;
	}
}
