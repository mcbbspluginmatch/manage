package cn.handy.constants;

import cn.handy.dao.message.IMessageService;
import cn.handy.dao.message.impl.MessageMySqlServiceImpl;
import cn.handy.dao.message.impl.MessageSqLiteServiceImpl;
import cn.handy.dao.pvp.IPvpService;
import cn.handy.dao.pvp.impl.PvpMySqlServiceImpl;
import cn.handy.dao.pvp.impl.PvpSqLiteServiceImpl;
import cn.handy.dao.user.IUserService;
import cn.handy.dao.user.impl.UserMySqlServiceImpl;
import cn.handy.dao.user.impl.UserSqLiteServiceImpl;
import cn.handy.utils.sql.MysqlManagerUtil;
import cn.handy.utils.sql.SqLiteManagerUtil;

/**
 * @author hanshuai
 * @Description: {}
 * @date 2019/6/25 11:04
 */
public class Beans {
    private static IUserService userService = null;
    private static IMessageService messageService = null;
    private static IPvpService pvpService = null;
    private static MysqlManagerUtil mysqlManagerUtil = null;
    private static SqLiteManagerUtil sqLiteManagerUtil = null;

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
        if (BaseConfigCache.isUseMySql) {
            if (userService == null) {
                userService = new UserMySqlServiceImpl();
            }
        } else {
            if (userService == null) {
                userService = new UserSqLiteServiceImpl();
            }
        }
        return userService;
    }

    public IPvpService getPvpService() {
        if (BaseConfigCache.isUseMySql) {
            if (pvpService == null) {
                pvpService = new PvpMySqlServiceImpl();
            }
        } else {
            if (pvpService == null) {
                pvpService = new PvpSqLiteServiceImpl();
            }
        }
        return pvpService;
    }

    public IMessageService getMessageService() {
        if (BaseConfigCache.isUseMySql) {
            if (messageService == null) {
                messageService = new MessageMySqlServiceImpl();
            }
        } else {
            if (messageService == null) {
                messageService = new MessageSqLiteServiceImpl();
            }
        }
        return messageService;
    }

    public MysqlManagerUtil getMysqlManagerUtil() {
        if (mysqlManagerUtil == null) {
            mysqlManagerUtil = new MysqlManagerUtil();
        }
        return mysqlManagerUtil;
    }

    public SqLiteManagerUtil getSqLiteManagerUtil() {
        if (sqLiteManagerUtil == null) {
            sqLiteManagerUtil = new SqLiteManagerUtil();
        }
        return sqLiteManagerUtil;
    }
}
