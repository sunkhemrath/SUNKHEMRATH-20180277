package controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ValidateAddressTest {

	//sun khemrath 20180277
	private PlaceOrderController placeOrderController;

	@BeforeEach
	void setUp() throws Exception {
		placeOrderController = new PlaceOrderController();
	}

	@ParameterizedTest
	@CsvSource({
		"hanoi, true",
		"ngo74, false",
		", false"
	})

	void test(String address, boolean expected) {
		boolean isValid = placeOrderController.validateName(address);
		assertEquals(expected, isValid);
	}

}