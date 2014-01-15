package bll;

public class Worker {
	private int worker_ID;
	private String worker_Name;
	private int office_ID;
	private String job;
	
	public Worker(int worker_ID, String worker_Name, int office_ID, String job){
		super();
		this.worker_ID = worker_ID;
		this.worker_Name = worker_Name;
		this.office_ID = office_ID;
		this.job = job;
	}
	
	public Worker(){
		
	}
	
	public int getWorker_ID() {
		return worker_ID;
	}

	public void setWorker_ID(int worker_ID) {
		this.worker_ID = worker_ID;
	}

	public String getWorker_Name() {
		return worker_Name;
	}

	public void setWorker_Name(String worker_Name) {
		this.worker_Name = worker_Name;
	}

	public int getOffice_ID() {
		return office_ID;
	}

	public void setOffice_ID(int office_ID) {
		this.office_ID = office_ID;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

}
