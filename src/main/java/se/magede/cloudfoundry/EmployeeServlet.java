package se.magede.cloudfoundry;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.XML;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;


public class EmployeeServlet extends HttpServlet {

	private static final long serialVersionUID = -8313644801261514548L;
	//private static final String restResource = "http://localhost:8080/AzureTest/rest/employee";
	private static final String restResource = "http://magede.cloudapp.net/AzureTest/rest/employee";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			response.setCharacterEncoding("UTF-8");
			
			// Get input from the request
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String searchtext = request.getParameter("searchtext");
			
			// Call the Azure REST service
			String restParameters = "/" + username + "/" + password + "/" + searchtext;
		    Client client = Client.create();
			
		    WebResource webResource  = client.resource(restResource + restParameters);
		    ClientResponse restResponse = webResource.type("text/xml").get(ClientResponse.class);
		    
		    String xml = restResponse.getEntity(String.class);
		    
		    // Create JSON data from the XML and return
		    String json = XML.toJSONObject(xml).toString();
		    response.getWriter().println(json);
		    
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Failed processing request", e);
		}
	}
	
}
