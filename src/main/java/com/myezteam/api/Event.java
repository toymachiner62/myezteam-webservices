/**
 * Team.java
 * webservices
 * 
 * Created by jeremy on Nov 1, 2013
 * DoApp, Inc. owns and reserves all rights to the intellectual
 * property and design of the following application.
 *
 * Copyright 2013 - All rights reserved.  Created by DoApp, Inc.
 */
package com.myezteam.api;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.myezteam.api.Response.ResponseType;
import com.yammer.dropwizard.json.JsonSnakeCase;


/**
 * @author jeremy
 * 
 */
@JsonSnakeCase
public class Event {
  @JsonProperty
  private Long id;
  @JsonProperty
  private String name;
  @JsonProperty
  private Long teamId;
  @JsonIgnore
  private DateTime start;
  @JsonIgnore
  private DateTime end;
  @JsonProperty
  private String description;
  @JsonProperty
  private String location;
  @JsonProperty
  private ResponseType defaultResponse;

  private static final DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendYear(4, 4).appendLiteral("-")
      .appendMonthOfYear(2).appendLiteral("-").appendDayOfMonth(2).appendLiteral(" ").appendHourOfDay(2).appendLiteral(":")
      .appendMinuteOfHour(2).appendLiteral(":").appendSecondOfMinute(2).appendLiteral(".").appendMillisOfSecond(1)
      .toFormatter();

  private Event() {}

  /**
   * @param long1
   */
  public Event(Long id, String name, Long teamId, String start, String end, String description, String location,
      ResponseType defaultResponse) {
    this.id = id;
    this.name = name;
    this.teamId = teamId;
    if (start != null) {
      this.start = formatter.parseDateTime(start);
    }
    if (end != null) {
      this.end = formatter.parseDateTime(end);
    }
    this.description = description;
    this.location = location;
    this.defaultResponse = defaultResponse;
  }

  public Long getId() {
    return this.id;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @return
   */
  public Long getTeamId() {
    return teamId;
  }

  @JsonProperty
  public String getStart() {
    if (start != null) { return start.toString(); }
    return null;
  }

  @JsonProperty
  public String getEnd() {
    if (end != null) { return end.toString(); }
    return null;
  }
}
