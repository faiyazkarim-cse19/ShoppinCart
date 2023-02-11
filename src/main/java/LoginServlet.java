import java.io.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class LoginServlet extends HttpServlet {
    private String message;

    /**
     * Initializer
     **/
    public void init() {
        //Initializing Servlet instance
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");

        String username = request.getParameter("username");
        String password = request.getParameter("pw");

        HttpSession s = request.getSession(true);

        /**
         *Hardcoded the login credentials for ease of access into the application.
         **/

        if(username.equals("Faiyaz") && password.equals("123")){

            /**
             *Standard Cookie creation for session management.
             **/
            Cookie cookie = new Cookie("namekey", username);
            response.addCookie(cookie);

            /**
             * After logging in, we are taken to the ShopServlet via a RequestDispatcher
             **/
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/shop");
            rd.include(request, response);
        }
        else{
            /**
            *Edge case for when login isn't successful
            **/
            PrintWriter out = response.getWriter();
            out.println("<h2>Username not registered!</h2>");

            RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
            rd.include(request, response);
        }

    }

    /**
     *Destructor
     **/
    public void destroy() {
        //Destroying Servlet instance
    }
}