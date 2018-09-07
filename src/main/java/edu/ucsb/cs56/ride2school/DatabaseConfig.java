package edu.ucsb.cs56.ride2school;

public class DatabaseConfig {
	ArrayList<String> initDatabase() {
        ArrayList<String> dbQuery = new ArrayList<>();
        try {
            dbQuery = Database.createDocument();
        } catch (UnknownHostException e) {
            System.out.println("Unknown Host thrown");
        }
        return dbQuery;
    }

}
