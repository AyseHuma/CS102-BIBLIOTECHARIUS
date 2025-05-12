package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class Geography extends Category {
    String countryCode; 

    // Public for access in FlagYesNo
    public static final Map<String, String> countryCodeMap = Map.ofEntries(
        Map.entry("Albania", "al"),
        Map.entry("Argentina", "ar"),
        Map.entry("Australia", "au"),
        Map.entry("Austria", "at"),
        Map.entry("Bangladesh", "bd"),
        Map.entry("Belgium", "be"),
        Map.entry("Brazil", "br"),
        Map.entry("Bulgaria", "bg"),
        Map.entry("Canada", "ca"),
        Map.entry("Chile", "cl"),
        Map.entry("China", "cn"),
        Map.entry("Colombia", "co"),
        Map.entry("Croatia", "hr"),
        Map.entry("Czechia", "cz"),
        Map.entry("Denmark", "dk"),
        Map.entry("Egypt", "eg"),
        Map.entry("Estonia", "ee"),
        Map.entry("Finland", "fi"),
        Map.entry("France", "fr"),
        Map.entry("Germany", "de"),
        Map.entry("Ghana", "gh"),
        Map.entry("Greece", "gr"),
        Map.entry("Hungary", "hu"),
        Map.entry("India", "in"),
        Map.entry("Indonesia", "id"),
        Map.entry("Iran", "ir"),
        Map.entry("Iraq", "iq"),
        Map.entry("Ireland", "ie"),
        Map.entry("Israel", "il"),
        Map.entry("Italy", "it"),
        Map.entry("Japan", "jp"),
        Map.entry("Kenya", "ke"),
        Map.entry("Malaysia", "my"),
        Map.entry("Mexico", "mx"),
        Map.entry("Morocco", "ma"),
        Map.entry("Netherlands", "nl"),
        Map.entry("New Zealand", "nz"),
        Map.entry("Nigeria", "ng"),
        Map.entry("Norway", "no"),
        Map.entry("Pakistan", "pk"),
        Map.entry("Peru", "pe"),
        Map.entry("Philippines", "ph"),
        Map.entry("Poland", "pl"),
        Map.entry("Portugal", "pt"),
        Map.entry("Romania", "ro"),
        Map.entry("Russia", "ru"),
        Map.entry("Saudi Arabia", "sa"),
        Map.entry("Serbia", "rs"),
        Map.entry("Singapore", "sg"),
        Map.entry("Slovakia", "sk"),
        Map.entry("Slovenia", "si"),
        Map.entry("South Africa", "za"),
        Map.entry("South Korea", "kr"),
        Map.entry("Spain", "es"),
        Map.entry("Sweden", "se"),
        Map.entry("Switzerland", "ch"),
        Map.entry("Thailand", "th"),
        Map.entry("Turkey", "tr"),
        Map.entry("Ukraine", "ua"),
        Map.entry("United Arab Emirates", "ae"),
        Map.entry("United Kingdom", "gb"),
        Map.entry("United States", "us"),
        Map.entry("Vietnam", "vn")

 );

    private String selectedCountry;

    public Geography() {

        possibleFalseGenres.add("English");
        possibleFalseGenres.add("Spanish");
        possibleFalseGenres.add("French");
        possibleFalseGenres.add("German");
        possibleFalseGenres.add("Italian");
        possibleFalseGenres.add("Portuguese");
        possibleFalseGenres.add("Russian");
        possibleFalseGenres.add("Japanese");
        possibleFalseGenres.add("Korean");
        possibleFalseGenres.add("Chinese");
        possibleFalseGenres.add("Mandarin");
        possibleFalseGenres.add("Cantonese");
        possibleFalseGenres.add("Hindi");
        possibleFalseGenres.add("Arabic");
        possibleFalseGenres.add("Turkish");
        possibleFalseGenres.add("Dutch");
        possibleFalseGenres.add("Swedish");
        possibleFalseGenres.add("Danish");
        possibleFalseGenres.add("Norwegian");
        possibleFalseGenres.add("Finnish");
        possibleFalseGenres.add("Greek");
        possibleFalseGenres.add("Polish");
        possibleFalseGenres.add("Czech");
        possibleFalseGenres.add("Hungarian");
        possibleFalseGenres.add("Thai");
        possibleFalseGenres.add("Hebrew");
        possibleFalseGenres.add("Persian");
        possibleFalseGenres.add("Ukrainian");
        possibleFalseGenres.add("Malay");
        possibleFalseGenres.add("Vietnamese");

        List<String> countries = new ArrayList<>(countryCodeMap.keySet());
        selectedCountry = countries.get((int)(Math.random() * countries.size()));
        countryCode = countryCodeMap.get(selectedCountry).toUpperCase(); 
        title = selectedCountry;   
        
        try
        (
        // create a database connection
            Connection conn = DriverManager.getConnection("jdbc:sqlite:lib/geographydata.db");
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM countries WHERE id = ? AND population IS NOT NULL AND language IS NOT NULL");
        )
        {      
            stmt.setString(1, countryCode); 
            stmt.setQueryTimeout(30);  // set timeout to 30 sec.

            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                rs = stmt.executeQuery();
                genresArr.add(rs.getString("language"));
                creators.add(selectedCountry);
                releaseDate = rs.getInt("population");
            }
            possibleFalseCreators = getThreeRandomNames();
        }
        catch(SQLException e)
        {
        // if the error message is "out of memory",
        // it probably means no database file is found
        e.printStackTrace(System.err);
        }       
    }

    public String getSelectedCountry() {
        return selectedCountry;
    }

    public  String getFlagImagePath() {
        String code = countryCodeMap.get(selectedCountry);
        return "/flags/" + code + ".png"; // resource path
    }

    @Override
    public ArrayList<String> getThreeRandomNames() {
        ArrayList<String> all = new ArrayList<>(countryCodeMap.keySet());
        all.remove(selectedCountry);
        Collections.shuffle(all);
        return new ArrayList<>(all.subList(0, 3));
    }
}
