package Examen;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongoConexion
{
	private static final String DATABASE_NAME = "almacen";
	private static MongoClient mongoClient;
	private static MongoDatabase database;

	public static MongoDatabase getDatabase()
	{
		if (database == null)
		{
			mongoClient = MongoClients.create("mongodb://localhost:27017");
			database = mongoClient.getDatabase(DATABASE_NAME);
		}
		return database;
	}

	public static void closeConnection()
	{
		if (mongoClient != null)
		{
			mongoClient.close();
		}
	}
}