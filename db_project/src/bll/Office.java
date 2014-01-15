package bll;

public class Office {
	private int office_ID;
	private String province;
	private String city;
	private String district;
	private String street;
	private int admin_ID;

	public Office(int office_ID, String province, String city, String district,
			String street, int admin_ID) {
		super();
		this.office_ID = office_ID;
		this.province = province;
		this.city = city;
		this.district = district;
		this.street = street;
		this.admin_ID = admin_ID;
	}

	public int getOffice_ID() {
		return office_ID;
	}

	public void setOffice_ID(int office_ID) {
		this.office_ID = office_ID;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public int getAdmin_ID() {
		return admin_ID;
	}

	public void setAdmin_ID(int admin_ID) {
		this.admin_ID = admin_ID;
	}

}
