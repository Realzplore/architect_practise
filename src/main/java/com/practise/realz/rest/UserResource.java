package com.practise.realz.rest;

import com.practise.realz.domain.User;
import com.practise.realz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


/**
 * @author: realz
 * @package: com.practise.realz.rest
 * @date: 2019-09-23
 * @email: zlp951116@hotmail.com
 */
@RestController
@RequestMapping(value = "/api/user")
public class UserResource {
    @Autowired
    private UserService userService;

    @PostMapping()
    public ResponseEntity<User> createUser(@RequestBody @Valid User user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @RequestMapping
    public ResponseEntity<Void> testMultiThread() {
        userService.testMultiThread();
        return ResponseEntity.ok(null);
    }
}
