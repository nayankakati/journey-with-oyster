package com.oyster.map.repository;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.oyster.domain.Card;

/**
 * Created by nayan.kakati on 4/19/18.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest(CardMapRepository.class)
public class CardMapRepositoryTest {
	private Map<Integer, Card> cards;

	Card card;
	CardMapRepository cardMapRepository;
	@Before
	public void init() {
		cards = new HashMap<>();
	}

	@Test
	public void save_card_success() {
		cardMapRepository =  new CardMapRepository();
		card = new Card(1,3);
		Card actualCard = cardMapRepository.saveCard(card);
		assertEquals(card.getNumber(), actualCard.getNumber());
	}

	@Test
	public void get_card_success() {
		cardMapRepository =  new CardMapRepository();
		card = new Card(1,3);
		cardMapRepository.saveCard(card);
		Card actualCard = cardMapRepository.getCard(card.getNumber());
		assertEquals(card, actualCard);
	}
}
