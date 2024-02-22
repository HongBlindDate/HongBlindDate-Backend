package hongblinddate.backend.domain.member.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class SuspensionRequest {

    private final int days;
    @JsonCreator
    public SuspensionRequest(@JsonProperty("days") int days) {
        this.days = days;
    }
}