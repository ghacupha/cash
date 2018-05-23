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

import java.math.RoundingMode;

/**
 * Implementation of the money pattern to better represent and create monetary operations.
 *
 * @author edwin.njeru
 */
public interface Cash extends Comparable<Cash>, IsNumberical, HasDenomination {

    /**
     * @param arg {@link Cash} amount for comparison
     * @return True if the parameter argument is more than this amount
     */
    boolean isMoreThan(Cash arg);

    /**
     * @param arg {@link Cash} amount for comparison
     * @return True if the parameter argument is less than this amount
     */
    boolean isLessThan(Cash arg);

    /**
     * @param arg {@link Cash} amount to be added to this
     * @return New instance of {@link Cash} being the summation of this and the argument
     */
    Cash plus(Cash arg);

    /**
     * @param arg {@link Cash} amount to be subtracted to this
     * @return New instance of {@link Cash} being the remainder when the argument is subtracted from this
     */
    Cash minus(Cash arg);

    /**
     * Multiplies this using a double amount using {@link RoundingMode#HALF_EVEN}
     *
     * @param arg double amount by which we are to multiply this
     * @return New instance of {@link Cash} object containing multiplied amount
     */
    Cash multiply(double arg);

    /**
     * Multiplies this using a double amount and an explicit {@link RoundingMode}
     *
     * @param arg          double amount by which we are to multiply this
     * @param roundingMode {@link RoundingMode} to apply to the result
     * @return New instance of {@link Cash} object containing multiplied amount
     */
    Cash multiply(double arg, RoundingMode roundingMode);

    /**
     * Divides this by the double amount in the parameter, using {@link RoundingMode#HALF_EVEN}
     *
     * @param arg double amount by which we are to divide this
     * @return New instance of {@link Cash} object containing divided amount
     */
    Cash divide(double arg);

    /**
     * Divides this by the double amount in the parameter, using an explicit {@link RoundingMode}
     *
     * @param arg          double amount by which we are to divide this
     * @param roundingMode {@link RoundingMode} to apply to the result
     * @return New instance of {@link Cash} object containing divided amount
     */
    Cash divide(double arg, RoundingMode roundingMode);

    /**
     * @return True if the instrinsic amount in the {@link Cash} object is zero
     */
    boolean isZero();

    /**
     * @return {@link Cash} as absolute amount
     */
    Cash abs();

    boolean equals(Object o);

    int hashCode();
}
