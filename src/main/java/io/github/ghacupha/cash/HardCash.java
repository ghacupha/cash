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


import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.math.RoundingMode;
import java.util.Currency;

import static java.math.RoundingMode.HALF_EVEN;

/**
 * Implements the {@link Cash} interface and is immutable
 *
 * @author edwin.njeru
 */
public class HardCash implements Cash {

    private final Money base;

    public HardCash(double amount, String currencyCode) {

        // Make sure double doesn't bring items with more than 2 DPs
        //base = Money.of(CurrencyUnit.getInstance(currencyCode), amount);
        Money major = Money.ofMajor(CurrencyUnit.getInstance(currencyCode), Math.round(amount * 100));
        base = major.dividedBy(100, HALF_EVEN);
    }

    // for use in class only
    public HardCash(Money arg) {
        this.base = arg;
    }

    public HardCash(double amount, Currency currency) {
        this(amount, currency.getCurrencyCode());
    }

    public HardCash(Cash cash) {
        base = Money.of(CurrencyUnit.of(cash.getCurrency()), cash.getNumber().doubleValue());
    }

    /**
     * Creates {@link Cash} with double amount and an ISO-4217 getCurrency code
     *
     * @param value          amount of Cash in double
     * @param currencyString getCurrency code in ISO-4217 denotation
     * @return {@link Cash} amount in the string getCurrency specified
     */
    public static Cash of(double value, String currencyString) {

        return new HardCash(value, currencyString);
    }

    public static Cash of(double value, Currency currency) {

        return of(value, currency.getCurrencyCode());
    }

    /**
     * Creates {@link Cash} with double amount using USD {@link Currency}
     *
     * @param value amount of Cash in double
     * @return {@link Cash} amount in USD {@link Currency}
     */
    public static Cash dollar(double value) {

        return new HardCash(value, Currency.getInstance("USD"));
    }

    /**
     * Creates {@link Cash} with double amount using GBP {@link Currency}
     *
     * @param value amount of Cash in double
     * @return {@link Cash} amount in GBP {@link Currency}
     */
    public static Cash sterling(double value) {

        return new HardCash(value, Currency.getInstance("GBP"));
    }

    /**
     * Creates {@link Cash} with double amount using EUR {@link Currency}
     *
     * @param value amount of Cash in double
     * @return {@link Cash} amount in EUR {@link Currency}
     */
    public static Cash euro(double value) {

        return new HardCash(value, Currency.getInstance("EUR"));
    }

    /**
     * Creates {@link Cash} with double amount using KES {@link Currency}
     *
     * @param value amount of Cash in double
     * @return {@link Cash} amount in KES {@link Currency}
     */
    public static Cash shilling(double value) {

        return new HardCash(value, Currency.getInstance("KES"));
    }

    public static Cash fromMoneta(org.javamoney.moneta.Money money) {

        return HardCash.of(money.getNumber().doubleValue(), Currency.getInstance(money.getCurrency().getCurrencyCode()));
    }

    @Override
    public Currency getCurrency() {
        return base.getCurrencyUnit().toCurrency();
    }

    @Override
    public boolean isMoreThan(Cash arg) {

        HardCash hardCash = (HardCash) arg;

        return base.isGreaterThan(hardCash.base);
    }

    @Override
    public boolean isLessThan(Cash arg) {

        HardCash hardCash = (HardCash) arg;

        return base.isLessThan(hardCash.base);
    }

    @Override
    public Cash plus(Cash arg) {

        return getSum(getNativeAmount(arg));
    }

    @Override
    public Cash minus(Cash arg) {

        return getSum(-getNativeAmount(arg));
    }

    @Override
    public Cash multiply(double arg) {

        return multiply(arg, HALF_EVEN);
    }

    @Override
    public Cash multiply(double arg, RoundingMode roundingMode) {

        return new HardCash(this.base.multipliedBy(arg, roundingMode));
    }

    @Override
    public Cash divide(double arg) {

        return divide(arg, HALF_EVEN);
    }

    @Override
    public Cash divide(double arg, RoundingMode roundingMode) {

        return multiply(1 / arg, roundingMode);
    }

    @Override
    public Number getNumber() {

        return this.base.getAmount();
    }

    /**
     * @return True if the instrinsic amount in the {@link Cash} object is zero
     */
    @Override
    public boolean isZero() {

        return getNumber().doubleValue() == 0.00;
    }

    @Override
    public String toString() {

        return this.base.getCurrencyUnit().getCurrencyCode() + " " + this.base.getAmount().doubleValue();
    }

    /**
     * @return {@link Cash} as absolute amount
     */
    @Override
    public Cash abs() {
        return new HardCash(this.base.abs());
    }

    /**
     * @param arg the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
    @Override
    public int compareTo(Cash arg) {

        HardCash other = (HardCash) arg;

        return this.base.compareTo(other.base);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        HardCash hardCash = (HardCash) o;

        return base.equals(hardCash.base);
    }

    @Override
    public int hashCode() {
        return base.hashCode();
    }

    private HardCash getSum(double damount) {
        return new HardCash(getNativeAmount(this) + damount, this.base.getCurrencyUnit().toCurrency());
    }

    private double getNativeAmount(Cash arg) {

        return arg.getNumber().doubleValue();
    }
}
