package com.spring.springcloudlibraryemp.Controller;

import com.spring.springcloudlibraryemp.Service.EmpService;
import com.spring.springcloudlibraryemp.Service.EmpServiceImpl;
import com.spring.springcloudlibraryemp.pojo.attendance;
import com.spring.springcloudlibraryemp.pojo.employee;
import com.spring.springcloudlibraryemp.pojo.fenye;
import com.spring.springcloudlibraryemp.pojo.leave;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class EmpController {

    @Autowired
    private EmpServiceImpl empServiceimpl;
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public int login(@RequestParam(value = "username",required = false)String username, @RequestParam("password")String password){
        int result = empServiceimpl.login(username, password);
        return result;
    }
    @ResponseBody
    @RequestMapping(value = "/selectEmpByname", method = RequestMethod.GET)
    public employee selectEmpByname(@RequestParam(value = "username",required = false)String username){
         System.out.println("进入selectEmpByname");
         return empServiceimpl.selectEmpByname(username);
    }
    @ResponseBody
    @RequestMapping(value = "/getattendByid", method = RequestMethod.GET)
    public List<attendance> getattendByid(@RequestParam(value = "empid", required = false)int empid
            ,@RequestParam(value = "pageSize",required = false) Integer pageSize
            ,@RequestParam(value = "pageNo",required = false)Integer pageNo){
        return empServiceimpl.getattendByid(empid, pageSize,pageNo);
    }
    @ResponseBody
    @RequestMapping(value = "/getattendBycondition", method = RequestMethod.GET)
    public List<attendance> getattendBycondition(@RequestParam(value = "empid",required = false)int empid
            , @RequestParam(value = "pageSize",required = false)Integer pageSize,@RequestParam(value = "pageNo",required = false)Integer pageNo
            , @RequestParam(value = "prefixdate",required = false)String prefixdate,@RequestParam(value = "suffixdate",required = false)String suffixdate
            , @RequestParam(value = "daka_state",required = false)Integer daka_state){
        return empServiceimpl.getattendBycondition(empid, pageSize, pageNo, prefixdate, suffixdate, daka_state);
    }
    @ResponseBody
    @RequestMapping(value = "/getEmpCount",method = RequestMethod.GET)
    public int getCount(@RequestParam(value = "empid",required = false)int empid,@RequestParam(value = "prefixdate",required = false)String prefixdate
            ,@RequestParam(value = "suffixdate",required = false)String suffixdate, @RequestParam(value = "daka_state",required = false)Integer daka_state){
        System.out.println(prefixdate+suffixdate);
        return empServiceimpl.getCount(empid,prefixdate,suffixdate,daka_state);
    }
    @ResponseBody
    @RequestMapping(value = "/daka", method = RequestMethod.GET)
    public int daka(@RequestParam(value = "datetime",required = false)String datetime
            ,@RequestParam(value = "username",required = false)String username,@RequestParam(value = "empid",required = false)Integer empid
            ,@RequestParam(value = "hour",required = false)Integer hour,@RequestParam("timea")String timea){
        int isdaka = empServiceimpl.isdaka(datetime, empid);
        if (isdaka==0) {
            empServiceimpl.adddaka(datetime, empid);
        }
        return empServiceimpl.daka(datetime,username,empid,hour,timea);
    }
    @ResponseBody
    @RequestMapping(value = "/addleave",method = RequestMethod.POST, consumes = "application/json")
    public int addleave(@RequestBody leave leave){
        return empServiceimpl.addleave(leave);
    }

}
