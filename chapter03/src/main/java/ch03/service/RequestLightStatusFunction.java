package ch03.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service("RequestLightStatusService")
@Description("Get light status")
public class RequestLightStatusFunction implements Function<RequestLightStatusFunction.Request, RequestLightStatusFunction.Response> {
    private final Logger logger = LoggerFactory.getLogger(RequestLightStatusFunction.class);

    LightService lightService;

    public RequestLightStatusFunction(LightService lightService) {
        this.lightService = lightService;
    }

    public record Request(String color) {
    }

    public record Response(String color, boolean on) {
    }

    public Response apply(RequestLightStatusFunction.Request request) {
        logger.info("Requesting status for light: {}", request);
        var light = lightService.getLight(request.color);
        return light.map(value -> new Response(value.getColor(), value.isOn()))
                .orElse(null);

    }
}
