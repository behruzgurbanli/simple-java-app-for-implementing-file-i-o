import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileManager {
    private static final List<Country> COUNTRIES = new ArrayList<>();

    public static List<Country> loadCountries() throws FileNotFoundException, CannotParsedException {
        String pathName = "data/countries.csv";
        File file = new File(pathName);
        Scanner scanner = new Scanner(file);
        scanner.nextLine();
        while (scanner.hasNextLine()) {
            String record = scanner.nextLine();
            COUNTRIES.add(Country.parseFrom(record));
        }
        return COUNTRIES;
    }
    public static void saveCountries(List<Country> countries, String fileName)
            throws IllegalArgumentException {
        File file = new File("data/" + fileName);
        if (file.exists())
            throw new IllegalArgumentException("The given path for this file already exists!");
        try (PrintWriter printWriter = new PrintWriter(file)) {
            printWriter.println("ID,Country,Continent,Population,IMF_GDP,UN_GDP,IMF_GDP_per_capita,UN_GDP_per_capita");
            countries.stream().map(country -> country.parseTo()).forEach(printWriter::println);
        } catch (FileNotFoundException e) {
            System.err.println("File fails to get created: " + e.toString());
        }
    }
}
