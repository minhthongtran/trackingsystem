package com.trackingsystem.driverapp.responseHandler;


import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

public class GlobalResponseHandler {

    public ResponseDetail handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest) {

        ResponseDetail detail = new ResponseDetail(
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false),
                "RESOURCE_NOT_FOUND"
        );

        return detail;
    }


}
