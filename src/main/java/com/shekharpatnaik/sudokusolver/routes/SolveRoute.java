package com.shekharpatnaik.sudokusolver.routes;

import com.shekharpatnaik.sudokusolver.Server;
import com.shekharpatnaik.sudokusolver.solver.SudokuGame;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

import javax.naming.CannotProceedException;

/**
 * Created by shpatnaik on 3/25/14.
 * This is the route which hosts the puzzle solving service
 */
public class SolveRoute extends Route {

    private static final Logger logger = LoggerFactory.getLogger(Server.class);

    public SolveRoute(String path) {
        super(path);
    }

    private String[] convertJSONObjectToStringArray(JSONArray inputArray) {
        String[] result = new String[inputArray.length()];

        for (int row = 0; row < inputArray.length(); row++) {

            JSONArray rowArray = inputArray.getJSONArray(row);
            String rowString = "";

            for (int col = 0; col < rowArray.length(); col++) {
                if (col == 0) {
                    rowString += rowArray.get(col);
                } else {
                    rowString += " " + rowArray.get(col);
                }
            }
            result[row] = rowString;
        }

        return result;
    }

    private JSONObject convertStringToJSONObject(String inputString) {
        JSONObject result = new JSONObject();

        String[] rowStrings = inputString.split("\\n");
        JSONArray rowArray = new JSONArray();

        for (int row = 0; row < rowStrings.length; row++) {
            JSONArray colArray = new JSONArray();
            String[] colStrings = rowStrings[row].split(" ");
            for (int col = 0; col < colStrings.length; col++) {
                colArray.put(colStrings[col]);
            }

            rowArray.put(colArray);
        }

        result.put("data", rowArray);
        result.put("status", "success");
        return result;
    }

    private JSONObject createErrorMessage(String message) {
        JSONObject object = new JSONObject();
        object.put("status", "error");
        object.put("error_message", message);
        return object;
    }

    @Override
    public Object handle(Request request, Response response) {

        JSONObject result;

        try {
            JSONObject requestJSON = new JSONObject(request.body());
            SudokuGame game = new SudokuGame(convertJSONObjectToStringArray(requestJSON.getJSONArray("data")));
            game.solve();
            result = convertStringToJSONObject(game.toString());
        } catch (CannotProceedException e) {
            result = createErrorMessage("cannot solve");
            logger.error(e.getMessage());
        } catch (Exception e) {
            result = createErrorMessage("unknown");
            logger.error(e.getMessage());
        }

        response.type("application/json");
        /*try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        return result;
    }
}
