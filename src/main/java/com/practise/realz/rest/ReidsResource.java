package com.practise.realz.rest;

import com.practise.realz.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * @author: realz
 * @package: com.practise.realz.rest
 * @date: 2019-11-21
 * @email: zlp951116@hotmail.com
 */
@RestController
@RequestMapping("/redis")
public class ReidsResource {
    @Autowired
    private RedisService redisService;
    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping(value = "/setKeys", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> setKeys(@RequestParam Map<String, String> parameters) {
        if (!CollectionUtils.isEmpty(parameters)) {
            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                redisService.setKey(entry);
            }
        }
        return ResponseEntity.ok(parameters);
    }
 }
