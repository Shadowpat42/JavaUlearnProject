package org.example.model;

public class Country {
    private String name;
    private String subregion;
    private String region;
    private long internetUsers;
    private long population;

    public Country(String name, String subregion, String region, long internetUsers, long population) {
        this.name = name;
        this.subregion = subregion;
        this.region = region;
        this.internetUsers = internetUsers;
        this.population = population;
    }

    public double getInternetUserPercentage() {
        return (double) internetUsers / population * 100;
    }

    public String getName() {
        return name;
    }

    public String getSubregion() {
        return subregion;
    }

    public String getRegion() {
        return region;
    }

    public long getInternetUsers() {
        return internetUsers;
    }

    public long getPopulation() {
        return population;
    }
}
