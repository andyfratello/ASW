package twitter;

import java.io.PrintStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import twitter4j.GeoLocation;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Trend;
import twitter4j.Trends;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.User;

public class SimpleClient {

	public static void main(String[] args) throws Exception {
		
		final Twitter twitter = new TwitterFactory().getInstance();
		
		//Tasca #5
		//Ho hem comentat per tal de no penjar un retweet cada cop que executem

		
		/*User usr = twitter.showUser("fib_was");
		long id = usr.getStatus().getId();
		
		System.out.println(usr.getStatus().getText());
		
		twitter.retweetStatus(id);
		/*
	
		
		//Tasca #4
		//Ho hem comentat per tal de no penjar un tweet cada cop que executem
		
		/*Date now = new Date();
		String latestStatus = "Hey @fib_was, we've just completed task #4 [timestamp: "+now+"]";
		Status status = twitter.updateStatus(latestStatus);
		System.out.println("Successfully updated the status to: " + status.getText());   */    
		
		
		//Tasca #6
		
		int woeidBcn = 753692;
		GeoLocation PzCat = new GeoLocation(41.3870154, 2.1700471);
		Trends trends = twitter.getPlaceTrends(woeidBcn);
		
		
			
		Trend[] aux = trends.getTrends();
		
        // Meses del anio
		String MES[] = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

        // Dias de la semana
        String DIA[] = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
	   
		Date today = new Date();
	   	Calendar day = Calendar.getInstance();
	  
	   	System.out.println("The Top 10 Trending Topics in Barcelona on " + DIA[day.get(Calendar.DAY_OF_WEEK) - 1] + ", " + day.get(Calendar.DAY_OF_MONTH) +  " "
			   				+ MES[day.get(Calendar.MONTH)] + " " +  day.get(Calendar.YEAR) + " @ " + today.getHours() + ":" + today.getMinutes());
	   	System.out.println();
	   	
	   	for(int i = 0; i < 10; ++i){
	   		System.out.println("* Trending topic: " + aux[i].getName());
	   		Query q = new Query(aux[i].getQuery());
	   		q.setGeoCode(PzCat, 5, Query.Unit.km);
	   		q.count(5);
	  
	   
	   		
	   		QueryResult qr = twitter.search(q);
	   		List <Status> ls = qr.getTweets();
	   		
		   	for (int j = 0; j < ls.size(); ++j) {
		   		System.out.println("    - " + ls.get(j).getUser().getName() + " (" + ls.get(j).getUser().getScreenName() + "): " + ls.get(j).getText());
		 	}
		   	System.out.println();
	   			
	   		
		   
	   	}
	}
}
