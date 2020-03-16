package com.coronavirustracker.CoronavirusTracker.Model;

public class ConfirmedStats {

	private String state;
	private String country;
	private int totalCases;
	private int increaseFromPreviousDay;

	public int getIncreaseFromPreviousDay() {
		return increaseFromPreviousDay;
	}

	public void setIncreaseFromPreviousDay(int previousDayCases) {
		this.increaseFromPreviousDay = previousDayCases;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getTotalCases() {
		return totalCases;
	}

	public void setTotalCases(int totalCases) {
		this.totalCases = totalCases;
	}

	@Override
	public String toString() {
		return "ConfirmedStats [state=" + state + ", country=" + country + ", totalCases=" + totalCases
				+ ", previousDayCases=" + increaseFromPreviousDay + "]";
	}

	

}
