package com.lb.shiro;


import com.lb.entity.AuthorizationInfoUser;
import com.lb.entity.Role;
import com.lb.entity.User;
import com.lb.mapper.RoleMapper;
import com.lb.mapper.UserMapper;
import com.lb.service.AuthorizationInfoService;
import com.lb.service.Impl.TeacherServiceImpl;
import com.lb.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;;import java.util.HashSet;
import java.util.List;

/**
 * 自定义的UserRealm，用于做认证和授权
 * */
public class UserRealm extends AuthorizingRealm {
    //日志打印
    private static final Logger LOGGER = LoggerFactory.getLogger(UserRealm.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private AuthorizationInfoService infoService;
    /**
     * 授权
     * */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
      LOGGER.info("*****************用户登录授权开始**************************");
        SimpleAuthorizationInfo simpleInfo = new SimpleAuthorizationInfo();
        AuthorizationInfoUser userName = (AuthorizationInfoUser)principal.getPrimaryPrincipal();
        String name=userName.getUsername();
        //查询用户信息
        AuthorizationInfoUser infoUser=null;
        infoUser= infoService.queryUserForAuthorization(name);
        if (infoUser==null){
            throw  new RuntimeException("查询用户为空");
        }
        Role role = infoUser.getRole();
        //List<Role> roles = infoUser.getRoles();
        HashSet<String> roleName = new HashSet<>();
        roleName.add(role.getRoleName());
        //遍历角色
        /*for (Role role : roles) {
            roleName.add(role.getName());
            simpleInfo.addRoles(roleName);
            //Integer roleId = role.getId();
            //Role queryRole = roleMapper.queryRoleById(roleId);
        }*/
        simpleInfo.addRoles(roleName);
        return simpleInfo;
        //return null;
    }
    /**
     *
     * 登录认证
     * */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //获取用户输入的账号
        String username= (String)token.getPrincipal();
        System.out.println("用户的名字"+username);
        //查询用户是否存在
       // User user = userService.loginByUsername(username);
        AuthorizationInfoUser infoUser = infoService.queryUserForAuthorization(username);
        System.out.println("查询出来的用户名"+infoUser);
        //将查询出来的数据放到session中用于对登录进行判断
        if (infoUser==null){
            LOGGER.info("用户信息查询为空");
            //throw new AuthenticationException("用户不存在");
            return null;
        }
        SecurityUtils.getSubject().getSession().setAttribute("user",infoUser);
        System.out.println("用户密码="+infoUser.getPassword());
        return new SimpleAuthenticationInfo(infoUser,infoUser.getPassword(),getName());
    }
}
