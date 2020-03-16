package com.coronavirustracker.CoronavirusTracker.Service;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.coronavirustracker.CoronavirusTracker.Model.ConfirmedStats;
import com.coronavirustracker.CoronavirusTracker.Model.DeathStats;

@Service
public class CoronaVirusDataService {

	LocalDate lastUpdateConfirmation = LocalDate.now();
	LocalDate lastUpdateDeaths = LocalDate.now();

	private List<ConfirmedStats> allStats = new ArrayList<ConfirmedStats>();
	private List<DeathStats> allDeathStats = new ArrayList<DeathStats>();

	private static String VIRUS_DATA_URL_CONFIRMED = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Confirmed.csv";
	private static String VIRUS_DATA_URL_DEATHS = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Deaths.csv";

	@PostConstruct
	@Scheduled(cron = "* * 1 * * *")
	public void fetchVirusConfirmedData() throws IOException, InterruptedException {
		List<ConfirmedStats> confirmedStats = new ArrayList<ConfirmedStats>();
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(VIRUS_DATA_URL_CONFIRMED)).build();
		HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

		StringReader csvBodyReader = new StringReader(httpResponse.body());
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);

		for (CSVRecord record : records) {
			try {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/dd/YY");
				record.get(formatter.format(lastUpdateConfirmation).toString());
				break;
			} catch (Exception e) {
				lastUpdateConfirmation = lastUpdateConfirmation.minusDays(1);
			}
		}
		for (CSVRecord record : records) {

			ConfirmedStats stats = new ConfirmedStats();
			stats.setState(record.get("Province/State"));
			stats.setCountry(record.get("Country/Region"));
			stats.setTotalCases(Integer.parseInt(record.get(record.size() - 1)));
			stats.setIncreaseFromPreviousDay((Integer.parseInt(record.get(record.size() - 1)))
					- (Integer.parseInt(record.get(record.size() - 2))));
			confirmedStats.add(stats);

		}
		this.allStats = confirmedStats;
	}

	@PostConstruct
	@Scheduled(cron = "* * 1 * * *")
	public void fetchVirusDeathData() throws IOException, InterruptedException {
		List<DeathStats> deathStats = new ArrayList<DeathStats>();
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(VIRUS_DATA_URL_DEATHS)).build();
		HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

		StringReader csvBodyReader = new StringReader(httpResponse.body());
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);

		
		for (CSVRecord record : records) {
			try {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/dd/YY");
				record.get(formatter.format(lastUpdateDeaths).toString());
				break;
			} catch (Exception e) {
				lastUpdateDeaths = lastUpdateDeaths.minusDays(1);
			}
		}
		for (CSVRecord record : records) {
			DeathStats stats = new DeathStats();
			stats.setState(record.get("Province/State"));
			stats.setCountry(record.get("Country/Region"));
			stats.setTotalDeaths(Integer.parseInt(record.get(record.size() - 1)));
			stats.setIncreaseFromPreviousDay((Integer.parseInt(record.get(record.size() - 1)))
					- (Integer.parseInt(record.get(record.size() - 2))));
			deathStats.add(stats);

		}
		this.allDeathStats = deathStats;
	}

	public LocalDate getLastUpdateDeaths() {
		return lastUpdateDeaths;
	}

	public void setLastUpdateDeaths(LocalDate lastUpdateDeaths) {
		this.lastUpdateDeaths = lastUpdateDeaths;
	}

	public List<ConfirmedStats> getAllStats() {
		return allStats;
	}

	public List<DeathStats> getAllDeathStats() {
		return allDeathStats;
	}

	public LocalDate getLastUpdateConfirmation() {
		return lastUpdateConfirmation;
	}

	public void setLastUpdateConfirmation(LocalDate lastUpdateConfirmation) {
		this.lastUpdateConfirmation = lastUpdateConfirmation;
	}

}
