package com.github.rhino10001.filestorage.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health-check")
public class HealthController {

    @GetMapping
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok()
                .body("Ok");
    }

    @GetMapping("/memory-info")
    public ResponseEntity<String> memoryUsage() {
        int bytesInMb = 1024 * 1024;
        long xmx = Runtime.getRuntime().maxMemory() / bytesInMb;
        long currentTotal = Runtime.getRuntime().totalMemory() / bytesInMb;
        long currentFree = Runtime.getRuntime().freeMemory() / bytesInMb;
        long used = currentTotal - currentFree;
        long free = xmx - used;
        String body = """
                          Xmx:  %d  MB
                Current total:  %d  MB
                 Current free:  %d  MB
                         Used:  %d  MB
                   Total free:  %d  MB
                """.formatted(xmx, currentTotal, currentFree, used, free);
        return ResponseEntity.ok()
                .body(body);
    }
}
