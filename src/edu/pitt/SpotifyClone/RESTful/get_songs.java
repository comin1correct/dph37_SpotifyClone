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
 * Servlet implementation class get_songs
 */

//virtual path to were the servlet lives	- create a virtual folder
@WebServlet("/api/get_songs")
public class get_songs extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public get_songs() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		response.setContentType("text/html");
		response.setContentType("application/json");

		JSONArray songList = new JSONArray();
			
		try {
			DbUtilities db = new DbUtilities();
			String sql = "SELECT * FROM song ORDER BY title ASC;";
			ResultSet rs = db.getResultSet(sql);
			while(rs.next()) {
				// placing each record into a JSONObject
				JSONObject song = new JSONObject();
				song.put("song_id", rs.getString("song_id"));
				song.put("title", rs.getString("title"));
				song.put("release_date", rs.getString("release_date"));
				song.put("record_date", rs.getString("record_date"));
				song.put("length", rs.getInt("length"));
				//song built and placing it into JSONArray
				songList.put(song);
				}
			response.getWriter().write(songList.toString());
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				ErrorLogger.log(e.getMessage());
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
