package am.itspace.converter.controller;

import am.itspace.converter.enums.UploadType;
import am.itspace.converter.service.CsvJsonService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/api")
@Slf4j
@AllArgsConstructor
public class CsvJsonController {

    private final CsvJsonService csvJsonService;

    @PostMapping(value = "/csvToJson")
    @ResponseBody
    public ResponseEntity<?> handleUploadData(@RequestParam MultipartFile file,
                                              @RequestParam UploadType command) {
        switch (command) {
            case JSON_TO_CSV:
                return handleUploadJsonToCsv(file);
            case CSV_TO_JSON:
                return handleUploadCsvToJson(file);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    private ResponseEntity<?> handleUploadJsonToCsv(MultipartFile file) {
        ByteArrayResource byteArrayResource = csvJsonService.jsonToCsv(file);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(byteArrayResource.contentLength())
                .body(byteArrayResource);
    }

    public ResponseEntity<?> handleUploadCsvToJson(MultipartFile file) {
        ByteArrayResource byteArrayResource = csvJsonService.csvToJson(file);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(byteArrayResource.contentLength())
                .body(byteArrayResource);
    }
}




