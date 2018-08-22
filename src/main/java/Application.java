import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

import com.oyster.domain.Barrier;
import com.oyster.domain.Card;
import com.oyster.enums.Direction;
import com.oyster.enums.JourneyType;
import com.oyster.exception.InsufficientBalanceException;
import com.oyster.exception.JourneyNotValidException;
import com.oyster.service.BarrierService;
import com.oyster.service.CardService;
import com.oyster.service.impl.BarrierServiceImpl;
import com.oyster.service.impl.CardServiceImpl;

/**
 * Created by nayan.kakati on 4/19/18.
 */
public class Application {

	static BarrierService barrierService = new BarrierServiceImpl();
	static CardService cardService = new CardServiceImpl();

	private static List<Barrier> barriers = barrierService.getBarriers();

	public static void main(String[] args) {
		Card card = cardService.saveCard();
		card.addToCard(30D);

		Scanner scanner = new Scanner(System.in);

		boolean continueMyJourney = true;

		while (continueMyJourney) {
			System.out.println("Welcome!!!!\n\nYou have "+ card.getBalance() +"£ in your card\n\nLet's begin your journey, how do you want to travel by BUS (1) or by TUBE (2) ");

			int journey = scanner.nextInt();

			if(journey == 1) {
				Barrier barrier = new Barrier(new HashSet<>(Arrays.asList(1)),"Earl’s Court", Direction.INWARD, JourneyType.BUS );
				getTravelResult(card, barrier);

			} else {
				boolean travelWithinTube = true;
				while (travelWithinTube) {
					System.out.println("\n\nWhere and which barrier you want to travel (PRESS -1 to exit without swipe out):\n\n ");
					for (int i = 0; i < barriers.size(); i++)
						System.out.println(i + ". " + barriers.get(i).getName() + " " + barriers.get(i).getDirection() + " with " + barriers.get(i).getJourneyType());

					int tubeJourney = scanner.nextInt();
					if (tubeJourney == -1) {
						System.out.println("\n\nYour available card balance is : "+ card.getBalance());
						System.exit(1);
					}
					Barrier barrier = barriers.get(tubeJourney);
					if (barrier.getDirection().name().equals("EXIT")) travelWithinTube = false ;
					getTravelResult(card, barrier);
				}
			}
			System.out.println("\n\nDo you want to end your travel 1. YES, 2. NO");
			int endJourney = scanner.nextInt();
			if(endJourney == 1) {
				continueMyJourney = false;
				System.out.println("\n\nYour available card balance is : "+ card.getBalance());
			}
		}

	}

	private static void getTravelResult(Card card, Barrier barrier) {
		try {
			boolean isTravelSuccessful = barrierService.isBarrierSuccessfullyPassed(barrier, card);
			if (isTravelSuccessful && (barrier.getDirection().name().equals("EXIT") || barrier.getJourneyType().name().equals("BUS"))) {
				System.out.println("\n\n*****Congratulations your travel has been successful***** \n\n  Your current card balance is " + card.getBalance() + "£\n\n");
			} else {
				System.out.println("\n\n*****Congratulations your journey has been started***** please select EXIT on completion of your journey\n\n");
			}
		} catch (JourneyNotValidException e) {
			System.out.println(e.getMessage());
		} catch (InsufficientBalanceException e) {
			System.out.println(e.getMessage());
		}
	}
}
