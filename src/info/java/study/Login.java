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

public class Login extends HttpServlet {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// DB関連の初期設定
		Connection conn = null;
		PreparedStatement user_info_state = null;
		PreparedStatement comment_read_state = null;
		PreparedStatement thread_read_state = null;
		PreparedStatement thread_info_state = null;
		ResultSet user_info_result_set = null;
		ResultSet comment_result_set = null;
		ResultSet thread_result_set = null;
		ResultSet thread_info_result_set = null;

		// 文字コードの設定
		request.setCharacterEncoding("Windows-31J");

		// thread_id(読み出し対象のスレッド情報)を取得
		String thread_id = request.getParameter("thread_id");
		// index.jspで入力したユーザーIDとパスワードを取得
		String user_id = request.getParameter("user_id");
		String enter_password = request.getParameter("password");

		try {
			// JDBC Driver の登録
			Class.forName("com.mysql.jdbc.Driver");
			// Connectionの作成
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/java_study?serverTimezone=UTC&useSSL=false",
					"root", "searchman");

			//ログインに必要なユーザー情報をusers_listから読み出し
			String sql_users_info = "select * from users_list where id =" + user_id;
			System.out.println(sql_users_info);
			user_info_state =conn.prepareStatement(new String(sql_users_info));
			user_info_state.execute();
			user_info_result_set = user_info_state.executeQuery();

			//読みだしたデータからパスワードを取得して変数に代入する
			String true_password = null;
			while(user_info_result_set.next()) {
				true_password = user_info_result_set.getString(7);
			}
			System.out.println(true_password);

		//入力されたパスワード情報が一致した場合の処理
		if(true_password != null && enter_password.equals(true_password)) {

			//user_idを遷移ページへ、引渡し（Attributeで追加する）
			request.setAttribute("user_id_set", user_id);

			// スレッドコメント読み出し

				// sql文作成の準備 (コメントの読み出し)
				String sql_comment_read = "select * from comment_list where thread_id =" + thread_id + ";";

				// sql文を表示 (コメントの読み出し)
				System.out.println(sql_comment_read);

				// sql文実行準備 (コメントの読み出し)
				comment_read_state = conn.prepareStatement(new String(sql_comment_read));

				// sql文実行 (コメントの読み出し)
				comment_read_state.execute();

				// 実行結果を、Resulthread_result_setクラスに代入 (コメントの読み出し)
				comment_result_set = comment_read_state.executeQuery();

				// 遷移ページへ、引渡し（Attributeで追加する） (コメントの読み出し)
				request.setAttribute("comment_kekka", comment_result_set);

				// スレッド読み出し--------------------------------------------------------------------

				// sql文作成の準備 (スレッドの読み出し)
				String sql_thread_read = "select * from thread_list;";

				// (スレッドの読み出し)
				System.out.println(sql_thread_read);

				// sql文実行準備 (スレッドの読み出し)
				thread_read_state = conn.prepareStatement(new String(sql_thread_read));

				// sql文実行 (スレッドの読み出し)
				thread_read_state.execute();

				// 実行結果を、Resulthread_result_setクラスに代入 (スレッドの読み出し)
				thread_result_set = thread_read_state.executeQuery();

				// 遷移ページへ、引渡し（Attributeで追加する） (スレッドの読み出し)
				request.setAttribute("thread_kekka", thread_result_set);

				//対応スレッド情報の表示-----------------------------------------------------------------------------------
				//sql文作成の準備
				String sql_thread_info = "select * from thread_list where thread_id =" + thread_id + ";";

				// sql文を表示
				System.out.println(sql_thread_info);

				// sql文実行準備
				thread_info_state = conn.prepareStatement(new String(sql_thread_info));

				// sql文実行
				thread_info_state.execute();

				// 実行結果を、ResultSetクラスに代入
				thread_info_result_set = thread_info_state.executeQuery();

				// 遷移ページへ、引渡し（Attributeで追加する）
				request.setAttribute("thread_info_kekka", thread_info_result_set);
				//-----------------------------------------------------------------------------------
				// thread2.jspへ遷移
				request.getRequestDispatcher("/thread2.jsp").forward(request, response);

				// 使用したオブジェクトを終了させる
				comment_result_set.close();
				thread_result_set.close();
				thread_info_result_set.close();
				comment_read_state.close();
				thread_read_state.close();
				thread_info_state.close();
				conn.close();
			}
			//入力されたパスワード情報が一致しなかった合の処理
			else {
				try {
					//使用したオブジェクトを終了させる
					user_info_state.close();
					user_info_result_set.close();
					// 念のため、finallyでDBとの接続を切断しておく
					conn.close();
					response.sendRedirect("http://localhost:8090/SkillShare/Login_Failure.jsp");
				} catch (Exception e) {
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
