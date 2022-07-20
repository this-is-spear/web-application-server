package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class IOUtils {
    private static final String NEW_LINE = System.getProperty("line.separator");
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    /**
     * @param BufferedReader는
     *            Request Body를 시작하는 시점이어야
     * @param contentLength는
     *            Request Header의 Content-Length 값이다.
     * @return
     * @throws IOException
     */
    public static String readData(BufferedReader br, int contentLength) throws IOException {
        char[] body = new char[contentLength];
        br.read(body, 0, contentLength);
        return String.copyValueOf(body);
    }

    public static String getRequestMessage(InputStream in) throws IOException {
        log.info("Get Request Message");
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder result = new StringBuilder();
        for (String line; (line = reader.readLine()) != null; ) {
            if (line.equals("")) {
                break;
            }
            if (result.length() > 0) {
                result.append(NEW_LINE);
            }
            result.append(line);
        }
        log.info("Get Request message, Message is \n {}", result);
        return result.toString();
    }
}
