package com.skilldistillery.filmquery.app;

import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {
  
  DatabaseAccessor db = new DatabaseAccessorObject();

  public static void main(String[] args) {
    FilmQueryApp app = new FilmQueryApp();
//    app.test();
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
	  loop:
	  do {
	  System.out.println("1) Find Film by Id");
	  System.out.println("2) Find Actor by Id");
	  System.out.println("3) Find Actor by Film Id");
	  System.out.println("4) QUIT");
    
	  int choice = input.nextInt();
	  switch(choice) {
	  case 1:
		  System.out.println("what film id?");
		  int filmId = input.nextInt();
		  System.out.println(db.findFilmById(filmId));
		  break;
	  case 2:
		  System.out.println("What actor id?");
		  int actorId = input.nextInt();
		  System.out.println(db.findActorById(actorId));
		  break;
	  case 3:
		  System.out.println("What film Id?");
		  int actorFilmId = input.nextInt();
		  System.out.println(db.findActorsByFilmId(actorFilmId));
		  break;
	  case 4:
		  System.out.println("Exiting");
		  break loop;
	 default:
			  
		  
	  }
	  }while(true);
  }

}
