package by.itstart.servlets;

import by.itstart.dao.GenericDao;
import by.itstart.dao.DaoException;
import by.itstart.dto.Student;
import by.itstart.dto.Subject;
import by.itstart.hibernate.HibernateSubjectDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

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

    @Autowired
    @Qualifier("studentDao")
    private GenericDao studentDao;
    @Autowired
    @Qualifier("subjectDao")
    private GenericDao subjectDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        requestHandler(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        requestHandler(req, resp);
        formsHandler(req, resp);
    }

    @Override
    public void init() throws ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext (this);
        super.init();
    }

    private void requestHandler(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String action = req.getParameter("action");
        try {
            if (action != null) {
                if (action.startsWith("student")) {
                    switch (action.split("_")[1]) {
                        case SHOW:
                            show(req, resp, "studentDao");
                            break;
                        case SHOW_ALL:
                            showAll(req, resp, "studentDao");
                            break;
                        case UPDATE:
                            req.getRequestDispatcher("/views/updateStudent.jsp").forward(req, resp);
                            break;
                        case DELETE:
                            delete(req, resp, "studentDao");
                            break;
                    }
                } else if (action.startsWith("subject")) {
                    switch (action.split("_")[1]) {
                        case SHOW:
                            show(req, resp, "subjectDao");
                            break;
                        case SHOW_ALL:
                            showAll(req, resp, "subjectDao");
                            break;
                        case UPDATE:
                            req.getRequestDispatcher("/views/updateSubject.jsp").forward(req, resp);
                            break;
                        case DELETE:
                            delete(req, resp, "subjectDao");
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
        }    }

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
        PrintWriter out = resp.getWriter();
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
            studentDao.update(student);
            req.setAttribute("result", "<p>Student with id" + id + " was updated</p>");
            req.getRequestDispatcher("/views/viewResult.jsp").forward(req, resp);
        } catch (DaoException | NumberFormatException | ServletException e) {
            out.println("<p>Can not update student</p>");
            out.println("<a href='../index.jsp'>На главную</a>");
        } finally {
            out.close();
        }
    }

    private void updateSubject(HttpServletRequest req, HttpServletResponse resp) throws IOException, DaoException {
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            int st_id = Integer.parseInt(req.getParameter("st_id"));
            String title = req.getParameter("title");
            Subject subject = new Subject();
            subject.setId(id);
            subject.setStudentId(st_id);
            subject.setTitle(title);
            subjectDao.update(subject);
            req.setAttribute("result", "<p>Subject with id" + id + " was updated</p>");
            req.getRequestDispatcher("/views/viewResult.jsp").forward(req, resp);
        } catch (Exception e) {
            out.println("<p>Can not update subject</p>");
            out.println("<a href='../index.jsp'>На главную</a>");
        } finally {
            out.close();
        }
    }

    private void createSubject(HttpServletRequest req, HttpServletResponse resp) throws IOException, DaoException {
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            int st_id = Integer.parseInt(req.getParameter("st_id"));
            String title = req.getParameter("title");
            Subject subject = new Subject();
            subject.setId(id);
            subject.setStudentId(st_id);
            subject.setTitle(title);
            subjectDao.insert(subject);
            req.setAttribute("result", "<p>Subject with id" + id + " was created</p>");
            req.getRequestDispatcher("/views/viewResult.jsp").forward(req, resp);
        } catch (DaoException | NumberFormatException | ServletException e) {
            out.println("<p>Can not add subject to student</p>");
            out.println("<a href='../index.jsp'>На главную</a>");
        } finally {
            out.close();
        }
    }

    private void show (HttpServletRequest req, HttpServletResponse resp, String daoType) throws IOException, DaoException {
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            if (daoType.startsWith("student")) {
                req.setAttribute("object", studentDao.read(id));
            }
            if (daoType.startsWith("subject")) {
                req.setAttribute("object", subjectDao.read(id));
            }
            req.getRequestDispatcher("/views/viewResult.jsp").forward(req, resp);
        } catch (DaoException | NumberFormatException | ServletException e) {
            out.println("<p>Can not select object</p>");
            out.println("<a href='index.jsp'>На главную</a>");
        } finally {
            out.close();
        }
    }

    private void showAll(HttpServletRequest req, HttpServletResponse resp, String daoType) throws IOException, DaoException {
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();
        List<? extends Object> objects = null;
        try {
            if (daoType.startsWith("student")) {
                objects =  studentDao.getAll();
            }
            if (daoType.startsWith("subject")) {
                int id = Integer.parseInt(req.getParameter("id"));
                objects = subjectDao.getAllByStudentId(id);
            }
            req.setAttribute("objects", objects);
            req.getRequestDispatcher("/views/viewResult.jsp").forward(req, resp);
        } catch (DaoException | NumberFormatException | ServletException e) {
            out.println("<p>Can not select all</p>");
            out.println("<a href='index.jsp'>На главную</a>");
        } finally {
            out.close();
        }
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp, String daoType) throws IOException, DaoException {
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            if (daoType.startsWith("student")) {
                studentDao.delete(id);
            }
            if (daoType.startsWith("subject")) {
                subjectDao.delete(id);
            }
            req.setAttribute("result", "<p>Object with id" + id + " was deleted</p>");
            req.getRequestDispatcher("/views/viewResult.jsp").forward(req, resp);
        } catch (Exception e) {
            out.print(e.getMessage());
            out.println("<p>Can not delete student</p>");
            out.println("<a href='index.jsp'>На главную</a>");
        }finally {
            out.close();
        }
    }
}

