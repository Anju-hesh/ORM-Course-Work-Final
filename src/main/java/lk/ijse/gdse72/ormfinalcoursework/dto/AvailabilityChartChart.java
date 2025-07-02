package lk.ijse.gdse72.ormfinalcoursework.dto;

public class AvailabilityChartChart {
    private String therapistName;
    private Long availableSlotCount;

    public AvailabilityChartChart(String therapistName, Long availableSlotCount) {
        this.therapistName = therapistName;
        this.availableSlotCount = availableSlotCount;
    }

    public String getTherapistName() {
        return therapistName;
    }

    public Long getAvailableSlotCount() {
        return availableSlotCount;
    }
}
