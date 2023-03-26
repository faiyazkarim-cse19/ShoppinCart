import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

/**
 * The ShopServlet is the second most important servlet in the application.
 * It showcases the list of items available in the shop via traversing an array.
 * It has utilized HTML form checkboxes and number fields to act as a shopping cart.
 * It also has a logout button.
 **/
public class ShopServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        Cookie [] cookieArray = request.getCookies();
        PrintWriter out = response.getWriter();
        boolean flag = false;

        /**
         *This is the hardcoded list of items available in the shop.
         * Again, the list was kept short for ease of visibility.
         **/
        String [] ShopList = {"Apple", "Orange", "Banana"};

        /**
         * The CookieArray was iterated over, and once a cookie with the required
         * name was found, the loop was broken and a flag value denoting a successful
         * session was set to true. If the flag value is false, then the session is
         * a failure.
         **/

        for (Cookie c:cookieArray)
        {
            if (c.getName().equals("namekey"))
            {
                flag = true;
                break;
            }
        }

        if(flag) {
            out.println("<h3> Welcome to the shop! </h3>");
            out.println("<h3> Take a look at the list of items available(for the sake of this project, consider all the items to always be in stock!):</h3>");
            int ct = 0;

            /**
             * Iterating over our array of items and printing them.
             **/
            for (int i = 0; i < ShopList.length; i++) {
                ct++;
                out.println("<h4>" + ct + ": " + ShopList[i] + "</h4>");
            }

            /**
             * From this point onwards, a database I created in MySQL is heavily involved in the project.
             * I used the database to complete the optional task, resulting in some of the
             * other tasks becoming easier to finish.
             **/
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:port/dbname", "username", "password");
                Statement statement = connection.createStatement();

                /**
                 * These three integers have been initialized to 0.
                 * These three integers are used in the placeholder section of the HTML form for the cart.
                 * These integers get overwritten by their respective values pulled from the database.
                 * This is to make sure that the users have an understanding of the values present in their carts.
                **/
                int appleno=0;
                int orangeno=0;
                int bananano=0;

                /**
                * As there is only one user currently, I added only one customer in the database.
                **/

                ResultSet rs = statement.executeQuery("SELECT * FROM iut_labs.cart WHERE customerName = 'Faiyaz' and itemName = 'Apple'");
                while (rs.next()) {
                    appleno = rs.getInt(3);
                }

                rs = statement.executeQuery("SELECT * FROM iut_labs.cart WHERE customerName = 'Faiyaz' and itemName = 'Orange'");
                while (rs.next()) {
                    orangeno = rs.getInt(3);
                }

                rs = statement.executeQuery("SELECT * FROM iut_labs.cart WHERE customerName = 'Faiyaz' and itemName = 'Banana'");
                while (rs.next()) {
                    bananano = rs.getInt(3);
                }


                /**
                * This form portrays the cart of my application. I used checkboxes to make people select whether they
                 * want something or not. Besides the checkboxes, I used number input fields with a step count of 1 and
                 * a minimum value of 0, preventing the case of negative entries. I used the three integers with the values
                 * fetched from my database as placeholders so that users remain upto date regarding their carts.
                 * After submission, we are taken to the CartServlet.
                **/
                    out.println("<br>");
                    out.println("Click on the checkbox to add an item on the list below to your cart and uncheck to remove it. You can also specify the number of each item you want to take via the counters to the right of the checkboxes:");
                    out.println("<form action=\"cart\" method=\"post\">\n" +
                            "Apple<input type=\"checkbox\" name=\"apple\">\n" +
                            "  <br>\n" +
                            "Number of Apples<input type=\"number\" min=\"0\" name=\"appleno\" placeholder= " + appleno + ">\n" +
                            "  <br><br>\n" +
                            "Orange<input type=\"checkbox\" name=\"orange\">\n" +
                            "  <br>\n" +
                            "Number of Oranges<input type=\"number\" min=\"0\" name=\"orangeno\" placeholder= " + orangeno + ">\n" +
                            "  <br><br>\n" +
                            "Banana<input type=\"checkbox\" name=\"banana\">\n" +
                            "  <br>\n" +
                            "Number of Bananas<input type=\"number\" min=\"0\" name=\"bananano\" placeholder= " + bananano +">\n" +
                            "  <br><br>\n" +
                            "  <input type=\"submit\" value=\"Go to Cart\">\n" +
                            "</form>");


                    /**
                    * I added a logout button here for those wanting to log out soon.
                    **/
                    out.println("<h4>If you want to log out, click on Log out.</h4>");
                    out.println("<br>\n" +
                            "<form method=\"post\" action=\"logout\"> <input" +
                            " type=\"submit\" value=\"Logout\">");
                }
             catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            /**
             * An error message is shown in the rare case that a session was invalid due to the wrong Cookie.
            **/
            out.println("<h4>Log in again as your session was invalid!" + "</h4>");
            RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
            rd.include(request, response);
        }
    }
}
