package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
		Film film = null;
		String user = "student";
		String password = "student";
		String sql = "SELECT film.id, title, description, release_year, language_id, rental_duration, rental_rate, length, replacement_cost, rating, special_features, language.name FROM film JOIN language on film.language_id = language.id WHERE film.id = ?";
		try {
			Connection conn = DriverManager.getConnection(URL, user, password);
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, filmId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				film = new Film(rs.getInt("id"), rs.getString("title"), rs.getString("description"),
						rs.getInt("release_year"), rs.getInt("language_id"), rs.getInt("rental_duration"),
						rs.getDouble("rental_rate"), rs.getInt("length"), rs.getDouble("replacement_cost"),
						rs.getString("rating"), rs.getString("special_features"),
						rs.getString("language.name"),findActorsByFilmId(rs.getInt("id")) );
			}
			conn.close();
			if (film == null) {
				System.out.println("No film found");
			}
			return film;

		} catch (SQLException e) {
			System.out.println(e);
			return null;
		}

	}

	@Override
	public Actor findActorById(int actorId) {
		String user = "student";
		String password = "student";
		String sql = "select * from actor where actor.id = ?";

		try {
			Connection conn = DriverManager.getConnection(URL, user, password);
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, actorId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Actor actor = new Actor(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"));
				return actor;
			}
			conn.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
		return null;
	}

	@Override
	public List<Actor> findActorsByFilmId(int filmId) {
		List<Actor> actors = new ArrayList<>();
		String user = "student";
		String password = "student";
//	String sql = "select * from actor join film_actor on actor.id = film_actor.actor_id join film on film_actor.film_id = film.id where film.id = ? ";
		String sql = "SELECT * FROM actor JOIN film_actor on actor.id = film_actor.actor_id JOIN film on film_actor.film_id = film.id WHERE film.id = ? ";
		try {
			Connection conn = DriverManager.getConnection(URL, user, password);
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, filmId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Actor actor = new Actor(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"));
				actors.add(actor);
			}
			conn.close();
			if (actors.size() > 0) {
				return actors;
			}
			return null;
		} catch (SQLException e) {
			System.out.println(e);
			return null;
		}

	}

	@Override
	public List<Film> findFilmByKeyword(String keyword) {
		List<Film> films = new ArrayList<Film>();
		String user = "student";
		String password = "student";
//		String sql = "SELECT * FROM film WHERE film.title like ? or film.description like ?";
		String sql = "SELECT film.id, title, description, release_year, language_id, rental_duration, rental_rate, length, replacement_cost, rating, special_features, language.name FROM film JOIN language on film.language_id = language.id WHERE (film.title like ?) or (film.description like ?)";
//		String sql = "SELECT film.id, title, description, release_year, language_id, rental_duration, rental_rate, length, replacement_cost, rating, special_features, language.name, actor.first_name, actor.last_name FROM film JOIN film_actor on actor.id = film_actor.actor_id JOIN film on film_actor.film_id = film.id JOIN language on film.language_id = language.id WHERE film.title like ? or film.description like ?";
		try {
			Connection conn = DriverManager.getConnection(URL, user, password);
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, "%" + keyword + "%");
			ps.setString(2, "%" + keyword + "%");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
//				System.out.println(rs.getString("description"));
				Film film = new Film(rs.getInt("id"), rs.getString("title"), rs.getString("description"),
						rs.getInt("release_year"), rs.getInt("language_id"), rs.getInt("rental_duration"),
						rs.getDouble("rental_rate"), rs.getInt("length"), rs.getDouble("replacement_cost"),
						rs.getString("rating"), rs.getString("special_features"), rs.getString("language.name"), findActorsByFilmId(rs.getInt("id")) );
				
						films.add(film);
						

			}
			conn.close();
			if (films.size() > 0) {
				System.out.println("size is " + films.size());
				return films;
			} else {
				System.out.println("No films found");
			}

		} catch (SQLException e) {
			System.out.println(e);
			return films;
		}
		return null;

	}

}
