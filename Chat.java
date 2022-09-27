import java.net.*;
import java.io.*;

public class Chat{
	private String ipAddress;
	private int localPortNo;
	private int remotePortNo;
	private String userName="Default User";
	private ServerSocket ss;	
	public Chat(String userName,String ip,String localPort,String remotePort)throws Exception{
		this.userName=userName;
		this.ipAddress = ip;
		this.localPortNo = Integer.parseInt(localPort);
		this.remotePortNo = Integer.parseInt(remotePort);
		ss = new ServerSocket(localPortNo);
	}

	public void lookForIncomingData()throws Exception{

		new PrintIncomingData(userName,ss).start();
		System.out.println("Wait till your Friend is online.");
		System.out.println("Press Enter if He/She is ready.");
		new BufferedReader(new InputStreamReader(System.in)).readLine();
		new SendDataToServer(userName,ipAddress,remotePortNo).sendToServer();
	}	

	

	public static void main(String[] arg){
		String ip="";
		String localPort ="";
		String remotePort ="";
		String userName="Not yet loged In !";
		try{
			System.out.println("Login Informations ...!");
			System.out.print("Please Enter Username : ");
			userName=new BufferedReader(new InputStreamReader(System.in)).readLine();
			System.out.print("Please Enter Remote IP Number : ");
			ip=new BufferedReader(new InputStreamReader(System.in)).readLine();
			System.out.print("Please Enter Local Port Number : ");
			localPort=new BufferedReader(new InputStreamReader(System.in)).readLine();
			System.out.print("Please Enter Remote Port number : ");
			remotePort=new BufferedReader(new InputStreamReader(System.in)).readLine();

			Chat c = new Chat(userName,ip,localPort,remotePort);
			c.lookForIncomingData();

		}catch(Exception e){System.out.println(e);}
	}

}

class PrintIncomingData extends Thread{
	private ServerSocket serSoc=null;
	private Socket soc = null;
	private String s = "Default String";
	private String userName= "Not yet loged In !";

	public PrintIncomingData(String userName,ServerSocket serSoc)throws Exception{
		this.serSoc=serSoc;
		this.userName=userName;
	}
		
	public void run(){
		waitForIncomingData();
	}
	private void waitForIncomingData(){
		try{
		soc =  serSoc.accept();
		while(true){
			try{
				s=(String) new  ObjectInputStream(soc.getInputStream()).readObject();
				if(s.substring(s.length()-4).equals("exit")){
					soc.close();
					break;
				}else{
					System.out.println();
					System.out.println(s);
				}
			}catch(Exception e ){System.out.println(e);}
		}
		}catch(Exception e){System.out.println(e);}
	}
	
}

class SendDataToServer{
	private Socket soc=null;
	private String userName="Not yet loged In !";
	private String s="Default String";
	private ObjectOutputStream output;

	public SendDataToServer(String userName,String ipAddress,int remortPortNo){
		try{
		//IF THE IP ADDRESS IS WRONG NO ERROR SHOWS :I THINK YOU NEED TO CATCH IT WITH RIGHT EXCEPTION
		soc= new Socket(ipAddress,remortPortNo);
		this.userName=userName;
		}catch(Exception e){System.out.println(e);}
	}
	
	public void sendToServer(){
		try{
		while(true){
			System.out.print(userName + " : ");
			s= userName + " : " + new BufferedReader(new InputStreamReader(System.in)).readLine();
			output = new  ObjectOutputStream(soc.getOutputStream());
			output.flush();
			output.writeObject(s);
			output.flush();
		}
		}catch(Exception e){System.out.println(e);}
	}
}
