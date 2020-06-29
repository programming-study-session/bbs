package info.java.study;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
		ResultSet thread_creator_result_set = null;
		ResultSet thread_result_set = null;

		// �����R�[�h�̐ݒ�
		request.setCharacterEncoding("Windows-31J");

		//thread2����쐬����X���b�h��,�X���b�h�쐬�҂�ID���擾
		String topic = request.getParameter("topic");
		String Enter_Char = request.getParameter("topic");
		String user_id = request.getParameter("UserID");

		//���͂��ꂽ�����񂪋󔒂łȂ����̔���
		String Enter_info = Enter_char_judge.Enter_char_replace(Enter_Char);
		System.out.println("Enter_info = '" + Enter_info + "'");

		if (!Enter_info.equals("")) {

			//user_id��J�ڃy�[�W�ցA���n���iAttribute�Œǉ�����j
			request.setAttribute("user_id_set", user_id);

			try {
				// JDBC Driver �̓o�^
				Class.forName("com.mysql.jdbc.Driver");
				// Connection�̍쐬
				conn = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/java_study?serverTimezone=UTC&useSSL=false",
						"root", "searchman");


				//�X���b�h�쐬�҂̖��O��uses_list����ǂݏo��
				thread_creator_result_set = readUsersDb(conn, user_id);

				String thread_creator = null;

				while (thread_creator_result_set.next()) {
					thread_creator = thread_creator_result_set.getString(2);
				}
				System.out.println(thread_creator);

				//�X���b�h�̍쐬����
				editThread(conn,topic,thread_creator);


				//�X���b�h�ǂݏo��
				thread_result_set = readThreadFromDb(conn);
				request.setAttribute("thread_kekka", thread_result_set);

				//�X���b�h�^�C�g���ƃX���b�hID���擾
				thread_result_set.last();
				String thread_id_result = thread_result_set.getString(1);
				String thread_title_result = thread_result_set.getString(2);
				thread_result_set.beforeFirst();

				request.setAttribute("thread_title", thread_title_result);
				request.setAttribute("thread_id_set", thread_id_result);

				// search.jsp�֑J��
				request.getRequestDispatcher("/thread2.jsp").forward(request, response);

				// �g�p�����I�u�W�F�N�g���I��������
				conn.close();
				//thread_read_state.close();
				//thread_info_state.close();
				thread_result_set.close();

			} catch (Exception e) {
				e.printStackTrace();
				response.sendRedirect("\"http://localhost:8090/SkillShare/Eroor.jsp");

			} finally {
				try {
					// �O�̂��߁Afinally��DB�Ƃ̐ڑ���ؒf���Ă���
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		else {
			response.sendRedirect("http://localhost:8090/SkillShare/Not_Enter_Warning.jsp");
		}
	}
	//�X���b�h�쐬�҂̖��O��uses_list����ǂݏo��----------------------------------------------------------------------
	private ResultSet readUsersDb(Connection conn, String user_id) throws SQLException {
		String sql_thread_creator = "select * from users_list where id =" + user_id + ";";
		System.out.println(sql_thread_creator);

		PreparedStatement thread_creator_state = conn.prepareStatement(new String(sql_thread_creator));
		thread_creator_state.execute();
		return thread_creator_state.executeQuery();
	}

	//�X���b�h�ւ̓��e����---------------------------------------------------------------------------------------------
	private static void editThread(Connection conn, String topic,String thread_creator) throws SQLException {
		String sql_thread_insert = "insert into thread_list(thread_title,name,time)";
		sql_thread_insert = sql_thread_insert + "values" + "(\"" + topic + "\",\"" + thread_creator + "\","
				+ "now()" + ");";

		System.out.println(sql_thread_insert);
		PreparedStatement thread_insert_state = conn.prepareStatement(new String(sql_thread_insert));
		thread_insert_state.execute();
		thread_insert_state.close();
	}

	//�X���b�h�̓ǂݏo������---------------------------------------------------------------------------------------------
	private ResultSet readThreadFromDb(Connection conn) throws SQLException {

		String sql_thread_read = "select * from thread_list;";
		System.out.println(sql_thread_read);
		PreparedStatement thread_read_state = conn.prepareStatement(new String(sql_thread_read));
		thread_read_state.execute();
		return thread_read_state.executeQuery();

	}
}

