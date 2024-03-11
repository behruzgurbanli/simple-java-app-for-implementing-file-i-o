public class Country {
    private String id;
    private String countryName;
    private String continent;
    private Double population;
    private Double IMF_GDP;
    private Double UN_GDP;
    private Double IMF_GDP_per_capita;
    private Double UN_GDP_per_capita;

    public Country(String id, String countryName, String continent, Double population, Double IMF_GDP,
                   Double UN_GDP, Double IMF_GDP_per_capita, Double UN_GDP_per_capita) {
        this.id = id;
        this.countryName = countryName;
        this.continent = continent;
        this.population = population;
        this.IMF_GDP = IMF_GDP;
        this.UN_GDP = UN_GDP;
        this.IMF_GDP_per_capita = IMF_GDP_per_capita;
        this.UN_GDP_per_capita = UN_GDP_per_capita;
    }

    public Country(String id, String countryName, String continent, Double population, Double IMF_GDP, Double UN_GDP, Double IMF_GDP_per_capita) {
        this.id = id;
        this.countryName = countryName;
        this.continent = continent;
        this.population = population;
        this.IMF_GDP = IMF_GDP;
        this.UN_GDP = UN_GDP;
        this.IMF_GDP_per_capita = IMF_GDP_per_capita;
        this.UN_GDP_per_capita = UN_GDP / population;
    }

    public String getId() {
        return this.id;
    }

    public String getCountryName() {
        return this.countryName;
    }

    public String getContinent() {
        return this.continent;
    }

    public Double getPopulation() {
        return this.population;
    }

    public Double getIMF_GDP() {
        return this.IMF_GDP;
    }

    public Double getUN_GDP() {
        return this.UN_GDP;
    }

    public Double getIMF_GDP_per_capita() {
        return this.IMF_GDP_per_capita;
    }

    public Double getUN_GDP_per_capita() {
        return this.UN_GDP_per_capita;
    }

    public static Country parseFrom(String countryRecord) throws CannotParsedException {
        String[] countryData = countryRecord.split(",");
        try {
            String id = countryData[1];
            String countryName = countryData[2];
            String continent = countryData[3];
            Double population = Double.parseDouble(countryData[4]);
            Double IMF_GDP = Double.parseDouble(countryData[5]);
            Double UN_GDP = Double.parseDouble(countryData[6]);
            Double IMF_GDP_per_capita = Double.parseDouble(countryData[7]);
            Double UN_GDP_per_capita = Double.parseDouble(countryData[8]);
            return new Country(id, countryName, continent, population, IMF_GDP,
                                UN_GDP, IMF_GDP_per_capita, UN_GDP_per_capita);
        } catch (Exception e) {
            throw new CannotParsedException("The given country record cannot be parsed into a country." + e);
        }
    }

    public String parseTo() {
        return String.format("%s,%s,%s,%f,%e,%e,%e,%e",
                this.id, this.countryName, this.continent, this.population,
                this.IMF_GDP, this.UN_GDP, this.IMF_GDP_per_capita, this.UN_GDP_per_capita);
    }

    public static String parseTo(Country countryInstance) {
        return countryInstance.parseTo();
    }
}
