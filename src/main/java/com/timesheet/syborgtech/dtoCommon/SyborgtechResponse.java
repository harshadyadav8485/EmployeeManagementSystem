package com.timesheet.syborgtech.dtoCommon;

        import com.fasterxml.jackson.annotation.JsonProperty;

        import lombok.AllArgsConstructor;
        import lombok.Builder;
        import lombok.Data;
        import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class SyborgtechResponse {

    @JsonProperty("apiStatus")
    private ApiStatus status;

    @JsonProperty("data")
    private DataResponse data;

}
