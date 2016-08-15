/*
 * This file is part of QuarterBukkit-Plugin.
 * Copyright (c) 2012 QuarterCode <http://www.quartercode.com/>
 *
 * QuarterBukkit-Plugin is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * QuarterBukkit-Plugin is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with QuarterBukkit-Plugin. If not, see <http://www.gnu.org/licenses/>.
 */

package com.quartercode.quarterbukkit.api;

import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * A very basic optional which actually works with two values at the same time. See {@link Optional} for more information.
 * Note that most methods are not implemented since they would require the introduction of a generic pair class, which would be suboptimal.
 *
 * @param <A> The type of the first value.
 * @param <B> The type of the second value.
 */
public class BiOptional<A, B> {

    private static final BiOptional<?, ?> EMPTY = new BiOptional<>();

    /**
     * Returns an empty {@code BiOptional} instance. No values are present for this BiOptional.
     *
     * @param <A> The type of the first value.
     * @param <B> The type of the second value.
     * @return The empty {@code BiOptional}.
     */
    @SuppressWarnings ("unchecked")
    public static <A, B> BiOptional<A, B> empty() {

        return (BiOptional<A, B>) EMPTY;
    }

    /**
     * Returns a {@code BiOptional} with the specified present non-null values.
     *
     * @param <A> The type of the first value.
     * @param <B> The type of the second value.
     * @param value1 The first value to be present, which must be non-null.
     * @param value2 The second value to be present, which must be non-null.
     * @return A {@code BiOptional} with the two given values.
     * @throws NullPointerException If a value is {@code null}.
     */
    public static <A, B> BiOptional<A, B> of(A value1, B value2) {

        return new BiOptional<>(value1, value2);
    }

    /**
     * Returns a {@code BiOptional} describing the specified values, if both are non-null, otherwise returns an empty {@code BiOptional}.
     *
     * @param <A> The type of the first value.
     * @param <B> The type of the second value.
     * @param value1 The first possibly-null value to describe.
     * @param value2 The second possibly-null value to describe.
     * @return An {@code BiOptional} with two present values if the specified values are non-null, otherwise an empty {@code BiOptional}.
     */
    public static <A, B> BiOptional<A, B> ofNullables(A value1, B value2) {

        return value1 == null || value2 == null ? empty() : of(value1, value2);
    }

    private final A value1;
    private final B value2;

    private BiOptional() {

        value1 = null;
        value2 = null;
    }

    private BiOptional(A value1, B value2) {

        this.value1 = Objects.requireNonNull(value1);
        this.value2 = Objects.requireNonNull(value2);
    }

    /**
     * Returns {@code true} if both values are present, otherwise {@code false}.
     *
     * @return {@code true} if both values are present, otherwise {@code false}.
     */
    public boolean arePresent() {

        return value1 != null && value2 != null;
    }

    /**
     * If both values are present, this method invokes the specified consumer with the two values, otherwise it does nothing.
     *
     * @param consumer Block to be executed if both values are present.
     * @throws NullPointerException If both values are present, but the consumer is {@code null}.
     */
    public void ifPresent(BiConsumer<? super A, ? super B> consumer) {

        if (value1 != null && value2 != null) {
            consumer.accept(value1, value2);
        }
    }

    /**
     * If both values are present, and the values match the given predicate, this method returns a {@code BiOptional} describing the two values, otherwise it returns an empty {@code BiOptional}.
     *
     * @param predicate A predicate to apply to the values, if present.
     * @return A {@code BiOptional} describing the values of this {@code BiOptional} if both values are present and the values match the given predicate, otherwise an empty {@code BiOptional}
     * @throws NullPointerException If the predicate is {@code null}.
     */
    public BiOptional<A, B> filter(BiPredicate<? super A, ? super B> predicate) {

        Objects.requireNonNull(predicate);
        if (!arePresent()) {
            return this;
        } else {
            return predicate.test(value1, value2) ? this : empty();
        }
    }

    @Override
    public int hashCode() {

        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {

        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public String toString() {

        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
