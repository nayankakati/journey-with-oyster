package com.oyster.domain;

import java.util.Set;

import com.oyster.enums.Direction;
import com.oyster.enums.JourneyType;

/**
 * Created by nayan.kakati on 4/19/18.
 */
public class Barrier {

	private Set<Integer> zones;
	private String name;
	private Direction direction;
	private JourneyType journeyType;

	public Barrier(Set<Integer> zones, String name, Direction direction, JourneyType journeyType) {
		this.zones = zones;
		this.name = name;
		this.direction = direction;
		this.journeyType = journeyType;
	}

	public Barrier() {
	}

	public Set<Integer> getZones() {
		return zones;
	}

	public void setZones(Set<Integer> zones) {
		this.zones = zones;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public JourneyType getJourneyType() {
		return journeyType;
	}

	public void setJourneyType(JourneyType journeyType) {
		this.journeyType = journeyType;
	}
}
