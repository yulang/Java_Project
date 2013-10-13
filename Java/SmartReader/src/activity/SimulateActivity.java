package activity;

import java.util.concurrent.TimeUnit;

/*
 *用于调试文件读取操作地类
 * 2012.12.4
 * @author 余浪
 * student number 11061105
 */
public class SimulateActivity implements Runnable{
	private volatile int current;
	
	private int target;
	//构造方法
	public SimulateActivity(int t){
		current = 0;
		target = t;
	}
	
	public int getTarget(){
		return target;
	}
	
	public int getCurrent(){
		return current;
	}
	
	public void run(){
		try{
			while(current<target){
//				Thread.sleep(100);
				TimeUnit.MILLISECONDS.sleep(100);
				current++;
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}

}
