package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;
import dao.*;
import domain.*;


/**
 * Created by Eugenia on 10.06.2017.
 */
@WebServlet(
        name = "storeServlet",
        urlPatterns = "/shop"
)
public class StoreServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String action = request.getParameter("action");
        if(action == null)
            action = "viewMainPage";

        switch(action)
        {
            case "viewProductSubCategory":
                this.viewProductSubCategory(request, response);
                break;

            case "addToCart":
                this.addToCart(request, response);
                break;

            case "emptyCart":
                this.emptyCart(request, response);
                break;

            case "viewCart":
                this.viewCart(request, response);
                break;

            case "updateCart":
                this.updateCart(request, response);
                break;

            case "removeItemFromCart":
                this.removeItemFromCart(request, response);
                break;

            case "addContacts":
                this.addContacts(request, response);
                break;

            case "takeOrder":
                this.takeOrder(request, response);
                break;

            case "viewMainPage":
            default:
                this.viewMainPage(request, response);
                break;
        }
    }
    //Реорганизовать
    private void addToCart(HttpServletRequest request,
                           HttpServletResponse response)
            throws ServletException, IOException
    {
        int productId;
        try
        {
            productId = Integer.parseInt(request.getParameter("productId"));
        }
        catch(Exception e)
        {
            response.sendRedirect("shop");
            return;
        }
        ProductDAO dao = new ProductDAO();
        Product product = dao.getProductById(productId);

        HttpSession session = request.getSession();
        if(session.getAttribute("order")!= null){
            session.removeAttribute("order");
        }
        //Поправить с учетом возможного юзера-администратора
        Customer customer = (Customer)session.getAttribute("user");
        if (session.getAttribute("cart") == null)
            session.setAttribute("cart", new HashMap<Product, Integer>());
        @SuppressWarnings("unchecked")
        Map<Product, Integer> cart =
                (Map<Product, Integer>) session.getAttribute("cart");

        if(customer != null){
            ShoppingCartDAO cartDAO = new ShoppingCartDAO();
            cartDAO.addProductInShoppingCart(customer, product);
        }
        //Попробовать переделать с учетом фишек java8
           if (!cart.containsKey(product)) {
            cart.put(product, 0);
         }
        cart.put(product, cart.get(product) + 1);

        response.sendRedirect(String.format("shop?action=viewProductSubCategory&petCategoryId=%d&" +
                "productCategoryId=%d",product.getPetCategory().getId(),
                product.getProductCategory().getId()));
    }

    private void emptyCart(HttpServletRequest request,
                           HttpServletResponse response)
            throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        Customer customer = (Customer)session.getAttribute("user");
        if(customer != null){
            ShoppingCartDAO cartDAO = new ShoppingCartDAO();
            cartDAO.removeCart(customer);
        }
        session.removeAttribute("cart");
        response.sendRedirect("shop?action=viewCart");
    }

    private void viewCart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        request.getRequestDispatcher("/WEB-INF/jsp/view/viewCart.jsp")
                .forward(request, response);
    }

    private void removeItemFromCart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        int productId;
        try
        {
            productId = Integer.parseInt(request.getParameter("productId"));
        }
        catch(Exception e)
        {
            response.sendRedirect("shop?action=viewCart");
            return;
        }
        ProductDAO productDAO = new ProductDAO();
        Product product = productDAO.getProductById(productId);
        HttpSession session = request.getSession();
        @SuppressWarnings("unchecked")
        Map<Product, Integer> cart =
                (Map<Product, Integer>) session.getAttribute("cart");
        Customer customer = (Customer)session.getAttribute("user");

            if(customer != null){
                ShoppingCartDAO cartDAO = new ShoppingCartDAO();
                cartDAO.removeProductFromCart(customer, product);
            }
            cart.remove(product);
        request.getRequestDispatcher("/WEB-INF/jsp/view/viewCart.jsp")
                .forward(request, response);
    }

    private void updateCart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        int productId;
        int productQuantityInCart;
        try
        {
            productId = Integer.parseInt(request.getParameter("productId"));
            productQuantityInCart = Integer.parseInt(request.getParameter("count"));
        }
        catch(Exception e)
        {
            response.sendRedirect("shop?action=viewCart");
            return;
        }
        ProductDAO productDAO = new ProductDAO();
        Product product = productDAO.getProductById(productId);
        HttpSession session = request.getSession();
        @SuppressWarnings("unchecked")
        Map<Product, Integer> cart =
                (Map<Product, Integer>) session.getAttribute("cart");
        Customer customer = (Customer)session.getAttribute("user");
        int productQuantityInStore = productDAO.getProductQuantity(product);
        if(productQuantityInCart>productQuantityInStore){
            request.setAttribute("updateFailed",true);
        }
        else {
            if(customer != null){
                ShoppingCartDAO cartDAO = new ShoppingCartDAO();
                cartDAO.updateProductCount(customer, product, productQuantityInCart);
            }
               cart.put(product, productQuantityInCart);
            request.setAttribute("updateFailed", false);
        }

        request.getRequestDispatcher("/WEB-INF/jsp/view/viewCart.jsp")
                .forward(request, response);
    }

    private void viewMainPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CategoryDAO dao = new CategoryDAO();
        List<PetCategory> petCategories = dao.getAllPetCategories();
        request.setAttribute("petCategories", petCategories);
        request.getRequestDispatcher("/WEB-INF/jsp/view/main.jsp")
                .forward(request, response);
    }

    private void viewProductSubCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //добавить проверку на null
        int petCategoryId = Integer.parseInt(request.getParameter("petCategoryId"));
        int productCategoryId = Integer.parseInt(request.getParameter("productCategoryId"));
        CategoryDAO categoryDAO = new CategoryDAO();
        PetCategory petCategory = categoryDAO.getPetCategoryById(petCategoryId);
        ProductCategory productCategory = categoryDAO.getProductCategoryById(productCategoryId);
        ProductDAO productDAO = new ProductDAO();
        Map<Product, Integer> products = productDAO.getAllProductsInCategory(petCategory, productCategory);
        request.setAttribute("products", products);
        request.setAttribute("petCategory", petCategory);
        request.setAttribute("productCategory", productCategory);
        request.getRequestDispatcher("/WEB-INF/jsp/view/productCategories.jsp")
                .forward(request, response);

    }
    private void addContacts(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/jsp/view/orderContacts.jsp")
                .forward(request, response);
    }

    private void takeOrder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ShoppingCartDAO cartDAO = new ShoppingCartDAO();
        OrderDAO orderDAO = new OrderDAO();
        HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute("user");
        if(customer == null){
            customer = (Customer)session.getAttribute("unregisteredUser");
        }
        @SuppressWarnings("unchecked")
        Map<Product, Integer> cart =
                (Map<Product, Integer>) session.getAttribute("cart");
        if((customer!=null)&&(cart!=null)){
            CustomerOrder order = cartDAO.takeOrder(customer, cart);
            List<OrderedProduct> productsInOrder = orderDAO.getProductsInOrder(order);
            session.removeAttribute("cart");
            session.setAttribute("order", order);
            session.setAttribute("productsInOrder", productsInOrder);
        }
        request.getRequestDispatcher("/WEB-INF/jsp/view/order.jsp")
                .forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        if(action.equals("addContacts")) {
            String fName = request.getParameter("fName");
            String lName = request.getParameter("lName");
            String address = request.getParameter("address");
            Customer customer = (Customer) session.getAttribute("user");
            ShoppingCartDAO cartDAO = new ShoppingCartDAO();
            UserDAO userDAO = new UserDAO();
            if(customer == null){
                String email = request.getParameter("email");
                String sessionId = session.getId();
                userDAO.registerCustomer(sessionId, sessionId, email);
                customer = (Customer)userDAO.getUserByLogin(sessionId);
                session.setAttribute("unregisteredUser", customer);
            }
            //request.setCharacterEncoding("utf-8");

            request.setAttribute("fName", fName);
            request.setAttribute("lName", lName);
            request.setAttribute("address", address);
            userDAO.addUserContacts(customer, fName, lName, address);
            request.getRequestDispatcher("/WEB-INF/jsp/view/order.jsp")
                    .forward(request, response);
        }
    }
}
