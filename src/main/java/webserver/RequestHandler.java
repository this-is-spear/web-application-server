package webserver;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;
import util.IOUtils;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Map;

import static util.HttpMethod.GET;
import static util.HttpMethod.POST;
import static util.HttpRequestUtils.parseQueryString;
import static util.Url.*;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private static final String NEW_LINE = System.getProperty("line.separator");
    private static final String REGEX = " ";
    private static final String QUERY = "?";
    private final Socket connection;
    private final UserService userService;

    public RequestHandler(Socket connectionSocket, UserService userService) {
        this.connection = connectionSocket;
        this.userService = userService;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
            connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // get request
            String requestMessage = IOUtils.getRequestMessage(in);
            String method = requestMessage.split(NEW_LINE)[0].split(REGEX)[0];
            String url = requestMessage.split(NEW_LINE)[0].split(REGEX)[1];

            // get response
            DataOutputStream dos = new DataOutputStream(out);
            getResponse(dos, out, url, method);

        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void getResponse(DataOutputStream dos, OutputStream out, String url, String method) throws IOException {
        log.info("URL {}, Http method is {}", url, method);
        if (POST.is(method) && REGISTER.is(url)) {
            log.info("Register User");
            Map<String, String> params = getParams(url);
            log.info("User info : [ id : {} ][ password : { secret } ][ name : {} ]", params.get("userId"), params.get("name"));
            userService.joinUser(new User(params.get("userId"), params.get("password"), params.get("name"), params.get("email")));
            response200Header(dos, 0);
        }

        if (GET.is(method) && INDEX_FORM.is(url)) {
            log.info("Move to main form");
            byte[] body = Files.readAllBytes(new File("./webapp" + url).toPath());
            response200Header(dos, body.length);
            responseBody(dos, body);
        }

        if (GET.is(method) && REGISTER_FORM.is(url)) {
            log.info("Move to register form");
            byte[] body = Files.readAllBytes(new File("./webapp" + url).toPath());
            response200Header(dos, body.length);
            responseBody(dos, body);
        }
    }

    private Map<String, String> getParams(String url) {
        int index = url.indexOf(QUERY);
        return parseQueryString(url.substring(index + 1));
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
