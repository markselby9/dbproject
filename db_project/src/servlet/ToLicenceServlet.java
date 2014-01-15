package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bll.GradeOperator;
import bll.LicenceOperator;

/**
 * Servlet implementation class ToLicenceServlet
 */
public class ToLicenceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ToLicenceServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<String> IDs = null;
		System.out.println("a");
		try {
			//�õ�����һ�гɼ���List<ID>
			IDs = GradeOperator.getIDList(1);
			System.out.println(IDs.size());
			//���۶�����
			IDs = GradeOperator.deleteInvalidIDFor2(IDs);
			System.out.println(IDs.size());
			//��Ŀ������
			IDs = GradeOperator.deleteInvalidIDFor3(IDs);
			System.out.println(IDs.size());
			//��Ŀ�Ĺ���
			IDs = GradeOperator.deleteInvalidIDFor4(IDs);
			System.out.println(IDs.size());
			if(IDs != null){
				for(int i = 0; i < IDs.size(); i++){
					LicenceOperator.addLicence(IDs.get(i));
				}
			}
			response.sendRedirect("worker.jsp");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
