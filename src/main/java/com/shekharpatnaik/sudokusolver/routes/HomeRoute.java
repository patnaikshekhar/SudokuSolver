package com.shekharpatnaik.sudokusolver.routes;

import com.shekharpatnaik.sudokusolver.Server;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shpatnaik on 3/24/14.
 * This is a Spark Route for the Home Page
 */
public class HomeRoute extends Route {

    private static final Logger logger = LoggerFactory.getLogger(Server.class);
    private Configuration freeMarkerConfiguration;

    public HomeRoute(String path, Configuration freeMarkerConfiguration) {
        super(path);
        this.freeMarkerConfiguration = freeMarkerConfiguration;
    }

    @Override
    public Object handle(Request request, Response response) {

        try {
            Template template = freeMarkerConfiguration.getTemplate("templates/home.ftl");
            StringWriter writer = new StringWriter();
            Map<String, Object> inputs = new HashMap<String, Object>();
            inputs.put("root", null);
            template.process(inputs, writer);

            return writer.toString();

        } catch (IOException e) {
            logger.error(e.getStackTrace().toString());
        } catch (TemplateException e) {
            logger.error(e.getStackTrace().toString());
        }

        return null;
    }
}
