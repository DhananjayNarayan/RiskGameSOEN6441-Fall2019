package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * This is MapConquest class which extends MapGeo
 * 
 * @author f_yazdan
 * @author s_shehna
 * @author AdinAshby
 */
public class MapConquest extends MapGeo {
	/**
	 * This method is for reading the conquest map
	 * 
	 * @param mapFileName
	 */
	public boolean readConquest(String mapFileName) {

		File file = new File(mapFolder + "/" + mapFileName + ".map");
		if (!file.exists()) {
			System.out.println(mapFileName + " conquest map file not found. Please try again");
			return false;
		} else {
			System.out.println("Read Conquest File: " + mapFolder + "/" + mapFileName + ".map");
		}

		continentList = new HashMap<Integer, Continent>();
		setCountryAdjacency(new AdjacencyList());
		Continent.setContinentsCounter(0);

		BufferedReader bufferedReader = null;
		try {

			bufferedReader = new BufferedReader(new FileReader(file));

			StringBuffer stringBuffer = new StringBuffer();
			String fileContent = "";
			String line = null;

			while ((line = bufferedReader.readLine()) != null) {
				stringBuffer.append(line).append("\n");
				fileContent += line + "\n";
			}
			bufferedReader.close();
			String patternString = "(?<=\\[Continents\\]\\s)(([\\w\\_\\- ]*)=(\\d)+\\s)*";
			Pattern pattern = Pattern.compile(patternString);
			Matcher matcher = pattern.matcher(fileContent);
			String continentLines = "";
			if (matcher.find()) {
				continentLines = matcher.group(0);
			}

			// Start getting continents
			System.out.println("---------Loading Continent-------------");
			patternString = "([\\w\\_\\- ]*)=(\\d)+\\s";
			pattern = Pattern.compile(patternString);
			matcher = pattern.matcher(continentLines);
			int count = 0;
			String continentDetail = "";
			String continentName = "";
			String continentValue = "";

			while (matcher.find()) {
				count++;
				continentDetail = matcher.group();
				continentName = matcher.group(1);
				continentValue = matcher.group(2);

				Continent continent = new Continent(continentName, Integer.parseInt(continentValue));
				addContinent(continent);
				System.out.println("Found Continent " + continent.getContinentId() + " " + continentName + "  Value="
						+ continentValue);
			}

			// Start getting countries
			System.out.println("\n---------Loading Country-------------");
			patternString = "(?<=\\[Territories\\]\\s)([\\w\\d\\,\\.\\s]*)";
			pattern = Pattern.compile(patternString);
			matcher = pattern.matcher(fileContent);
			String countryLines = "";
			int CountryId = 0;
			if (matcher.find()) {
				countryLines = matcher.group(0);
			}

			String[] countriesOfContinent = countryLines.split("\n\n");
			for (int cc = 0; cc < countriesOfContinent.length; cc++) {
				String[] countryLine = countriesOfContinent[cc].split("\n");
				for (int cl = 0; cl < countryLine.length; cl++) {

					String[] currentLine = countryLine[cl].split(",");

					String countryName = currentLine[0];
					int countryId = ++CountryId;
					Country country = new Country(countryName, CountryId);
					Continent continent = getContinent(currentLine[3]);
					countryAdjacency.addVertex(countryId);
					System.out.println(CountryId + "- Adding " + countryName + " to " + continent.getContinentName());

					continent.addCountry(country);

				}

			}

			for (int cc = 0; cc < countriesOfContinent.length; cc++) {
				String[] countryLine = countriesOfContinent[cc].split("\n");
				for (int cl = 0; cl < countryLine.length; cl++) {
					String[] currentLine = countryLine[cl].split(",");
					String countryName = currentLine[0];
					Country country = getCountryByName(countryName);
					for (int i = 4; i < currentLine.length; i++) {
//						System.out.println("Connect " + currentLine[i] + " to " + countryName);
						countryAdjacency.addEdge(country.getCountryId(), getCountryIdByName(currentLine[i]));
					}

				}

			}

			System.out.println("-------------------------------");

		} catch (FileNotFoundException e) {
			System.out.println("File Not Found");
		} catch (Exception e) {
			System.out.println("Map is invalid");
		}

		return true; // validateMap();
	}

	/**
	 * This method is for write the conquest map
	 */
	/**
	 * This method format the map
	 * 
	 * @param fileName
	 * @return
	 */
	public String mapFormat(String fileName) {
		String mapCountries = "";
		String mapContent = "[Map]\r\n" + "author=System\r\n" + "warn=yes\r\n" + "image=\r\n" + "wrap=no\r\n"
				+ "scroll=none\r\n" + "\r\n" + "[Continents]" + "\r\n";
		Iterator<Entry<Integer, Continent>> it = continentList.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Integer, Continent> continentMap = (Map.Entry<Integer, Continent>) it.next();
			int continentId = (int) continentMap.getKey();
			Continent c = continentList.get(continentId);

			mapContent += c.getContinentName() + "=" + c.getContinentControlValue() + "\r\n";
			List<Country> countryList = c.getCountriesList();
			for (Country co : countryList) {
				String AdjCountries = "";
				for (int AdjCountry : this.getCountryAdjacency(co.getCountryId())) {
					AdjCountries += getCountryById(AdjCountry).getCountryName() + ",";
				}
				AdjCountries = AdjCountries.substring(0, AdjCountries.length() - 1);
				mapCountries += co.getCountryName() + ",0,0," + c.getContinentName() + "," + AdjCountries + "\r\n";
			}
		}
		mapContent += "\r\n[Territories]\r\n" + mapCountries;

		return mapContent;
	}

	/**
	 * This method write the map with a string file name
	 * 
	 * @param fileName
	 * @throws Exception
	 */
	public boolean writeConquest(String fileName) throws Exception {

		if (validateMap()) {
			File file = new File(mapFolder + "/" + fileName + ".map");

			BufferedWriter br = new BufferedWriter(new FileWriter(file));

			br.write(mapFormat(fileName));

			br.close();
		} else {
			System.out.println("Map is not valid, we can not save it");
		}
		return true;
	}

	/** 
	 * This is  constructor for initializing MapConquest
	 * 
	 * @param mapDomination
	 */
	public MapConquest(MapDomination mapDomination) {
		this.continentList = mapDomination.continentList;
		this.theMapView = mapDomination.theMapView;
		this.countryAdjacency = mapDomination.countryAdjacency;
		this.players = mapDomination.players;
		this.random = mapDomination.random;

	}
}
