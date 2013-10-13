package sources;



import java.util.*;
import java.sql.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;

public class DBOperate
{
	//声明驱动类字符串
	private static String driver="sun.jdbc.odbc.JdbcOdbcDriver";
	//声明数据库连接字符串
	private static String url="jdbc:odbc:driver={Microsoft Access Driver (*.mdb)};DBQ=DB\\LinkMan.ab";
	//声明数据库连接对象引用
	private static Connection con=null;
	//声明语句对象引用
	private static Statement stat=null;
	//声明预编译语句对象引用
	private static PreparedStatement psInsert=null;
	//声明结果集对象引用
	private static ResultSet rs=null;

//	***************** 创建和关闭数据库连接方法*****************
	
	//得到数据库连接(创建连接对象)的方法
	private static Connection getConnection()
	{
		try
		{
			//加载驱动程序
			Class.forName(driver);
			//得到数据库连接(创建连接)
			con=DriverManager.getConnection(url);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		//返回连接对象
		return con;
	}
	
	//关闭数据库连接的方法
	public static void closeCon()
	{
		try
		{
			if(rs!=null)
			{
				//如果结果集不为空，则关闭结果集并赋值null
				rs.close();
				rs=null;
			}
			if(stat!=null)
			{
				//如果语句对象不为空，则关闭语句对象并赋值null
				stat.close();
				stat=null;
			}
			if(con!=null)
			{
				//如果连接不为空，则关闭连接并赋值null
				con.close();
				con=null;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
//******************** LoginFrame会用到的方法 *****************
	
	//判断用户登录情况的方法
	public static boolean check(String user,String pwd)
	{
		//定义返回布尔类型变量
		boolean flag=false;
		//尝试捕获异常
		try
		{
			//得到数据库（创建）连接
			con=DBOperate.getConnection();
			//创建SQL语句对象
			stat=con.createStatement();
			//执行查询，得到结果集
			rs=stat.executeQuery("SELECT Password FROM User WHERE UserName='"+user+"'");
			
			//结果集游标下移一位
			rs.next();
			//查询返回的密码赋值给变量
			String spwd=rs.getString(1);   //这里的1是指字段从0排序，1就代表第二个字段
			//判断密码是否正确
			if(spwd.equals(pwd))
			{
				//返回密码正确
				flag=true;
			}
			else
			{
				//返回密码错误
				flag=false;
			}
		}
		catch(Exception e)
		{
			//打印输出异常信息
			e.printStackTrace();
			//如果有任何异常发生，则都返回false
			flag=false;
		}
		finally
		{
			//关闭数据库连接
			DBOperate.closeCon();
		}
		//返回最终查询结果
		return flag;
	}
	
	//对于注册用户判断是否已经存在用户
	public static boolean isExist(String sql)
	{
		//定义返回布尔类型变量
		boolean flag=false;
		//尝试捕获异常
		try
		{
			//得到数据库（创建）连接
			con=DBOperate.getConnection();
			//创建SQL语句对象
			stat=con.createStatement();
			//执行查询，得到结果集
			rs=stat.executeQuery(sql);
			
			//如果存在，则返回false
			if(rs.next())
			{
				flag=true;
			}
		}
		catch(Exception e)
		{
			//打印输出异常信息
			e.printStackTrace();
			//如果有任何异常发生，则都返回false
			flag=false;
		}
		finally
		{
			//关闭数据库连接
			DBOperate.closeCon();
		}
		//返回最终查询结果
		return flag;
	}
	
	//执行注册的方法
	public static int update(String sql)
	{ 
		//定义查询到符合条件的纪录条数变量
		int count=0;
		//尝试捕获异常
		try
		{
			//得到数据库（创建）连接
			con=DBOperate.getConnection();
			//创建SQL语句对象
			stat=con.createStatement();
			//执行查询，得到结果集
			count=stat.executeUpdate(sql);
		}
		catch(Exception e)
		{
			//打印输出异常信息
			e.printStackTrace();
			//注册失败，返回-1
			count=-1;
		}
		finally
		{
			//关闭数据库连接
			DBOperate.closeCon();
		}
		//返回最终查询结果
		return count;
	}

	
//	****************** MainFrame会用到的方法 ******************

	//根据条件得到节点名称列表方法
	public static Vector<String> getNode(String user,String condition)
	{
		//根据登陆用户和条件得到节点名称
		Vector<String> node=new Vector<String>();
		//拆分条件的正则表达式
		String patternStr=";";
		//按条件将condition字符串进行拆分
		String[] scon=condition.split(patternStr);
		try
		{
			//得到数据库连接
			con=getConnection();
			//创建语句对象
			stat=con.createStatement();
			if(scon.length==1&&scon[0].equals("uid"))
			{
				//得到当前用户下有多少个分组
				rs=stat.executeQuery("SELECT DISTINCT pgroup FROM ContactInfo WHERE UserName='"+user.trim()+"'");
			}
			else if(scon.length==1)
			{
				//得到当前联系人下相册里照片名列表
				rs=stat.executeQuery("SELECT photoname FROM Photo WHERE pid = "+
				"(SELECT pid FROM ContactInfo WHERE UserName='"+user.trim()+"'AND pname='"+scon[0].trim()+"')");
			}
			else if(scon.length==2)
			{
				//得到分组里的联系人姓名列表 			
				rs=stat.executeQuery("SELECT pname FROM ContactInfo WHERE UserName='"
									 +user.trim()+"'AND pgroup='"+scon[1].trim()+"'");
			}
			while(rs.next())
			{
				//组织成Vector返回
			    String s=rs.getString(1);
			    node.add(s);
			} 				
		}
		catch(Exception e)
		{
			//打印异常信息
			e.printStackTrace();
		}
		finally
		{
			//关闭数据库连接
			DBOperate.closeCon();
		}
		//返回结果列表
		return node;
	}

	public static int delUser(String UserName)//删除用户
	{
		int count=0;//设置返回值
		Vector<String> vpid=new Vector<String>();//存放pid的集合 一个用户对应多个联系人
		try
		{
			con=DBOperate.getConnection();//得到数据库连接
			stat=con.createStatement();//创建语句对象
			rs=stat.executeQuery("SELECT pid FROM ContactInfo WHERE UserName='"+UserName+"'");//得到每个联系人的ID
			while(rs.next())
			{
				String pid=rs.getString(1);//得到用户下的pid				
				vpid.add(pid);//添加进联系人集合
			}
			stat=con.createStatement();//重新创建语句对象
			for(String s:vpid)
			{//循环删除每个联系人的相册
				stat.executeUpdate("DELETE FROM photo WHERE pid='"+s+"'");
			}
			//在联系人ContactInfo表中删除每个联系人			
			count=stat.executeUpdate("DELETE FROM ContactInfo WHERE UserName='"+UserName+"'");
			//在用户表中删除用户
			stat.executeUpdate("DELETE FROM user WHERE UserName='"+UserName+"'");			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally{DBOperate.closeCon();}//关闭数据库连接
		return count;//返回删除了多少个联系人
	}	
	

//	********************分组**************************
		public static int delGroup(String user,String group)
		{
			int count=0;		
			Vector<String> vpid=new Vector<String>();//一个分组对应多个联系人
			try
			{
				con=getConnection();//得到数据库连接
				stat=con.createStatement();//创建语句对象
				rs=stat.executeQuery("SELECT pid FROM ContactInfo WHERE pgroup='"+group+"'"
										+"AND UserName='"+user+"'");//从数据库中搜索到group组里的pid
				while(rs.next())
				{
					String pid=rs.getString(1);//得到用户下的pid循环删除photo表中pid下的照片			
					vpid.add(pid);//添加该分组下联系人名称到集合
				}
				stat=con.createStatement();//创建语句对象
				for(String s:vpid)
				{//循环删除每个联系人的相册
					stat.executeUpdate("DELETE FROM photo WHERE pid='"+s+"'");
				}
				//在联系人ContactInfo表中删除每个联系人
				count=stat.executeUpdate("DELETE FROM ContactInfo WHERE pgroup='"+group+"'");
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return count;//返回删除联系人数目
		}
//	*******************联系人***********************
		public static String insertPerson(String UserName,Vector<String> pInfo)
		{
			String isPathNull="isNotNull";//传过来的图像是不是合法，默认不为空
			try{
				con=getConnection();//得到数据库连接
				if(pInfo.get(9).equals("")||pInfo.get(9)==null)
				{//照片路径为空，则不插入图像
					psInsert=con.prepareStatement("INSERT into ContactInfo(pid,pname,pgender,page,pnumber,"+
		 										"pemail,pgroup,ppostalcode,padress,UserName)"+
		 										"VALUES(?,?,?,?,?,?,?,?,?,?)");
		 		}
				else
				{//照片路径不为空，则插入图像
					psInsert=con.prepareStatement("INSERT into ContactInfo(pid,pname,pgender,page,pnumber,"+
		 								"pemail,pgroup,ppostalcode,padress,UserName,pphoto)"+
		 								"VALUES(?,?,?,?,?,?,?,?,?,?,?)" );
		 			File f=new File(pInfo.get(9));//获取选取的图片文件
		 			byte[] b=new byte[(int)f.length()];//创建存储图片数据的数组
		 			FileInputStream fin=new FileInputStream(f);
					fin.read(b);fin.close();//读取文件存于byte数组中并关闭输入流
		 			psInsert.setBytes(11,b);//设置pphoto参数的数据
				}
				for(int i=0;i<9;i++)
				{//设置公共信息
					psInsert.setString(i+1,pInfo.get(i));
				}
				psInsert.setString(10,UserName);//所属用户			
				psInsert.execute();psInsert.close();//执行更新并关闭语句
			}
			catch(FileNotFoundException fnfe){isPathNull="isNull";}//图片路径不对
			catch(Exception e){e.printStackTrace();}
			finally{DBOperate.closeCon();}//关闭数据库连接
			return isPathNull;
		}
		public static String updatePerson(String UserName,Vector<String> pInfo)
{
			String isPathNull="isNotNull";//传过来的path是不是合法
			try{
				con=getConnection();
				if(pInfo.get(9).equals("")||pInfo.get(9)==null)
				{//更新时候，如果照片路径为空，则不更新图像
psInsert=con.prepareStatement("update ContactInfo set pname=?,pgender=?,page=?,pnumber=?,"+
"pemail=?,pgroup=?,ppostalcode=?,padress=?,UserName=? WHERE pid='"+pInfo.get(0).trim()+"'");
				}
				else
				{//如果照片路径不为空，则更新图像
psInsert=con.prepareStatement("update ContactInfo set pname=?,pgender=?,page=?,pnumber=?,"+
"pemail=?,pgroup=?,ppostalcode=?,padress=?,UserName=?,pphoto=? WHERE pid='"+pInfo.get(0).trim()+"'");
					File f=new File(pInfo.get(9));//获取选取的图片文件
		 			byte[] b=new byte[(int)f.length()];//创建存储图片数据的数组
		 			FileInputStream fin=new FileInputStream(f);
					fin.read(b);fin.close();//读取文件存于byte数组中并关闭输入流					
		 			psInsert.setBytes(10,b);	 			
				}
				for(int i=1;i<9;i++){//设置公共的信息部分
					psInsert.setString(i,pInfo.get(i));
				}			
				psInsert.setString(9,UserName);//所属用户	 			
				psInsert.execute();psInsert.close();//执行更新并关闭语句
			}
			catch(FileNotFoundException fnfe){isPathNull="isNull";}//路径不合法	
			catch(Exception e){e.printStackTrace();}
			finally{DBOperate.closeCon();}//关闭连接
			return isPathNull;
		}
		public static Vector<String> getPerInfo(String sql)//得到联系人信息
		{
			Vector<String> pInfo=new Vector<String>();
			try
			{
				con=getConnection();//得到数据库连接
				stat=con.createStatement();//创建语句对象
				rs=stat.executeQuery(sql);//执行查询 			
				while(rs.next())
				{	
					for(int i=1;i<10;i++)
					{ 	
				    	pInfo.add(rs.getString(i));//将联系人信息添加到返回向量
					} 			    
				} 					
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			finally{DBOperate.closeCon();}//关闭数据库连接
			return pInfo;//返回信息集合
		}
		public static Image getPic(String sql)
		{
			Image i=null;//声明Image对象引用
			try
			{
				con=getConnection();//得到数据库连接
				stat=con.createStatement();//创建语句对象
				rs=stat.executeQuery(sql);//执行SQL语句
				while(rs.next())
				{
					byte[] buff=rs.getBytes(1);//得到图像数据
					if(buff!=null)//如果数据存在
					{
						i=(new ImageIcon(buff)).getImage();//转换成ImageIcon对象
					} 				
				}		
			}
			catch(Exception e)
			{
				e.printStackTrace();//打印异常信息
			}
			finally
			{//关闭数据库连接
				DBOperate.closeCon();
			}
			return i;
		}
//	*****************************照  片****************************
		public static int insertPic(String path,String pid)
		{//flag=0表示上传成功 1表示找不到文件 2表示文件已经存在
			int flag=0;
			File f=new File(path);//获取选取的图片文件	
			try
			{
				con=getConnection();//得到数据库连接
				psInsert=con.prepareStatement("INSERT into photo VALUES(?,?,?)");
				byte[] b=new byte[(int)f.length()];//创建存储照片数据的数组
				FileInputStream fin=new FileInputStream(f);//
				fin.read(b);fin.close();//读取文件存于byte数组中并关闭输入流			
				psInsert.setString(1,pid);//设置此照片所属联系人
				psInsert.setString(2,f.getName());//设置此照片名称
				psInsert.setBytes(3,b);//设置照片数据
				psInsert.executeUpdate();psInsert.close();//执行更新并关闭语句							
			}
			catch(FileNotFoundException fnfe){flag=1;}//找不到照片文件
			catch(SQLException sqle){flag=2;}//文件已经存在
			catch(Exception e){e.printStackTrace();}
			finally{DBOperate.closeCon();}//关闭数据库连接
			return flag;
		}
		
		public static void main(String[] args)
		{
			System.out.println(DBOperate.delUser("aa"));
		}
}