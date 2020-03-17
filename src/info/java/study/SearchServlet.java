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

		// index.jsp�œ��͂���thread_id�̎擾
		 String thread_id = request.getParameter("thread_id");

		try {
			// JDBC Driver �̓o�^
			Class.forName("com.mysql.jdbc.Driver");
			// Connection�̍쐬
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/java_study?serverTimezone=UTC&useSSL=false",
					"root", "searchman");
// �X���b�h�R�����g�ǂݏo��--------------------------------------------------------------------

			// sql���쐬�̏��� (�R�����g�̓ǂݏo��)
			String sql_comment_read = "select * from comment_list where thread_id =" + thread_id + ";";

			// sql����\�� (�R�����g�̓ǂݏo��)
			System.out.println(sql_comment_read);

			// sql�����s���� (�R�����g�̓ǂݏo��)
			comment_read_state = conn.prepareStatement(new String(sql_comment_read));

			// sql�����s (�R�����g�̓ǂݏo��)
			comment_read_state.execute();

			// ���s���ʂ��AResulthread_result_set�N���X�ɑ�� (�R�����g�̓ǂݏo��)
			comment_result_set = comment_read_state.executeQuery();

			// �J�ڃy�[�W�ցA���n���iAttribute�Œǉ�����j (�R�����g�̓ǂݏo��)
			request.setAttribute("comment_kekka", comment_result_set);

// �X���b�h�ǂݏo��--------------------------------------------------------------------

			// sql���쐬�̏��� (�X���b�h�̓ǂݏo��)
			String sql_thread_read = "select * from thread_list;";

			// (�X���b�h�̓ǂݏo��)
			System.out.println(sql_thread_read);

			// sql�����s���� (�X���b�h�̓ǂݏo��)
			thread_read_state = conn.prepareStatement(new String(sql_thread_read));

			// sql�����s (�X���b�h�̓ǂݏo��)
			thread_read_state.execute();

			// ���s���ʂ��AResulthread_result_set�N���X�ɑ�� (�X���b�h�̓ǂݏo��)
			thread_result_set = thread_read_state.executeQuery();

			// �J�ڃy�[�W�ցA���n���iAttribute�Œǉ�����j (�X���b�h�̓ǂݏo��)
			request.setAttribute("thread_kekka", thread_result_set);

//�Ή��X���b�h���̕\��-----------------------------------------------------------------------------------
			//sql���쐬�̏���
			String sql_thread_info = "select * from thread_list where thread_id =" + thread_id + ";";

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
//-----------------------------------------------------------------------------------
			// thread2.jsp�֑J��
			request.getRequestDispatcher("/thread2.jsp").forward(request, response);

			// �g�p�����I�u�W�F�N�g���I��������
			comment_result_set.close();
			thread_result_set.close();
			thread_info_result_set.close();
			comment_read_state.close();
			thread_read_state.close();
			thread_info_state.close();
			conn.close();

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
