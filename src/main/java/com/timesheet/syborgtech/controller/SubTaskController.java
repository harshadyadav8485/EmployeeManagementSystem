package com.timesheet.syborgtech.controller;

import com.timesheet.syborgtech.dto.response.SubTaskListResponseDto;
import com.timesheet.syborgtech.dto.response.SubTaskRequestDto;
import com.timesheet.syborgtech.dto.response.SubTasksResponseDto;
import com.timesheet.syborgtech.dto.response.TaskRequestDto;
import com.timesheet.syborgtech.dtoCommon.ApiStatus;
import com.timesheet.syborgtech.dtoCommon.SyborgtechResponse;
import com.timesheet.syborgtech.service.SubTaskService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/subtask")

public class SubTaskController {

    @Autowired
    private SubTaskService subTaskService;
    @PostMapping("/v1")
    public SyborgtechResponse createSubTask(@RequestBody SubTaskRequestDto subTaskRequestDto) throws IOException {
        return SyborgtechResponse.builder()
                .status(ApiStatus.builder()
                        .status("SUCCESS")
                        .statusCode("Sprint-001")
                        .statusMessage("SubTask Created Successfully")
                        .build())
                .data(subTaskService.createSubTask(subTaskRequestDto)).build();
    }

    @GetMapping("/v1")
    public SyborgtechResponse getSubTasks(
            @RequestParam(required = false, name = "searchTerm") String searchTerm,
            @RequestParam(required = false, name = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(required = false, name = "recordsPerPage", defaultValue = "1000") Integer recordsPerPage,
            @RequestParam(name = "subTaskId",required = false)Long subTaskId,
            @RequestParam(name = "taskId",required = false)Long taskId
            ) throws IOException {
        return SyborgtechResponse.builder()
                .status(ApiStatus.builder()
                        .status("SUCCESS")
                        .statusCode("ORD-0001")
                        .statusMessage("SubTask Fetched Successfully")
                        .build())
                .data(subTaskService.getSubTasks(searchTerm,pageNo,recordsPerPage,subTaskId,taskId)).build();
    }

    @GetMapping("/v1/timesheet")
    public SyborgtechResponse getTimeSheet(
            @RequestParam(name = "subTaskId",required = false)Long subTaskId,
            @RequestParam(name = "taskId",required = false)Long taskId,
            @RequestParam(name = "sprintId",required = false)Long sprintId,
            @RequestParam(name = "userId",required = false)Long userId,
            @RequestParam(name = "projectId",required = false)Long projectId,
            @RequestParam(name = "sortOrder", required = false, defaultValue = "desc") String sortOrder
    ) throws IOException {
        return SyborgtechResponse.builder()
                .status(ApiStatus.builder()
                        .status("SUCCESS")
                        .statusCode("ORD-0001")
                        .statusMessage("Timesheet Fetched Successfully")
                        .build())
                .data(subTaskService.getTimeSheet(subTaskId,taskId,sprintId,userId,projectId,sortOrder)).build();
    }

    @GetMapping("/v1/timesheet/download")
    public ResponseEntity<byte[]> downloadTimeSheetAsExcel(
            @RequestParam(name = "subTaskId", required = false) Long subTaskId,
            @RequestParam(name = "taskId", required = false) Long taskId,
            @RequestParam(name = "sprintId", required = false) Long sprintId,
            @RequestParam(name = "userId", required = false) Long userId,
            @RequestParam(name = "projectId", required = false) Long projectId,
            @RequestParam(name = "sortOrder", required = false, defaultValue = "desc") String sortOrder
    ) throws IOException {
        SubTasksResponseDto responseDto = subTaskService.getTimeSheet(subTaskId, taskId, sprintId, userId, projectId, sortOrder);
        byte[] excelData = generateExcel(responseDto.getSubTaskListResponseDtos());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=subtasks.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .body(excelData);
    }

    private byte[] generateExcel(List<SubTaskListResponseDto> subTaskListResponseDtos) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("SubTasks");

        Row headerRow = sheet.createRow(0);
        String[] headers = {"ID","Sprint Name", "Task Name", "Sub Task Name", "Status", "User Name", "Reporter Name",
                "Budgeted Hours", "Actual Hours", "Start Date", "End Date", "Created At", "Updated At"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(getHeaderCellStyle(workbook));
        }

        int rowNum = 1;
        for (SubTaskListResponseDto subTask : subTaskListResponseDtos) {
            Row row = ((Sheet) sheet).createRow(rowNum++);
            row.createCell(0).setCellValue(subTask.getId());
            row.createCell(1).setCellValue(subTask.getSprintName());
            row.createCell(2).setCellValue(subTask.getTaskName());
            row.createCell(3).setCellValue(subTask.getSubTaskName());
            row.createCell(4).setCellValue(subTask.getStatus().toString());
            row.createCell(5).setCellValue(subTask.getUserName());
            row.createCell(6).setCellValue(subTask.getReporterName());
            row.createCell(7).setCellValue(subTask.getBudgetedHours());
            row.createCell(8).setCellValue(subTask.getActualHours());
            row.createCell(9).setCellValue(subTask.getStartDate().toString());
            row.createCell(10).setCellValue(subTask.getEndDate() != null ? subTask.getEndDate().toString() : "N/A");
            row.createCell(11).setCellValue(subTask.getCreateAt().toString());
            row.createCell(12).setCellValue(subTask.getUpdatedAt().toString());
        }

        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
        return outputStream.toByteArray();
    }

    private CellStyle getHeaderCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        return style;
    }
}
