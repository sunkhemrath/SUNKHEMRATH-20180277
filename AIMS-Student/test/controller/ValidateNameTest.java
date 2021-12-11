package controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ValidateNameTest {
	//sunkhemrath 20180277
	private PlaceOrderController placeOrderController;

	@BeforeEach
	void setUp() throws Exception {
		placeOrderController = new PlaceOrderController();
	}

	@ParameterizedTest
	@CsvSource({
		"khemrathSun, true",
		"khemrathSun01234, false",
		"khemrath12, false"
	})

	void test(String name, boolean expected) {
		boolean isValid = placeOrderController.validateName(name);
		assertEquals(expected, isValid);
	}

}
