package edu.pitt.SpotifyClone.RESTful;

//IMPORTING THE JSON jar files
import org.json.JSONObject;

import edu.pitt.SpotifyClone.utils.DbUtilities;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GetArtistList
 */
@WebServlet("/api/get_artist")
public class get_artist extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public get_artist() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		EXplictly telling the server that the data coming back is in JSON format
		response.setContentType("application/json");
		
		try {
			DbUtilities db = new DbUtilities();
			String sql = "SELECT * FROM artist ORDER BY first_name ASC;";
			ResultSet rs = db.getResultSet(sql);	
			JSONArray artistList = new JSONArray();
				while(rs.next()) {
					JSONObject artist = new JSONObject();
					artist.put("id", rs.getString("artist_id"));
					artist.put("first_name", rs.getString("first_name"));
					artist.put("last_name", rs.getString("last_name"));
					artist.put("band_name", rs.getString("band_name"));
					artist.put("bio", rs.getString("bio"));
					
//					Adding to JSON array;
					artistList.put(artist);
					
				}
//				print to web browser
				response.getWriter().write(artistList.toString());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
