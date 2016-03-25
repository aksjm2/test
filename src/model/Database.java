package model;

import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	
	static {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch(Exception e) { e.printStackTrace(); }
	}
	
	public boolean dbConnect() {
		try{
			conn = DriverManager.getConnection("jdbc:sqlite:resource/db.sqlite");
		}catch(Exception e){
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			return false;
		}
		return true;
	}
	
    public Database() {
    	dbConnect();
    }

    public Word selectGameWord(int randomword) {
    	Word word = new Word();
        String sql = "select * from Word where key = "+randomword;
        try {
			stmt = conn.createStatement();        
			rs = stmt.executeQuery(sql);
			if(rs.next())
			{
				word.setName(rs.getString("name"));
				word.setImageURL(rs.getString("imageURL"));
				word.setSoundURL(rs.getString("soundURL"));
				word.setType(Integer.parseInt(rs.getString("type")));
				word.setLength(word.getName().length());
			}
			rs.close();
			stmt.close();
        }catch(SQLException e)
        {
        	System.out.println(e.getMessage());        	
        }
        
        return word;
    }

    public Alphabet selectGameAlphabet(char a) {
        Alphabet talphabet = new Alphabet();
        String sql = "select * from Alphabet where name = '"+a+"'";
        try{
        	stmt = conn.createStatement();
        	rs = stmt.executeQuery(sql);
        	if(rs.next())
        	{
        		talphabet.setName(rs.getString("name").charAt(0));
        		talphabet.setImageURL(rs.getString("imageURL"));
        		talphabet.setSoundURL(rs.getString("soundURL"));
        	}
        	rs.close();
        	stmt.close();
        }catch(SQLException e)
        {
        	System.out.println(e.getMessage());
        }
        return talphabet;
    }

    public void updateWord(Word word) {
        String sql = "update Word set correct = 1 where name = '";
        sql = sql.concat(word.getName());
        sql = sql.concat("'");
        try{
        	stmt = conn.createStatement();
        	stmt.executeUpdate(sql);
        	stmt.close();
        }catch(SQLException e)
        {
        	System.out.println(e.getMessage());
        }
        
    }

    public void initializeDictionary(Dictionary dic) {
       searchCorrectWord(dic);
    }
    
    public void searchCorrectWord(Dictionary dic) {
    	 String sql = "select * from Word where correct = 1";
         try{
         	stmt = conn.createStatement();
         	rs = stmt.executeQuery(sql);
         	while(rs.next())
         	{
         		Word temp = new Word();
         		temp.setName(rs.getString("name"));
         		temp.setImageURL(rs.getString("imageURL"));
         		temp.setSoundURL(rs.getString("soundURL"));
         		temp.setCorrect(true);
         		temp.setType(Integer.parseInt(rs.getString("type")));
         		dic.addWordArr(temp);
         	}
         	rs.close();
			stmt.close();
         }catch(SQLException e)
         {
         	System.out.println(e.getMessage());
         }
        
    }

    public void selectRewardImageURL(Picture p) {
        String sql = "select URL from Picture where name = 'reward'";
        try{
        	stmt = conn.createStatement();
        	rs = stmt.executeQuery(sql);
        	if(rs.next())
        	{
        		p.setImageURL(rs.getString("URL"));
        	}
        	else
        	{
        		p.setImageURL("image/reward/default.jpg");
        	}
        	rs.close();
			stmt.close();
        }catch(SQLException e)
        {
        	System.out.println(e.getMessage());
        }
        
    }

    public void updateRewardImage(Picture p) {
        String sql = "update Picture set URL = '";
        sql=sql.concat(p.getImageURL());
        sql=sql.concat("' where name ='reward'");
        try{
        	stmt = conn.createStatement();
        	stmt.executeUpdate(sql);
        	stmt.close();
        }catch(SQLException e)
        {
        	System.out.println(e.getMessage());
        }
    }

    public void updateRewardToDefault(Picture p) {
    	String defaultImg = "image/reward/default.jpg";
        p.setImageURL(defaultImg);
        String sql = "update Picture set URL ='"+defaultImg+"'where name ='reward'";
        try{
        	stmt = conn.createStatement();
        	stmt.executeUpdate(sql);
        	stmt.close();
        }catch(SQLException e)
        {
        	System.out.println(e.getMessage());
        }
    }
    
    public void selectKeyboardImage(Picture vKeyboard, char c)
    {
    	String sql = "select URL from Picture where name ='"+c+"'";
    	try{
    		stmt = conn.createStatement();
    		rs = stmt.executeQuery(sql);
    		if(rs.next())
    		{
    			vKeyboard.setImageURL(rs.getString("URL"));
    		}
    		rs.close();
			stmt.close();
    	}catch(SQLException e)
    	{
    		System.out.println(e.getMessage());
    	}
    }
	public Word selectWordbyText(String userTypedWord) {
		Word temp = new Word();
		String sql = "select * from Word where name = '";
		sql = sql.concat(userTypedWord);
		sql = sql.concat("'");
		try{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if(rs.next())
			{
				temp.setName(rs.getString("name"));
         		temp.setImageURL(rs.getString("imageURL"));
         		temp.setSoundURL(rs.getString("soundURL"));
			}
			else
			{
				temp.setImageURL("image/notfound.jpg");
				temp.setSoundURL("sound/notfound.mp3");
			}
			rs.close();
			stmt.close();
		}catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		
		return temp;
	}

	public Word selectRandomWordbyAlphabet(char alphabet) {
		double randomvalue = Math.random();
    	int randNum = 0;
		Vector<Word> wordarr = new Vector<Word>();
		String sql = "select * from Word where name like '"+alphabet+"%'";
		try{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next())
			{
				Word temp = new Word();
				temp.setName(rs.getString("name"));
         		temp.setImageURL(rs.getString("imageURL"));
         		temp.setSoundURL(rs.getString("soundURL"));
         		wordarr.add(temp);
			}
			rs.close();
			stmt.close();
		}catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		randNum = (int)( randomvalue * wordarr.size() );
		return wordarr.elementAt(randNum);
	}
	
	public void disconnect()
	{
		try{
			conn.close();	
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
}