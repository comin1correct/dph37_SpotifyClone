package edu.pitt.SpotifyClone.RESTful;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.pitt.SpotifyClone.utils.DbUtilities;

/**
 * Servlet implementation class get_album
 */
@WebServlet("/api/get_album")
public class get_album extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public get_album() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		
//		EXplictly telling the server that the data coming back is in JSON format
		response.setContentType("application/json");
		
		try {
			DbUtilities db = new DbUtilities();
			String sql = "SELECT * FROM album ORDER BY title ASC;";
			ResultSet rs = db.getResultSet(sql);	
			JSONArray albumList = new JSONArray();
				while(rs.next()) {
					JSONObject album = new JSONObject();
					album.put("album_id", rs.getString("album_id"));
					album.put("title", rs.getString("title"));
					album.put("release_date", rs.getString("release_date"));
					album.put("cover_image_path", rs.getString("cover_image_path"));
					album.put("recording_company_name", rs.getString("recording_company_name"));
					album.put("number_of_tracks", rs.getString("number_of_tracks"));
					album.put("PMRC_rating", rs.getString("PMRC_rating"));
					album.put("length", rs.getString("length"));
					
//					Adding to JSON array;
					albumList.put(album);
					
				} 
//				print to web browser
				response.getWriter().write(albumList.toString());
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
