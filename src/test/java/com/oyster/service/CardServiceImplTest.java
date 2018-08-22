package com.oyster.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.oyster.domain.Card;
import com.oyster.map.repository.CardMapRepository;
import com.oyster.service.impl.CardServiceImpl;

/**
 * Created by nayan.kakati on 4/19/18.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest(CardServiceImpl.class)
public class CardServiceImplTest {
	@Mock
	private CardMapRepository cardMapRepository;

	private CardServiceImpl cardService;

	private Card card;

	@Before
	public void init() {
		card = new Card(Math.abs(new Random().nextInt()),0.0D);
	}

	@Test
	public void save_card_test_with_success() {
		cardService = new CardServiceImpl();
		when(cardMapRepository.saveCard(any())).thenReturn(card);
		Card actualCard = cardService.saveCard();
		assertNotNull(actualCard);
		assertEquals(actualCard.getBalance(), card.getBalance());
	}

	@Test
	public void get_card_test_with_success() throws Exception {
		cardMapRepository = mock(CardMapRepository.class);
		whenNew(CardMapRepository.class).withAnyArguments().thenReturn(cardMapRepository);
		cardService = new CardServiceImpl();

		when(cardMapRepository.getCard(any())).thenReturn(card);
		Card actualCard = cardService.getCard(any());
		assertNotNull(actualCard);
		assertEquals(actualCard.getBalance(), card.getBalance());
	}
}
