package com.github.gate.back.rest;

import com.github.pagehelper.PageHelper;
import com.github.gate.back.biz.UserBiz;
import com.github.gate.back.entity.User;
import com.github.gate.common.msg.TableResultResponse;
import com.github.gate.common.rest.BaseController;
import com.github.gate.common.msg.ListRestResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

/**
 * ${DESCRIPTION}
 *
 * @author luozhonghua
 * @create 2017-06-08 11:51
 */
@Controller
@RequestMapping("user")
public class UserController extends BaseController<UserBiz,User> {

    @RequestMapping(value = "/page",method = RequestMethod.GET)
    @ResponseBody
    public TableResultResponse<User> page(@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "1")int offset, String name){
        Example example = new Example(User.class);
        if(StringUtils.isNotBlank(name)) {
            example.createCriteria().andLike("name", "%" + name + "%");
            example.createCriteria().andLike("username", "%" + name + "%");
        }
        int count = baseBiz.selectCountByExample(example);
        PageHelper.startPage(offset, limit);
        return new TableResultResponse<User>(count,baseBiz.selectByExample(example));
    }


}