package info.java.study;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

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
		ResultSet thread_result_set = null;
		ResultSet user_info_result_set =null;
		ResultSet comment_result_set = null;

		// �����R�[�h�̐ݒ�
		request.setCharacterEncoding("Windows-31J");
		//thread2����R�����g�𓊍e����X���b�h�����擾
		String thread_id = request.getParameter("thread_id");
		//thread2����R�����g�𓊍e�������UserID���擾
		String user_id = request.getParameter("UserId");
		//thread2���瓊�e���e���擾
		String comment = request.getParameter("comment");
		String Enter_Char = request.getParameter("comment");

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
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/java_study?serverTimezone=UTC&useSSL=false",
						"root", "searchman");

				//���e�҂̖��O���擾
				user_info_result_set = readUsersDb(conn, user_id);
				user_info_result_set.next();
				String Contributor_name = user_info_result_set.getString(2);

				//���e�Ώۂ̃X���b�h�̃R�����g�����擾
				comment_result_set =countCommentDb(conn ,thread_id);
				comment_result_set.next();
				int comment_no_result = comment_result_set.getInt(1);
				//�e�[�u��commnet_list��insert����commnet_no�ɂ���
				comment_no_result++;
				String comment_no_set = Integer.toString(comment_no_result);

				//�X���b�h�ւ̓��e����
				editComment(conn,thread_id , comment_no_set , Contributor_name , comment);

				//�X���b�h�̈ꗗ��\��
				thread_result_set = readThreadFromDb(conn);
				request.setAttribute("thread_kekka", thread_result_set);


				//�Ή��X���b�h���̕\��
				comment_result_set = readCommentFromDb(conn,thread_id);
				request.setAttribute("comment_kekka", comment_result_set);

				//�������݉\�ȃX���b�h�^�C�g�����擾
				String currentThreadId = request.getParameter("thread_id");
				HashMap<String,String> threadTitleMap = new HashMap<String,String>();

				String threadId;
				String threadTitle;

				while(thread_result_set.next()){
				  threadId = thread_result_set.getString(1);
				  threadTitle = thread_result_set.getString(2);

				  threadTitleMap.put(threadId, threadTitle);
				}

				thread_result_set.beforeFirst();

				String thread_title_result = threadTitleMap.get(currentThreadId);
				request.setAttribute("thread_title", thread_title_result);

				// search.jsp�֑J��
				request.getRequestDispatcher("/thread2.jsp").forward(request, response);

				// �g�p�����I�u�W�F�N�g���I��������
				conn.close();
				//thread_read_state.close();
				//comment_read_state.close();
				thread_result_set.close();
				comment_result_set.close();

			} catch (Exception e) {
				e.printStackTrace();
				response.sendRedirect("http://localhost:8090/SkillShare/Eroor.jsp");

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

	private ResultSet readUsersDb(Connection conn, String user_id) throws SQLException {
		//���O�C���ɕK�v�ȃ��[�U�[����users_list����ǂݏo��
		String sql_users_info = "select * from users_list where id =" + user_id + ";";
		System.out.println(sql_users_info);
		PreparedStatement user_info_state =conn.prepareStatement(new String(sql_users_info));
		user_info_state.execute();
		return user_info_state.executeQuery();
	}

	private ResultSet countCommentDb(Connection conn, String thread_id) throws SQLException {
		//���O�C���ɕK�v�ȃ��[�U�[����users_list����ǂݏo��
		String sql_comment_count = "select count(*) from comment_list where thread_id =" + thread_id + ";";
		System.out.println(sql_comment_count);
		PreparedStatement comment_count_state = conn.prepareStatement(new String(sql_comment_count));
		comment_count_state.execute();
		return comment_count_state.executeQuery();
	}

	private static void editComment(Connection conn, String thread_id, String comment_no_set, String Contributor_name,String comment) throws SQLException {
		String sql_comment_insert = "insert into comment_list(thread_id,comment_no,name,comment,time)";
		sql_comment_insert = sql_comment_insert + "values"+ "(\"" + thread_id + "\"" + ',' + "\"" + comment_no_set + "\"" + ',' + "\"" + Contributor_name + "\"" + ',' + "\"" + comment + "\",now());";
		System.out.println(sql_comment_insert);
		PreparedStatement comment_insert_state = conn.prepareStatement(new String(sql_comment_insert));
		comment_insert_state.execute();
		comment_insert_state.close();
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


}