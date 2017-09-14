package com.mi.controller;

import com.mi.common.annotation.Pro;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Swagger 控制器案例模版
 *
 * @author yesh
 *         (M.M)!
 *         Created by 2017/5/7.
 */
@Api(description = "测试用例控制器")
@Pro
@RestController
@RequestMapping("/swagger")
public class TestController {

    @ApiOperation(value = "TEST接口简介", notes = "接口详细描述")
    @RequestMapping(value = "helloTest", method = RequestMethod.POST)
    public UserRes hello(@RequestBody User user) {
        UserRes u = new UserRes();
        u.setId(user.getId());
        return u;
    }

    @ApiOperation(value = "TEST接口简介2", notes = "接口详细描述2")
    @RequestMapping(value = "helloTest", method = RequestMethod.GET)
    public UserRes hello1(@RequestBody User user) {
        UserRes u = new UserRes();
        u.setId(user.getId());
        return u;
    }
}

/**
 *
 *
 */
@ApiModel(value = "User//参数实体")
class User {

    @ApiModelProperty(value = "主键")
    /**
     * 主键
     */
    private int id; //主键
    @ApiModelProperty(value = "姓名")
    private int name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }
}

@ApiModel(value = "UserRes//返回实体")
class UserRes {

    @ApiModelProperty(value = "返回主键")
    private int id;
    @ApiModelProperty(value = "返回姓名")
    private int name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

}