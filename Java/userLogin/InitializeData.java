package StudentManegeSystem.userLogin;
import java.util.*;
import java.io.*;
public class InitializeData {
	private ArrayList< Persons> person=new ArrayList<Persons>();
	private String fileName="person.dat";
	private File file=new File(fileName);
	public ArrayList<Persons> readData() throws Exception{
		String s;
		try{
			if(!file.exists()) 
				file.createNewFile();
			BufferedReader in=new BufferedReader(new FileReader(fileName));
			while((s=in.readLine())!=null){
				List<String> a=Arrays.asList(s.split(" "));
				Persons person1=new Persons();
				if(a.get(0).equals("")) a.remove(0);
				person1.setUserName(a.get(0));
				person1.setName(a.get(1));
				person1.setPasswd(a.get(2));
				
				person1.setClassName(a.get(3));
				person1.setChinese(Double.parseDouble(a.get(4)));
				person1.setMath(Double.parseDouble(a.get(5)));
				person1.setEnglish(Double.parseDouble(a.get(6)));
				person.add(person1);
			}
			in.close();
		}
		catch(FileNotFoundException e){
			File file=new File(fileName);
			file.createNewFile();
			person=new ArrayList<Persons>();
			
		}
		finally{
		
		return person;
		}
	}
	public void Store(ArrayList<Persons> person) throws IOException{
		PrintWriter out=new PrintWriter(fileName);
		for(Persons p:person){
			out.println(p.getUsername()+" "+p.getName()+" "+String.valueOf(p.getPasswd())+" "
				+p.getClassName()+" "+p.getChinese()+" "+
					p.getMath()+" "+p.getEnglish());
			System.out.println(p.getUsername()+" "+p.getName()+" "+String.valueOf(p.getPasswd())+" "
				+p.getClassName()+" "+p.getChinese()+" "+
					p.getMath()+" "+p.getEnglish());
		}
		out.close();
	}
	private ArrayList<DetailedStudentInfo> person1= new ArrayList<DetailedStudentInfo>();
	public ArrayList<DetailedStudentInfo> readDetailedData(){
		String fileName="DeTailedStudentInfo.dat";
		
		String s;
		try{
			if(!file.exists()) 
				file.createNewFile();
			BufferedReader in=new BufferedReader(new FileReader(fileName));
			while((s=in.readLine())!=null){
				List<String> a=Arrays.asList(s.split(" "));
				DetailedStudentInfo person11=new DetailedStudentInfo();
				if(a.get(0).equals("")) a.remove(0);
				person11.setUserName(a.get(0));
				person11.setPasswd(a.get(1));
				person11.setUserRealName(a.get(2));
				person11.setGender(a.get(3));
				person11.setStudentAge(a.get(4));
				person11.setStudentNumber(a.get(5));
				person11.setLivingAdress(a.get(6));
				person11.setStudentPhoneNumber(a.get(7));
				person1.add(person11);
			}
			in.close();
		}
		catch(FileNotFoundException e){
			File file=new File(fileName);
			file.createNewFile();	
		}
		finally{
		return person1;
		}	
	}
	public void StoreDetailed(ArrayList<DetailedStudentInfo> person) throws IOException{
		String fileName="DeTailedStudentInfo.dat";
		File file =new File(fileName);
		PrintWriter out=new PrintWriter(fileName);
		if(!file.exists())
			file.createNewFile();
		for(DetailedStudentInfo p:person)
			out.println(p);
		out.close();
	}
	public void replaceDetailedInfo(ArrayList<DetailedStudentInfo> p1, DetailedStudentInfo user,String username) throws IOException{
		String fileName="DeTailedStudentInfo.dat";
		File file =new File(fileName);
		PrintWriter out=new PrintWriter(fileName);
		if(!file.exists())
			file.createNewFile();
	
			
		for(DetailedStudentInfo p2:p1){
			if(p2.getUserName().equals(username)){
				continue;
			}
			out.println(p2);
		}
		out.println(user);
		out.close();
	}
	
	
}
