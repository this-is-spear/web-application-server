package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.IOUtils;
import util.URL;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Map;

import static util.HttpRequestUtils.parseQueryString;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private static final String NEW_LINE = System.getProperty("line.separator");
    public static final String REGEX = " ";
    public static final String QUERY = "?";

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
            connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // get request
            String requestMessage = IOUtils.getRequestMessage(in);
            String url = requestMessage.split(NEW_LINE)[0].split(REGEX)[1];

            // send response
            if (url.contains(QUERY)) {
                int index = url.indexOf(QUERY);
                String requestPath = url.substring(0, index);
                String params = url.substring(index + 1);
                sendResponse(out, requestPath, parseQueryString(params));
            } else {
                sendResponsePage(out, url);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void sendResponse(OutputStream out, String url, Map<String, String> params) {
        if (URL.REGISTER.getUrl().equals(url)) {
            log.info("Register User {}", params.get("name"));
            log.info("User info : [ id : {} ][ password : { secret } ][ name : {} ]", params.get("userId"), params.get("name"));
            DataOutputStream dos = new DataOutputStream(out);
            response200Header(dos, 0);
        }
    }

    private void sendResponsePage(OutputStream out, String url) throws IOException {
        log.info("Get URL {}", url);
        if (URL.INDEX_FORM.getUrl().equals(url)) {
            log.info("Move to main form");
            getResponse(out, url);
        }

        if (URL.REGISTER_FORM.getUrl().equals(url)) {
            log.info("Move to register form");
            getResponse(out, url);
        }
    }

    private void getResponse(OutputStream out, String url) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        byte[] body = Files.readAllBytes(new File("./webapp" + url).toPath());
        response200Header(dos, body.length);
        responseBody(dos, body);
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
