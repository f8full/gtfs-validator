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

import static com.google.common.truth.Truth.assertThat;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mobilitydata.gtfsvalidator.input.GtfsFeedName;
import org.mobilitydata.gtfsvalidator.notice.EmptyFileNotice;
import org.mobilitydata.gtfsvalidator.notice.MissingRequiredFieldError;
import org.mobilitydata.gtfsvalidator.notice.NoticeContainer;
import org.mobilitydata.gtfsvalidator.validator.ValidatorLoader;

/** Runs GtfsStopTableLoader on test CSV data. */
@RunWith(JUnit4.class)
public class GtfsStopTableLoaderTest {
  private static final GtfsFeedName FEED_NAME = GtfsFeedName.parseString("au-sydney-buses");

  @Test
  public void validFileShouldNotGenerateNotice() throws IOException {
    ValidatorLoader validatorLoader = new ValidatorLoader();
    Reader reader =
        new StringReader(
            "stop_id,stop_lat,stop_lon,stop_name"
                + System.lineSeparator()
                + "stop id value,23.45,-50.55,stop name");
    GtfsStopTableLoader loader = new GtfsStopTableLoader();
    NoticeContainer noticeContainer = new NoticeContainer();
    GtfsStopTableContainer tableContainer =
        (GtfsStopTableContainer) loader.load(reader, FEED_NAME, validatorLoader, noticeContainer);
    reader.close();

    assertThat(noticeContainer.getValidationNotices()).isEmpty();
    assertThat(tableContainer.entityCount()).isEqualTo(1);
    GtfsStop stop = tableContainer.byStopId("stop id value");
    assertThat(stop).isNotNull();
    assertThat(stop.stopId()).matches("stop id value");
    assertThat(stop.stopLat()).isEqualTo(23.45);
    assertThat(stop.stopLon()).isEqualTo(-50.55);
    assertThat(stop.stopName()).matches("stop name");
  }

  @Test
  public void missingRequiredFieldShouldGenerateNotice() throws IOException {
    ValidatorLoader validatorLoader = new ValidatorLoader();
    Reader reader =
        new StringReader(
            "stop_id,stop_lat,stop_lon,stop_name"
                + System.lineSeparator()
                + ",30.88,-50.55,stop name");
    GtfsStopTableLoader loader = new GtfsStopTableLoader();
    NoticeContainer noticeContainer = new NoticeContainer();
    GtfsStopTableContainer tableContainer =
        (GtfsStopTableContainer) loader.load(reader, FEED_NAME, validatorLoader, noticeContainer);
    reader.close();

    assertThat(noticeContainer.getValidationNotices())
        .containsExactly(new MissingRequiredFieldError("stops.txt", 2, "stop_id"));
    assertThat(tableContainer.entityCount()).isEqualTo(0);
  }

  @Test
  public void emptyFileShouldGenerateNotice() throws IOException {
    ValidatorLoader validatorLoader = new ValidatorLoader();
    Reader reader = new StringReader("");
    GtfsStopTableLoader loader = new GtfsStopTableLoader();
    NoticeContainer noticeContainer = new NoticeContainer();

    loader.load(reader, FEED_NAME, validatorLoader, noticeContainer);
    reader.close();

    assertThat(noticeContainer.getValidationNotices())
        .containsExactly(new EmptyFileNotice("stops.txt"));
  }
}