package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

import dao.*;
import domain.*;

/**
 * Created by Eugenia on 15.06.2017.
 */
@WebServlet(name = "loginServlet",  urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    //Выделить два отдельных метода
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String action = request.getParameter("action");

            if (session.getAttribute("user") != null) {
                response.sendRedirect("shop");
                return;
            }

            String userLogin = request.getParameter("userLogin");
            String password = request.getParameter("password");
            UserDAO userDAO = new UserDAO();
        if (action.equals("login")) {
            if (userLogin == null || password == null ||
                    !userDAO.login(userLogin, password)) {
                request.setAttribute("loginFailed", true);
                request.getRequestDispatcher("/WEB-INF/jsp/view/login.jsp")
                        .forward(request, response);
            } else {
                ShoppingCartDAO cartDAO = new ShoppingCartDAO();
                Customer customer = (Customer) userDAO.getUserByLogin(userLogin);
                if (session.getAttribute("cart") != null) {
                    session.removeAttribute("cart");
                }
                Map<Product, Integer> userCart = cartDAO.getAllProductsInCart(customer);
                    session.setAttribute("cart", userCart);


                session.setAttribute("user", customer);
                request.changeSessionId();
                response.sendRedirect("shop");
            }
        }
        else if(action.equals("register")){
            String email = request.getParameter("email");
            if (userLogin == null || password == null ||
                    !userDAO.registerCustomer(userLogin, password, email)) {
                request.setAttribute("registrationFailed", true);
                request.getRequestDispatcher("/WEB-INF/jsp/view/registration.jsp")
                        .forward(request, response);
            }
            else{
                Customer customer = (Customer) userDAO.getUserByLogin(userLogin);
                session.setAttribute("user", customer);
                request.changeSessionId();
                request.setAttribute("isRegistered", true);
                request.getRequestDispatcher("/WEB-INF/jsp/view/registration.jsp")
                        .forward(request, response);
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if(request.getParameter("logout") != null)
        {
            session.invalidate();
            response.sendRedirect("shop");
            return;
        }
        String action = request.getParameter("action");
        if(action.equals("login")) {
            if (session.getAttribute("user") != null) {
                response.sendRedirect("shop");
                return;
            }

            request.setAttribute("loginFailed", false);
            request.getRequestDispatcher("/WEB-INF/jsp/view/login.jsp")
                    .forward(request, response);
        }
        else if(action.equals("register")){
            request.setAttribute("isRegistered", false);
            request.setAttribute("registrationFailed", false);
            request.getRequestDispatcher("/WEB-INF/jsp/view/registration.jsp")
                    .forward(request, response);
        }
    }
}
