/*
 * Copyright 2020 Google LLC
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

package org.mobilitydata.gtfsvalidator.notice;

import com.google.common.collect.ImmutableMap;
import org.mobilitydata.gtfsvalidator.type.GtfsTime;

public class StopTimeWithArrivalBeforePreviousDepartureTimeNotice extends ValidationNotice {
  public StopTimeWithArrivalBeforePreviousDepartureTimeNotice(
      long csvRowNumber,
      long prevCsvRowNumber,
      String tripId,
      GtfsTime arrivalTime,
      GtfsTime departureTime) {
    super(
        ImmutableMap.of(
            "csvRowNumber", csvRowNumber,
            "prevCsvRowNumber", prevCsvRowNumber,
            "tripId", tripId,
            "departureTime", departureTime.toHHMMSS(),
            "arrivalTime", arrivalTime.toHHMMSS()));
  }

  @Override
  public String getCode() {
    return "stop_time_with_arrival_before_previous_departure_time";
  }
}
