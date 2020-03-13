package com.coronavirustracker.CoronavirusTracker.Model;

public class DeathStats {

	private String state;
	private String country;
	private int totalDeaths;

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

	public int getTotalDeaths() {
		return totalDeaths;
	}

	public void setTotalDeaths(int totalDeaths) {
		this.totalDeaths = totalDeaths;
	}

	@Override
	public String toString() {
		return "DeathStats [state=" + state + ", country=" + country + ", totalDeaths=" + totalDeaths + "]";
	}

}
