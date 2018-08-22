package com.oyster.service;

import com.oyster.domain.Card;

/**
 * Created by nayan.kakati on 4/19/18.
 */
public interface CardService {
	Card saveCard();
	Card getCard(Integer number);
}
