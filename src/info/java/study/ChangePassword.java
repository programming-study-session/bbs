package info.java.study;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;

public class ChangePassword extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Connection conn = null;
		ResultSet user_info_result_set = null;

		// thread_id(�ǂݏo���Ώۂ̃X���b�h���)���擾
		String thread_id = request.getParameter("thread_id");
		//ChangePasswprd.jsp�œ��͂������[�U�[ID�̎擾
		String user_id = request.getParameter("UserID");
		//ChangePasswprd.jsp�œ��͂����ύX�O�̃p�X���[�h���擾
		String now_password = request.getParameter("now_password");
		//ChangePasswprd.jsp�œ��͂����ύX��̃p�X���[�h���擾
		String new_password = request.getParameter("new_password");
		//ChangePasswprd.jsp�œ��͂����ύX��̃p�X���[�h���ē��͏����擾
		String new_password_2 = request.getParameter("new_password2");

		try {
			// JDBC Driver �̓o�^
			Class.forName("com.mysql.jdbc.Driver");
			// Connection�̍쐬
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/java_study?serverTimezone=UTC&useSSL=false",
					"root", "searchman");


			//users_list�ǂ݂��������ǂݏo��
			user_info_result_set = readUsersDb(conn, user_id);

			//users_list����ǂݏo�����f�[�^����p�X���[�h���擾���ĕϐ��ɑ������
			String true_password = null;
			while(user_info_result_set.next()) {
				true_password = user_info_result_set.getString(7);
			}

			//���͂��ꂽ���݂̃p�X���[�h�̃n�b�V����
			String sha256_now_password = DigestUtils.sha256Hex(now_password);


			//���݂̃p�X���[�h�̔�r
			if(sha256_now_password.equals(true_password) && new_password.equals(new_password_2)) {
				/*�p�X���[�h���[��
				(1)���p�������p��,���p�啶���p��,���p������3�̗v�f�̓�2�ȏ�܂܂�Ă��邱�ƁB
				(2)8�����ȏ�ł��邱��*/

				//�p�X���[�h�̕������̊m�F
				int Password_Count = new_password.length();

				//�p�X���[�h�̏����`�F�b�N���e���L��
				Pattern Pass_conditions_0 = Pattern.compile("[A-Z]");
				Pattern Pass_conditions_1 = Pattern.compile("[a-z]");
				Pattern Pass_conditions_2 = Pattern.compile("[0-9]");
				Matcher Pass_match_0 = Pass_conditions_0.matcher(new_password);
				Matcher Pass_match_1 = Pass_conditions_1.matcher(new_password);
				Matcher Pass_match_2 = Pass_conditions_2.matcher(new_password);

				//�p�X���[�h�̏������`�F�b�N
				boolean Pass_Check_0 = Pass_match_0.find();
				boolean Pass_Check_1 = Pass_match_1.find();
				boolean Pass_Check_2 = Pass_match_2.find();
				//�p�X���[�h�����̃`�F�b�N���ʂ�z��ɑ������
				boolean Pass_Check_result[] = {Pass_Check_0, Pass_Check_1, Pass_Check_2};

				//�p�X���[�h�̏��������̃N���A���̃`�F�b�N
				int True_Count=0;
				for( int i = 0 ; i < Pass_Check_result.length; i++) {
					if(Pass_Check_result[i]==true) {
						True_Count++;
					}
				}

				//�p�X���[�h�̐ݒ�����N���A���̃p�X���[�h�ύX�������J�n����
				if(Password_Count >= 8 && True_Count==3) {

					//�p�X���[�h�̃n�b�V�������s��
					String sha256_new_password = DigestUtils.sha256Hex(new_password);

					//�p�X���[�h�ύX�������\�b�h�Ăяo��
					UpdatePassword(conn,user_id,sha256_new_password);


					//user_id,thread_id��J�ڃy�[�W�ցA���n���iAttribute�Œǉ�����j
					request.setAttribute("user_id_set", user_id);
					request.setAttribute("thread_id_set", thread_id);

					request.getRequestDispatcher("/ChangeSuccess.jsp").forward(request, response);

				}


				else {
					try {
						//user_id,thread_id��J�ڃy�[�W�ցA���n���iAttribute�Œǉ�����j
						request.setAttribute("user_id_set", user_id);
						request.setAttribute("thread_id_set", thread_id);

						//�g�p�����I�u�W�F�N�g���I��������
						//user_info_state.close();
						user_info_result_set.close();
						// �O�̂��߁Afinally��DB�Ƃ̐ڑ���ؒf���Ă���
						conn.close();
						response.sendRedirect("http://localhost:8090/SkillShare/ChangeFailure.jsp");
					} catch (Exception e) {
						response.sendRedirect("http://localhost:8090/SkillShare/Eroor.jsp");
						e.printStackTrace();
					}
				}
			}

		}catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("http://localhost:8090/SkillShare/Eroor.jsp");

		} finally {
			try {
				// �O�̂��߁Afinally��DB�Ƃ̐ڑ���ؒf���Ă���
				conn.close();
			} catch (Exception e) {
			}
		}

	}

	//�e�[�u��users_list����ǂݏo��
	private ResultSet readUsersDb(Connection conn, String user_id) throws SQLException {
		//���O�C���ɕK�v�ȃ��[�U�[����users_list����ǂݏo��
		String sql_users_info = "select * from users_list where id =" + user_id + ";";
		System.out.println(sql_users_info);
		PreparedStatement user_info_state =conn.prepareStatement(new String(sql_users_info));
		return user_info_state.executeQuery();
	}

	//�p�X���[�h�ύX����
	private static void UpdatePassword(Connection conn, String user_id,String sha256_new_password) throws SQLException {

		//users_list�Ƀp�X���[�h�̕ύX�����ƍŏI�X�V���Ԃ�update����p���čX�V����
		String sql_new_password = "UPDATE users_list SET password = "+ "\"" + sha256_new_password + "\"," + "last_update = now()" + " WHERE id =" + user_id + ";";
		System.out.println(sql_new_password);
		PreparedStatement new_password_state =conn.prepareStatement(new String(sql_new_password));
		new_password_state.execute();
		new_password_state.close();
	}

}