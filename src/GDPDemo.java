import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class GDPDemo {
    public static void main(String[] args) {

        List<Country> listOfCountries = new ArrayList<>();

        /* Program starts executing */
        // Loading all the countries from countries.csv file
        System.out.println("Loading all the countries from countries.csv file...");
        try {
            listOfCountries = FileManager.loadCountries();
            System.out.println("Loading countries done successfully.");
        } catch (FileNotFoundException e) {
            System.err.println("Source file (countries.csv) not found.");
        } catch (CannotParsedException e) {
            System.err.println(e.toString());
        }

        System.out.println();


        // Sorting
        System.out.println("Sorting:");
        // Call the sort method to sort all countries based on the continent (first) and country names
        System.out.println("Sorting based on continent first...");
        try {
            sort(listOfCountries, "continent", "asc");
        } catch (IllegalArgumentException e) {
            System.err.println(e.toString());
        }
        System.out.println("Sorting based on country names...");
        try {
            sort(listOfCountries, "countryName", "asc");
        } catch (IllegalArgumentException e) {
            System.err.println(e.toString());
        }
        System.out.println("Saving the results in ContinentAndCountryAsc.csv file...");
        try {
            FileManager.saveCountries(listOfCountries, "ContinentAndCountryAsc.csv");
        } catch (IllegalArgumentException e) {
            System.err.println(e.toString());
        }

        System.out.println();

        // Call the sort method again to sort all countries based on the descending order of population
        System.out.println("Sorting based on descending order of population...");
        try {
            sort(listOfCountries, "population", "desc");
        } catch (IllegalArgumentException e) {
            System.err.println(e.toString());
        }
        System.out.println("Saving the results in PopulationDesc.csv file...");
        try {
            FileManager.saveCountries(listOfCountries, "PopulationDesc.csv");
        } catch (IllegalArgumentException e) {
            System.err.println(e.toString());
        }

        System.out.println();

        // Call the filterByContinent method to find the info about countries in Oceania
        System.out.println("Filtering based on Continent -> Oceania");
        List<Country> filterByOceania = filterByContinent(listOfCountries, "Oceania");
        System.out.println("Saving the results in filterByOceania.csv file...");
        try {
            FileManager.saveCountries(filterByOceania, "filterByOceania.csv");
        } catch (IllegalArgumentException e) {
            System.err.println(e.toString());
        }

        // Call the filterByPerCapita method to find the info about countries whose per capita gdp is in the range of [40000,50000]
        System.out.println("Filtering based on UN_GDP_per_capita");
        List<Country> filterByPerCapita = filterByPerCapita(listOfCountries, 40000.0, 50000.0);
        System.out.println("Saving the results in filterByPerCapita.csv file...");
        try {
            FileManager.saveCountries(filterByPerCapita, "filterByPerCapita.csv");
        } catch (IllegalArgumentException e) {
            System.err.println(e.toString());
        }

        /* Program starts executing */
        System.out.println("\n---\nProgram run successfully!\n---");
    }

    public static void sort(List<Country> countries, String fieldName, String order) {
        Comparator<Country> comparator = getComparator(fieldName, order);
        Collections.sort(countries, comparator);
    }

    private static Comparator<Country> getComparator(String fieldName, String order) throws IllegalArgumentException {
        Comparator<Country> comparator = switch (fieldName) {
            case "id" -> Comparator.comparing(Country::getId);
            case "countryName" -> Comparator.comparing(Country::getCountryName);
            case "continent" -> Comparator.comparing(Country::getContinent);
            case "population" -> Comparator.comparing(Country::getPopulation);
            case "IMF_GDP" -> Comparator.comparing(Country::getIMF_GDP);
            case "UN_GDP" -> Comparator.comparing(Country::getUN_GDP);
            case "IMF_GDP_per_capita" -> Comparator.comparing(Country::getIMF_GDP_per_capita);
            case "UN_GDP_per_capita" -> Comparator.comparing(Country::getUN_GDP_per_capita);
            default -> throw new IllegalArgumentException("Invalid field name: " + fieldName);
        };
        if (order.equals("desc")) {
            comparator = comparator.reversed();
        } else if (!order.equals("asc")) {
            throw new IllegalArgumentException("Provided invalid order: " + order);
        }
        return comparator;
    }

    public static List<Country> filterByContinent(List<Country> countries, String continent) {
        return countries.stream().filter(country -> country.getContinent().equals(continent))
                .toList();
    }

    public static List<Country> filterByPerCapita(List<Country> countries, Double lower, Double upper) {
        return countries.stream().filter(country -> country.getUN_GDP_per_capita() >= lower &&
                                            country.getUN_GDP_per_capita() <= upper).toList();
    }
}