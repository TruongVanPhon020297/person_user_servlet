package com.codegym.controller;

import com.codegym.dao.CountryDao;
import com.codegym.dao.UserDao;
import com.codegym.model.Country;
import com.codegym.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

@WebServlet(name = "UserServlet",urlPatterns = "/persons")
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDao userDAO;
    private CountryDao countryDao;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDao();
        countryDao = new CountryDao();
    }
    private void listUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int page = 1;
        int recordsPerPage = 5;
        String id_country = request.getParameter("id_country");
        String name_person = request.getParameter("name_person");
        if (id_country == null) {
            id_country = "3";
            name_person = "";
        }
        List<Country> listCountry = countryDao.selectAllCountry();
        if(request.getParameter("page") != null){
            page = Integer.parseInt(request.getParameter("page"));
        }
        System.out.println("size country : " + listCountry.size());
        List<User> listUser = userDAO.selectAllUserPage((page-1)*recordsPerPage,recordsPerPage, Integer.parseInt(id_country),name_person);
        int noOfRecords = userDAO.getNoOfRecords();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        request.setAttribute("listUser",listUser);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);
        request.setAttribute("listCountry",listCountry);
        request.setAttribute("id_country",id_country);
        request.setAttribute("name_person",name_person);
        RequestDispatcher dispatcher = request.getRequestDispatcher("user/list.jsp");
        dispatcher.forward(request,response);
    }
    private void showNewForm(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("user/create.jsp");
        List<Country> listCountry = countryDao.selectAllCountry();
        request.setAttribute("listCountry",listCountry);
        dispatcher.forward(request,response);
    }
    private void showEditForm(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        User existingUser = userDAO.selectUser(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("user/edit.jsp");
        request.setAttribute("user",existingUser);
        dispatcher.forward(request,response);
    }
    private void insertUser(HttpServletRequest request,HttpServletResponse response) throws SQLException, ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        int country = Integer.parseInt(request.getParameter("id_country"));
        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setId_country(country);
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(newUser);
        List<Country> listCountry = countryDao.selectAllCountry();
        request.setAttribute("listCountry",listCountry);
        if (!constraintViolations.isEmpty()){
            String err = "";
            for (ConstraintViolation<User> constraintViolation : constraintViolations){
                err += constraintViolation.getMessage() + " ";
            }
            request.setAttribute("err",err);
            request.setAttribute("name",name);
            request.setAttribute("email",email);
            request.setAttribute("id_country",country);
            RequestDispatcher dispatcher = request.getRequestDispatcher("user/create.jsp");
            dispatcher.forward(request,response);
        }else {
            userDAO.insertUser(newUser);
            RequestDispatcher dispatcher = request.getRequestDispatcher("user/create.jsp");
            request.setAttribute("alert","Thành công");
            dispatcher.forward(request,response);
        }
    }
    private void updateUser(HttpServletRequest request,HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        int country = Integer.parseInt(request.getParameter("id_country"));

        User book = new User(id,name,email,country);
        userDAO.updateUser(book);
        RequestDispatcher dispatcher = request.getRequestDispatcher("user/edit.jsp");
        dispatcher.forward(request,response);
    }
    private void deleteUser(HttpServletRequest request,HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        userDAO.deleteUser(id);

        List<User> listUser = userDAO.selectAllUser();
        request.setAttribute("listUser",listUser);
        RequestDispatcher dispatcher = request.getRequestDispatcher("user/list.jsp");
        dispatcher.forward(request,response);
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        try{
            switch (action) {
                case "create" :
                    showNewForm(req,resp);
                    break;
                case "edit" :
                    showEditForm(req,resp);
                    break;
                case "delete" :
                    deleteUser(req,resp);
                    break;
                default:
                    listUser(req,resp);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        try {
            switch (action) {
                case "create":
                    insertUser(req, resp);
                    break;
                case "edit":
                    updateUser(req, resp);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
