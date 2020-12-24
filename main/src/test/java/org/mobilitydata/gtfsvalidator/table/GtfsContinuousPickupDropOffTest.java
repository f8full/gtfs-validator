/*
 * Copyright 2020 Google LLC, MobilityData IO
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mobilitydata.gtfsvalidator.table;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static com.google.common.truth.Truth.assertThat;

@RunWith(JUnit4.class)
public class GtfsContinuousPickupDropOffTest {
    @Test
    public void shouldReturnEnumValue() {
        assertThat(GtfsContinuousPickupDropOff.forNumber(0)).isEqualTo(GtfsContinuousPickupDropOff.ALLOWED);
        assertThat(GtfsContinuousPickupDropOff.forNumber(1)).isEqualTo(GtfsContinuousPickupDropOff.NOT_AVAILABLE);
        assertThat(GtfsContinuousPickupDropOff.forNumber(2)).isEqualTo(GtfsContinuousPickupDropOff.MUST_PHONE);
        assertThat(GtfsContinuousPickupDropOff.forNumber(3)).isEqualTo(GtfsContinuousPickupDropOff.ON_REQUEST_TO_DRIVER);
    }

    @Test
    public void shouldReturnNull() {
        assertThat(GtfsContinuousPickupDropOff.forNumber(-1)).isNull();
        assertThat(GtfsContinuousPickupDropOff.forNumber(4)).isNull();
    }
}