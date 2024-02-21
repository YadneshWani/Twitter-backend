package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.model.Status;
import com.model.Users;

public class UserDAOImpl implements UserDAO {

	private Connection connection;

	public UserDAOImpl() {
		// TODO Load the DBUtil class
		connection = DBUtil.getConnection();
		System.out.println("User impl's connection= " + connection.hashCode());
	}

	@Override
	public Status signUp(Users user) throws SQLException {
		String sql="Insert into Users (user_id,user_name,user_email,user_password) values(?,?,?,?)";
		PreparedStatement pst=connection.prepareStatement(sql);
		pst.setInt(1, user.getUserId());
		pst.setString(2, user.getUserName());
		pst.setString(3, user.getUserEmail());
		pst.setString(4, user.getUserPassword());
		pst.executeUpdate();
		Status s=new Status();
		s.setQueryStatus(true);
		return s;
	}

	@Override
	public Users signIn(Users user) throws SQLException {
		String sql="SELECT * FROM users WHERE user_email =?";
		PreparedStatement pst=connection.prepareStatement(sql);
		
		pst.setString(1, user.getUserEmail());
		ResultSet rs=pst.executeQuery();
		
		if(rs.next())
		{
			if(rs.getString("user_password").equals(user.getUserPassword()))
				return user;
			else
				return null;
		}
		return null;
	}

	@Override
	public Users viewProfile(Users user) throws SQLException {
		String sql="SELECT * FROM users WHERE user_email =?";
		PreparedStatement pst=connection.prepareStatement(sql);		
		pst.setString(1, user.getUserEmail());
		ResultSet rs=pst.executeQuery();
		
		if(rs.next())
		{
			
			return (new Users(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(5),rs.getString(6)));
			
			
		}
		return null;
	}

	@Override
	public Status updateProfile(Users user,String email) throws SQLException {
		// TODO Auto-generated method stub
		String sql="Update Users SET user_name=? ,user_email=?,user_password=?,user_bio=?,user_avatar=? where user_email=?";
		PreparedStatement pst=connection.prepareStatement(sql);
		
		pst.setString(1, user.getUserName());
		pst.setString(2, user.getUserEmail());
		pst.setString(3, user.getUserPassword());
		pst.setString(4, user.getUserBio());
		pst.setString(5, user.getUserAvatar());
		pst.setString(6,email);
		System.out.println(email);
		int res=pst.executeUpdate();
		Status s=new Status();
		
		if(res==1) {
			s.setQueryStatus(true);
			return s;
		}
		s.setQueryStatus(false);
		return s;
	}

	

}
