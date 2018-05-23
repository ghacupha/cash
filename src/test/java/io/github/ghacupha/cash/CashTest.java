/**
 * fassets - Project for light-weight tracking of fixed assets
 * Copyright © 2018 Edwin Njeru (mailnjeru@gmail.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General License for more details.
 *
 * You should have received a copy of the GNU General License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package io.github.ghacupha.cash;

import org.joda.money.CurrencyMismatchException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.math.RoundingMode;
import java.util.Currency;

import static io.github.ghacupha.cash.HardCash.dollar;
import static io.github.ghacupha.cash.HardCash.euro;
import static io.github.ghacupha.cash.HardCash.shilling;
import static io.github.ghacupha.cash.HardCash.sterling;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@DisplayName("General use cash test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CashTest {

    private Cash cash;
    private final static Currency KES = Currency.getInstance("KES");
    private final static Currency USD = Currency.getInstance("USD");
    private final static Currency EUR = Currency.getInstance("EUR");
    private final static Currency GBP = Currency.getInstance("GBP");

    @BeforeAll
    void setUp() throws Exception {

        cash = shilling(106.50);
    }

    @Test
    @DisplayName("Check if #isMoreThan method is logically correct")
    void isMoreThan() throws Exception {

        assertTrue(cash.isMoreThan(shilling(100)));
    }

    @Test
    @DisplayName("Check if #isMoreThan method is logically correct if the difference is 0.01")
    void isMoreThan1() throws Exception {

        assertTrue(cash.isMoreThan(HardCash.of(106.49,"KES")));
    }

    @Test
    @DisplayName("Check if #isMoreThan method will throw exception if compared with non-similar denomination")
    void cannotCashWithDifferentCurrencies() throws Exception {

        /*try {
            cash.isMoreThan(HardCash.of(106.49,"USD"));
            fail( "My method didn't throw when I expected it to" );
        } catch (CurrencyMismatchException e) {
            e.printStackTrace();
        }*/

        assertThrows(CurrencyMismatchException.class, () -> cash.isMoreThan(HardCash.of(106.49,"USD")));
    }

    @Test
    @DisplayName("Check if #isLessThan method is logically correct")
    void isLessThan() throws Exception {

        assertTrue(cash.isLessThan(HardCash.of(106.51,"KES")));
    }

    @Test
    @DisplayName("Currency codes ISO 4217:2015 test")
    void currencyIsCorrectlyDenominated() throws Exception {

        assertEquals(KES, cash.getCurrency());
        assertEquals(USD, dollar(120).getCurrency());
        assertEquals(EUR, euro(120).getCurrency());
        assertEquals(GBP, sterling(120).getCurrency());

        assertEquals("USD", dollar(120).getCurrency().getCurrencyCode());
        assertEquals("EUR", euro(120).getCurrency().getCurrencyCode());
        assertEquals("KES", shilling(120).getCurrency().getCurrencyCode());
        assertEquals("GBP", sterling(120).getCurrency().getCurrencyCode());

    }


    @Test
    @DisplayName("Additions test")
    void plus() throws Exception {

        assertEquals(shilling(206.53),cash.plus(shilling(100.03)));
    }

    @Test
    @DisplayName("Subtractions test")
    void minus() throws Exception {

        assertEquals(shilling(105.00),cash.minus(HardCash.of(1.5,"KES")));
    }

    @Test
    @DisplayName("Multiplications test")
    void multiply() throws Exception {

        assertEquals(shilling(319.50),cash.multiply(3));
        assertEquals(shilling(319.50),cash.multiply(3, RoundingMode.HALF_EVEN));
        assertEquals(shilling(319.50),cash.multiply(3, RoundingMode.HALF_DOWN));
    }

    @Test
    @DisplayName("Divisions test")
    void divide() throws Exception {

        assertEquals(shilling(32.08),cash.divide(3.32));
        assertEquals(shilling(32.08),cash.divide(3.32,RoundingMode.HALF_DOWN));
        assertEquals(shilling(32.27),cash.divide(3.3,RoundingMode.HALF_EVEN));
    }

    @Test
    @DisplayName("Absolute number tests")
    void ABSworks() {
        assertEquals(dollar(32.08),dollar(-32.08).abs());
    }
}