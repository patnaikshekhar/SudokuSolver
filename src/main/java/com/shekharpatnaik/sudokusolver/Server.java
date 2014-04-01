package com.shekharpatnaik.sudokusolver;

import com.shekharpatnaik.sudokusolver.routes.HomeRoute;
import com.shekharpatnaik.sudokusolver.routes.SolveRoute;
import freemarker.template.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Spark;

/**
 * Created by shpatnaik on 3/24/14.
 * This is the main server class which starts the spark application
 */
public class Server {

    private static final Logger logger = LoggerFactory.getLogger(Server.class);
    private static final Configuration freeMarkerConfiguration = new Configuration();

    public static void main(String args[]) {

        Spark.setPort(Integer.valueOf(System.getenv("PORT")));
        Spark.staticFileLocation("/public");

        freeMarkerConfiguration.setClassForTemplateLoading(Server.class, "/");

        Spark.get(new HomeRoute("/", freeMarkerConfiguration));
        Spark.post(new SolveRoute("/solve"));

        logger.info("com.shekharpatnaik.sudokusolver.Server Started");
    }
}
