import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/saveServlet")
public class SaveServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

     response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

    PrintWriter out = response.getWriter();

    String name = request.getParameter("name");
    String breed = request.getParameter("breed");
    String owner = request.getParameter("owner");

    var dog = new Dog();

        dog.setName(name);
        dog.setBreed(breed);
        dog.setOwner(owner);

    out.println(dog.toString());
    out.println(DogRepository.getConnection());

    int status = DogRepository.save(dog);
    out.println(status);

        if (status > 0) {
        out.print("Record saved successfully!");
    } else {
        out.println("Sorry! unable to save record");
    }
        out.close();
}
}
