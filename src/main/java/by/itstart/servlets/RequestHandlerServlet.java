package main.java.by.itstart.servlets;

import main.java.by.itstart.dao.CrudDao;
import main.java.by.itstart.dao.DaoException;
import main.java.by.itstart.dto.Student;
import main.java.by.itstart.dto.Subject;
import main.java.by.itstart.mysql.MySqlDaoFactory;
import main.java.by.itstart.mysql.MySqlStudentDao;
import main.java.by.itstart.mysql.MySqlSubjectDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class RequestHandlerServlet extends HttpServlet {

    private final String SHOW = "show";
    private final String SHOW_ALL = "show-all";
    private final String DELETE = "delete";
    private final String UPDATE = "update";
    private final String CREATE = "create";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        requestHandler(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        requestHandler(req, resp);
        formsHandler(req, resp);
    }

    private void requestHandler(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String action = req.getParameter("action");
        try {
            if (action != null) {
                if (action.startsWith("student")) {
                    switch (action.split("_")[1]) {
                        case SHOW:
                            show(req, resp, Student.class);
                            break;
                        case SHOW_ALL:
                            showAll(req, resp, Student.class);
                            break;
                        case UPDATE:
                            req.getRequestDispatcher("/views/updateStudent.jsp").forward(req, resp);
                            break;
                        case DELETE:
                            delete(req, resp, Student.class);
                            break;
                    }
                } else if (action.startsWith("subject")) {
                    switch (action.split("_")[1]) {
                        case SHOW:
                            show(req, resp, Subject.class);
                            break;
                        case SHOW_ALL:
                            showAll(req, resp, Subject.class);
                            break;
                        case UPDATE:
                            req.getRequestDispatcher("/views/updateSubject.jsp").forward(req, resp);
                            break;
                        case DELETE:
                            delete(req, resp, Subject.class);
                            break;
                        case CREATE:
                            req.getRequestDispatcher("/views/addSubject.jsp").forward(req, resp);
                            break;
                    }
                }
            } else {
                req.getRequestDispatcher("index.jsp");
            }
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    private void formsHandler(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final String ST_UPDATE = "stUpdate";
        final String SUB_UPDATE = "subUpdate";
        final String ADD = "add";

        String request = req.getParameter("request");
        try {
            if (request != null) {
                switch (request) {
                    case ST_UPDATE:
                        updateStudent(req, resp);
                        break;
                    case SUB_UPDATE:
                        updateSubject(req, resp);
                        break;
                    case ADD:
                        createSubject(req, resp);
                        break;
                }
            }
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    private void updateStudent(HttpServletRequest req, HttpServletResponse resp) throws IOException, DaoException {
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        MySqlStudentDao dao = null;
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            String firstName = req.getParameter("firstName");
            String secondName = req.getParameter("secondName");
            int enterYear = Integer.parseInt(req.getParameter("enterYear"));
            Student student = new Student();
            student.setId(id);
            student.setFirstName(firstName);
            student.setSecondName(secondName);
            student.setEnterYear(enterYear);
            MySqlDaoFactory factory = new MySqlDaoFactory();
            dao = (MySqlStudentDao) factory.getDao(factory.getConnection(), Student.class);
            dao.update(student);
            req.setAttribute("result", "<p>Student with id" + id + " was updated</p>");
            req.getRequestDispatcher("/views/viewResult.jsp").forward(req, resp);
        } catch (DaoException | NumberFormatException | ServletException e) {
            out.println("<p>Can not update student</p>");
            out.println("<a href='../index.jsp'>На главную</a>");
        } finally {
            out.close();
            if (dao != null) {
                dao.closeDao();
            }
        }
    }

    private void updateSubject(HttpServletRequest req, HttpServletResponse resp) throws IOException, DaoException {
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        MySqlSubjectDao dao = null;
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            int st_id = Integer.parseInt(req.getParameter("st_id"));
            String title = req.getParameter("title");
            Subject subject = new Subject();
            subject.setId(id);
            subject.setStudentId(st_id);
            subject.setTitle(title);
            MySqlDaoFactory factory = new MySqlDaoFactory();
            dao = (MySqlSubjectDao) factory.getDao(factory.getConnection(), Subject.class);
            dao.update(subject);
            req.setAttribute("result", "<p>Subject with id" + id + " was updated</p>");
            req.getRequestDispatcher("/views/viewResult.jsp").forward(req, resp);
        } catch (DaoException | NumberFormatException | ServletException e) {
            out.println("<p>Can not update subject</p>");
            out.println("<a href='../index.jsp'>На главную</a>");
        } finally {
            out.close();
            if (dao != null) {
                dao.closeDao();
            }
        }
    }

    private void createSubject(HttpServletRequest req, HttpServletResponse resp) throws IOException, DaoException {
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        MySqlSubjectDao dao = null;
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            int st_id = Integer.parseInt(req.getParameter("st_id"));
            String title = req.getParameter("title");
            Subject subject = new Subject();
            subject.setId(id);
            subject.setStudentId(st_id);
            subject.setTitle(title);
            MySqlDaoFactory factory = new MySqlDaoFactory();
            dao = (MySqlSubjectDao) factory.getDao(factory.getConnection(), Subject.class);
            dao.insert(subject);
            req.setAttribute("result", "<p>Subject with id" + id + " was created</p>");
            req.getRequestDispatcher("/views/viewResult.jsp").forward(req, resp);
        } catch (DaoException | NumberFormatException | ServletException e) {
            out.println("<p>Can not add subject to student</p>");
            out.println("<a href='../index.jsp'>На главную</a>");
        } finally {
            out.close();
            if (dao != null) {
                dao.closeDao();
            }
        }
    }

    private void show (HttpServletRequest req, HttpServletResponse resp, Class dtoClass) throws IOException, DaoException {
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();
        CrudDao dao = null;
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            MySqlDaoFactory factory = new MySqlDaoFactory();
            dao = factory.getDao(factory.getConnection(), dtoClass);
            req.setAttribute("object", dao.read(id));
            req.getRequestDispatcher("/views/viewResult.jsp").forward(req, resp);
        } catch (DaoException | NumberFormatException | ServletException e) {
            out.println("<p>Can not select " + dtoClass.getSimpleName() + "</p>");
            out.println("<a href='index.jsp'>На главную</a>");
        } finally {
            out.close();
            if (dao != null) {
                dao.closeDao();
            }
        }
    }

    private void showAll(HttpServletRequest req, HttpServletResponse resp, Class dtoClass) throws IOException, DaoException {
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();
        MySqlDaoFactory factory = new MySqlDaoFactory();
        MySqlStudentDao studentDao = null;
        MySqlSubjectDao subjectDao = null;
        List<? extends Object> objects = null;
        try {
            if (dtoClass == Student.class) {
                studentDao = (MySqlStudentDao) factory.getDao(factory.getConnection(), dtoClass);
                objects =  studentDao.getAll();
            }
            if (dtoClass == Subject.class) {
                int id = Integer.parseInt(req.getParameter("id"));
                subjectDao = (MySqlSubjectDao) factory.getDao(factory.getConnection(), dtoClass);
                objects = subjectDao.getAllByStudentId(id);
            }
            req.setAttribute("objects", objects);
            req.getRequestDispatcher("/views/viewResult.jsp").forward(req, resp);
        } catch (DaoException | NumberFormatException | ServletException e) {
            out.println("<p>Can not select all students</p>");
            out.println("<a href='index.jsp'>На главную</a>");
        } finally {
            out.close();
            if (studentDao != null) {
                studentDao.closeDao();
            }
            if (subjectDao != null) {
                subjectDao.closeDao();
            }
        }
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp, Class dtoClass) throws IOException, DaoException {
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();
        CrudDao dao = null;
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            MySqlDaoFactory factory = new MySqlDaoFactory();
            dao = factory.getDao(factory.getConnection(), dtoClass);
            dao.delete(id);
            req.setAttribute("result", "<p>" + dtoClass.getSimpleName() + " with id" + id + " was deleted</p>");
            req.getRequestDispatcher("/views/viewResult.jsp").forward(req, resp);
        } catch (DaoException | NumberFormatException | ServletException e) {
            out.println("<p>Can not delete student</p>");
            out.println("<a href='index.jsp'>На главную</a>");
        }finally {
            out.close();
            if (dao != null) {
                dao.closeDao();
            }
        }
    }
}

