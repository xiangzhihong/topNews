package xzh.com.wangyinews.utils.sql;

import xzh.com.wangyinews.WYApplication;
import xzh.com.wangyinews.db.SQLHelper;

/**
 * Created by xiangzhihong on 2016/4/27 on 19:25.
 */
public class SqlLiteHelper {
    private  static SQLHelper sqlHelper;
    public static SqlLiteHelper sqlLiteHelper=null;

    public static SqlLiteHelper getInstance() {
      if (sqlLiteHelper==null){
          sqlLiteHelper=new SqlLiteHelper();
      }
      return sqlLiteHelper;
    }

    /** 获取数据库Helper */
    public static SQLHelper getSQLHelper() {
        if (sqlHelper == null)
            sqlHelper = new SQLHelper(WYApplication.getInstance());
        return sqlHelper;
    }

    public static void closeSql(){
        if (sqlHelper != null)
            sqlHelper.close();
    }
}
