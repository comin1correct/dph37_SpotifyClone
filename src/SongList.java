

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.pitt.SpotifyClone.utils.DbUtilities;

/**
 * Servlet implementation class SongList
 */
@WebServlet("/SongList")
public class SongList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SongList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String songTitle = "";
		if(request.getParameter("songTitle") != null) {
			response.setContentType("text/html");  // making it out put html and text rather than just printing the text and tags.
			songTitle = request.getParameter("songTitle");
			response.getWriter().println(songTitle);
			
			DbUtilities db = new DbUtilities();
			String sql = "SELECT * FROM song WHERE title LIKE '%"+songTitle+"%';";
			ResultSet rs = db.getResultSet(sql);
			try {
				while(rs.next()) {
					
					response.getWriter().println(rs.getString("song_id"));

				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		} //end of if statement 
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
