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

import org.apache.commons.codec.digest.DigestUtils;

public class Login extends HttpServlet {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// DB関連の初期設定
		Connection conn = null;
		ResultSet user_info_result_set = null;
		ResultSet comment_result_set = null;
		ResultSet thread_result_set = null;

		// 文字コードの設定
		request.setCharacterEncoding("Windows-31J");

		// thread_id(読み出し対象のスレッド情報)を取得
		String thread_id = request.getParameter("thread_id");
		request.setAttribute("thread_id_set", thread_id);
		// index.jspで入力したユーザーIDとパスワードを取得
		String MailAdress = request.getParameter("mail_adress");
		String enter_password = request.getParameter("password");

		try {
			// JDBC Driver の登録
			Class.forName("com.mysql.jdbc.Driver");
			// Connectionの作成
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/java_study?serverTimezone=UTC&useSSL=false",
					"root", "searchman");

			user_info_result_set = readUsersDb(conn, MailAdress);

			user_info_result_set.next();

			//ユーザーIDの取得
			String user_id = user_info_result_set.getString(1);
			request.setAttribute("user_id_set", user_id);

			//読みだしたデータからパスワードを取得して変数に代入する
			String true_password = user_info_result_set.getString(7);;

			//入力された現在のパスワードのハッシュ化
			String sha256_enter_password = DigestUtils.sha256Hex(enter_password);

		//入力されたパスワード情報が一致した場合の処理
		if(sha256_enter_password.equals(true_password)) {

				//comment_listからコメントの読み出し
				comment_result_set = readCommentFromDb(conn, thread_id);
				request.setAttribute("comment_kekka", comment_result_set);

				//thread_listからコメントの読み出し
				thread_result_set = readThreadFromDb(conn , thread_id);
				request.setAttribute("thread_kekka", thread_result_set);

				//対応スレッド情報の表示
				String currentThreadId = request.getParameter("thread_id");
				HashMap<String,String> threadTitleMap = new HashMap<String,String>();

				String threadId;
				String threadTitle;

				while(thread_result_set.next()){
				  threadId = thread_result_set.getString(1);
				  threadTitle = thread_result_set.getString(2);

				  threadTitleMap.put(threadId, threadTitle);
				}

				//JSPで使用するためカーソルをリセットする
				thread_result_set.beforeFirst();

				String thread_title_result = threadTitleMap.get(currentThreadId);
				request.setAttribute("thread_title", thread_title_result);

				// thread2.jspへ遷移
				request.getRequestDispatcher("/thread2.jsp").forward(request, response);

				// 使用したオブジェクトを終了させる
				//user_info_state.close();
				user_info_result_set.close();
				comment_result_set.close();
				thread_result_set.close();
				//thread_info_result_set.close();
				//comment_read_state.close();
				//thread_read_state.close();
				conn.close();
			}
			//入力されたパスワード情報が一致しなかった合の処理
			else {
				try {
					//使用したオブジェクトを終了させる
					//user_info_state.close();
					user_info_result_set.close();
					// 念のため、finallyでDBとの接続を切断しておく
					conn.close();
					response.sendRedirect("http://localhost:8090/SkillShare/Login_Failure.jsp");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			}catch (Exception e) {
	            e.printStackTrace();
				response.sendRedirect("http://localhost:8090/SkillShare/Eroor.jsp");

			} finally {
				try {
					// 念のため、finallyでDBとの接続を切断しておく
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
		}

	}
	//テーブルusers_listから読み出し
	private ResultSet readUsersDb(Connection conn, String MailAdress) throws SQLException {
		//ログインに必要なユーザー情報をusers_listから読み出し
		String sql_users_info = "select * from users_list where mail_adress =" + "\""+ MailAdress + "\"" + ";";
		System.out.println(sql_users_info);
		PreparedStatement user_info_state =conn.prepareStatement(new String(sql_users_info));
		return user_info_state.executeQuery();
	}

	//テーブルcomment_listから読み出し
	private ResultSet readCommentFromDb(Connection conn, String thread_id) throws SQLException {

		String sql_comment_read = "select * from comment_list where thread_id =" + thread_id + ";";
		System.out.println(sql_comment_read);
		PreparedStatement comment_read_state = conn.prepareStatement(new String(sql_comment_read));
		return comment_read_state.executeQuery();
	}
	//テーブルcomment_listから読み出し
	private ResultSet readThreadFromDb(Connection conn, String thread_id) throws SQLException {

		String sql_thread_read = "select * from thread_list;";
		System.out.println(sql_thread_read);
		PreparedStatement thread_read_state = conn.prepareStatement(new String(sql_thread_read));
		return thread_read_state.executeQuery();

	}
}
