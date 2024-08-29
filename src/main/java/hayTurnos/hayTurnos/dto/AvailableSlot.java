package hayTurnos.hayTurnos.dto;
import lombok.Data;

@Data
public class AvailableSlot {
    private String start;
    private int duration;
    private Price price;
}