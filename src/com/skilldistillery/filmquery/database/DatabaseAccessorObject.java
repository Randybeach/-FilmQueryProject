package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {
	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false";
	
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

  @Override
  public Film findFilmById(int filmId) {
	  String user = "student";
	  String password = "student";
	  String sql = "SELECT id, title, description, release_year, language_id, rental_duration,"
	  		+ " rental_rate, length, replacement_cost, rating, special_features "
	  		+  " FROM film  "
	  		+ " WHERE film.id = ?";;
	  try {
		  Connection conn = DriverManager.getConnection(URL, user, password);
		  PreparedStatement  ps = conn.prepareStatement(sql);
		  ps.setInt(1, filmId);
		  ResultSet rs = ps.executeQuery();
		  if(rs.next()) {
			  Film film = new Film(rs.getInt("id"),
					  rs.getString("title"),
					  rs.getString("description"),
					  rs.getInt("release_year"),
					  rs.getInt("language_id"),
					  rs.getInt("rental_duration"),
					  rs.getDouble("rental_rate"),
					  rs.getInt("length"),
					  rs.getDouble("replacement_cost"),
					  rs.getString("rating"),
					  rs.getString("special_features"));
			  return film;
		  }
		  conn.close();
		  
	  }catch(SQLException e) {
		  System.out.println(e);
		  return null;
	  }
	  
	  return null;
    
  }

@Override
public Actor findActorById(int actorId) {
	String user = "student";
	String password = "student";
	String sql = "";
	
	try {
		Connection conn = DriverManager.getConnection(URL,user,password);
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, actorId);
		ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			Actor actor = new Actor();
		}
	}catch(SQLException e) {
		System.out.println(e);
	}
	return null;
}

@Override
public List<Actor> findActorsByFilmId(int filmId) {
	// TODO Auto-generated method stub
	return null;
}

}
