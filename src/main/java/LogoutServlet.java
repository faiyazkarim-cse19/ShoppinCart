import java.io.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
public class LogoutServlet extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /**
        * The CookieArray is iterated over to find the needed cookie. Then the value of the cookie
         * is assigned to the tempCookie variable.
        **/
        Cookie [] cookieArray = request.getCookies();
        response.setContentType("text/html");
        Cookie tempCookie = null;
        for (Cookie c:cookieArray)
        {
            if (c.getName().equals("namekey"))
            {
                tempCookie = c;
                break;
            }
        }

        /**
        * The MaxAge is set to 0 so that the cookie can get invalidated after logging out.
        **/
        if (tempCookie!=null)
        {
            tempCookie.setMaxAge(0);
        }
        PrintWriter out = response.getWriter();
        out.println("<h2>Successfully logged out!</h2>");

        /**
        * A RequestDispatcher takes us back to the starting page.
        **/
        RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
        rd.include(request, response);
    }
}
