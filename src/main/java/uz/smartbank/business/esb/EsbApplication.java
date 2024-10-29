package uz.smartbank.business.esb;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import uz.smartbank.business.esb.verticles.ApiVerticle;

public class EsbApplication {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        DeploymentOptions deploymentOptions = new DeploymentOptions().setInstances(3);
        vertx.deployVerticle(ApiVerticle.class, deploymentOptions);
    }
}
