package mongo.demo.app.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Map;

@Document(collection = "generic_response")
@Setter
@Getter
public class GenericResponse {
    Map<String,Object> apiResponse =new HashMap<>();
    public void addMessage(String key,Object value){
        apiResponse.put(key,value);
    }
}
