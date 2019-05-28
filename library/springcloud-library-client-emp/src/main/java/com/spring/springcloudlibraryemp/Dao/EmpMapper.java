package com.spring.springcloudlibraryemp.Dao;

import com.spring.springcloudlibraryemp.pojo.attendance;
import com.spring.springcloudlibraryemp.pojo.employee;
import com.spring.springcloudlibraryemp.pojo.fenye;
import com.spring.springcloudlibraryemp.pojo.leave;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Mapper
public interface EmpMapper {
    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    public int login(@Param(value = "username")String username, @Param(value = "password")String password);

    /**
     * 根据用户名查询员工
     * @param username
     * @return
     */
    public employee selectEmpByname(@Param(value = "username")String username);

    /**
     * 根据员工id查询出勤信息
     * @param empid
     * @param pageSize
     * @param pageNo
     * @return
     */
    public List<attendance> selectattendByid(@Param(value = "empid")int empid, @Param(value = "pageSize")int pageSize
            , @Param(value = "pageNo")int pageNo);

    /**
     * 根据条件查询出勤信息总数
     * @param empid
     * @param prefixdate
     * @param suffixdate
     * @param daka_state
     * @return
     */
    public int getCount(@Param(value = "empid")int empid, @Param("prefixdate")String prefixdate,@Param("suffixdate")String suffixdate,@Param("daka_state")Integer daka_state);

    /**
     * 根据条件查询出勤信息
     * @param empid
     * @param pageSize
     * @param pageNo
     * @param prefixdate
     * @param suffixdate
     * @param daka_state
     * @return
     */
    public List<attendance> getattendBycondition(@Param("empid")int empid, @Param("pageSize")Integer pageSize,@Param("pageNo")Integer pageNo
            , @Param("prefixdate")String prefixdate,@Param("suffixdate")String suffixdate,@Param("daka_state")Integer daka_state);

    /**
     * 打卡
     * @param datetime
     * @param username
     * @return
     */
    public int daka(@Param("datetime") String datetime, @Param("username")String username, @Param("empid")Integer empid,@Param("hour")Integer hour,@Param("timea")String timea);


    public int isdaka(@Param("datetime")String datetime, @Param("empid")Integer empid);

    public int adddaka(@Param("datetime")String datetime, @Param("empid")Integer empid);

    public int addleave(leave leave);

}
