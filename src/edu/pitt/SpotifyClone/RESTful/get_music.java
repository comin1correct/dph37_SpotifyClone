package edu.pitt.SpotifyClone.RESTful;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.pitt.SpotifyClone.utils.DbUtilities;
import edu.pitt.SpotifyClone.utils.ErrorLogger;

/**
 * Servlet implementation class get_music
 */
@WebServlet("/api/get_music")
public class get_music extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public get_music() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		//Explicitly telling the Servlet to respond with a JSON object
		response.setContentType("application/json");
		//enabling sessions:
		HttpSession session = request.getSession(true);
		
		String searchTerm;
		String sql = "";
		//creating JSON object to store three resultsets
		JSONObject searchResults = new JSONObject();
		final int RESULTS_LIMIT = 100;
		
		//data container
		session.setAttribute("SEARCH_RESULTS", "");
		
		/*
		 * check to see if the GET is empty
		 * */
		if(request.getParameter("searchTerm") != null) 
		{
			
			searchTerm = request.getParameter("searchTerm");
			if(!searchTerm.equals("")) 
			{
				try
				{
					
					
				/*============================================
				 * 				Search Song
				 *============================================
				 */
					
					sql = "SELECT s.song_id, s.title, s.length, s.release_date, a.number_of_tracks, a.title as 'album_title'," + 
							"    ar.band_name, ar.first_name, ar.last_name" + 
							" FROM" + 
							"    song s" + 
							"        JOIN" + 
							"    album_song als ON s.song_id = als.fk_song_id" + 
							"        JOIN" + 
							"    album a ON als.fk_album_id = a.album_id" + 
							"        JOIN" + 
							"    song_artist sa ON s.song_id = sa.fk_song_id" + 
							"        JOIN" + 
							"    artist ar ON sa.fk_artist_id = ar.artist_id" + 
							" WHERE" + 
							"    s.title LIKE '%bob%'" + 
							"        OR a.title LIKE '%" + searchTerm + "%'" + 
							"        OR ar.band_name LIKE '% "+ searchTerm +"%'" + 
							"        OR ar.first_name LIKE '%"+ searchTerm +"%'" + 
							"        OR ar.last_name LIKE '%"+ searchTerm +"%';";
					
					
					JSONArray songArtistAlbum = new JSONArray();
					DbUtilities db = new DbUtilities();
					ResultSet rs = db.getResultSet(sql);
					while(rs.next()){
						JSONObject music = new JSONObject();
						music.put("song_id", rs.getString("song_id"));
						music.put("title", rs.getString("title"));
						music.put("length", rs.getDouble("length"));
						music.put("release_date", rs.getString("release_date"));
						music.put("number_of_tracts", rs.getInt("number_of_tracks"));
						music.put("album_title", rs.getString("album_title"));
						music.put("band_name", rs.getString("band_name"));
						music.put("first_name", rs.getString("first_name"));
						music.put("last_name", rs.getString("last_name"));
						songArtistAlbum.put(music);
						}
					//Store music in searchResults JSON object
					searchResults.put("music",songArtistAlbum);
					
				/*============================================
				 * 				Search Song
				 *============================================
				 */
					sql = "SELECT * FROM song WHERE title LIKE '%" + searchTerm + "%' LIMIT " + RESULTS_LIMIT + ";";
					JSONArray songList = new JSONArray();

					rs = db.getResultSet(sql);
					while(rs.next()){
						JSONObject song = new JSONObject();
						song.put("song_id", rs.getString("song_id"));
						song.put("title", rs.getString("title"));
						song.put("release_date", rs.getString("release_date"));
						song.put("record_date", rs.getString("record_date"));
						song.put("length", rs.getDouble("length"));
						songList.put(song);
					}

					// Store song list in searchResults JSON object
					searchResults.put("songs", songList);

					
				/*============================================
				 * 				Search Albums
				 *============================================
				 */
					sql = "SELECT * FROM album WHERE title LIKE '%" + searchTerm + "%' LIMIT " + RESULTS_LIMIT + ";";
					JSONArray albumList = new JSONArray();

					rs = db.getResultSet(sql);
					while(rs.next()){
						JSONObject album = new JSONObject();
						album.put("album_id", rs.getString("album_id"));
						album.put("title", rs.getString("title"));
						album.put("release_date", rs.getString("release_date"));
						album.put("cover_image_path", rs.getString("cover_image_path"));
						album.put("recording_company_name", rs.getString("recording_company_name"));
						album.put("number_of_tracks", rs.getInt("number_of_tracks"));
						album.put("PMRC_rating", rs.getString("PMRC_rating"));
						album.put("length", rs.getDouble("length"));
						albumList.put(album);
					}

					// Store album list in searchResults JSON object
					searchResults.put("albums", albumList);
					
					
					
				/*============================================
				 * 				Search Artist
				 *============================================
				 */

					sql = "SELECT * FROM artist "
							+ "WHERE first_name LIKE '%" + searchTerm + "%' "
							+ "OR last_name LIKE '%" + searchTerm + "%' "
							+ "OR band_name LIKE '%" + searchTerm + "%' "
							+ "LIMIT " + RESULTS_LIMIT + ";";
					
					JSONArray artistList = new JSONArray();

					rs = db.getResultSet(sql);
					while(rs.next()){
						JSONObject artist = new JSONObject();
						artist.put("artist_id", rs.getString("artist_id"));
						artist.put("first_name", rs.getString("first_name"));
						artist.put("last_name", rs.getString("last_name"));
						artist.put("band_name", rs.getString("band_name"));
						artist.put("bio", rs.getString("bio"));
						
						artistList.put(artist);
					}

					// Store album list in searchResults JSON object
					searchResults.put("artists", artistList);
					
					
					response.getWriter().write(searchResults.toString());

				}
				catch (SQLException e) {
				
				e.printStackTrace();
				} catch (JSONException e) {

				e.printStackTrace();
				}
			
			}
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}