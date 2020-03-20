package info.java.study;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ThreadEdit extends HttpServlet {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// DB�֘A�̏����ݒ�
		Connection conn = null;
		PreparedStatement thread_creator_state = null;
		PreparedStatement thread_insert_state = null;
		PreparedStatement thread_read_state = null;
		PreparedStatement thread_info_state = null;
		ResultSet thread_creator_result_set = null;
		ResultSet thread_result_set = null;
		ResultSet thread_info_result_set = null;


		// �����R�[�h�̐ݒ�
		request.setCharacterEncoding("Windows-31J");

		//thread2����쐬����X���b�h��,�X���b�h�쐬�҂�ID���擾
		String topic = request.getParameter("topic");
		String UserId = request.getParameter("UserID");

		try {
			// JDBC Driver �̓o�^
			Class.forName("com.mysql.jdbc.Driver");
			// Connection�̍쐬
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/java_study?serverTimezone=UTC&useSSL=false",
					"root", "searchman");
//�X���b�h�쐬�҂̖��O��uses_list����ǂݏo��-------------------------------------------------------------------------

			//sql���̍쐬
            String sql_thread_creator = "select * from users_list where id =" + UserId + ";";
            System.out.println(sql_thread_creator);

            //
            thread_creator_state = conn.prepareStatement(new String(sql_thread_creator));
            thread_creator_state.execute();
            thread_creator_result_set = thread_creator_state.executeQuery();



            String thread_creator = null;

            while(thread_creator_result_set.next()) {
            	thread_creator = thread_creator_result_set.getString(2);
            }
            System.out.println(thread_creator);

//�X���b�h�̍쐬����------------------------------------------------------------------------
			// sql�� �̍쐬
			String sql_thread_insert = "insert into thread_list(thread_title,name,time)";
			sql_thread_insert = sql_thread_insert + "values"+ "(\""  + topic + "\",\""+ thread_creator + "\"," + "now()" +");";

			// sql����\��
			System.out.println(sql_thread_insert);

			// sql�����s����
			thread_insert_state = conn.prepareStatement(new String(sql_thread_insert));

			// sql�����s
			thread_insert_state.execute();

//�X���b�h�̈ꗗ��\��----------------------------------------------------------------------
			// sql�� �̍쐬
			String sql_thread_read = "select * from thread_list;";

			//sql����\��
			System.out.println(sql_thread_read);

			thread_read_state = conn.prepareStatement(new String(sql_thread_read));

				// sql�����s
			thread_read_state.execute();

			// ���s���ʂ��AResultSet�N���X�ɑ��
			thread_result_set = thread_read_state.executeQuery();


			// �J�ڃy�[�W�ցA���n���iAttribute�Œǉ�����j
			request.setAttribute("thread_kekka", thread_result_set);

//�Ή��X���b�h���̕\��-----------------------------------------------------------------------------------
			//sql���쐬�̏���
			String sql_thread_info = "select thread_id, thread_title from thread_list where thread_id=(select max(thread_id) from thread_list);";

			// sql����\��
			System.out.println(sql_thread_info);

			// sql�����s����
			thread_info_state = conn.prepareStatement(new String(sql_thread_info));

			// sql�����s
			thread_info_state.execute();

			// ���s���ʂ��AResultSet�N���X�ɑ��
			thread_info_result_set = thread_info_state.executeQuery();

			// �J�ڃy�[�W�ցA���n���iAttribute�Œǉ�����j
			request.setAttribute("thread_info_kekka", thread_info_result_set);
//-------------------------------------------------------------------------------------


			// search.jsp�֑J��
			request.getRequestDispatcher("/thread2.jsp").forward(request, response);

			// �g�p�����I�u�W�F�N�g���I��������
			conn.close();
			thread_insert_state.close();
			thread_read_state.close();
			thread_info_state.close();
			thread_result_set.close();
			thread_info_result_set.close();

		} catch (Exception e) {
			request.getRequestDispatcher("/Eroor.jsp");

		} finally {
			try {
				// �O�̂��߁Afinally��DB�Ƃ̐ڑ���ؒf���Ă���
				conn.close();
			} catch (Exception e) {
			}
		}

	}

}
