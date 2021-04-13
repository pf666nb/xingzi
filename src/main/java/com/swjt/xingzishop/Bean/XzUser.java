package com.swjt.xingzishop.Bean;

import java.io.Serializable;
import java.util.Date;

public class XzUser implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column XZ_User.User_Id
     *
     * @mbggenerated
     */
    private String userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column XZ_User.User_Name
     *
     * @mbggenerated
     */
    private String userName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column XZ_User.User_PassWord
     *
     * @mbggenerated
     */
    private String userPassword;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column XZ_User.User_CreateTime
     *
     * @mbggenerated
     */
    private Date userCreatetime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column XZ_User.User_UpdateTime
     *
     * @mbggenerated
     */
    private Date userUpdatetime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column XZ_User.User_IsBan
     *
     * @mbggenerated
     */
    private Boolean userIsban;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column XZ_User.User_LoginName
     *
     * @mbggenerated
     */
    private String userLoginname;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table XZ_User
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column XZ_User.User_Id
     *
     * @return the value of XZ_User.User_Id
     *
     * @mbggenerated
     */
    public String getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column XZ_User.User_Id
     *
     * @param userId the value for XZ_User.User_Id
     *
     * @mbggenerated
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column XZ_User.User_Name
     *
     * @return the value of XZ_User.User_Name
     *
     * @mbggenerated
     */
    public String getUserName() {
        return userName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column XZ_User.User_Name
     *
     * @param userName the value for XZ_User.User_Name
     *
     * @mbggenerated
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column XZ_User.User_PassWord
     *
     * @return the value of XZ_User.User_PassWord
     *
     * @mbggenerated
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column XZ_User.User_PassWord
     *
     * @param userPassword the value for XZ_User.User_PassWord
     *
     * @mbggenerated
     */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword == null ? null : userPassword.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column XZ_User.User_CreateTime
     *
     * @return the value of XZ_User.User_CreateTime
     *
     * @mbggenerated
     */
    public Date getUserCreatetime() {
        return userCreatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column XZ_User.User_CreateTime
     *
     * @param userCreatetime the value for XZ_User.User_CreateTime
     *
     * @mbggenerated
     */
    public void setUserCreatetime(Date userCreatetime) {
        this.userCreatetime = userCreatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column XZ_User.User_UpdateTime
     *
     * @return the value of XZ_User.User_UpdateTime
     *
     * @mbggenerated
     */
    public Date getUserUpdatetime() {
        return userUpdatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column XZ_User.User_UpdateTime
     *
     * @param userUpdatetime the value for XZ_User.User_UpdateTime
     *
     * @mbggenerated
     */
    public void setUserUpdatetime(Date userUpdatetime) {
        this.userUpdatetime = userUpdatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column XZ_User.User_IsBan
     *
     * @return the value of XZ_User.User_IsBan
     *
     * @mbggenerated
     */
    public Boolean getUserIsban() {
        return userIsban;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column XZ_User.User_IsBan
     *
     * @param userIsban the value for XZ_User.User_IsBan
     *
     * @mbggenerated
     */
    public void setUserIsban(Boolean userIsban) {
        this.userIsban = userIsban;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column XZ_User.User_LoginName
     *
     * @return the value of XZ_User.User_LoginName
     *
     * @mbggenerated
     */
    public String getUserLoginname() {
        return userLoginname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column XZ_User.User_LoginName
     *
     * @param userLoginname the value for XZ_User.User_LoginName
     *
     * @mbggenerated
     */
    public void setUserLoginname(String userLoginname) {
        this.userLoginname = userLoginname == null ? null : userLoginname.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table XZ_User
     *
     * @mbggenerated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userId=").append(userId);
        sb.append(", userName=").append(userName);
        sb.append(", userPassword=").append(userPassword);
        sb.append(", userCreatetime=").append(userCreatetime);
        sb.append(", userUpdatetime=").append(userUpdatetime);
        sb.append(", userIsban=").append(userIsban);
        sb.append(", userLoginname=").append(userLoginname);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}