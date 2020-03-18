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


public class CommentEdit extends HttpServlet {


	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// DB�֘A�̏����ݒ�
		Connection conn = null;
		PreparedStatement comment_insert_state = null;
		PreparedStatement Contributor_name_state = null;
		PreparedStatement thread_read_state = null;
		PreparedStatement comment_read_state = null;
		PreparedStatement thread_info_state = null;
		ResultSet thread_result_set = null;
		ResultSet Contributor_name_result_set =null;
		ResultSet comment_result_set = null;
		ResultSet thread_info_result_set = null;

		// �����R�[�h�̐ݒ�
		request.setCharacterEncoding("Windows-31J");

		//thread2����R�����g�𓊍e����X���b�h�����擾
		String thread_id = request.getParameter("thread_id");
		//thread2����R�����g�𓊍e�������UserID���擾
		String UserId = request.getParameter("UserId");
		//thread2���瓊�e���e���擾
		String comment = request.getParameter("comment");

		try {
			// JDBC Driver �̓o�^
			Class.forName("com.mysql.jdbc.Driver");
			// Connection�̍쐬
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/java_study?serverTimezone=UTC&useSSL=false",
					"root", "searchman");

//���e�҂̖��O���擾-------------------------------------------------------------------------------------------
			// sql���쐬
			String sql_Contributor_name = "select * from users_list where id =" + UserId + ";";
			System.out.println(sql_Contributor_name);

			//sql���s�����s���ʂ���
			Contributor_name_state = conn.prepareStatement(new String(sql_Contributor_name));
			Contributor_name_state.execute();
			Contributor_name_result_set = Contributor_name_state.executeQuery();

			//���s���ʂ����o����Contributor_name�N���X�ɑ��

			String Contributor_name = null;

			while(Contributor_name_result_set.next()) {
				Contributor_name = Contributor_name_result_set.getString(2);
			}
////�X���b�h�̓��e�����擾-------------------------------------------------------------------------------------
///*			//�X���b�h�̓��e�����擾
//			String sql_thread_count = "select count(*) from comment_list where thread_id =";
//				sql_thread_count = sql_thread_count + thread_id + ";";
//
//			// sql����\��
//			System.out.println(sql_thread_count);
//
//			// sql�����s����
//			thread_count_state = conn.prepareStatement(new String(sql_thread_count));
//
//			// sql�����s
//			thread_count_state.execute();
//
//			// ���s���ʂ��AResultSet�N���X�ɑ��
//			thread_count_result_set =thread_count_state.executeQuery();
//
//			//���s���ʂ����o����int�^�̕ϐ��ɑ������
//			int comment_no_result = thread_count_state.getInt(thread_id);
//
//			System.out.println(comment_no_result);
//			*/
//comment_no���K�v�ł��邽�ߓ��e���̎擾---------------------------------------------------------------------
			//sql���쐬�̏��� (�R�����g�̓ǂݏo��)
			String sql_comment_read = "select * from comment_list where thread_id =" + thread_id + ";";

			// sql����\�� (�R�����g�̓ǂݏo��)
			System.out.println(sql_comment_read);

			// sql�����s���� (�R�����g�̓ǂݏo��)
			comment_read_state = conn.prepareStatement(new String(sql_comment_read));

			// sql�����s (�R�����g�̓ǂݏo��)
			comment_read_state.execute();

			// ���s���ʂ��AResultSet�N���X�ɑ�� (�R�����g�̓ǂݏo��)
			comment_result_set = comment_read_state.executeQuery();

			//���݂̃R�����g�v������ϐ��̏�����
			int comment_no_result = 0;

			//�J��Ԃ������ɂ���Č��݂̓��e���̎擾
			while(comment_result_set.next()) {
				comment_no_result++;
			}

			//���蓖�Ă�comment_no��ݒ�(comment_no=���݂̓��e��+1)
			comment_no_result++;

			//comment_no�𕶎���^�ɕύX
			String comment_no_set = Integer.toString(comment_no_result);

//�X���b�h�ւ̓��e����---------------------------------------------------------------------------------

			// sql�� �̍쐬�icomment_no����j
			String sql_comment_insert = "insert into comment_list(thread_id,comment_no,name,comment,time)";
			sql_comment_insert = sql_comment_insert + "values"+ "(\"" + thread_id + "\"" + ',' + "\"" + comment_no_set + "\"" + ',' + "\"" + Contributor_name + "\"" + ',' + "\"" + comment + "\",now());";

			// sql����\��
			System.out.println(sql_comment_insert);

			// sql�����s����
			comment_insert_state = conn.prepareStatement(new String(sql_comment_insert));

			// sql�����s
			comment_insert_state.execute();

//�X���b�h�̈ꗗ��\��------------------------------------------------------------------------
			String sql_thread_read = "select * from thread_list;";

				// sql����\��
				System.out.println(sql_thread_read);


				thread_read_state = conn.prepareStatement(new String(sql_thread_read));

				// sql�����s
				thread_read_state.execute();

				// ���s���ʂ��AResultSet�N���X�ɑ��
				thread_result_set = thread_read_state.executeQuery();


				// �J�ڃy�[�W�ցA���n���iAttribute�Œǉ�����j
				request.setAttribute("thread_kekka", thread_result_set);


//�Ή��X���b�h���̕\��--------------------------------------------------------------------------------------
			// sql���쐬�̏��� (�R�����g�̓ǂݏo��)
			sql_comment_read = "select * from comment_list where thread_id =" + thread_id + ";";

			// sql����\�� (�R�����g�̓ǂݏo��)
			System.out.println(sql_comment_read);

			// sql�����s���� (�R�����g�̓ǂݏo��)
			comment_read_state = conn.prepareStatement(new String(sql_comment_read));

			// sql�����s (�R�����g�̓ǂݏo��)
			comment_read_state.execute();

			// ���s���ʂ��AResultSet�N���X�ɑ�� (�R�����g�̓ǂݏo��)
			comment_result_set = comment_read_state.executeQuery();

			// �J�ڃy�[�W�ցA���n���iAttribute�Œǉ�����j (�R�����g�̓ǂݏo��)
			request.setAttribute("comment_kekka", comment_result_set);
//�������݉\�ȃX���b�h���擾---------------------------------------------------------------
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
//--------------------------------------------------------------------------------------
			// search.jsp�֑J��
			request.getRequestDispatcher("/thread2.jsp").forward(request, response);

			// �g�p�����I�u�W�F�N�g���I��������
			conn.close();
			comment_insert_state.close();
			thread_read_state.close();
			comment_read_state.close();
			thread_info_state.close();
			thread_result_set.close();
			comment_result_set.close();
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