package hayTurnos.hayTurnos.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Response {
    @JsonProperty("available_courts")
    private List<AvailableCourt> availableCourts;
}

