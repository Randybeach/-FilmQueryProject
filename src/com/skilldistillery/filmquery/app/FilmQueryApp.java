package com.skilldistillery.filmquery.app;

import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {

	DatabaseAccessor db = new DatabaseAccessorObject();

	public static void main(String[] args) {
		FilmQueryApp app = new FilmQueryApp();
		app.launch();
	}

	private void test() {
		Film film = db.findFilmById(1);
		System.out.println(film);
	}

	private void launch() {
		Scanner input = new Scanner(System.in);

		startUserInterface(input);

		input.close();
	}

	private void startUserInterface(Scanner input) {
		loop: do {
			System.out.println("1) Find Film by Id");
			System.out.println("2) Find Actor by Id");
			System.out.println("3) Find Actors by Film Id");
			System.out.println("4) Find Film by Keyword");
			System.out.println("5) QUIT");

			String choice = input.next();
			switch (choice) {
			case "1":
				System.out.println("what film Id?");
				String filmId = input.next();
				try {
					System.out.println(db.findFilmById(Integer.parseInt(filmId)));
				}catch(Exception e) {System.out.println("None Found");}
				input.nextLine();
				break;
			case "2":
				System.out.println("What actor Id?");
				String actorId = input.next();
				try {
					System.out.println(db.findActorById(Integer.parseInt(actorId)));
				}catch(Exception e) {System.out.println("Error");}
				input.nextLine();
				break;
			case "3":
				System.out.println("What film Id?");
				String actorFilmId = input.next();
				try {
					printActors(db.findActorsByFilmId(Integer.parseInt(actorFilmId)));
				}catch (Exception e) {System.out.println("Error");}
				input.nextLine();
				break;
			case "4":
				System.out.println("Enter in a keyword");
				String keyword = input.next();
				printFilms(db.findFilmByKeyword(keyword));
				input.nextLine();
				break;
			case "5":
				System.out.println("Exiting");
				break loop;
			default:
				System.out.println("Not an option\n");
				break;

			}
		} while (true);
	}

	private void printFilms(List<Film> films) {
		StringBuilder sb = new StringBuilder();
			for (Film film : films) {
				if(film.getTitle().equals("")) {
					System.out.println("None found");
					return;
				}else {
					sb.append(film);
					
				}
			}
			System.out.println(sb);
	}
	private void printActors(List<Actor> actors) {
		StringBuilder sb = new StringBuilder();
		for (Actor actor : actors) {
			if(actor.toString().equals("")) {
				System.out.println("None found");
				continue;
			}else {
				sb.append(actor + ", ");
				
			}
		}
		sb.deleteCharAt(sb.length()-2);
		System.out.println(sb);
	}

}
