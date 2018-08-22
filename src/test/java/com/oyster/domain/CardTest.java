package com.oyster.domain;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Created by nayan.kakati on 4/19/18.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest(Card.class)
public class CardTest {

	private Integer number;
	private double balance;

	Card expectedCard;
	Card card;

	@Before
	public void init() {
		number = 1234;
		balance = 30d;
		card = new Card(number,balance);
	}

	@Test
	public void get_all_card_details_success() {
		expectedCard = Mockito.mock(Card.class);

		when(expectedCard.getBalance()).thenReturn(card.getBalance());
		when(expectedCard.getNumber()).thenReturn(card.getNumber());

		Integer actualNumber = expectedCard.getNumber();
		double actualBalance = expectedCard.getBalance();

		assertEquals(number, actualNumber);
		assertEquals(balance, actualBalance, 0.2);
	}

	@Test
	public void add_amount_to_card_success() {
		double actualBalance = card.addToCard(0);
		assertEquals(balance, actualBalance, 0.2);
	}

	@Test
	public void deduct_amount_from_card_success() {
		double actualBalance = card.deductFromCard(0);
		assertEquals(balance, actualBalance, 0.2);
	}
}
