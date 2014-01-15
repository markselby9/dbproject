package bll;

import java.sql.Timestamp;

public class Student {
	private int stu_ID;
	private int ID_Type;
	private String ID;
	private String stu_Name;
	private String stu_Gender;
	private Timestamp birthday;
	private String nationality;
	private String home_Address;
	private String pass;
	private String reject_Reason;
	private Timestamp apply_Time;
	private int worker_ID;

	public Student(int stu_ID, int ID_Type, String ID, String stu_Name, String stu_Gender, Timestamp birthday,
			String nationality, String home_Address, String pass, String reject_Reason,
			Timestamp apply_Time, int worker_ID) {
		super();
		this.stu_ID = stu_ID;
		this.ID_Type = ID_Type;
		this.ID = ID;
		this.stu_Name = stu_Name;
		this.stu_Gender = stu_Gender;
		this.birthday = birthday;
		this.nationality = nationality;
		this.home_Address = home_Address;
		this.pass = pass;
		this.reject_Reason = reject_Reason;
		this.apply_Time = apply_Time;
		this.worker_ID = worker_ID;
	}

	public int getStu_ID() {
		return stu_ID;
	}

	public void setStu_ID(int stu_ID) {
		this.stu_ID = stu_ID;
	}

	public int getID_Type() {
		return ID_Type;
	}

	public void setID_Type(int iD_Type) {
		ID_Type = iD_Type;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getStu_Name() {
		return stu_Name;
	}

	public void setStu_Name(String stu_Name) {
		this.stu_Name = stu_Name;
	}

	public String getStu_Gender() {
		return stu_Gender;
	}

	public void setStu_Gender(String stu_Gender) {
		this.stu_Gender = stu_Gender;
	}

	public Timestamp getBirthday() {
		return birthday;
	}

	public void setBirthday(Timestamp birthday) {
		this.birthday = birthday;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getHome_Address() {
		return home_Address;
	}

	public void setHome_Address(String home_Address) {
		this.home_Address = home_Address;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getReject_Reason() {
		return reject_Reason;
	}

	public void setReject_Reason(String reject_Reason) {
		this.reject_Reason = reject_Reason;
	}

	public Timestamp getApply_Time() {
		return apply_Time;
	}

	public void setApply_Time(Timestamp apply_Time) {
		this.apply_Time = apply_Time;
	}

	public int getWorker_ID() {
		return worker_ID;
	}

	public void setWorker_ID(int worker_ID) {
		this.worker_ID = worker_ID;
	}

}