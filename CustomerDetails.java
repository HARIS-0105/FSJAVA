package ATM;
import java.util.*;
import java.io.IOException;
import java.sql.*;
public class CustomerDetails
{
   public static int getPin(int accno) throws SQLException
   {
	   Connection conn=DButil.createConnection();
	   Statement stmt=conn.createStatement();
	   ResultSet rs=stmt.executeQuery("Select Pin from Users where Acc_No="+accno);
	   int pin=0;
	   while (rs.next())
	   {
	      pin=rs.getInt(1);
	   }
	   conn.close();
	   return pin;
   }
   public static int getBalance(int accno) throws SQLException{
	   Connection conn=DButil.createConnection();
	   Statement stmt=conn.createStatement();
	   ResultSet rs=stmt.executeQuery("Select Balance from Users where Acc_No="+accno);
	   int bal=0;
	   while(rs.next()) {
		   bal=rs.getInt(1);
	   }
	   conn.close();
	   return bal;
   }
   public static void withdraw(int accno,int amount) throws SQLException{
	   Connection conn=DButil.createConnection();
	   Statement stmt=conn.createStatement();
	   stmt.executeUpdate("Update Users Set Balance=" +(getBalance(accno)-amount)+ " where Acc_No="+accno);
       conn.close();
   }
   public static void Transfer(int accno1,int accno2,int amount) throws SQLException{
	   Connection conn=DButil.createConnection();
	   Statement stmt=conn.createStatement();
	   stmt.executeUpdate("Update Users Set Balance=" +(getBalance(accno1)-amount)+ " where Acc_No="+accno1);
	   stmt.executeUpdate("Update Users Set Balance=" +(getBalance(accno2)+amount)+ " where Acc_No="+accno2);
       conn.close();
   }
   public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException
   {
	   Scanner sc=new Scanner(System.in);
	   System.out.println("Enter Account Number:");
	   int acc_no=sc.nextInt();
	   System.out.println("Enter Pin Number:");
	   int inp_pin=sc.nextInt();
	   if(inp_pin==getPin(acc_no))
	   {
		   System.out.println("1:Check Balance");
		   System.out.println("2:Withdraw Amount");
		   System.out.println("3:Transfer Amount");
		   System.out.println("4:Check ATM Balance");
		   System.out.println("Enter Your Choice:");
		   int choice=sc.nextInt();
		   switch(choice) 
		   {
			   case 1:
				   System.out.println("Account balance:"+getBalance(acc_no));
				   break;
			   case 2:
				   System.out.println("Enter the amount to withdraw:");
				   int amount=sc.nextInt();
				   if(amount>getBalance(acc_no) || ATMMachine.getATMBalance()<amount)
				   {
					   System.out.println("Insufficient Balance");
				       break;
				   }
				   if(amount>10000 || amount<100 || amount%100!=0) {
					   System.out.println("Invalid Amount");
				       break;
				   }
				   ATMMachine.reduceATM(amount);
				   withdraw(acc_no,amount);
				   System.out.println("Bill");
				   System.out.println("Withdraw Amount:"+amount);
				   break;
			   case 3:
				   System.out.println("Enter the amount to be transferred:");
				   int tamount=sc.nextInt();
				   if(tamount>getBalance(acc_no))
				   {
					   System.out.println("Insufficient Balance!");
				       break;
				   }
				   System.out.println("Enter Receiver Account Number:");
				   int Racc_no=sc.nextInt();
				   Transfer(acc_no,Racc_no,tamount);
				   System.out.println("Transaction Successful");
				   System.out.println(tamount+" Transeferred to "+Racc_no);
				   break;
			   case 4:
				   ATMMachine.displayBalance();
				   break;
		   }
	   }
	   else
		  System.out.println("You've Entered Wrong Pin");
	   sc.close();
   }
}

