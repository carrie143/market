///*
// * 文件名：LogUtil.java 版权：Copyright by www.guoren.com 描述： 修改人：zhengminghai 修改时间：2015年12月17日 跟踪单号：
// * 修改单号： 修改内容：
// */
//
//package com.gop.util;
//
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//// import org.apache.log4j.Logger;
//// import org.apache.log4j.Priority;
//
//
///**
// * 日志帮助类。
// * 
// * @author zhengminghai
// * @version 2015年12月17日
// * @see LogFacade
// * @since
// */
//public class LogFacade
//{
//
//    private Logger logger;
//
//    public void debug(String arg0, Object arg1, Object arg2)
//    {
//        logger.debug(arg0, arg1, arg2);
//    }
//
//    public void debug(String arg0, Object... arg1)
//    {
//        logger.debug(arg0, arg1);
//    }
//
//    public void debug(String arg0, Object arg1)
//    {
//        logger.debug(arg0, arg1);
//    }
//
//    public void debug(String arg0, Throwable arg1)
//    {
//        logger.debug(arg0, arg1);
//    }
//
//    public void debug(String arg0)
//    {
//        logger.debug(arg0);
//    }
//
//    public void error(String arg0, Object arg1, Object arg2)
//    {
//        logger.error(arg0, arg1, arg2);
//    }
//
//    public void error(String arg0, Object... arg1)
//    {
//        logger.error(arg0, arg1);
//    }
//
//    public void error(String arg0, Object arg1)
//    {
//        logger.error(arg0, arg1);
//    }
//
//    public void error(String arg0, Throwable arg1)
//    {
//        logger.error(arg0, arg1);
//    }
//
//    public void error(String arg0)
//    {
//        logger.error(arg0);
//    }
//
//    public void info(String arg0, Object arg1, Object arg2)
//    {
//        logger.info(arg0, arg1, arg2);
//    }
//
//    public void info(String arg0, Object... arg1)
//    {
//        logger.info(arg0, arg1);
//    }
//
//    public void info(String arg0, Object arg1)
//    {
//        logger.info(arg0, arg1);
//    }
//
//    public void info(String arg0, Throwable arg1)
//    {
//        logger.info(arg0, arg1);
//    }
//
//    public void info(String arg0)
//    {
//        logger.info(arg0);
//    }
//
//    public void trace(String arg0, Object arg1, Object arg2)
//    {
//        logger.trace(arg0, arg1, arg2);
//    }
//
//    public void trace(String arg0, Object... arg1)
//    {
//        logger.trace(arg0, arg1);
//    }
//
//    public void trace(String arg0, Object arg1)
//    {
//        logger.trace(arg0, arg1);
//    }
//
//    public void trace(String arg0, Throwable arg1)
//    {
//        logger.trace(arg0, arg1);
//    }
//
//    public void trace(String arg0)
//    {
//        logger.trace(arg0);
//    }
//
//    public void warn(String arg0, Object arg1, Object arg2)
//    {
//        logger.warn(arg0, arg1, arg2);
//    }
//
//    public void warn(String arg0, Object... arg1)
//    {
//        logger.warn(arg0, arg1);
//    }
//
//    public void warn(String arg0, Object arg1)
//    {
//        logger.warn(arg0, arg1);
//    }
//
//    public void warn(String arg0, Throwable arg1)
//    {
//        logger.warn(arg0, arg1);
//    }
//
//    public void warn(String arg0)
//    {
//        logger.warn(arg0);
//    }
//
//    private LogFacade(Class<?> clazz)
//    {
//        logger = LoggerFactory.getLogger(clazz);
//    }
//
//    private LogFacade(String clazz)
//    {
//        logger = LoggerFactory.getLogger(clazz);
//    }
//
//    public static LogFacade getLogger(Class<?> clazz)
//    {
//        return new LogFacade(clazz);
//    }
//
//    public static LogFacade getLogger(String clazz)
//    {
//        return new LogFacade(clazz);
//    }
//
//    // /**
//    // * Description: <br> 修改人：zhengminghai
//    // *
//    // * @param loger
//    // * @param fmt
//    // * 格式化，占位符为{}
//    // * @param strings
//    // * @see
//    // */
//    // public final void info(String fmt, Object... objs)
//    // {
//    // log(Priority.INFO, fmt, objs);
//    // }
//    //
//    // public final void warn(String fmt, Object... objs)
//    // {
//    // log(Priority.WARN, fmt, objs);
//    // }
//    //
//    // /**
//    // * Description: <br> 修改人：zhengminghai
//    // *
//    // * @param loger
//    // * @param fmt
//    // * 格式化，占位符为{}
//    // * @param strings
//    // * @see
//    // */
//    // public final void debug(String fmt, Object... objs)
//    // {
//    // log(Priority.DEBUG, fmt, objs);
//    // }
//    //
//    // /**
//    // * Description: <br> 修改人：zhengminghai
//    // *
//    // * @param loger
//    // * @param fmt
//    // * 格式化，占位符为{}
//    // * @param strings
//    // * @see
//    // */
//    // public final void error(String fmt, Object... objs)
//    // {
//    // log(Priority.ERROR, fmt, objs);
//    // }
//    //
//    // /**
//    // * Description: <br> 修改人：zhengminghai
//    // *
//    // * @param logger
//    // * @param fmt
//    // * 格式化，占位符为{}
//    // * @param strings
//    // * @see
//    // */
//    // public final void log(Priority level, String fmt, Object... objs)
//    // {
//    // Throwable t = null;
//    // Object[] fmtObjs = null;
//    // if (objs != null && objs.length > 0)
//    // {
//    // Object o = objs[objs.length - 1];
//    // if (o instanceof Throwable)
//    // {
//    // t = (Throwable)o;
//    // fmtObjs = new Object[objs.length - 1];
//    // System.arraycopy(objs, 0, fmtObjs, 0, objs.length - 1);
//    // }
//    // else
//    // {
//    // fmtObjs = objs;
//    // }
//    // }
//    // fmt = fmt.replace("{}", "%s");
//    // if (t != null)
//    // {
//    // if (level == Priority.DEBUG)
//    // {
//    // if (logger.isEnabledFor(level))
//    // {
//    // logger.debug(String.format(fmt, fmtObjs), t);
//    // }
//    // }
//    // else if (level == Priority.ERROR)
//    // {
//    // if (logger.isEnabledFor(level))
//    // {
//    // logger.error(String.format(fmt, fmtObjs), t);
//    // }
//    // }
//    // else if (level == Priority.INFO)
//    // {
//    // if (logger.isEnabledFor(level))
//    // {
//    // logger.info(String.format(fmt, fmtObjs), t);
//    // }
//    // }
//    // }
//    // else
//    // {
//    // if (level == Priority.DEBUG)
//    // {
//    // if (logger.isEnabledFor(level))
//    // {
//    // logger.debug(String.format(fmt, fmtObjs));
//    // }
//    // }
//    // else if (level == Priority.ERROR)
//    // {
//    // if (logger.isEnabledFor(level))
//    // {
//    // logger.error(String.format(fmt, fmtObjs));
//    // }
//    // }
//    // else if (level == Priority.INFO)
//    // {
//    // if (logger.isEnabledFor(level))
//    // {
//    // logger.info(String.format(fmt, fmtObjs));
//    // }
//    // }
//    // }
//    // }
//
//    public static void main(String[] args)
//    {
//
//    }
//
//}
