package wallOfTweets;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Vector;

public class Database {

	private static SimpleDateFormat mySQLTimeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static Connection dbCon;

	public static void setConnection(Connection con) {
		dbCon = con;
	}

	public static Vector<Tweet> getTweets() throws SQLException 
	{
		Vector<Tweet> result = new Vector<Tweet>();
		Statement stmt = dbCon.createStatement();
		String query = "select * from tweets order by twTIME desc";
		ResultSet rs = stmt.executeQuery(query);
		while (rs.next()) {
			Tweet tt = new Tweet();
			tt.setDate(mySQLTimeStamp.parse(rs.getString("twTIME"), new ParsePosition(0)));
			tt.setAuthor(rs.getString("twAUTHOR"));
			tt.setText(rs.getString("twTEXT"));
			tt.setTwid(rs.getLong("twID"));
			result.add(tt);
		}
		rs.close();
		stmt.close();
		return result;
	}

	public static long insertTweet(String author, String text) throws SQLException
	// returns the id (long) of the newly created tweed, if successful.
	{
		long twid = -1;
		if (text != null && !text.equals(""))
		{		
			if (author == null || author.equals("")) author ="Anonymous";
			String insert = "insert into tweets(twAUTHOR, twTEXT) values (?, ?)";
			PreparedStatement pst = dbCon.prepareStatement(insert, PreparedStatement.RETURN_GENERATED_KEYS);
			pst.setString(1, author);
			pst.setString(2, text);
			pst.executeUpdate();
			ResultSet rs = pst.getGeneratedKeys();
			if (rs != null && rs.next()) 
				twid = rs.getLong(1);
			rs.close();
			pst.close();
		}
		return twid;
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
