package hongblinddate.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataResponse<T> extends BaseResponse {

    protected T data;

    @Builder
    public DataResponse(boolean isSuccess, HttpStatusCode statusCode, T data) {
        super(isSuccess, statusCode);
        this.data = data;
    }

    public static <T> DataResponse<T> ok(T data) {
        return DataResponse.<T>builder()
                .isSuccess(true)
                .statusCode(HttpStatus.OK)
                .data(data)
                .build();
    }

    public static <T> DataResponse<T> ok() {
        return DataResponse.<T>builder()
                .isSuccess(true)
                .statusCode(HttpStatus.OK)
                .build();
    }
}
