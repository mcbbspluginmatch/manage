package cn.handy.utils;

import cn.handy.dao.message.IMessageService;
import cn.handy.dao.message.impl.MessageServiceImpl;
import cn.handy.dao.pvp.IPvpService;
import cn.handy.dao.pvp.impl.PvpServiceImpl;
import cn.handy.dao.user.IUserService;
import cn.handy.dao.user.impl.UserServiceImpl;
import cn.handy.utils.sql.SqlManagerUtil;

/**
 * @author hanshuai
 * @Description: {组件化bean}
 * @date 2019/6/25 11:04
 */
public class Beans {
    private static IUserService userService = null;
    private static IMessageService messageService = null;
    private static IPvpService pvpService = null;
    private static SqlManagerUtil sqlManagerUtil = null;

    private Beans() {
    }

    private static Beans beans = null;

    public static Beans getBeans() {
        if (beans == null) {
            beans = new Beans();
        }
        return beans;
    }

    public IUserService getUserService() {
        if (userService == null) {
            userService = new UserServiceImpl();
        }
        return userService;
    }

    public IPvpService getPvpService() {
        if (pvpService == null) {
            pvpService = new PvpServiceImpl();
        }
        return pvpService;
    }

    public IMessageService getMessageService() {
        if (messageService == null) {
            messageService = new MessageServiceImpl();
        }
        return messageService;
    }

    public SqlManagerUtil getSqlManagerUtil() {
        if (sqlManagerUtil == null) {
            sqlManagerUtil = new SqlManagerUtil();
        }
        return sqlManagerUtil;
    }
}
