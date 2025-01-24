package com.timesheet.syborgtech.dtoCommon;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ApiStatus {
    private String statusCode;
    private String statusMessage;
    private String status;

    public ApiStatus cloneApiStatus() {
        return ApiStatus.builder()
                .status(this.status)
                .statusMessage(this.statusMessage)
                .statusCode(this.statusCode)
                .build();
    }
}
