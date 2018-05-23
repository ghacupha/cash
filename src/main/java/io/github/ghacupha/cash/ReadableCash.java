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

/**
 * This interface extends the Cash interface with the ability to parse cash amounts from string
 * allowing the client to perceive money items as a single items that can be fit into a single
 * database column
 *
 * @author edwin.njeru
 */
public interface ReadableCash extends Cash {

    /**
     * @return String equivalent of this
     */
    String getString();

    /**
     * Returns the {@link Cash} equivalent of the string which is given in the argument. For instance
     * if the string argument is 'KES 300.50' the cash value will be same as HardCash.shilling(300.50)
     *
     * @param cashString String inform of string
     * @return Cash equivalent of string in the parameter arg
     */
    Cash parseString(String cashString);
}
