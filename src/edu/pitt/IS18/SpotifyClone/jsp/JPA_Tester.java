package edu.pitt.IS18.SpotifyClone.jsp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import edu.pitt.SpotifyClone.core.Album;
import edu.pitt.SpotifyClone.core.Artist;
import edu.pitt.SpotifyClone.core.Song;
import edu.pitt.SpotifyClone.utils.JpaUtilities;

public class JPA_Tester {

	
	public static void main(String[] args) {
		
		JpaUtilities JPA = new JpaUtilities();
		
		Song song = new Song("Assignment2PartIII", 99, "2014-02-14", "2000-02-14");
		Album album = new Album("Assignment2 Album", "2014-02-12","pitt.edu");
		Artist artist = new Artist("David","Hinton","N/A");
		
		JPA.songCreate(song);
		System.out.println(song+"\n");
		JPA.songUpdate(song);
		System.out.println(song+"\n");
		JPA.songDelete(song);
		
		JPA.albumCreate(album);
		JPA.albumUpdate(album);
		JPA.albumDelete(album);
		
		JPA.artistCreate(artist);
		JPA.artistUpdate(artist);
		JPA.artistDelete(artist);

		JPA.closeJPA();
	
	}

}
/*
 * using View with JPA:
 * 
 * @Entity 
 * @Table (name = "song_artist_list") <--- calling the view table
 * @Id
 * @GeneratedValue(strategy = GenerationType.AUTO)
 * @Column (name = "song_id")
 * private String songID;
 * @column (name = "title")
 * private String title;
 * 
 * continued... for all properties of the view entity/table
 * 
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * 
 * 
 * Auto commit: sql example on/off   --- server shutdown
 * 
 * set autocommit =0;
 * 
 * Statements
 * 
 * commit;
 * 
 */
