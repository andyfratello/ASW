package wallOfTweets;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Vector;

public class Database {

	private static Connection dbCon;

	public static void setConnection(Connection con) {
		dbCon = con;
	}

	public static Vector<Tweet> getTweets() 
	{
		Vector<Tweet> result = new Vector<Tweet>();
		try {
			Statement stmt = dbCon.createStatement();
			String query = "select * from tweets order by twTIME desc";
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				Tweet tt = new Tweet();
				Date dat = rs.getTimestamp("twTIME");
				tt.setDate(dat.getTime());
				tt.setAuthor(rs.getString("twAUTHOR"));
				tt.setText(rs.getString("twTEXT"));
				tt.setId(rs.getLong("twID"));
				tt.setLikes(rs.getInt("twLIKES"));
				result.add(tt);
			}
			rs.close();
			stmt.close();
		}
		catch (SQLException ex) {
			System.err.println(ex.getStackTrace());
		}
		return result;
	}

	public static Tweet insertTweet(String author, String text)
	{
		Tweet result =null;
		if (text != null && !text.equals(""))
		{		
			if (author == null || author.equals("")) author ="Anonymous";
			String insert = "insert into tweets(twAUTHOR, twTEXT) values (?, ?)";
			try {
				PreparedStatement pst = dbCon.prepareStatement(insert, PreparedStatement.RETURN_GENERATED_KEYS);
				pst.setString(1, author);
				pst.setString(2, text);
				pst.executeUpdate();
				ResultSet rs = pst.getGeneratedKeys();
				if (rs != null && rs.next()) { 
					Statement stmt = dbCon.createStatement();
					String query = "select * from tweets where twID = " + rs.getLong(1);
					ResultSet rs2 = stmt.executeQuery(query);
					rs2.first();
					result = new Tweet();
					result.setDate(rs2.getTimestamp("twTIME").getTime());
					result.setAuthor(rs2.getString("twAUTHOR"));
					result.setText(rs2.getString("twTEXT"));
					result.setId(rs2.getLong("twID"));
					result.setLikes(rs2.getInt("twLIKES"));
					rs2.close();
					stmt.close();
				}
				rs.close();
				pst.close();
			}
			catch (SQLException ex) {
				System.err.println(ex.getStackTrace());
			}
		}
		return result;
	}

	public static int likeTweet(long id)
	{
		int output = -1;
		String update = "update tweets set twLIKES = twLIKES + 1 where twID = "+id;
		String query = "select twLIKES from tweets where twID = "+id;
		try {
			dbCon.setAutoCommit(false);
			try {
				Statement stmt = dbCon.createStatement();
				stmt.executeUpdate(update);
				ResultSet rs = stmt.executeQuery(query);
				rs.first();
				output = rs.getInt(1);
				rs.close();
				stmt.close();
				dbCon.commit();
			}
			catch (SQLException ex ) {
				System.err.println(ex.getStackTrace());
				dbCon.rollback();
			}
			dbCon.setAutoCommit(true);

		}
		catch (SQLException ex ) { System.err.println(ex.getStackTrace()); }
		return output;
	}

	public static boolean deleteTweet(long id)
	{
		int dts = 0;
		String delete = "delete from tweets where twID = "+id;
		try {
			Statement stmt = dbCon.createStatement();
			dts = stmt.executeUpdate(delete);
			stmt.close();
		}
		catch (SQLException ex) {
			System.err.println(ex.getStackTrace());
		}
		return dts > 0;


	}
}
