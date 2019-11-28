package test;

import static org.junit.Assert.*;

import org.junit.Test;

import model.Dice;

/**
 * This test case class tests the dice numbers 
 * @author s_shehna
 *
 */
public class TestDice {

	int diceNumber= 3;
	boolean check;
	Dice dice= new Dice(diceNumber);
	/**
	 * This test case tests dice numbers generated when rolled 3 times are always less than  6
	 */
	@Test
	public void testDiceValid() {
		
		int results[] = dice.getDiceArray();
			
		for(int i=0;i<3;i++) {
	
		check = results[i] >6 ? false : true;

			assertTrue(check);
		
}
	
		
}
	/**
	 * This testcase test dice numbers generated for invalid values
	 */
	@Test
	public void testDiceInValid() {
		
		int results[] = dice.getDiceArray();
			
		for(int i=0;i<3;i++) {
	
		check = results[i] >6 ? true: false ;

			assertFalse(check);
		
}
	
		
}
		}


