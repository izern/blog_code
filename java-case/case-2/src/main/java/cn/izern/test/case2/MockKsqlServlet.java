package cn.izern.test.case2;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: zern
 * @since 1.0.0
 */
@WebServlet(name = "mockKsqlServlet", urlPatterns = "/query")
public class MockKsqlServlet extends HttpServlet {


  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    resp.setHeader("Content-Type", "application/vnd.ksql.v1+json");
    resp.setHeader("Vary", "Accept-Encoding, User-Agent");
    resp.setHeader("Transfer-Encoding", "chunked");
    ServletOutputStream outputStream = resp.getOutputStream();
    if (req.getHeader("Accept-Encoding") != null
        && req.getHeader("Accept-Encoding").toLowerCase().contains("gzip")) {
      resp.setHeader("Content-Encoding", "gzip");
      outputStream.print("a");
      outputStream.print("·");
    } else {
      while (true) {
        outputStream.println(UUID.randomUUID().toString());
        outputStream.flush();
        try {
          TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }

  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    doGet(req, resp);
  }


}
