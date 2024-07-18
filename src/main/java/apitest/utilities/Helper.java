package apitest.utilities;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.DataProvider;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;


public class Helper {
	String  path;
	private static Helper instance = null;
	
	private Helper() {

		
	}

	public static Helper getInstance() {
		
		if (instance == null) {
			instance = new Helper();
		}
		return instance;
	}
	
	
	public String loadProperties(String property) {
		Properties prop = new Properties();
		InputStream input;
		set_path("src/test/resources/Constants.properties");
		try {
			input = new FileInputStream(path);
			
			// load a properties file
			
			prop.load(input);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return prop.getProperty(property);
	}

	@DataProvider
	public static String readDataFromJson(String jsonName) throws IOException {
		//json to String
		return FileUtils.readFileToString(new File(System.getProperty("user.dir")+"\\src\\test\\java\\testData\\"+jsonName),StandardCharsets.UTF_8);
		//String to hashmap
		/*
		 * ObjectMapper mapper = new ObjectMapper(); return mapper.readValue(jsonData,
		 * new TypeReference<List<HashMap<String,String>>>(){});
		 */
	}

	public void set_path(String path2) {
		path = path2;
		
	}

	public static List<String[]> ReadCSV(String file) throws Exception {
		FileReader filereader = new FileReader(file);
		CSVReader csvReader = new CSVReaderBuilder(filereader).withSkipLines(1).build();
		List<String[]> allData = csvReader.readAll();

		return allData;
	}
	
	
	
	public static String getRandomValue() {
		return RandomStringUtils.randomAlphanumeric(8).toUpperCase();
	}

	public static long getRandomNumber() {
		return ThreadLocalRandom.current().nextLong(9999999999l);
	}

	
	public static String getOneDigitNumber() {
		Random rand = new Random();
		return Integer.toString(rand.nextInt(24)+1);
	}
	
	public static String currentDataTime() {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		return dateTimeFormatter.format(now);
	}
	
	
	public static String futureTestDateTime() {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		LocalDateTime futureDateTime = LocalDateTime.of(2100, 01, 01, 01, 10, 10);
		return dateTimeFormatter.format(futureDateTime);
	}
	
	

}