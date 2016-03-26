package de.app.controller;

import java.lang.Exception;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AbstractController {

@RequestMapping(value="/errors")
@ResponseBody
public Map<String, Object> errorA( ){
    Map<String, Object> response = new HashMap<String, Object>();
    response.put( "Error", new String("Error happened") );
    return response;
}

@ExceptionHandler(Exception.class)
@ResponseBody
public ResponseEntity<?> exceptionHandler( HttpServletRequest request, Exception exception ){
    return null;
}

}
