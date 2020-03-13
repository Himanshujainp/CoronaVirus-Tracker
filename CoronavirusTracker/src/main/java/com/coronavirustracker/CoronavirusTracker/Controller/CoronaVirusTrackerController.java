package com.coronavirustracker.CoronavirusTracker.Controller;

import java.text.DecimalFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.coronavirustracker.CoronavirusTracker.Model.DeathStats;
import com.coronavirustracker.CoronavirusTracker.Model.ConfirmedStats;
import com.coronavirustracker.CoronavirusTracker.Service.CoronaVirusDataService;

@Controller
public class CoronaVirusTrackerController {

	@Autowired
	CoronaVirusDataService dataService;

	@GetMapping("/confirmedCases")
	public String confirmedCases(Model model) {
		List<ConfirmedStats> allStats = dataService.getAllStats();
		int total = allStats.stream().mapToInt(stat -> stat.getTotalCases()).sum();
		model.addAttribute("title", "Coronavirus Tracker");
		model.addAttribute("records", allStats);
		model.addAttribute("total", total);
		return "confirmed_cases";
	}

	@GetMapping("/")
	public String home(Model model) {
		 DecimalFormat df2 = new DecimalFormat("#.##");
		model.addAttribute("title", "Coronavirus Tracker");
		double deaths = dataService.getAllDeathStats().stream().mapToInt(stat -> stat.getTotalDeaths()).sum();
		double cases = dataService.getAllStats().stream().mapToInt(stat -> stat.getTotalCases()).sum();
		double mortalityRate = (deaths / cases) * 100;
		model.addAttribute("mortalityRate", df2.format(mortalityRate));
		return "home";
	}

	@GetMapping("/deathCases")
	public String deathCases(Model model) {
		List<DeathStats> allDeathStats = dataService.getAllDeathStats();
		int totalDeaths = allDeathStats.stream().mapToInt(stat -> stat.getTotalDeaths()).sum();
		model.addAttribute("title", "Coronavirus Tracker");
		model.addAttribute("records", allDeathStats);
		model.addAttribute("totalDeaths", totalDeaths);
		return "death_cases";
	}
}
