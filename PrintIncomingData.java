import java.net.*;
import java.io.*;

class PrintIncomingData extends Thread{
	private ServerSocket soc=null;
	private Socket sc = null;
	private String s = " ";
	private String userName= "Not yet loged In !";
	private ObjectInputStream input;
	public PrintIncomingData(String userName,ServerSocket soc)throws Exception{
		this.soc=soc;
		this.userName=userName;
	}
		
	public void run(){
		printToCommand();
	}
	private void printToCommand(){
		try{
		sc= soc.accept();
		while(true){
			try{
			input = new ObjectInputStream(sc.getInputStream());
			System.out.println( userName + " : " + (String)input.readObject());
			}catch(Exception e){System.out.println(e);}
			
		}
		}catch(Exception e ){System.out.println(e);}
	}
	public static void main(String[] arg){
		try{
		ServerSocket soc= new ServerSocket(5555);
		PrintIncomingData pid = new PrintIncomingData("User",soc);
		pid.start();
		}catch(Exception e){System.out.println(e);}

	}
}
