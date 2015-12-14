package sla.harmony.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class UtilsTest {

	/**
	 * Tests the read and write helper functions
	 */
	@Test
	public void fileTest() {
		// TODO when I properly figure out eclipse and its path system
	}

	/**
	 * Tests the encrypt and decrypt functions. Three tests using common strings that are likely to be encoded
	 */
	@Test
	public void cryptoTestNormal() {

		// Test 1
		String key = "test crypto key";
		String data = "This is data that should be encrypted";

		helper(key, data);
		
		// Test 2
		key = "Another test";
		data = "{\"regular\": \"data\"}";

		helper(key, data);

		// Test 3
		key = "final test";
		data = "{[more data \n that would be commonly \nencoded]}";

		helper(key, data);
	}

	/**
	 * Tests the encrypt and decrypt functions. Three tests using symbols in different combinations.
	 */
	@Test
	public void cryptoTestSymbols() {

		// Test 1
		String key = "Symbol data test";
		String data = "638749+*/63987\\/*&*(%^3$*&Y^)&*(^?><?>}{\";''```~";

		helper(key, data);

		// Test 2
		key = "6549826/++*(&%^$@$#(dd)}{}[]/\\\":::";
		data = "Symbol key test";
		
		helper(key, data);

		// Test 3
		key = "\n\t];.];'.}:\">}.;]'/`];.]`~}>}.;5648634'";
		data = "907123897985&*^$%*^%#$&^]';.]\\`~~\n]\\]}{:>}{;.";

		helper(key, data);
	}

	/**
	 * Tests the encrypt and decrypt functions. Three tests using blank strings in different combinations.
	 */
	@Test
	public void cryptoTestBlank() {
		// Test 1
		String key = "Blank data Test";
		String data = "";

		helper(key, data);

		// Test 2
		key = "";
		data = "Blank key test";

		helper(key, data);
		
		// Test 3
		key = "";
		data = "";

		helper(key, data);
	}

	/**
	 * Tests the encrypt and decrypt functions. Three tests using null strings in different combinations.
	 */
	@Test
	public void cryptoTestNull() {

		// Test 1
		String key = null;
		String data = "null key test";

		helper(key, data);

		// Test 2
		key = "null data test";
		data = null;
		boolean nullPointer = false;
		
		try {
			helper(key, data);
		} catch(NullPointerException e) {
			// Expected NullPointerException
			nullPointer = true;
		}
		assertTrue(nullPointer);
		nullPointer = false;

		// Test 3
		key = null;
		data = null;

		try {
			helper(key, data);
		} catch(NullPointerException e) {
			// Expected NullPointerException
			nullPointer = true;
		}
		assertTrue(nullPointer);
		nullPointer = false;

	}
	
	/**
	 * not as useful anymore but good for when everything needs to change
	 * @param key
	 * @param data
	 */
	private static void helper(String key, String data) {
		assertEquals(data, Utils.decrypt(key, Utils.encrypt(key, data)));
	}
}
