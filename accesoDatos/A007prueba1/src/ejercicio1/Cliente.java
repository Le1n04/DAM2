package ejercicio1;

import java.io.Serializable;

public class Cliente implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7453479140479769806L;
	private String name, email, city;
	private int age;
	
	public Cliente(String name, int age, String email, String city)
	{
		this.name = name;
		this.age = age;
		this.city = city;
		this.email = email;
	}
	
	public String getName() {
		return name;
	}
	public String getEmail() {
		return email;
	}
	public String getCity() {
		return city;
	}
	public int getAge() {
		return age;
	}

	@Override
	public String toString() {
		return "Cliente [name=" + name + ", email=" + email + ", city=" + city + ", age=" + age + "]";
	}
}
