import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/api/problems")
public class ProblemServlet extends HttpServlet {


    // ==========================================
    // GET ALL PROBLEMS
    // ==========================================

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {


        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");


        String sql =
                "SELECT id, contest_id, problem_index, " +
                "problem_name, rating, solution_file " +
                "FROM problems ORDER BY contest_id";


        try (
                Connection conn =
                        Database.getConnection();

                PreparedStatement ps =
                        conn.prepareStatement(sql);

                ResultSet rs =
                        ps.executeQuery()
        ) {


            PrintWriter out =
                    response.getWriter();


            out.print("[");


            boolean first = true;


            while (rs.next()) {


                if (!first) {
                    out.print(",");
                }


                first = false;


                out.print("{");


                out.print(
                        "\"id\":" +
                        rs.getInt("id") +
                        ","
                );


                out.print(
                        "\"contestId\":" +
                        rs.getInt("contest_id") +
                        ","
                );


                out.print(
                        "\"index\":\"" +
                        escape(
                            rs.getString("problem_index")
                        ) +
                        "\","
                );


                out.print(
                        "\"name\":\"" +
                        escape(
                            rs.getString("problem_name")
                        ) +
                        "\","
                );


                out.print(
                        "\"rating\":" +
                        rs.getInt("rating") +
                        ","
                );


                out.print(
                        "\"file\":\"" +
                        escape(
                            rs.getString("solution_file")
                        ) +
                        "\""
                );


                out.print("}");
            }


            out.print("]");


        } catch (Exception e) {


            e.printStackTrace();


            response.setStatus(
                    HttpServletResponse
                            .SC_INTERNAL_SERVER_ERROR
            );


            response.getWriter().write(
                    "{\"error\":\"" +
                    escape(e.getMessage()) +
                    "\"}"
            );
        }
    }


    // ==========================================
    // ADD NEW PROBLEM
    // ==========================================

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {


        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");


        String contestId =
                request.getParameter("contestId");


        String index =
                request.getParameter("index");


        String name =
                request.getParameter("name");


        String rating =
                request.getParameter("rating");


        String file =
                request.getParameter("file");


        if (
                contestId == null ||
                index == null ||
                name == null ||
                rating == null
        ) {


            response.setStatus(
                    HttpServletResponse.SC_BAD_REQUEST
            );


            response.getWriter().write(
                    "{\"error\":\"Missing required fields\"}"
            );


            return;
        }


        String sql =
                "INSERT INTO problems " +
                "(contest_id, problem_index, " +
                "problem_name, rating, solution_file) " +
                "VALUES (?, ?, ?, ?, ?)";


        try (
                Connection conn =
                        Database.getConnection();

                PreparedStatement ps =
                        conn.prepareStatement(sql)
        ) {


            ps.setInt(
                    1,
                    Integer.parseInt(contestId)
            );


            ps.setString(
                    2,
                    index
            );


            ps.setString(
                    3,
                    name
            );


            ps.setInt(
                    4,
                    Integer.parseInt(rating)
            );


            ps.setString(
                    5,
                    file == null ? "" : file
            );


            ps.executeUpdate();


            response.getWriter().write(
                    "{\"success\":true}"
            );


        } catch (Exception e) {


            e.printStackTrace();


            response.setStatus(
                    HttpServletResponse
                            .SC_INTERNAL_SERVER_ERROR
            );


            response.getWriter().write(
                    "{\"error\":\"" +
                    escape(e.getMessage()) +
                    "\"}"
            );
        }
    }


    // ==========================================
    // DELETE PROBLEM
    // ==========================================

    @Override
    protected void doDelete(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {


        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");


        String id =
                request.getParameter("id");


        if (
                id == null ||
                id.isEmpty()
        ) {


            response.setStatus(
                    HttpServletResponse.SC_BAD_REQUEST
            );


            response.getWriter().write(
                    "{\"error\":\"ID is required\"}"
            );


            return;
        }


        String sql =
                "DELETE FROM problems WHERE id = ?";


        try (
                Connection conn =
                        Database.getConnection();

                PreparedStatement ps =
                        conn.prepareStatement(sql)
        ) {


            ps.setInt(
                    1,
                    Integer.parseInt(id)
            );


            int rows =
                    ps.executeUpdate();


            if (rows > 0) {


                response.getWriter().write(
                        "{\"success\":true}"
                );


            } else {


                response.setStatus(
                        HttpServletResponse.SC_NOT_FOUND
                );


                response.getWriter().write(
                        "{\"error\":\"Problem not found\"}"
                );
            }


        } catch (Exception e) {


            e.printStackTrace();


            response.setStatus(
                    HttpServletResponse
                            .SC_INTERNAL_SERVER_ERROR
            );


            response.getWriter().write(
                    "{\"error\":\"" +
                    escape(e.getMessage()) +
                    "\"}"
            );
        }
    }


    // ==========================================
    // ESCAPE JSON
    // ==========================================

    private String escape(String text) {

        if (text == null) {
            return "";
        }


        return text
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r");
    }
}
