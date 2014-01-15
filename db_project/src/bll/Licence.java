package bll;

import java.sql.Timestamp;

public class Licence {
	private int licence_ID;
	private String ID;
	private int ID_Type;
	private String stu_Gender;
	private int score;
	private String licence_State;
	private String stu_Name;
	private String nationality;
	private Timestamp birthday;
	private String home_Address;
	private Timestamp get_Licence_Time;
	private String allowed_Car_Type;
	private String valid_Time;
	private int office_ID;
	private String photo_Address;

	public Licence(int licence_ID, String stu_Name, String stu_Gender,
			int ID_Type, String ID, String nationality, Timestamp birthday,
			String home_Address, String licence_State, Timestamp get_Licence_Time,
			String allowed_Car_Type, String valid_Time, int office_ID,
			int score, String photo_Address) {
		super();
		this.licence_ID = licence_ID;
		this.ID = ID;
		this.ID_Type = ID_Type;
		this.stu_Gender = stu_Gender;
		this.score = score;
		this.licence_State = licence_State;
		this.stu_Name = stu_Name;
		this.nationality = nationality;
		this.birthday = birthday;
		this.home_Address = home_Address;
		this.get_Licence_Time = get_Licence_Time;
		this.allowed_Car_Type = allowed_Car_Type;
		this.valid_Time = valid_Time;
		this.office_ID = office_ID;
		this.photo_Address = photo_Address;
	}

	public Licence() {

	}

	public int getLicence_ID() {
		return licence_ID;
	}

	public void setLicence_ID(int licence_ID) {
		this.licence_ID = licence_ID;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public int getID_Type() {
		return ID_Type;
	}

	public void setID_Type(int ID_Type) {
		this.ID_Type = ID_Type;
	}

	public String getStu_Gender() {
		return stu_Gender;
	}

	public void setStu_Gender(String stu_Gender) {
		this.stu_Gender = stu_Gender;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getlicence_State() {
		return licence_State;
	}

	public void setlicence_State(String licence_State) {
		this.licence_State = licence_State;
	}

	public String getStu_Name() {
		return stu_Name;
	}

	public void setStu_Name(String stu_Name) {
		this.stu_Name = stu_Name;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public Timestamp getBirthday() {
		return birthday;
	}

	public void setBirthday(Timestamp birthday) {
		this.birthday = birthday;
	}

	public String gethome_Address() {
		return home_Address;
	}

	public void sethome_Address(String home_Address) {
		this.home_Address = home_Address;
	}

	public Timestamp getget_Licence_Time() {
		return get_Licence_Time;
	}

	public void setget_Licence_Time(Timestamp get_Licence_Time) {
		this.get_Licence_Time = get_Licence_Time;
	}

	public String getallowed_Car_Type() {
		return allowed_Car_Type;
	}

	public void setallowed_Car_Type(String allowed_Car_Type) {
		this.allowed_Car_Type = allowed_Car_Type;
	}

	public String getvalid_Time() {
		return valid_Time;
	}

	public void setvalid_Time(String valid_Time) {
		this.valid_Time = valid_Time;
	}

	public int getOffice_ID() {
		return office_ID;
	}

	public void setOffice_ID(int office_ID) {
		this.office_ID = office_ID;
	}

	public String getphoto_Address() {
		return photo_Address;
	}

	public void setphoto_Address(String photo_Address) {
		this.photo_Address = photo_Address;
	}

}
