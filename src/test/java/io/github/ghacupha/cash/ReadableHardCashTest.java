/**
 * fassets - Project for light-weight tracking of fixed assets
 * Copyright © 2018 Edwin Njeru (mailnjeru@gmail.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package io.github.ghacupha.cash;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static io.github.ghacupha.cash.HardCash.shilling;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Cash test for reading cash strings :-)")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ReadableHardCashTest {

    private ReadableCash readableCash;
    private Cash commaSeparatedCash;

    @BeforeAll
    void setUp() throws Exception {

        readableCash = new ReadableHardCash(shilling(300.52));
        commaSeparatedCash = new ReadableHardCash(shilling(10250.58));
    }

    @Test
    @DisplayName("Convert cash to readable string")
    void getString() {

        assertEquals("KES 300.52", readableCash.toString());
        assertEquals("KES 10250.58", commaSeparatedCash.toString());
    }

    @Test
    @DisplayName("Convert readable string to cash")
    void parseString() {

        assertEquals(shilling(10358.31), readableCash.parseString("KES 10358.31"));
    }
}