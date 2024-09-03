package bank;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
 class bankManagement { // these class provides all
							// bank method

	private static final int NULL = 0;

	static Connection con = connection.getConnection();
	static String sql = "";
	
	public static boolean createAccount(String name, int passCode) // create account function
	{
		try {
			// validation
			if (name.isEmpty() || passCode == NULL) {
				System.out.println("All Fields Are Required!");
				return false;
			}
			// query
			Statement st = con.createStatement();
			sql = "INSERT INTO customer(C_name, Balance, Pass_code) values('"
				+ name + "', 1000, " + passCode + ")";

			// Execution
			if (st.executeUpdate(sql) == 1) {
				System.out.println(name + ", Now You Can Login!");
				return true;
			}
		} catch (SQLIntegrityConstraintViolationException e) {
			System.out.println("Username Not Available!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean loginAccount(String name, int passCode) // login method
	{
		try {
			// validation
			if (name.equals(" ") || passCode == 0) {
				System.out.println("All Fields Are Required!");
				return false;
			}
			// query
			sql = "SELECT * FROM customer WHERE C_name = ? AND Pass_code = ?";
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, name);
			st.setInt(2, passCode);
			ResultSet rs = st.executeQuery();
			
			// Execution
			BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));

			if (rs.next()) {
				// after login menu driven interface method

				int ch = 5;
				int amt = 0;
				int senderAc = rs.getInt("Acc_no");
				int receiveAc;
				
				while (true) {
					try {
						System.out.println("Hello, " + rs.getString("C_name"));
						System.out.println("1)Transfer Money");
						System.out.println("2)View Balance");
						System.out.println("5)LogOut");

						System.out.print("Enter Choice: ");
						ch = Integer.parseInt(sc.readLine());
						
						if (ch == 1) {
							System.out.print("Enter Receiver A/c No: ");
							receiveAc = Integer.parseInt(sc.readLine());
							System.out.print("Enter Amount: ");
							amt = Integer.parseInt(sc.readLine());

							if (bankManagement.transferMoney(senderAc, receiveAc, amt)) {
								System.out.println("MSG : Money Sent Successfully!\n");
							} else {
								System.out.println("ERR : Transfer Failed!\n");
							}
						} else if (ch == 2) {
							bankManagement.getBalance(senderAc);
						} else if (ch == 5) {
							break;
						} else {
							System.out.println("Err : Enter Valid Input!\n");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else {
				return false;
			}
			return true;
		} catch (SQLIntegrityConstraintViolationException e) {
			System.out.println("Username Not Available!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static void getBalance(int acNo) // fetch balance method
	{
		try {
			// query
			sql = "SELECT * FROM customer WHERE Acc_no = ?";
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, acNo);
			ResultSet rs = st.executeQuery();

			System.out.println("-----------------------------------------------------------");
			System.out.printf("%12s %10s %10s\n", "Account No", "Name", "Balance");

			// Execution
			while (rs.next()) {
				System.out.printf("%12d %10s %10d.00\n",
								rs.getInt("Acc_no"),
								rs.getString("C_name"),
								Integer.parseInt(rs.getString("Balance")));
			}
			System.out.println("-----------------------------------------------------------\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static boolean transferMoney(int sender_ac, int receiver_ac, int amount)
		throws SQLException // transfer money method
	{
		// validation
		if (receiver_ac == NULL || amount == NULL) {
			System.out.println("All Fields Are Required!");
			return false;
		}
		try {
			con.setAutoCommit(false);
			
			// Check sender balance
			sql = "SELECT * FROM customer WHERE Acc_no = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, sender_ac);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				if (Integer.parseInt(rs.getString("Balance")) < amount) {
					System.out.println("Insufficient Balance!");
					return false;
				}
			}

			Statement st = con.createStatement();

			// debit
			con.setSavepoint();

			sql = "UPDATE customer SET Balance = Balance - ? WHERE Acc_no = ?";
			PreparedStatement debitStmt = con.prepareStatement(sql);
			debitStmt.setInt(1, amount);
			debitStmt.setInt(2, sender_ac);

			if (debitStmt.executeUpdate() == 1) {
				System.out.println("Amount Debited!");
			}

			// credit
			sql = "UPDATE customer SET Balance = Balance + ? WHERE Acc_no = ?";
			PreparedStatement creditStmt = con.prepareStatement(sql);
			creditStmt.setInt(1, amount);
			creditStmt.setInt(2, receiver_ac);
			creditStmt.executeUpdate();

			con.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			con.rollback();
		}
		return false;
	}
}
