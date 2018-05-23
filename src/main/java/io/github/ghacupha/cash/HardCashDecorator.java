/**
 * cash - A light weight monetary facade for the rest of us
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

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.util.Currency;

/**
 * This object is created simply to allow child classes to implement the {@code Cash} interface methods
 * without the boiler plate since we are extending the {@code HardCash} class
 *
 * @author edwin.njeru
 */
public class HardCashDecorator extends HardCash implements Cash {

    /**
     * This new element added into the decorator will add the ability for cash to be converted to and from
     * String using the APIs provided by the joda money libary.
     */
    protected final Money readableBase;

    public HardCashDecorator(double amount, String currencyCode) {
        super(amount, currencyCode);
        this.readableBase = Money.of(CurrencyUnit.of(currencyCode), amount);
    }

    public HardCashDecorator(double amount, Currency currency) {
        super(amount, currency);
        this.readableBase = Money.of(CurrencyUnit.of(currency), amount);
    }

    public HardCashDecorator(Money arg) {
        super(arg);
        this.readableBase = arg;
    }

    public HardCashDecorator(Cash cash) {
        super(cash);
        readableBase = Money.of(CurrencyUnit.of(cash.getCurrency()), cash.getNumber().doubleValue());
    }
}
