import java.net.*;
import java.io.*;

class SendDataToServer{
	private Socket soc=null;
	private String userName="Not yet loged In !";
	String s=" ";
	private ObjectOutputStream output;

	public SendDataToServer(String userName,String ipAddress,int remortPortNo){
		try{
		//IF THE IP ADDRESS IS WRONG NO ERROR SHOWS
		soc= new Socket(ipAddress,remortPortNo);
		this.userName=userName;
		}catch(Exception e){System.out.println(e);}
	}

	public void sendToServer(){
		try{
		while(true){
			System.out.print(userName + " : ");
			s= new BufferedReader(new InputStreamReader(System.in)).readLine();
			output = new ObjectOutputStream(soc.getOutputStream());
			output.flush();
			output.writeObject(s);
			
		}
		}catch(Exception e){System.out.println(e);}
	}

	public static void main(String[] arg){

		SendDataToServer sts = new SendDataToServer("client","127.0.0.1",5555);
		sts.sendToServer();
	}
}