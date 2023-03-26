import java.io.*;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;

/**
 * This is the most important class in my application. This handles
 * most of the interactions with the database and also showcases a user's
 * cart under different conditions.
 **/

public class CartServlet extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        /**
         *These three strings have been initialized as off.
         * However, their main task is to get the value passed in the
         * response sent by the ShopServlet. If the value is on, then the
         * database will carry out update statements for that particular item.
         **/
        String apple = "off";
        String orange = "off";
        String banana = "off";

        /**
        * Null values aren't considered.
        **/
        if(request.getParameter("apple") != null){
            apple = request.getParameter("apple");
        }

        if(request.getParameter("orange") != null){
            orange = request.getParameter("orange");
        }

        if(request.getParameter("banana") != null){
            banana = request.getParameter("banana");
        }

        /**
        * Like in the ShopServlet, these three integers hold the
         * amount of each particular item selected by the user.
        **/
        int appleno, orangeno, bananano;

        /**
         * For no input, parseInt returns an exception.
         * So, a try and catch block was needed for this.
         **/

        try {
            appleno = Integer.parseInt(request.getParameter("appleno"));
        } catch (NumberFormatException nfe) {
            appleno = 0;
        }

        try {
            orangeno = Integer.parseInt(request.getParameter("orangeno"));
        } catch (NumberFormatException nfe) {
            orangeno = 0;
        }

        try {
            bananano = Integer.parseInt(request.getParameter("bananano"));
        } catch (NumberFormatException nfe) {
            bananano = 0;
        }



        out.println("<h3>Here is a list of all the things added to your cart:</h3>");

        /**
         *Depending on the value of the three strings, the database carries out different functions.
         * If the string values are on, then the database carries out update statements, changing the value
         * of the quantity of the items in the database according to the new values specified by
         * the users in the ShopServlet. The database helps to ensure that the values won't change
         * even if the user logs out without checking out. In the case of the user not adding anything to the cart, the
         * original database value is shown. However, if the value is 0, then it is not shown as output. In the case that
         * all of the items have 0 as the quantity, the application will return an empty page with only a few words and the
         * Checkout and the Logout buttons.
        **/
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:port/dbname", "username", "password");

            Statement statement = connection.createStatement();

            if(apple.equals("on")){
                statement = connection.prepareStatement("UPDATE iut_labs.cart SET quantity = ? WHERE customerName = 'Faiyaz' and itemName = 'Apple'");
                ((PreparedStatement) statement).setInt(1, appleno);
                int res = ((PreparedStatement) statement).executeUpdate();

                ResultSet rs = statement.executeQuery("SELECT * FROM iut_labs.cart WHERE customerName = 'Faiyaz' and itemName = 'Apple'");
                while (rs.next()) {
                    if(appleno == 0){
                        break;
                    }
                    out.println("Quantity of Apples: " + rs.getInt(3));
                }
            }
            else if(apple.equals("off")){
                ResultSet rs = statement.executeQuery("SELECT * FROM iut_labs.cart WHERE customerName = 'Faiyaz' and itemName = 'Apple'");
                while (rs.next()) {
                    if(rs.getInt(3) == 0){
                        break;
                    }
                    out.println("Quantity of Apples: " + rs.getInt(3));
                }
            }
            out.println("<br>");

            if(orange.equals("on")){
                statement = connection.prepareStatement("UPDATE iut_labs.cart SET quantity = ? WHERE customerName = 'Faiyaz' and itemName = 'Orange'");
                ((PreparedStatement) statement).setInt(1, orangeno);
                int res = ((PreparedStatement) statement).executeUpdate();

                ResultSet rs = statement.executeQuery("SELECT * FROM iut_labs.cart WHERE customerName = 'Faiyaz' and itemName = 'Orange'");
                while (rs.next()) {
                    if(orangeno == 0){
                        break;
                    }
                    out.println("Quantity of Oranges: " + rs.getInt(3));
                }
            }
            else if(orange.equals("off")){
                ResultSet rs = statement.executeQuery("SELECT * FROM iut_labs.cart WHERE customerName = 'Faiyaz' and itemName = 'Orange'");
                while (rs.next()) {
                    if(rs.getInt(3) == 0){
                        break;
                    }
                    out.println("Quantity of Oranges: " + rs.getInt(3));
                }
            }
            out.println("<br>");

            if(banana.equals("on")){
                statement = connection.prepareStatement("UPDATE iut_labs.cart SET quantity = ? WHERE customerName = 'Faiyaz' and itemName = 'Banana'");
                ((PreparedStatement) statement).setInt(1, bananano);
                int res = ((PreparedStatement) statement).executeUpdate();

                ResultSet rs = statement.executeQuery("SELECT * FROM iut_labs.cart WHERE customerName = 'Faiyaz' and itemName = 'Banana'");
                while (rs.next()) {
                    if(bananano == 0){
                        break;
                    }
                    out.println("Quantity of Bananas: " + rs.getInt(3));
                }
            }
            else if(banana.equals("off")){
                ResultSet rs = statement.executeQuery("SELECT * FROM iut_labs.cart WHERE customerName = 'Faiyaz' and itemName = 'Banana'");
                while (rs.next()) {
                    if(rs.getInt(3) == 0){
                        break;
                    }
                    out.println("Quantity of Bananas: " + rs.getInt(3));
                }
            }

            /**
            * The Checkout and Logout form logs out of the program whilst also making sure to reset the contents of
             * the cart to zero. This is done via calling an update SQL statement in the CheckoutServet class.
            **/
            out.println("<br>");
            out.println("<h4>If you want to check out and log out, click on the button below. Keep in mind that your cart items will be reset once you do so.</h4>");
            out.println("<br>\n" +
                    "<form method=\"post\" action=\"checkout\"> <input" +
                    " type=\"submit\" value=\"Checkout and Logout\">");


            /**
            * Logout Button.
            **/
            out.println("</form><h4>If you want to log out, click on Log out.</h4>");
            out.println("<br>\n" +
                    "<form method=\"post\" action=\"logout\"> <input" +
                    " type=\"submit\" value=\"Logout\">");
        }
        catch (Exception e){
            out.println(e);
        }

    }
}
