/**
 * cash - A Java light weight monetary representation facade for the rest of us
 * Copyright © 2018 Edwin Njeru (mailnjeru@gmail.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package io.github.ghacupha.cash;

import org.joda.money.Money;

import java.util.Currency;

/**
 * Allows clients to be able to parse monetary amounts from string and to convert monetary amounts
 * into string that represent both the number and the currency
 *
 * @author edwin.njeru
 */
public class ReadableHardCash extends HardCashDecorator implements ReadableCash {

    public ReadableHardCash(double amount, String currencyCode) {
        super(amount, currencyCode);
    }

    public ReadableHardCash(double amount, Currency currency) {
        super(amount, currency);
    }

    public ReadableHardCash(Money arg) {
        super(arg);
    }

    public ReadableHardCash(Cash shilling) {
        super(shilling);
    }

    public static Cash parse(String moneyStr) {

        return new ReadableHardCash(Money.parse(moneyStr));
    }

    /**
     * @return String equivalent of this
     */
    @Override
    public String getString() {

        return readableBase.toString();
    }

    /**
     * Returns the {@link Cash} equivalent of the string which is given in the argument. For instance
     * if the string argument is 'KES 300.50' the cash value will be same as HardCash.shilling(300.50)
     *
     * @param cashString String inform of string
     * @return Cash equivalent of string in the parameter arg
     */
    @Override
    public Cash parseString(String cashString) {

        return new HardCash(Money.parse(cashString));
    }
}
