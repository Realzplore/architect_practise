package com.practise.realz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.practise.realz.domain.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper extends BaseMapper<User> {

    User selectByLogin(@Param("login") String login);
}
