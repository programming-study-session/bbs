package info.java.study;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
		PreparedStatement user_info_state = null;
		ResultSet user_info_result_set = null;
		PreparedStatement new_password_state = null;
		PreparedStatement password_change_state = null;

		// thread_id(読み出し対象のスレッド情報)を取得
		String thread_id = request.getParameter("thread_id");
		//ChangePasswprd.jspで入力したユーザーIDの取得
		String user_id = request.getParameter("UserID");
		//ChangePasswprd.jspで入力した変更前のパスワードを取得
		String now_password = request.getParameter("now_password");
		//ChangePasswprd.jspで入力した変更後のパスワードを取得
		String new_password = request.getParameter("new_password");
		//ChangePasswprd.jspで入力した変更後のパスワードを再入力情報を取得
		String new_password_2 = request.getParameter("new_password2");

		try {
			// JDBC Driver の登録
			Class.forName("com.mysql.jdbc.Driver");
			// Connectionの作成
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/java_study?serverTimezone=UTC&useSSL=false",
					"root", "searchman");

			//ログインに必要なユーザー情報をusers_listから読み出し
			String sql_users_info = "select * from users_list where id =" + user_id + ";";
			System.out.println(sql_users_info);
			user_info_state =conn.prepareStatement(new String(sql_users_info));
			user_info_state.execute();
			user_info_result_set = user_info_state.executeQuery();

			//読みだしたデータからパスワードを取得して変数に代入する
			String true_password = null;
			while(user_info_result_set.next()) {
				true_password = user_info_result_set.getString(7);
			}

			//入力された現在のパスワードのハッシュ化
			String sha256_now_password = DigestUtils.sha256Hex(now_password);

			//現在のパスワードの比較
			if(sha256_now_password.equals(true_password) && new_password.equals(new_password_2)) {
				/*パスワードルール
				(1)半角小文字英語,半角大文字英語,半角数字の3つの要素の内2つ以上含まれていること。
				(2)8文字以上であること*/

				//パスワードの文字数の確認
				int Password_Count = new_password.length();

				//パスワードの書式チェック内容を記載
				Pattern Pass_conditions_0 = Pattern.compile("[A-Z]");
				Pattern Pass_conditions_1 = Pattern.compile("[a-z]");
				Pattern Pass_conditions_2 = Pattern.compile("[0-9]");
				Matcher Pass_match_0 = Pass_conditions_0.matcher(new_password);
				Matcher Pass_match_1 = Pass_conditions_1.matcher(new_password);
				Matcher Pass_match_2 = Pass_conditions_2.matcher(new_password);

				//パスワードの書式をチェック
				boolean Pass_Check_0 = Pass_match_0.find();
				boolean Pass_Check_1 = Pass_match_1.find();
				boolean Pass_Check_2 = Pass_match_2.find();
				//パスワード書式のチェック結果を配列に代入する
				boolean Pass_Check_result[] = {Pass_Check_0, Pass_Check_1, Pass_Check_2};

				//パスワードの書式条件のクリア数のチェック
				int True_Count=0;
				for( int i = 0 ; i < Pass_Check_result.length; i++) {
					if(Pass_Check_result[i]==true) {
						True_Count++;
					}
				}

				//パスワードの設定条件クリア時のパスワード変更処理を開始する
				if(Password_Count >= 8 && True_Count==3) {
					//パスワードのハッシュ化を行う
					String sha256_new_password = DigestUtils.sha256Hex(new_password);
					String sql_new_password = "UPDATE users_list SET password = "+ "\"" + sha256_new_password + "\"" + " WHERE id =" + user_id + ";";
					System.out.println(sql_new_password);

					new_password_state =conn.prepareStatement(new String(sql_new_password));
					new_password_state.execute();
					new_password_state.close();

					//パスワードの変更したためusers_listの最終更新時間を更新
					String sql_change_time = "UPDATE users_list SET last_update = now()" +  " WHERE id =" + user_id + ";";
					System.out.println(sql_change_time);
					password_change_state =conn.prepareStatement(new String(sql_change_time));
					password_change_state.execute();
					password_change_state.close();

					//user_id,thread_idを遷移ページへ、引渡し（Attributeで追加する）
					request.setAttribute("user_id_set", user_id);
					request.setAttribute("thread_id_set", thread_id);

					request.getRequestDispatcher("/ChangeSuccess.jsp").forward(request, response);

				}


				else {
					try {
						//user_id,thread_idを遷移ページへ、引渡し（Attributeで追加する）
						request.setAttribute("user_id_set", user_id);
						request.setAttribute("thread_id_set", thread_id);

						//使用したオブジェクトを終了させる
						user_info_state.close();
						user_info_result_set.close();
						// 念のため、finallyでDBとの接続を切断しておく
						conn.close();
						response.sendRedirect("http://localhost:8090/SkillShare/ChangeFailure.jsp");
					} catch (Exception e) {
					}
				}
			}

		}catch (Exception e) {
			response.sendRedirect("http://localhost:8090/SkillShare/Eroor.jsp");

		} finally {
			try {
				// 念のため、finallyでDBとの接続を切断しておく
				conn.close();
			} catch (Exception e) {
			}
		}

	}

}