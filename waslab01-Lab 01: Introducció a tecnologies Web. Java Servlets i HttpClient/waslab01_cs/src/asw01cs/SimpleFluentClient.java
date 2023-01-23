package asw01cs;



import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;

//This code uses the Fluent API

public class SimpleFluentClient {

    private static String URI = "http://localhost:8080/waslab01_ss/";

    public final static void main(String[] args) throws Exception {
        
        String idTwt = Request.Post(URI + "wot")
        .bodyForm(Form.form().add("author",  "Andreu").add("tweet_text",  "hola bon dia").build())
        .addHeader("Accept", "text/plain").execute().returnContent().asString();
        System.out.println(idTwt);
        
        
        System.out.println(Request.Get(URI).addHeader("Accept", "text/plain").execute().returnContent());
        
    
        System.out.println(String.format("El tweet #%s ha sigut esborrat", Integer.parseInt(idTwt)-1));
        Request.Post(URI + "wot").addHeader("Accept", "text/plain").bodyForm(Form.form().add("twid", idTwt).build()).execute();
    }
}