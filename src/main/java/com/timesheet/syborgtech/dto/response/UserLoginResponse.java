package com.timesheet.syborgtech.dto.response;

import com.timesheet.syborgtech.dtoCommon.DataResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginResponse implements DataResponse {
    private String userName;

}
