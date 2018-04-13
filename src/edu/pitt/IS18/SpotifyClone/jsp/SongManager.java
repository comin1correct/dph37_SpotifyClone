package edu.pitt.IS18.SpotifyClone.jsp;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.pitt.SpotifyClone.core.Song;
import edu.pitt.SpotifyClone.utils.DbUtilities;

/**
 * Servlet implementation class SongManager
 */
@WebServlet("/SongManager")
public class SongManager extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SongManager() {
        super();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//check for nulls
//		String songTitle = request.getParameter("songTitle");
//		String bandName = "Empty";
//		if(bandName != null) {
//			bandName = request.getParameter("bandName");
//		}
//		
//		
//		response.getWriter().append("The song title you searched for is "+ songTitle);
//		response.getWriter().append("\nThe band you searched for is "+ bandName);
		
		
		Vector<Vector<String>> songTable = new Vector<>();

		try {
		    String sql = "SELECT * FROM song;";
		    DbUtilities db = new DbUtilities();
		    ResultSet rs = db.getResultSet(sql);
		    while(rs.next())
		    {
		        Song s = new Song(rs.getString("song_id"),
		        rs.getString("title"),
		        rs.getDouble("length"),
		        rs.getString("file_path"),
		        rs.getString("release_date"),
		        rs.getString("record_date"));

		        songTable.add(s.getSongRecord());
		    }
		    rs.close();
		    db.closeDbConnection();
		    db = null;
		}catch (SQLException e){
		    e.printStackTrace();
		}
		
		response.getWriter().append("<table border = \"1\">");
		for(int i = 0; i < songTable.size(); i++)
        {
			response.getWriter().append("<tr>");
            for(int j = 0; j <songTable.get(i).size();j++)
            {
	            	
	            	response.getWriter().append("<td>"+songTable.get(i).get(j)+"</td>");
	            
            }
        	response.getWriter().append("<tr>");
         } 
		response.getWriter().append("</table>");
		
	}
	
	
/*	=================================================================================================================================
 * 											IMPORT JAR "mysql-connector"
 *  =================================================================================================================================
 * 		https://stackoverflow.com/questions/29755812/unable-to-connect-to-mysql-database-through-jsp-class-fornamecom-mysql-jdbc-d
 * 
 * 		NOTE: Use second response
 * 			 Deployment Assembly>> right click project --> select "properties" --> Deployment Assembly on left side
 * 
 *  =================================================================================================================================
*/
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		
	}

}
