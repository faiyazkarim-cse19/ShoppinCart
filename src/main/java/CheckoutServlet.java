import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
public class CheckoutServlet extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /**
         * The CheckoutServlet also has a connection to the database. This is done to handle one final
         * update. When a user wants to check out, his cart will be reset. So, here update statements are
         * used to set all of the item numbers to zero.
        **/
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/iut_labs", "username", "password");
            Statement statement = connection.createStatement();
            statement = connection.prepareStatement("UPDATE iut_labs.cart SET quantity = ? WHERE customerName = 'Faiyaz' and itemName = 'Apple'");
            ((PreparedStatement) statement).setInt(1, 0);
            ((PreparedStatement) statement).executeUpdate();

            statement = connection.prepareStatement("UPDATE iut_labs.cart SET quantity = ? WHERE customerName = 'Faiyaz' and itemName = 'Orange'");
            ((PreparedStatement) statement).setInt(1, 0);
            ((PreparedStatement) statement).executeUpdate();

            statement = connection.prepareStatement("UPDATE iut_labs.cart SET quantity = ? WHERE customerName = 'Faiyaz' and itemName = 'Banana'");
            ((PreparedStatement) statement).setInt(1, 0);
            ((PreparedStatement) statement).executeUpdate();


            /**
            * Cookies here are handled in the same way as in the LogoutServlet.
            **/
            response.setContentType("text/html");
            Cookie[] cookieArray = request.getCookies();
            Cookie tempCookie = null;
            for (Cookie c : cookieArray) {
                if (c.getName().equals("namekey")) {
                    tempCookie = c;
                    break;
                }
            }

            if (tempCookie != null) {
                tempCookie.setMaxAge(0);
            }
            PrintWriter out = response.getWriter();
            out.println("<h2>Successfully checked out cart and logged out!</h2>");

            /**
            * Back to the starting page after checking and logging out.
            **/
            RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
            rd.include(request, response);
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    }

