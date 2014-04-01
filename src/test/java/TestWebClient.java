import com.shekharpatnaik.sudokusolver.Server;
import com.shekharpatnaik.sudokusolver.solver.SudokuGame;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import spark.utils.IOUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by shpatnaik on 3/24/14.
 * Integration Tests for the client
 */
public class TestWebClient {

    private static final Configuration config = new Configuration();

    private static class UrlResponse {
        public Map<String, List<String>> headers;
        private String body;
        private int status;
    }

    private UrlResponse doGet(String path) throws IOException {

        Integer PORT = Integer.valueOf(System.getenv("PORT"));
        URL url = new URL("http://localhost:" + PORT + path);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        String responseString = IOUtils.toString(connection.getInputStream());
        UrlResponse response = new UrlResponse();
        response.body = responseString;
        response.headers = connection.getHeaderFields();
        response.status = connection.getResponseCode();
        return response;
    }

    @BeforeClass
    public static void setup() {
        Server.main(null);
        config.setClassForTemplateLoading(Server.class, "/");
    }

    @AfterClass
    public static void tearDown() {
        // Nothing
    }

    @Test
    public void HomeRoute_RequestToHomeShouldReturnHomeTemplate_ReturnsHomeTemplate() throws Exception {

        Template template = config.getTemplate("templates/home.ftl");
        StringWriter writer = new StringWriter();
        Map<String, Object> inputs = new HashMap<String, Object>();
        inputs.put("root", null);

        template.process(inputs, writer);

        UrlResponse homeResponse = doGet("/");
        assertNotNull(homeResponse);
        assertNotNull(homeResponse.body);
        assertEquals(200, homeResponse.status);

        assertEquals(writer.toString(), homeResponse.body);
    }

    private String[] getImpossibleGame() {
        String board[] = new String[9];

        board[0] = "0 0 5 0 6 0 7 0 4";
        board[1] = "2 8 0 0 0 1 0 6 9";
        board[2] = "7 0 0 5 4 0 8 0 0";
        board[3] = "9 3 0 0 8 6 0 0 0";
        board[4] = "1 0 4 0 0 0 6 0 8";
        board[5] = "0 0 0 9 5 0 0 7 1";
        board[6] = "0 0 9 0 7 8 0 0 6";
        board[7] = "6 7 0 4 0 0 0 3 0";
        board[8] = "3 0 2 0 1 0 9 0 0";

        return board;
    }

    private String[] getStandardEasyGame() {
        String board[] = new String[9];

        board[0] = "0 0 5 0 6 0 7 0 4";
        board[1] = "0 8 0 0 0 1 0 6 9";
        board[2] = "7 0 0 5 4 0 8 0 0";
        board[3] = "9 3 0 0 8 6 0 0 0";
        board[4] = "1 0 4 0 0 0 6 0 8";
        board[5] = "0 0 0 9 5 0 0 7 1";
        board[6] = "0 0 9 0 7 8 0 0 6";
        board[7] = "6 7 0 4 0 0 0 3 0";
        board[8] = "3 0 2 0 1 0 9 0 0";

        return board;
    }

    private String getStandardEasyGameResult() {
        String result = "";

        result += "2 9 5 8 6 3 7 1 4\n";
        result += "4 8 3 7 2 1 5 6 9\n";
        result += "7 6 1 5 4 9 8 2 3\n";
        result += "9 3 7 1 8 6 4 5 2\n";
        result += "1 5 4 2 3 7 6 9 8\n";
        result += "8 2 6 9 5 4 3 7 1\n";
        result += "5 1 9 3 7 8 2 4 6\n";
        result += "6 7 8 4 9 2 1 3 5\n";
        result += "3 4 2 6 1 5 9 8 7\n";

        return result;
    }

    private JSONObject convertStringArrayToJSONObject(String[] board) {

        JSONObject object = new JSONObject();
        JSONArray rows = new JSONArray();

        for (int i = 0; i < board.length; i++) {
            JSONArray row = new JSONArray();
            String[] rowStrings = board[i].split(" ");
            for (int j = 0; j < rowStrings.length; j++) {
                row.put(rowStrings[j]);
            }

            rows.put(row);
        }

        object.put("data", rows);
        return object;
    }

    private String convertJSONObjectToString(JSONObject object) {
        JSONArray array = object.getJSONArray("data");

        String result = "";
        for (int i = 0; i < array.length(); i++) {
            JSONArray row = array.getJSONArray(i);
            for (int j = 0; j < row.length(); j++) {
                if (j == 0) {
                    result += row.get(j);
                } else {
                    result += " " + row.get(j);
                }
            }
            result += "\n";
        }

        return result;
    }

    @Test
    public void SolveService_RequestShouldResultInSolved_ReturnsSolution() throws Exception {
        UrlResponse solveResponse = doPost("/solve", convertStringArrayToJSONObject(getStandardEasyGame()));
        assertNotNull(solveResponse);
        assertNotNull(solveResponse.body);
        assertEquals(200, solveResponse.status);

        JSONObject result = new JSONObject(solveResponse.body);
        assertEquals("success", result.get("status"));
        assertEquals(getStandardEasyGameResult(), convertJSONObjectToString(result));
    }

    @Test
    public void SolveService_UnsolvableBoard_ReturnsError() throws Exception {
        UrlResponse solveResponse = doPost("/solve", convertStringArrayToJSONObject(getImpossibleGame()));
        assertNotNull(solveResponse);
        assertNotNull(solveResponse.body);
        assertEquals(200, solveResponse.status);

        JSONObject result = new JSONObject(solveResponse.body);
        assertEquals("error", result.get("status"));
        assertEquals("cannot solve", result.get("error_message"));
    }

    private UrlResponse doPost(String path, JSONObject object) throws IOException {
        Integer PORT = Integer.valueOf(System.getenv("PORT"));
        URL url = new URL("http://localhost:" + PORT + path);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("content-type", "application/json; charset=utf-8");
        connection.setDoOutput(true);
        connection.connect();
        OutputStream writer = connection.getOutputStream();
        writer.write(object.toString().getBytes());
        writer.close();

        String responseString = IOUtils.toString(connection.getInputStream());
        UrlResponse response = new UrlResponse();
        response.body = responseString;
        response.headers = connection.getHeaderFields();
        response.status = connection.getResponseCode();
        return response;
    }
}
