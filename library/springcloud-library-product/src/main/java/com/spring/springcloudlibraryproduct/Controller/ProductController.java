package com.spring.springcloudlibraryproduct.Controller;


import com.spring.springcloudlibraryproduct.Service.EmpService;
import com.spring.springcloudlibraryproduct.Service.libraryServiceImpl;
import com.spring.springcloudlibraryproduct.Service.noticeService;
import com.spring.springcloudlibraryproduct.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value = "/library")
public class ProductController {

    /*@Autowired
    private libraryServiceImpl librarySerivce;*/
    @Autowired
    private EmpService empService;

    @Autowired
    private com.spring.springcloudlibraryproduct.Service.noticeService noticeService;

    @RequestMapping(value = "dologin")
    public String dologin(){
        return "html/Login";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(@RequestParam(value = "username")String username, @RequestParam(value = "password")String password
                , HttpSession session, Model model){
        session.setAttribute("message","");
        System.out.println("进入login.................");
        Integer login = empService.login(username, password);
        if (login==1) {
            System.out.println("欢迎你:"+username);
            model.addAttribute("USER",username);
            return "html/index";
        }else if (login==4) {
            session.setAttribute("message","服务熔断!");
            return "html/Login";
        }else {
            session.setAttribute("message","登录失败,账号或密码错误!");
            return "html/Login";
        }
    }
    @RequestMapping(value = "doindex")
    public String doindex(){
        return "html/index";
    }

    @SuppressWarnings("all")
    @RequestMapping(value = "doPerson")
    public String doPerson(Model model, @RequestParam(value = "empName", required = false)String empName
                , @RequestParam(value = "pageNo", required = false)Integer pageNo){
        System.out.println("empName:"+empName);
        employee employeea = empService.getEmpByname(empName);
        List<notice> noticeList = noticeService.getAllnotice();
        int Count = empService.getEmpCount(employeea.getEmp_id(),null,null,null);
        if (pageNo==null) {
            pageNo=1;
        }
        fenye fenye = new fenye();
        fenye.setPageSize(4);
        fenye.setCurrentPageNo(pageNo);
        fenye.setTotalCount(Count);
        if(fenye.getCurrentPageNo()>fenye.getTotalPageCount()){
            fenye.setCurrentPageNo(fenye.getTotalPageCount());
        }else if(fenye.getCurrentPageNo()<1){
            fenye.setCurrentPageNo(1);
        }
        List<attendance> attendancelist = empService.getattendByid(employeea.getEmp_id(),fenye.getPageSize()
                ,(fenye.getCurrentPageNo()-1)*fenye.getPageSize());
        model.addAttribute("employeea", employeea);
        model.addAttribute("attendancelist", attendancelist);
        model.addAttribute("fenye", fenye);
        model.addAttribute("noticeList", noticeList);
        return "html/Personalinformation";
    }
    @SuppressWarnings("all")
    @ResponseBody
    @RequestMapping(value = "/dofenye")
    public Object doPersona(@RequestParam(value = "empName", required = false)String empName
            , @RequestParam(value = "pageNo", required = false)Integer pageNo, @RequestParam(value = "prefixdate",required = false)String prefixdate
            ,@RequestParam(value = "suffixdate", required = false)String suffixdate,@RequestParam(value = "daka_state",required = false)Integer daka_state){
        if (pageNo==null) {
            pageNo=1;
        }
        employee employeea = empService.getEmpByname(empName);
        int Count = empService.getEmpCount(employeea.getEmp_id(), prefixdate, suffixdate, daka_state);
        fenye fenye = new fenye();
        fenye.setPageSize(4);
        fenye.setCurrentPageNo(pageNo);
        fenye.setTotalCount(Count);
        if(fenye.getCurrentPageNo()>fenye.getTotalPageCount()){
            fenye.setCurrentPageNo(fenye.getTotalPageCount());
        }else if(fenye.getCurrentPageNo()<1){
            fenye.setCurrentPageNo(1);
        }
        List<attendance> attendancelist = empService.getattendBycondition(employeea.getEmp_id(),fenye.getPageSize()
                ,(fenye.getCurrentPageNo()-1)*fenye.getPageSize(), prefixdate, suffixdate, daka_state);
        Map<String,Object> attendMap = new HashMap<>();
        attendMap.put("attendancelist",attendancelist);
        attendMap.put("fenye",fenye);
        return attendMap;
    }
    @RequestMapping(value = "donotice")
    public synchronized String donotice(@RequestParam(value = "empName",required = false)String empName, Model model){
        employee employeea = empService.getEmpByname(empName);
        model.addAttribute("employeea", employeea);
        return "html/notice";
    }
    @ResponseBody
    @RequestMapping(value = "/daka", method = RequestMethod.GET)
    public int getDate(@RequestParam(value = "datetime",required = false)String datetime
            ,@RequestParam(value = "username",required = false)String username,@RequestParam(value = "empid",required = false)Integer empid
            ,@RequestParam(value = "hour",required = false)Integer hour,@RequestParam("timea")String timea){
        String [] datetimes = datetime.split("/");
        System.out.println("年:"+datetimes[0]);
        System.out.println("月:"+datetimes[1]);

        return empService.daka(datetime,username,empid,hour,timea);
    }
    @ResponseBody
    @RequestMapping(value = "/addleave",method = RequestMethod.POST)
    public Object addleave(leave leave){
        int result = empService.addleave(leave);
        if (result==1) {
            return result;
        }
        return null;
    }
}
