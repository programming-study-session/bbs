package info.java.study;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SearchServlet extends HttpServlet {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// DB�֘A�̏����ݒ�
		Connection conn = null;
		PreparedStatement comment_read_state = null;
		PreparedStatement thread_read_state = null;
		PreparedStatement thread_info_state = null;
		ResultSet comment_result_set = null;
		ResultSet thread_result_set = null;
		ResultSet thread_info_result_set = null;

		// �����R�[�h�̐ݒ�
		request.setCharacterEncoding("Windows-31J");

		// thread_id(�ǂݏo���Ώۂ̃X���b�h���)���擾
		 String thread_id = request.getParameter("thread_id");
		 //���[�U�[ID���擾
		 String user_id = request.getParameter("UserID");

		try {

			//user_id��J�ڃy�[�W�ցA���n���iAttribute�Œǉ�����j
			request.setAttribute("user_id_set", user_id);

			// JDBC Driver �̓o�^
			Class.forName("com.mysql.jdbc.Driver");
			// Connection�̍쐬
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/java_study?serverTimezone=UTC&useSSL=false",
					"root", "searchman");
// �X���b�h�R�����g�ǂݏo��--------------------------------------------------------------------
			// ���s���ʂ��AResulthread_result_set�N���X�ɑ�� (�R�����g�̓ǂݏo��)
			comment_result_set = readCommentFromDb(conn, thread_id);

			// �J�ڃy�[�W�ցA���n���iAttribute�Œǉ�����j (�R�����g�̓ǂݏo��)
			request.setAttribute("comment_kekka", comment_result_set);

// �X���b�h�ǂݏo��--------------------------------------------------------------------

			thread_result_set = readThreadFromDb(conn);


			// �J�ڃy�[�W�ցA���n���iAttribute�Œǉ�����j (�X���b�h�̓ǂݏo��)
			request.setAttribute("thread_kekka", thread_result_set);


//�Ή��X���b�h���̕\��-----------------------------------------------------------------------------------

			List thread_info=new ArrayList<String>();

			String thread_id_info = null;
			String thread_title_info = null;
			java.sql.ResultSetMetaData Dat = thread_result_set.getMetaData();
			int col = Dat.getColumnCount();

			while(comment_result_set.next()){
				thread_id_info=comment_result_set.getString(1);
				thread_info.add(thread_id_info);
				thread_title_info=comment_result_set.getString(2);
				thread_info.add(thread_title_info);
			}

			for(int i=1; i <= col; i++) {

			}






			//sql���쐬�̏���
//			String sql_thread_info = "select * from thread_list where thread_id =" + thread_id + ";";
//
//			// sql����\��
//			System.out.println(sql_thread_info);
//
//			// sql�����s����
//			thread_info_state = conn.prepareStatement(new String(sql_thread_info));
//
//			// sql�����s
//			thread_info_state.execute();
//
//			// ���s���ʂ��AResultSet�N���X�ɑ��
//			thread_info_result_set = thread_info_state.executeQuery();
//
//			// �J�ڃy�[�W�ցA���n���iAttribute�Œǉ�����j
//			request.setAttribute("thread_info_kekka", thread_info_result_set);
//-----------------------------------------------------------------------------------
			// thread2.jsp�֑J��
			request.getRequestDispatcher("/thread2.jsp").forward(request, response);

			// �g�p�����I�u�W�F�N�g���I��������
			comment_result_set.close();
			thread_result_set.close();
			thread_info_result_set.close();
			//comment_read_state.close();
			//thread_read_state.close();
			thread_info_state.close();
			conn.close();

		} catch (Exception e) {
			response.sendRedirect("\"http://localhost:8090/SkillShare/Eroor.jsp");

		} finally {
			try {
				// �O�̂��߁Afinally��DB�Ƃ̐ڑ���ؒf���Ă���
				conn.close();
			} catch (Exception e) {
			}
		}

	}

	private ResultSet readCommentFromDb(Connection conn, String thread_id) throws SQLException {
		// sql���쐬�̏��� (�R�����g�̓ǂݏo��)
		String sql_comment_read = "select * from comment_list where thread_id =" + thread_id + ";";

		// sql����\�� (�R�����g�̓ǂݏo��)
		System.out.println(sql_comment_read);

		// sql�����s���� (�R�����g�̓ǂݏo��)
		PreparedStatement comment_read_state = conn.prepareStatement(new String(sql_comment_read));

		// sql�����s (�R�����g�̓ǂݏo��)
		comment_read_state.execute();

		return comment_read_state.executeQuery();
	}

	private ResultSet readThreadFromDb(Connection conn) throws SQLException {
		// sql���쐬�̏��� (�X���b�h�̓ǂݏo��)
		String sql_thread_read = "select * from thread_list;";

		// (�X���b�h�̓ǂݏo��)
		System.out.println(sql_thread_read);

		// sql�����s���� (�X���b�h�̓ǂݏo��)
		PreparedStatement thread_read_state = conn.prepareStatement(new String(sql_thread_read));

		// sql�����s (�X���b�h�̓ǂݏo��)
		thread_read_state.execute();

		// ���s���ʂ��AResulthread_result_set�N���X�ɑ�� (�X���b�h�̓ǂݏo��)
		return thread_read_state.executeQuery();
	}

}
