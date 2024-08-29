package hayTurnos.hayTurnos.dto;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class AvailableCourt {
    private String id;
    private String name;
    @JsonProperty("surface_type")
    private String surfaceType;
    @JsonProperty("has_lighting")
    private boolean hasLighting;
    @JsonProperty("is_roofed")
    private boolean isRoofed;
    @JsonProperty("sport_ids")
    private List<String> sportIds;
    @JsonProperty("available_slots")
    private List<AvailableSlot> availableSlots;
    @JsonProperty("is_beelup")
    private boolean isBeelup;
}
