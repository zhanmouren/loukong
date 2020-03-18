package com.koron.inwlms.bean.DTO.sysManager;

import lombok.Data;

/**
    * 1管理员添加职员信息bean
    * 2修改职员信息bean
 * @Author xiaozhan
 * @Date 2020.03.17
 */

@Data
public class userDTO {
  //职员id
  private Integer userId;
  //职员名称
  private String name;
  //职员登录名称
  private String loginName;
  //职员登录密码
  private String password;
  //职员手机号
  private String phone;
  //职员Email	
  private String Email;
  //职员职位
  private String position;
  //职员性别
  private String sex;
  //职员电话
  private String photo;
  //微信Id
  private String openID;
  //工号
  private String workNum;
}
