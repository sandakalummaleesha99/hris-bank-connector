package hris_bank_adapter.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class CommonResponse {
    private HttpStatus status;
    private String message;
    private Object data;
}
